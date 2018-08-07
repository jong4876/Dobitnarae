package com.example.dobitnarae;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class OrderManagementFragment extends Fragment {
    private ArrayList<String> showedData;
    private ArrayList<String> nonAllowedIDs;
    private ArrayList<String> allowedIDs;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private Button button;
    private Button button4;

    public OrderManagementFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_management_order, container, false);

        nonAllowedIDs = new ArrayList<String>();
        nonAllowedIDs.add("1111");
        nonAllowedIDs.add("1112");
        nonAllowedIDs.add("1113");

        // 초기 데이터
        showedData = new ArrayList<String>();
        showedData.clear();
        for (String item:nonAllowedIDs) {
            showedData.add(item.toString());
        }

        allowedIDs = new ArrayList<String>();

        // 어댑터 생성
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, showedData);

        // 어댑터 설정
        listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        button = (Button) rootView.findViewById(R.id.btnAllow);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cnt=0;
                SparseBooleanArray sbArray = listView.getCheckedItemPositions();
                // 선택된 아이템의 위치를 알려주는 배열 ex) {0=true, 3=true, 4=false}
                Log.d("OrderManagementFragment", sbArray.toString());

                if(sbArray.size() !=0) {
                    // 목록의 역순으로 순회하면서 항목 제거
                    for(int i = listView.getCount() -1; i>=0; i--) {
                        if(sbArray.get(i)) {
                            allowedIDs.add(nonAllowedIDs.get(i).toString());
                            nonAllowedIDs.remove(i);
                            showedData.remove(i);
                            cnt++;
                        }
                    }
                    listView.clearChoices();
                    adapter.notifyDataSetChanged();
                    Snackbar.make(v, cnt + "개의 주문이 승인되었습니다", Snackbar.LENGTH_LONG).show();
                    cnt=0;
                }
            }
        });

        button4 = (Button) rootView.findViewById(R.id.btnComplete);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cnt=0;
                SparseBooleanArray sbArray = listView.getCheckedItemPositions();
                // 선택된 아이템의 위치를 알려주는 배열 ex) {0=true, 3=true, 4=false}
                Log.d("OrderManagementFragment", sbArray.toString());

                if(sbArray.size() !=0) {
                    // 목록의 역순으로 순회하면서 항목 제거
                    for(int i = listView.getCount() -1; i>=0; i--) {
                        if(sbArray.get(i)) {
                            allowedIDs.remove(i);
                            showedData.remove(i);
                            cnt++;
                        }
                    }
                    listView.clearChoices();
                    adapter.notifyDataSetChanged();
                    Snackbar.make(v, cnt + "개의 완료된 주문이 삭제되었습니다", Snackbar.LENGTH_LONG).show();
                    cnt=0;
                }
            }
        });

        Button button2 = (Button) rootView.findViewById(R.id.nonAllowedList);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setVisibility(View.VISIBLE);
                button4.setVisibility(View.GONE);
                showedData.clear();
                for (String item:nonAllowedIDs) {
                    showedData.add(item.toString());
                }
                adapter.notifyDataSetChanged();

                if(allowedIDs.size()==0)
                    Snackbar.make(v, "미승인된 주문이 없습니다.", Snackbar.LENGTH_LONG).show();
            }
        });

        Button button3 = (Button) rootView.findViewById(R.id.allowedList);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setVisibility(View.GONE);
                button4.setVisibility(View.VISIBLE);
                showedData.clear();
                for (String item:allowedIDs) {
                    showedData.add(item.toString());
                }
                adapter.notifyDataSetChanged();

                if(allowedIDs.size()==0)
                    Snackbar.make(v, "승인된 주문이 없습니다.", Snackbar.LENGTH_LONG).show();
            }
        });

        return rootView;
    }
}
