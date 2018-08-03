package com.example.dobitnarae;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ItemManagementFragment extends Fragment {
    private EditText editText;
    private ArrayList<String> items;
    private ArrayAdapter<String> listViewAdapter;
    private ListView listView;

    public ItemManagementFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_management_item, container, false);

        //String[] list_menu = {"상의", "하의", "신발"};
        items = new ArrayList<String>();
        items.add("상의");
        items.add("하의");
        items.add("신발");

        // 어댑터 설정
        listView = (ListView) rootView.findViewById(R.id.itemList);

        // 어댑터 생성 - 단일선택
        listViewAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_single_choice, items);

        // 어댑터 반영
        listView.setAdapter(listViewAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        editText = (EditText) rootView.findViewById(R.id.editText);

        Button button1 = (Button) rootView.findViewById(R.id.registerBtn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if(!text.isEmpty()){
                    items.add(text);
                    editText.setText("");
                    // 리스트 목록 갱신
                    listViewAdapter.notifyDataSetChanged();
                    Snackbar.make(v, text + "가 추가되었습니다", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        Button button2 = (Button) rootView.findViewById(R.id.deleteBtn);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = listView.getCheckedItemPosition();
                if(pos != ListView.INVALID_POSITION) {
                    items.remove(pos);
                    listView.clearChoices();
                    // 어댑터와 연결된 원본데이터의 값이 변경됨을 알려 리스트뷰 목록 갱신
                    listViewAdapter.notifyDataSetChanged();
                    Snackbar.make(v, "삭제되었습니다", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
    }
}
