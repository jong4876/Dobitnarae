package com.example.dobitnarae;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

@SuppressLint("ValidFragment")
public class StoreManagementFragment extends Fragment {
    private Store store;
    private Button btn_edit;
    private Button btn_register;
    private EditText edit_name;
    private EditText edit_intro;
    private EditText edit_info;
    private EditText edit_sales_info;
    private EditText edit_owner_info;
    private EditText edit_address;

    private InputMethodManager imm; //전역변수

    public StoreManagementFragment(Store store) {
        this.store = store;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_store_info, container, false);


        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE); //onCreate 이후,,

        edit_name = (EditText)rootView.findViewById(R.id.edit_name);
        edit_name.setText(store.getName());
        edit_intro = (EditText)rootView.findViewById(R.id.edit_intro);
        edit_intro.setText(store.getIntroduction());
        edit_info = (EditText)rootView.findViewById(R.id.edit_info);
        edit_info.setText(store.getInformation());
        edit_sales_info = (EditText)rootView.findViewById(R.id.edit_sales_info);
        edit_sales_info.setText(store.getPhoneNumber());
        edit_owner_info = (EditText)rootView.findViewById(R.id.edit_owner_info);
        edit_owner_info.setText(store.getOwnerName());
        edit_address = (EditText)rootView.findViewById(R.id.edit_address);
        edit_address.setText(store.getAddress());

        btn_edit = (Button) rootView.findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_register.setVisibility(View.VISIBLE);
                btn_edit.setVisibility(View.GONE);

                edit_name.setFocusableInTouchMode(true);
                edit_intro.setFocusableInTouchMode(true);
                edit_info.setFocusableInTouchMode(true);
                edit_sales_info.setFocusableInTouchMode(true);
                edit_owner_info.setFocusableInTouchMode(true);
                edit_address.setFocusableInTouchMode(true);

                edit_name.setClickable(true);
                edit_intro.setClickable(true);
                edit_info.setClickable(true);
                edit_sales_info.setClickable(true);
                edit_owner_info.setClickable(true);
                edit_address.setClickable(true);

                edit_name.setFocusable(true);
                edit_intro.setFocusable(true);
                edit_info.setFocusable(true);
                edit_sales_info.setFocusable(true);
                edit_owner_info.setFocusable(true);
                edit_address.setFocusable(true);

                showKeyboard(edit_name);
                showKeyboard(edit_intro);
                showKeyboard(edit_info);
                showKeyboard(edit_sales_info);
                showKeyboard(edit_owner_info);
                showKeyboard(edit_address);
            }
        });

        btn_register = (Button) rootView.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_edit.setVisibility(View.VISIBLE);
                btn_register.setVisibility(View.GONE);

                edit_name.setFocusableInTouchMode(false);
                edit_intro.setFocusableInTouchMode(false);
                edit_info.setFocusableInTouchMode(false);
                edit_sales_info.setFocusableInTouchMode(false);
                edit_owner_info.setFocusableInTouchMode(false);
                edit_address.setFocusableInTouchMode(false);

                edit_name.setClickable(false);
                edit_intro.setClickable(false);
                edit_info.setClickable(false);
                edit_sales_info.setClickable(false);
                edit_owner_info.setClickable(false);
                edit_address.setClickable(false);

                edit_name.setFocusable(false);
                edit_intro.setFocusable(false);
                edit_info.setFocusable(false);
                edit_sales_info.setFocusable(false);
                edit_owner_info.setFocusable(false);
                edit_address.setFocusable(false);

                hideKeyboard(edit_name);
                hideKeyboard(edit_intro);
                hideKeyboard(edit_info);
                hideKeyboard(edit_sales_info);
                hideKeyboard(edit_owner_info);
                hideKeyboard(edit_address);
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
}
