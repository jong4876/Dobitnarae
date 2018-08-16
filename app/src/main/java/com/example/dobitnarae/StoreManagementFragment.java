package com.example.dobitnarae;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class StoreManagementFragment extends Fragment {
    private Store store;
    private ImageButton btn_edit;
    private EditText edit_name;
    private EditText edit_admin_id;
    private EditText edit_tel;
    private EditText edit_intro;
    private EditText edit_info;
    private EditText edit_address;
    private EditText edit_sector;

    private ImageView imageView_store;

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
        edit_admin_id = (EditText)rootView.findViewById(R.id.edit_admin_id);
        edit_tel = (EditText)rootView.findViewById(R.id.edit_tel);
        edit_intro = (EditText)rootView.findViewById(R.id.edit_intro);
        edit_info = (EditText)rootView.findViewById(R.id.edit_info);
        edit_address = (EditText)rootView.findViewById(R.id.edit_address);
        edit_sector = (EditText)rootView.findViewById(R.id.edit_sector);

        setEditText(store);

        editTextArrayList.add(edit_name);
        editTextArrayList.add(edit_admin_id);
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
        btn_edit.setVisibility(View.VISIBLE);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(getContext(), store);
            }
        });

        return rootView;
    }

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
                        // 여기에 update 메소드
                        setEditText(store);
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
                        Toast.makeText(getContext(), "취소되었습니다.", Toast.LENGTH_SHORT).show();
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
}