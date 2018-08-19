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
    Clothes inCloth;
    ArrayList<Store> storeList = new ArrayList<Store>();
    ArrayList<Clothes> clothesList = new ArrayList<Clothes>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbstore);
        String results;
        Intent intent = getIntent();

        txtView = (TextView) findViewById(R.id.txtView);

        try {

            clothesList = JSONTask.getInstance().getClothesAll("kang123");// kang123 매장의 옷들 검색




           // JSONTask.getInstance().deleteCloth("만신홍 모자충");
            //옷삭제 예시



            /*
            inCloth = clothesList.get(0);
            inCloth.setName("만신홍 모자");
            inCloth.setCategory(5);
            inCloth.setIntro("만신홍 한복과 잘 어울리는 모자");
            JSONTask.getInstance().insertCloth(inCloth,"jong4876");
            */
            //cloth삽입 예시



            /*
            storeList = JSONTask.getInstance().getStoreAll("jong4876");
            storeList.get(0).setSector(10);
            JSONTask.getInstance().updateStore(storeList.get(0),"jong4876");
            */
            //store 수정 예시






            StringBuffer sb = new StringBuffer();
            /*
            for(int i=0; i<storeList.size(); i++){

                sb.append(// test용 stringbuffer
                        "한복id: " + storeList.get(i).getId()+
                                "\n\n매장명: " + storeList.get(i).getName()  +
                                "\n\n매장아이디: " + storeList.get(i).getAdmin_id()  +
                                "\n\n매장번호: " + storeList.get(i).getTel()  +
                                "\n\n매장소개: " + storeList.get(i).getIntro()  +
                                "\n\n매장정보: " + storeList.get(i).getInform()  +
                                "\n\n매장주소: " + storeList.get(i).getAddress()  +
                                "\n\n매장구역: " + storeList.get(i).getSector()  +
                                "\n\n\n"
                );
            }
            */
            for(int i=0; i<clothesList.size(); i++){

                sb.append(// test용 stringbuffer
                        "한복id: " + clothesList.get(i).getCloth_id()+
                                "\n\n매장명: " + clothesList.get(i).getStore_id() +
                                "\n\n매장아이디: " + clothesList.get(i).getCategory()  +
                                "\n\n매장번호: " + clothesList.get(i).getName() +
                                "\n\n매장소개: " + clothesList.get(i).getIntro()  +
                                "\n\n매장정보: " + clothesList.get(i).getPrice() +
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