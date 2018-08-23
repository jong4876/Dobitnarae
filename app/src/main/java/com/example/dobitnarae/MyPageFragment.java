package com.example.dobitnarae;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class MyPageFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private String password, name, phone;
    private EditText passwordET, nameET, phoneET;

    public MyPageFragment() {
    }

    public static MyPageFragment newInstance(int sectionNumber) {
        MyPageFragment fragment = new MyPageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_page, container, false);

        passwordET = (EditText)rootView.findViewById(R.id.myPage_password);
        nameET = (EditText)rootView.findViewById(R.id.myPage_name);
        phoneET = (EditText)rootView.findViewById(R.id.myPage_phone);

        // 유저 정보 가져옴
        getUserInfo();

        nameET.setText(name);
        phoneET.setText(phone);

        CardView editBtn = (CardView)rootView.findViewById(R.id.myPage_edit_btn);
        editBtn.setOnClickListener(new CardView.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(getContext());
            }
        });

        return rootView;
    }

    private void getUserInfo(){
        // TODO 서버에서 유저 정보 가져옴
        password = "*****";
        name = "홍길동";
        phone = "01012345678";
    }

    private void showAlert(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("정보를 수정 하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tmp = passwordET.getText().toString();
                        if(tmp.length() > 0)
                            password = tmp;
                        name = nameET.getText().toString();
                        phone = phoneET.getText().toString();
                        // TODO 서버로 전송

                        Toast.makeText(context, "수정 완료", Toast.LENGTH_SHORT).show();
                        refresh();
                    }
                });
        builder.setNegativeButton("아니요",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    private void refresh(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
        passwordET.setText("");
    }
}
