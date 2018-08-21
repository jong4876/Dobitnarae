package com.example.dobitnarae;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ReserveDialog extends Dialog {

    private Context context;
    private LinearLayout linearLayout;

    public ReserveDialog(final Context context) {
        super(context);

        this.context = context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
        setContentView(R.layout.component_reserve_time);     //다이얼로그에서 사용할 레이아웃입니다.

        linearLayout = findViewById(R.id.reserve_picker);
        linearLayout.addView(getDatePicker());

        LinearLayout setDate = findViewById(R.id.basket_reservation_btn);
        setDate.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.removeAllViews();
                linearLayout.addView(getTimePicker());
            }
        });
    }

    public DatePicker getDatePicker(){
        DatePicker datePicker = new DatePicker(context);
        datePicker.setMinDate(new Date().getTime()); // 오늘 이후로 예약 가능
        return datePicker;
    }

    public TimePicker getTimePicker() {
        TimePicker timePicker = new TimePicker(context);
        return timePicker;
    }
}
