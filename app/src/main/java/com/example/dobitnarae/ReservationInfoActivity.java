package com.example.dobitnarae;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class ReservationInfoActivity extends AppCompatActivity {

    private Reserve reserve;
    private ArrayList<BasketItem> clothes;
    private TextView totalClothesCnt, reservationCost;
    private ReservationInfoRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_info);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        ImageButton backButton = (ImageButton)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

        Intent intent = getIntent();
        reserve = (Reserve) intent.getSerializableExtra("reserveInfo");

        // TODO reservationID 값으로 서버에서 해당 예약된 한복 목록 가져오기
        clothes = new ArrayList<>();
        for(int i=0; i<7; i++){
            int store_id = JSONTask.getInstance().changeStoreID(reserve.getAdmin_id());
            Clothes tmp = new Clothes(i, store_id, i % Constant.category_cnt + 1,
                    "불곱창" + (i + 1), "이 곱창은 왕십리에서 시작하여...",
                    1000 * (i + 1), i,  0);
            BasketItem bTmp = new BasketItem(tmp, i + 1);
            clothes.add(bTmp);
        }

        // 예약 목록 레이아웃 설정
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.reservation_list);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 290, this.getResources().getDisplayMetrics());
        recyclerView.setMinimumHeight(displayMetrics.heightPixels - px);
        recyclerView.setLayoutManager(layoutManager);

        //전체 옷 개수 설정
        totalClothesCnt = findViewById(R.id.reservation_clothes_total_cnt);
        setTotalClothesCnt();

        // 전체 가격 설정
        mAdapter = new ReservationInfoRecyclerAdapter(this, clothes);
        recyclerView.setAdapter(mAdapter);

        reservationCost = findViewById(R.id.reservation_cost);
        setTotalCost();

        int acceptStatus = reserve.getAcceptStatus();

        if(acceptStatus == 2){
            LinearLayout cancelBtn = (LinearLayout) findViewById(R.id.reservation_btn_layout);
            cancelBtn.setBackgroundColor(Color.parseColor("#606060"));
            TextView cancelBtnText = (TextView) findViewById(R.id.reservation_btn_text);
            cancelBtnText.setText("대여 거절");
        }
        else {
            LinearLayout cancelBtn = (LinearLayout) findViewById(R.id.reservation_btn_layout);
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlert();
                }
            });
            TextView cancelBtnText = (TextView) findViewById(R.id.reservation_btn_text);
            cancelBtnText.setText("대여 취소");
        }
    }

    public void setTotalClothesCnt()
    {
        int cnt = 0;
        for(BasketItem item : clothes)
            cnt += item.getCnt();
        totalClothesCnt.setText("" + cnt);
    }

    public void setTotalCost()
    {
        int price = 0;
        for(BasketItem item : clothes)
            price += item.getCnt() * item.getClothes().getPrice();
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");
        String str = decimalFormat.format(price) + " 원";
        reservationCost.setText(str);
    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("대여를 취소하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO 예약 취소 서버에 요청
//                        ((MyReserveFragment)getSupportFragmentManager().
//                                findFragmentByTag(MyPageActivity.myReservationListFragmentTag))
//                                .testM();
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

}
