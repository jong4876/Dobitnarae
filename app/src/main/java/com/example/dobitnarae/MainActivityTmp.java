package com.example.dobitnarae;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Objects;

public class MainActivityTmp extends AppCompatActivity {

    public static Basket basket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tmp);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        ImageButton backButton = (ImageButton)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

        View.OnClickListener gotoStoreList = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityTmp.this, StoreListActivity.class);
                int sector = Integer.parseInt((String) v.getTag());
                intent.putExtra("sector", sector);
                startActivity(intent);
            }
        };

        LinearLayout sector1 = (LinearLayout)findViewById(R.id.store_sector_1);
        sector1.setOnClickListener(gotoStoreList);

        LinearLayout sector2 = (LinearLayout)findViewById(R.id.store_sector_2);
        sector2.setOnClickListener(gotoStoreList);

        basket = new Basket();
    }

}
