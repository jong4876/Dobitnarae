package com.example.dobitnarae;

import android.os.AsyncTask;
import android.util.Log;
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

public  class JSONTask extends AsyncTask<String, String, String> {
    String user_id;
    String URL = "http://13.209.89.187:3443/";


    public JSONTask(String user_id){
        this.user_id = user_id;
        Log.e("err",user_id);
    }



    @Override
    protected String doInBackground(String... urls) {

        StringBuilder jsonHtml = new StringBuilder();
        try {
            // 연결 url 설정

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("user_id", user_id);
            jsonObject.accumulate("name", "yun");// 서버로 보낼 데이터들 req.on


            URL url = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
            conn.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
            conn.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
            conn.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
            conn.connect();

            //서버로 보내기위해서 스트림 만듬
            OutputStream outStream = conn.getOutputStream();
            //버퍼를 생성하고 넣음
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
            writer.write(jsonObject.toString());
            writer.flush();
            writer.close();//버퍼를 받아줌
            //////////서버로 데이터 전송

            // 연결되었으면.
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            for (; ; ) {
                // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                String line = br.readLine();
                if (line == null) break;
                // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                jsonHtml.append(line + "\n");
            }
            br.close();

            conn.disconnect();

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return jsonHtml.toString();

    }

    public static ArrayList<Store> getStoreAll(String user_id){ // JSON.HTML넣어서 사용, 전송되는 user_id jong4876~~
        ArrayList<Store> storeList = new ArrayList<Store>();
        Store store;


        try {
            String str = new JSONTask(user_id).execute("http://13.209.89.187:3443/store").get();

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
                double latitude = jo.getDouble("latitude");
                double longitude = jo.getDouble("longitude");


                store = new Store(id, name, admin_id,tel,intro, inform, address, sector, latitude, longitude);
                storeList.add(store);//accountList 차례대로 삽입



            }


        }catch(Exception e){
            e.printStackTrace();
        }


        return storeList;
    }

    public static ArrayList<Clothes> getClothesAll(int user_id){ // JSON.HTML넣어서 사용, user_id값은 1,2,3,4,~~~~
        ArrayList<Clothes> clothesList = new ArrayList<Clothes>();
        Clothes clothes;

        StringBuffer sb = new StringBuffer();

        try{
            String str = new JSONTask("1").execute("http://13.209.89.187:3443/clothes").get();

            JSONArray ja = new JSONArray(str);
            for(int i=0; i<ja.length(); i++){
                JSONObject jo = ja.getJSONObject(i);
                int cloth_id = jo.getInt("cloth_id");
                int store_id = jo.getInt("store_id");
                int category = jo.getInt("category");
                String name= jo.getString("name");
                String intro = jo.getString("intro");
                int price = jo.getInt("price");
                int count = jo.getInt("count");
                int sex = jo.getInt("sex");


                clothes = new Clothes(cloth_id,store_id,category, name,intro, price, count, sex);
                clothesList.add(clothes);//accountList 차례대로 삽입

            }


        }catch(Exception e){
            e.printStackTrace();
        }


        return clothesList;
    }




}
