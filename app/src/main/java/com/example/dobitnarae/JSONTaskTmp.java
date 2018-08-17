package com.example.dobitnarae;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public  class JSONTaskTmp extends AsyncTask<String, String, String> {
    String user_id;
    String URL = "http://192.168.219.104:3443/";
    Store upStore = new Store(0,"example","example","example","example","example","example",0,0.0,0.0);
    ArrayList<Store> storeList;

    int Uflag = 0;
    private JSONTaskTmp jsonTaskTmp;

    private JSONTaskTmp(){
    }

    private static class Singleton{
        private static final JSONTaskTmp instance = new JSONTaskTmp();
    }

    public static JSONTaskTmp getInstance(){
        return Singleton.instance;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public void setUpStore(Store upStore){
        this.upStore = upStore;
    }

    public JSONTaskTmp(Store upStore, String user_id){//store 수정을 위한 생성자, admin_id로 수정데이터 선택
        this.upStore = upStore;
        this.user_id = user_id;

        Uflag = 1;
    }



    @Override
    protected String doInBackground(String... urls) {

        StringBuilder jsonHtml = new StringBuilder();
        try {

            // 연결 url 설정
            Log.e("err","?? "+upStore.getName());

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("user_id", user_id);

            if(Uflag == 1) {

                jsonObject.accumulate("name", upStore.getName());//update를 위해 서버로 보낼 데이터들 req.on
                jsonObject.accumulate("admin_id", upStore.getAdmin_id());
                jsonObject.accumulate("tel", upStore.getTel());
                jsonObject.accumulate("intro", upStore.getIntro());
                jsonObject.accumulate("inform", upStore.getInform());
                jsonObject.accumulate("address", upStore.getAddress());
                jsonObject.accumulate("sector", upStore.getSector());
                jsonObject.accumulate("longitude", upStore.getLongitude());
                jsonObject.accumulate("latitude", upStore.getLatitude());
                Uflag = 0;
            }

            //jsonObject.accumulate("upStore", upStore);// 서버로 보낼 데이터들 req.on

            URL url = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();//이거문제

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
    public  ArrayList<Store> getStoreAll(String user_id){ // JSON.HTML넣어서 사용, 전송되는 user_id jong4876~~
        ArrayList<Store> storeList = new ArrayList<Store>();
        Store store;


        try {
            JSONTaskTmp JT = new JSONTaskTmp();
            JT.setUser_id(user_id);
            String str = JT.execute("http://13.209.89.187:3443/store").get();




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

    public void updateStore(Store upStore, String user_id){ // JSON.HTML넣어서 사용, 전송되는 user_id jong4876~~
        try {
            JSONTaskTmp JT = new JSONTaskTmp();
            JT.setUpStore(upStore);
            JT.setUser_id(user_id);
            JT.execute("http://13.209.89.187:3443/updateStore");
            Log.e("err","where are you");



        }catch(Exception e){
            e.printStackTrace();
        }


    }


}
