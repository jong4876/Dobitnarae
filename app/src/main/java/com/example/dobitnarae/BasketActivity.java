package com.example.dobitnarae;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class BasketActivity extends AppCompatActivity {
    BasketRecyclerAdapter bAdapter;
    ReserveDialog rd;
    DecimalFormat decimalFormat;

    TextView priceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        ImageButton backButton = (ImageButton)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

        // 예약 목록 레이아웃 설정
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_basket_list);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 280, this.getResources().getDisplayMetrics());
        recyclerView.setMinimumHeight(displayMetrics.heightPixels - px);
        recyclerView.setLayoutManager(layoutManager);

        // 전체 가격 설정
        bAdapter = new BasketRecyclerAdapter(this);
        recyclerView.setAdapter(bAdapter);

        priceTextView = findViewById(R.id.basket_reserve_clothes_total_price);
        decimalFormat = new DecimalFormat("###,###,###,###");

        setTotalCost();

        // 다이얼로그 설정
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
        int width = dm.widthPixels; //디바이스 화면 너비
        int height = dm.heightPixels; //디바이스 화면 높이

        rd = new ReserveDialog(this);
        WindowManager.LayoutParams wm = rd.getWindow().getAttributes();  //다이얼로그의 높이 너비 설정하기위해
        wm.copyFrom(rd.getWindow().getAttributes());  //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미
        wm.width = 2*width / 3;  //화면 너비의 절반
        wm.height = 2*height / 3;  //화면 높이의 절반

        LinearLayout reserveBtn = (LinearLayout) findViewById(R.id.basket_reservation);
        reserveBtn.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                rd.show();
            }
        });
    }

    public void setTotalCost()
    {
        int price = Basket.getInstance().getTotalPrice();
        String str = decimalFormat.format(price) + " 원";
        priceTextView.setText(str);
    }
}
