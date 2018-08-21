package com.example.dobitnarae;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import java.util.Date;

public class ReserveSuccess extends Dialog {

    private Context context;
    private LinearLayout linearLayout;

    public ReserveSuccess(Context context) {
        super(context);

        this.context = context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
        setContentView(R.layout.component_reserve_success);     //다이얼로그에서 사용할 레이아웃입니다.
    }
}
