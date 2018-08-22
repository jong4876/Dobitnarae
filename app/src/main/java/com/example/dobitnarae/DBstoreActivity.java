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
    ArrayList<Order> orderList = new ArrayList<Order>();
    ArrayList<BasketItem> bascketList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbstore);
        String results;
        Intent intent = getIntent();

        txtView = (TextView) findViewById(R.id.txtView);

        try {

            /*
            clothesList = JSONTask.getInstance().getClothesAll("jong4876");// jong4876 매장의 옷들 검색
            inCloth = clothesList.get(0);// 업데이트 할 데이터 -> cloth의 첫 데이터
            int cnt = inCloth.getCount();
            inCloth.setCount(cnt - 1 );// set으로 데이터 수젇
            JSONTask.getInstance().updateCloth(inCloth);// 바꿀 cloth의 id역시 set으로 만들어 준 후 보냄
            */
            //cloth 수정 예시



            // JSONTask.getInstance().deleteCloth("만신홍 모자충");
            //cloth 삭제 예시



            /*
            inCloth = clothesList.get(0);
            inCloth.setName("만신홍 모자");
            inCloth.setCategory(5);
            inCloth.setIntro("만신홍 한복과 잘 어울리는 모자");
            JSONTask.getInstance().insertCloth(inCloth,"jong4876");
            */
            //cloth 삽입 예시



            /*
            storeList = JSONTask.getInstance().getStoreAll("jong4876");
            storeList.get(0).setSector(1);
            JSONTask.getInstance().updateStore(storeList.get(0),"jong4876");
            */
            //store 수정 예시


            //orderList = JSONTask.getInstance().getOrderCustomerAll("su123");


            /*
            Order inOrder = orderList.get(0);
            inOrder.setClothID(2);
            JSONTask.getInstance().insertOrder(inOrder);
            */
            //order 삽입 예시

            //JSONTask.getInstance().deleteOrder(4);
            //order 삭제 예시

            //JSONTask.getInstance().updateOrderAccept(5,1);
            //update accept 예시

            storeList = JSONTask.getInstance().getCustomerStoreAll();


            StringBuffer sb = new StringBuffer();

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


            /*
            for(int i=0; i<clothesList.size(); i++){
                sb.append(// test용 stringbuffer
                        "한복id: " + clothesList.get(i).getCloth_id()+
                                "\n\n매장명: " + clothesList.get(i).getStore_id() +
                                "\n\n카테고리: " + clothesList.get(i).getCategory()  +
                                "\n\n옷이름: " + clothesList.get(i).getName() +
                                "\n\n옷소개: " + clothesList.get(i).getIntro()  +
                                "\n\n옷 가격: " + clothesList.get(i).getPrice() +
                                "\n\n옷 수량: " + clothesList.get(i).getCount()  +
                                "\n\n옷 성별: " + clothesList.get(i).getSex()  +
                                "\n\n\n"
                );
            }
            */

/*
            for(int i=0; i<orderList.size(); i++){

                sb.append(// test용 stringbuffer
                        "주문번호: " + orderList.get(i).getOrderNo()+
                                "\n\n주문아이디: " + orderList.get(i).getUserID() +
                                "\n\n매장아이디: " + orderList.get(i).getAdminID()  +
                                "\n\n승인여부: " + orderList.get(i).getAcceptStatus()  +
                                "\n\n예약날짜: " + orderList.get(i).getOrderDate() +
                                "\n\n\n"
                );
            }
*/

            txtView.setText(sb);
        }catch(Exception E){
            E.printStackTrace();
        }



    }
}