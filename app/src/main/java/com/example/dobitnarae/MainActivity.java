package com.example.dobitnarae;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button sqlBtn;
    Button clothBtn;
    Button storeBtn;
    EditText storeEtxt;
    public static final int RequestKey = 1001;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqlBtn = (Button)findViewById(R.id.sqlBtn);
        clothBtn = (Button)findViewById(R.id.clothBtn);
        storeBtn = (Button)findViewById(R.id.storeBtn);
        storeEtxt = (EditText)findViewById(R.id.storeEtxt);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        ImageButton backButton = (ImageButton)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ExampleSwipeMenu.class);
                startActivity(intent);
            }
        });
        sqlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),SQLActivity.class);

                startActivityForResult(intent,RequestKey);
                //Toast.makeText(getApplicationContext(),"??",Toast.LENGTH_LONG).show();

            }
        });

        clothBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// 아이디에 해당하는 상점의 옷 가져오기
                String str = storeEtxt.getText().toString();// 입력받은 아이디
                intent = new Intent(getApplicationContext(),clothActivity.class);
                intent.putExtra("ID",str);

                startActivityForResult(intent,RequestKey);

            }
        });
        storeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// 아이디에 해당하는 상점의 옷 가져오기
                String str = storeEtxt.getText().toString();// 입력받은 아이디
                intent = new Intent(getApplicationContext(),storeActivity.class);
                intent.putExtra("ID",str);

                startActivityForResult(intent,RequestKey);

            }
        });

    }

}
