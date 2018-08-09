package com.example.dobitnarae;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        ImageButton backButton = (ImageButton)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        Button button = (Button)findViewById(R.id.gotoStore);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
    }

    // 액티비티는 새로만들어지지 않고 유지
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(this, "가로방향", Toast.LENGTH_LONG).show();
        } else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //Toast.makeText(this, "세로방향", Toast.LENGTH_LONG).show();
        }
    }
}
