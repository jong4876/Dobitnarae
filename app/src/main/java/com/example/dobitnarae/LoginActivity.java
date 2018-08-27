package com.example.dobitnarae;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText IDTxt;
    EditText PasswordTxt;
    Button LoginBtn;
    ArrayList<Account> accountList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        IDTxt = (EditText) findViewById(R.id.IDTxt);
        PasswordTxt = (EditText) findViewById(R.id.PASSWORD);
        LoginBtn = (Button)findViewById(R.id.Login);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                accountList = JSONTask.getInstance().getAccountAll(IDTxt.getText().toString());

                if(accountList.size() !=0 ){
                    String DBPassword = accountList.get(0).getPw();
                    String Password = PasswordTxt.getText().toString();

                    if(DBPassword.equals(Password)) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("ID", IDTxt.getText().toString());
                        startActivityForResult(intent, 1000);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"비밀번호를 다시 입력하세요"+Password,Toast.LENGTH_LONG).show();
                    }
                }else{
                   Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_LONG).show();
                }
            }
        });




    }
}
