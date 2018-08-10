package com.example.dobitnarae;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DBstoreActivity extends AppCompatActivity {// db실험용

    TextView txtView;
    Store store;
    ArrayList<Store> storeList = new ArrayList<Store>();
    ArrayList<Clothes> clothesList = new ArrayList<Clothes>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbstore);
        String results;
        Intent intent = getIntent();
        //user_id = intent.getExtras().getString("ID");
        //Toast.makeText(getApplicationContext(), user_id + "님 안녕하세요!", Toast.LENGTH_LONG).show();
        txtView = (TextView) findViewById(R.id.txtView);

        try {

           clothesList = JSONTask.getClothesAll();


                StringBuffer sb = new StringBuffer();

                for(int i=0; i<clothesList.size(); i++){

                    sb.append(// test용 stringbuffer
                            "한복id: " + clothesList.get(i).getCloth_id()+
                                    "\n\n매장명: " + clothesList.get(i).getStore_id()  +
                                    "\n\n매장아이디: " + clothesList.get(i).getCategory()  +
                                    "\n\n매장번호: " + clothesList.get(i).getName()  +
                                    "\n\n매장소개: " + clothesList.get(i).getIntro()  +
                                    "\n\n매장정보: " + clothesList.get(i).getPrice()  +
                                    "\n\n매장주소: " + clothesList.get(i).getCount()  +
                                    "\n\n매장구역: " + clothesList.get(i).getSex()  +
                                    "\n\n\n"
                    );
                }
                txtView.setText(sb);
        }catch(Exception E){
            E.printStackTrace();
        }



    }
}







