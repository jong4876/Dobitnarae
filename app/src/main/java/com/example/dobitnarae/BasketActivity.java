package com.example.dobitnarae;

import android.content.Context;
import android.content.pm.ActivityInfo;
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
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class BasketActivity extends AppCompatActivity {
    BasketRecyclerAdapter bAdapter;
    DecimalFormat decimalFormat;
    TextView priceTextView, totalClothesCnt;

    DatePickerDialog dpd;
    TimePickerDialog tpd;
    Context context;

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

        this.context = this;
        // 예약 목록 레이아웃 설정
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_basket_list);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 290, this.getResources().getDisplayMetrics());
        recyclerView.setMinimumHeight(displayMetrics.heightPixels - px);
        recyclerView.setLayoutManager(layoutManager);

        //전체 옷 개수 설정
        totalClothesCnt = findViewById(R.id.reserve_clothes_total_cnt);
        setTotalClothesCnt();

        // 전체 가격 설정
        bAdapter = new BasketRecyclerAdapter(this);
        recyclerView.setAdapter(bAdapter);

        priceTextView = findViewById(R.id.basket_reserve_clothes_total_price);
        decimalFormat = new DecimalFormat("###,###,###,###");

        setTotalCost();

        // 날짜 및 시간 설정
        setDateCalendar();
        setTimePicker();

        // 대여하기 버튼 설정
        LinearLayout reserveBtn = (LinearLayout) findViewById(R.id.basket_reservation);
        reserveBtn.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Basket.getInstance().getClothesCnt() == 0)
                    Toast.makeText(v.getContext(), "담은 한복이 없습니다", Toast.LENGTH_SHORT).show();
                else {
                    dpd.show(getFragmentManager(), "Datepickerdialog");
                }
            }
        });
    }

    public void setTotalClothesCnt()
    {
        totalClothesCnt.setText("" + Basket.getInstance().getTotalClothesCnt());
    }

    public void setTotalCost()
    {
        int price = Basket.getInstance().getTotalPrice();
        String str = decimalFormat.format(price) + " 원";
        priceTextView.setText(str);
    }

    private void setDateCalendar()
    {
        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                null,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setTitle("예약 날짜");
        dpd.setOkText("다음");
        dpd.setCancelText("취소");
        dpd.setMinDate(now);
        dpd.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                tpd.show(getFragmentManager(), "TimePickerdialog");
            }
        });
    }

    private void setTimePicker()
    {
        Calendar now = Calendar.getInstance();
        tpd = TimePickerDialog.newInstance(null,
                Calendar.HOUR_OF_DAY,
                Calendar.MINUTE,
                false
        );
        tpd.setVersion(TimePickerDialog.Version.VERSION_2);
        tpd.setTitle("예약 시간");
        tpd.setOkText("예약 완료");
        tpd.setCancelText("취소");
        tpd.setTimeInterval(1, 5);
        tpd.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                // TODO
                // 서버로 선택한 옷, 사용자 정보, 예약 날짜및 시간 전송

                // 임시 성공 모달창
                DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
                int width = dm.widthPixels; //디바이스 화면 너비
                int height = dm.heightPixels; //디바이스 화면 높이
                ReserveSuccess rs = new ReserveSuccess(context);
                WindowManager.LayoutParams wm = rs.getWindow().getAttributes();  //다이얼로그의 높이 너비 설정하기위해
                wm.copyFrom(rs.getWindow().getAttributes());  //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미
                wm.width = width / 2;  //화면 너비의 절반
                wm.height = height / 4;  //화면 높이의 절반
                rs.show();
            }
        });
    }

}
