package com.example.dobitnarae;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressLint("ValidFragment")
public class StoreManagementFragment extends Fragment {
    private static final int MY_PERMISSION_CAMERA = 1111;
    private static final int REQUEST_TAKE_PHOTO = 2222;
    private static final int REQUEST_TAKE_ALBUM = 3333;
    private static final int REQUEST_IMAGE_CROP = 4444;

    private Uri imageURI;
    private Uri photoURI, albumURI;
    private ImageView imageView_store;
    private String mCurrentPhotoPath;

    private int iv_width;
    private int iv_height;

    private Store store;
    private ImageButton btn_edit;
    private EditText edit_name;
    private TextView edit_admin_id;
    private EditText edit_tel;
    private EditText edit_intro;
    private EditText edit_info;
    private EditText edit_address;
    private EditText edit_sector;

    private ArrayList<EditText> editTextArrayList;

    private InputMethodManager imm; //전역변수

    public StoreManagementFragment(Store store) {
        this.store = store;
    }

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static StoreManagementFragment newInstance(int sectionNumber, Store store) {
        StoreManagementFragment fragment = new StoreManagementFragment(store);
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putString("test", "storefragment");
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_management_store, container, false);

        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE); //onCreate 이후,,

        editTextArrayList = new ArrayList<EditText>();

        imageView_store = (ImageView) rootView.findViewById(R.id.imageView_store);
        edit_name = (EditText)rootView.findViewById(R.id.edit_name);
        edit_admin_id = (TextView)rootView.findViewById(R.id.edit_admin_id);
        edit_tel = (EditText)rootView.findViewById(R.id.edit_tel);
        edit_intro = (EditText)rootView.findViewById(R.id.edit_intro);
        edit_info = (EditText)rootView.findViewById(R.id.edit_info);
        edit_address = (EditText)rootView.findViewById(R.id.edit_address);
        edit_sector = (EditText)rootView.findViewById(R.id.edit_sector);

        setEditText(store);

        editTextArrayList.add(edit_name);
        editTextArrayList.add(edit_tel);
        editTextArrayList.add(edit_intro);
        editTextArrayList.add(edit_info);
        editTextArrayList.add(edit_address);
        editTextArrayList.add(edit_sector);

        for (final EditText item:editTextArrayList) {
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.setFocusableInTouchMode(true);
                    item.setFocusable(true);
                    showKeyboard(item);
                }
            });
        }

        // 부모액티비티 툴바 요소인 이미지 버튼에 접근
        btn_edit = ((AdminActivity)getActivity()).getImageButton();
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(getActivity(), store);
            }
        });


        iv_width = imageView_store.getMaxWidth();
        iv_height = imageView_store.getMaxHeight();

        imageView_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        captureCamera();
                    }
                };
                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getAlbum();
                    }
                };
                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };

                new AlertDialog.Builder(getActivity())
                        .setTitle("업로드할 이미지 선택")
                        .setPositiveButton("사진촬영", cameraListener)
                        .setNeutralButton("앨범선택", albumListener)
                        .setNegativeButton("취소", cancelListener)
                        .show();
            }
        });

        checkPermission();
        return rootView;
    }

    private void captureCamera(){
        String state = Environment.getExternalStorageState();
        // 외장메모리 검사
        if(Environment.MEDIA_MOUNTED.equals(state)){
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if(takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex ){
                    Log.e("captureCamera Error", ex.toString());
                }
                if(photoFile != null) {
                    // getUriForFile의 두 번째 인자는 Manifest provider authorites와 일치해야함

                    Uri providerURI = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName(), photoFile);
                    imageURI = providerURI;

                    // 인텐트에 전달할 때는 FileProvider의 Return값인 content로만, providerURI의 값에 카메라 데이터를 넣어 보냄
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);

                    getActivity().startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        }else {
            Toast.makeText(getActivity(), "저장공간이 접근 불가능한 기기입니다.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public File createImageFile() throws  IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File imageFile = null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "img");

        if(!storageDir.exists()){
            Log.i("mCurrentPhotoPath1", storageDir.toString());
            storageDir.mkdirs();
        }
        imageFile = new File(storageDir, imageFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();

        return imageFile;
    }

    private void getAlbum() {
        Log.i("getAlbum", "Call");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        getActivity().startActivityForResult(intent, REQUEST_TAKE_ALBUM);
    }

    private void galleryAddPic(){
        Log.i("galleryAddPic", "Call");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        // 해당 경로에 있는 파일을 객체화
        File f = new File(mCurrentPhotoPath);
        Uri contentURI = Uri.fromFile(f);
        mediaScanIntent.setData(contentURI);
        getActivity().sendBroadcast(mediaScanIntent);
        Toast.makeText(getActivity(), "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

    // 카메라 전용 크랍
    public void cropImage(){
        Log.i("cropImage", "Call");
        Log.i("cropImage", "PhotoURI: " + photoURI + " / albumURI: " + albumURI);

        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        // 50X50픽셀 미만은 편집할 수 없다는 문구 처리 + 갤러리, 포토 둘다 호환하는 방법
        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cropIntent.setDataAndType(photoURI, "image/*");
        cropIntent.putExtra("outputX", iv_width); // CROP한 이미지의 x축 크기
        cropIntent.putExtra("outputY", 200); // CROP한 이미지의 y축 크기
        cropIntent.putExtra("aspectX", iv_width/200); // CROP 박스의 X축 비율
        cropIntent.putExtra("aspectY", 1); // CROP 박스의 Y축 비율
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("output", albumURI);
        getActivity().startActivityForResult(cropIntent, REQUEST_IMAGE_CROP); // CROP_FROM_CAMERA case문 이동
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case REQUEST_TAKE_PHOTO:
                if(resultCode == Activity.RESULT_OK){
                    try {
                        Log.i("REQUEST_TAKE_PHOTO", "OK");
                        // 갤러리에 추가만 시킴
                        galleryAddPic();
                        //imageView_store.setImageURI(imageURI);
                    } catch (Exception e){
                        Log.e("REQUEST_TAKE_PHOTO", e.toString());
                    }
                } else {
                    Toast.makeText(getActivity(), "사진찍기를 취소하였습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_TAKE_ALBUM:
                if(resultCode == Activity.RESULT_OK){
                    if(data.getData() != null){
                        try {
                            File albumFile = null;
                            albumFile = createImageFile();
                            photoURI = data.getData();
                            albumURI = Uri.fromFile(albumFile);
                            //cropImage();
                            cropSingleImage(photoURI);
                        } catch (Exception e) {
                            Log.e("TAKE_ALBUM_SINGLE ERROR", e.toString());
                        }
                    }
                }
                break;
            case REQUEST_IMAGE_CROP:
                if(resultCode == Activity.RESULT_OK) {
                    galleryAddPic();
                    imageView_store.setImageURI(photoURI);
                }
                break;
        }
    }

    private void checkPermission() {
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 처음 호출시엔 if()안의 부분은 false로 리턴 됨 -> else{...}의 요청으로 넘어감
            if((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) || (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA))){
                new AlertDialog.Builder(getActivity())
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
                                getActivity().startActivity(intent);
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSION_CAMERA);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case MY_PERMISSION_CAMERA:
                for(int i = 0; i< grantResults.length; i++){
                    // grantResult[]: 허용된 권한은 0, 거부한 권한은 -1
                    if(grantResults[i] < 0){
                        Toast.makeText(getActivity(), "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 허용했다면
                break;
        }
    }

    // 카메라 전용 크랍(앨범엔 크롭된 이미지만 저장시키기 위해)
    public void cropSingleImage(Uri photoURIPath) {
        Log.i("cropSingleImage", "Call");
        Log.i("cropSingleImage", "PhotoURIPath: " + photoURIPath);

        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        // 50X50픽셀 미만은 편집할 수 없다는 문구 처리 + 갤러리, 포토 둘다 호환하는 방법
        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cropIntent.setDataAndType(photoURI, "image/*");
        cropIntent.putExtra("outputX", iv_width); // CROP한 이미지의 x축 크기
        cropIntent.putExtra("outputY", iv_height); // CROP한 이미지의 y축 크기
        cropIntent.putExtra("aspectX", iv_width/iv_height); // CROP 박스의 X축 비율
        cropIntent.putExtra("aspectY", 1); // CROP 박스의 Y축 비율
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("output", photoURIPath);

        // 같은 photoURIPath에 저장하려면 아래가 있어야함
        List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(cropIntent, 0);
        getActivity().grantUriPermission(list.get(0).activityInfo.packageName, photoURIPath, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Intent i = new Intent(cropIntent);
        ResolveInfo res =  list.get(0);
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        getActivity().grantUriPermission(res.activityInfo.packageName, photoURIPath, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

        getActivity().startActivityForResult(i, REQUEST_IMAGE_CROP);
    }

    // 외부저장소(앨범)에 크롭된 이미지를 저장하는 함수
    // Dobitnarae라는 디렉토리가 있는지 if문을 체크
    // 이후에 createNewFile()을 통해 파일을 생성하고 BufferedOutputStream과 FileOutputStream 복사를 진행한다.

    // sendBroadCast() 함수는 폰의 앨범에 크롭된 사진을 갱신하는 함수이다.
    // 이 함수를 쓰지 않는다면 크롭된 사진을 저장해도 앨범에 안보이며, 직접 파일 관리자 앱을 통해 폴더를 들어가야만 사진을 볼 수 있다.

    private void showKeyboard(EditText editText) {
        imm.showSoftInput(editText, 0);
    }

    private void hideKeyboard(EditText editText) {
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void showAlert(Context context, final Store store){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("매장정보를 수정하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateStoreByText();
                        JSONTask.getInstance().updateStore(store, store.getAdmin_id());
                        setEditText(store);
                        Toast.makeText(getContext(), "변경되었습니다.", Toast.LENGTH_SHORT).show();
                        for (EditText item:editTextArrayList) {
                            item.setFocusableInTouchMode(false);
                            item.setFocusable(false);
                            hideKeyboard(item);
                        }
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 이전 데이터로 다시 셋
                        setEditText(store);
                        Toast.makeText(getActivity(), "취소되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }

    private void setEditText(Store store){
        edit_name.setText(store.getName());
        edit_admin_id.setText(store.getAdmin_id());
        edit_tel.setText(store.getTel());
        edit_intro.setText(store.getIntro());
        edit_info.setText(store.getInform());
        edit_address.setText(store.getAddress());
        edit_sector.setText(""+store.getSector());
    }

    private void updateStoreByText(){
        store.setName(edit_name.getText().toString());
        store.setTel(edit_tel.getText().toString());
        store.setIntro(edit_intro.getText().toString());
        store.setInform(edit_info.getText().toString());
        store.setAddress(edit_address.getText().toString());
        store.setSector(Integer.parseInt(edit_sector.getText().toString()));
    }
}