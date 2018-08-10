package com.example.dobitnarae;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StoreListActivity extends AppCompatActivity {
    ArrayList<Store> stores;
    ClothesListRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        ImageButton backButton = (ImageButton)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_store_list);
        LinearLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);

        recyclerView.setLayoutManager(layoutManager);

        stores = new ArrayList<>();

        //TODO 서버에서 상점 목록 받아 어뎁터에 장착
        Store item = new Store(0, "세종대학교", "Kuril", "서울특별시 광진구 군자동 능동로 209",
                "02-3408-3114", "세종대학교는 대한민국 서울특별시 광진구 군자동에 위치한 사립 종합대학이다." +
                " 세종대나 SJU의 약칭으로 불리기도 한다. 10개의 단과 대학, 1개의 교양 대학," +
                " 1개의 독립학부, 1개의 일반대학원, 1개의 전문대학원, 5개의 특수대학원과 57개의 연구소," +
                " 8개의 BK21사업팀으로 구성되어 있다. 학교법인 대양학원에 의해 운영된다. 현재 총장은 화학 박사 신구이다. ",
                "24시간 영업", 0,
                37.550278, 127.073114);
        stores.add(item);

        mAdapter = new ClothesListRecyclerAdapter(getApplicationContext(), stores);
        recyclerView.setAdapter(mAdapter);
    }
}
