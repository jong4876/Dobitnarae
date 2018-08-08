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

public class DBstoreActivity extends AppCompatActivity {

    TextView txtView;
    Store store;
    ArrayList<Store> storeList = new ArrayList<Store>();

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
           String str =  new JSONTask().execute("http://192.168.219.103:3443/store").get();
           StringBuffer sb = new StringBuffer();
                JSONArray ja = new JSONArray(str);
                // txtView.setText(str);
                for(int i=0; i<ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);
                    int id = jo.getInt("id");
                    String name = jo.getString("name");
                    String admin_id = jo.getString("admin_id");
                    String tel = jo.getString("tel");
                    String intro = jo.getString("intro");
                    String inform = jo.getString("inform");
                    String address = jo.getString("address");
                    int sector = jo.getInt("sector");

                    this.store = new Store(id, name, admin_id,tel,intro, inform, address, sector);
                    storeList.add(store);//accountList 차례대로 삽입

                    sb.append(// test용 stringbuffer
                            "id: " + storeList.get(0).getId()+
                                    "\n\n매장명: " + name  +
                                    "\n\n매장아이디: " + admin_id  +
                                    "\n\n매장번호: " + tel  +
                                    "\n\n매장소개: " + intro  +
                                    "\n\n매장정보: " + inform  +
                                    "\n\n매장주소: " + address  +
                                    "\n\n매장구역: " + sector  +
                                    "\n\n"

                    );

                }
                txtView.setText(sb);
        }catch(Exception E){
            E.printStackTrace();
        }



    }
}







