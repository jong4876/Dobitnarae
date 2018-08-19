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

public  class JSONTask extends AsyncTask<String, String, String> {
    String user_id;
    String store_id;
    String URL = "http://192.168.219.104:3443/";
    String delClothName;
    Store upStore = new Store(0,"example","example","example","example","example","example",0,0.0,0.0);
    Clothes inCloth = new Clothes(0,0,0,"example","example",0,0,0);
    ArrayList<Store> storeList;

    int flag = 0;
    private JSONTask jsonTaskTmp;

    private JSONTask(){
    }

    private static class Singleton{
        private static final JSONTask instance = new JSONTask();
    }

    public static JSONTask getInstance(){
        return Singleton.instance;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public void setStore_id(String store_id){
        user_id = store_id;
    }
    public void setUpStore(Store upStore){
        this.upStore = upStore;
        flag = 1;
    }
    public void setInCloth(Clothes inCloth){
        this.inCloth = inCloth;
        flag = 2;
    }
    public void setDelClothName(String delClothName){// 삭제를 위한 셋함수(매개변수 옷이름)
        user_id = delClothName;
    }



    @Override
    protected String doInBackground(String... urls) {

        StringBuilder jsonHtml = new StringBuilder();
        try {

            // 연결 url 설정
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("user_id", user_id);

            if(flag == 1) {//updateStore를 위한 서버에 데이터 전송

                jsonObject.accumulate("name", upStore.getName());//update를 위해 서버로 보낼 데이터들 req.on
                jsonObject.accumulate("admin_id", upStore.getAdmin_id());
                jsonObject.accumulate("tel", upStore.getTel());
                jsonObject.accumulate("intro", upStore.getIntro());
                jsonObject.accumulate("inform", upStore.getInform());
                jsonObject.accumulate("address", upStore.getAddress());
                jsonObject.accumulate("sector", upStore.getSector());
                jsonObject.accumulate("longitude", upStore.getLongitude());
                jsonObject.accumulate("latitude", upStore.getLatitude());
                flag = 0;
            }

            if(flag == 2) {//insertCloth
                Log.e("err",inCloth.getName());
               // jsonObject.accumulate("cloth_ID", inCloth.getCloth_id());//insert를 위해 서버로 보낼 데이터들 req.on
                jsonObject.accumulate("store_ID", inCloth.getStore_id());
                jsonObject.accumulate("category", inCloth.getCategory());
                jsonObject.accumulate("name", inCloth.getName());
                jsonObject.accumulate("intro", inCloth.getIntro());
                jsonObject.accumulate("price", inCloth.getPrice());
                jsonObject.accumulate("count", inCloth.getCount());
                jsonObject.accumulate("sex", inCloth.getSex());
                flag = 0;
            }




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
            JSONTask JT = new JSONTask();
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
    public int chageStoreID(String user_id){ // "jong4876" 값을 -> 1로
        int id = -1;

        try{
            JSONTask JT = new JSONTask();
            JT.setUser_id(user_id);
            String str = JT.execute("http://192.168.219.104:3443/changeID").get();
            Log.e("err",str);
            JSONArray ja = new JSONArray(str);
            for(int i=0; i<ja.length(); i++){
                JSONObject jo = ja.getJSONObject(i);
                id = jo.getInt("id");
                Log.e("err",""+id);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return id;
    }

    public ArrayList<Clothes> getClothesAll(String user_id){ // 아이디에 해당하는 매장의 옷 검색
        ArrayList<Clothes> clothesList = new ArrayList<Clothes>();
        Clothes clothes;

        try{
            int id = JSONTask.getInstance().chageStoreID(user_id);// store클래스의 id값으로 변환 1,2,3,4~~
            JSONTask JT = new JSONTask();
            JT.setUser_id(id+"");
            String str = JT.execute("http://192.168.219.104:3443/clothes").get();

            JSONArray ja = new JSONArray(str);
            for(int i=0; i<ja.length(); i++){
                JSONObject jo = ja.getJSONObject(i);
                int cloth_ids = jo.getInt("cloth_id");
                int store_ids = jo.getInt("store_id");
                int category = jo.getInt("category");
                String name= jo.getString("name");
                String intro = jo.getString("intro");
                int price = jo.getInt("price");
                int count = jo.getInt("count");
                int sex = jo.getInt("sex");


                clothes = new Clothes(cloth_ids,store_ids,category, name,intro, price, count, sex);
                clothesList.add(clothes);//accountList 차례대로 삽입

            }


        }catch(Exception e){
            e.printStackTrace();
        }


        return clothesList;
    }


    public void updateStore(Store upStore, String user_id){ //바꿀 값이 들어 있는 store 클래스와, 바꿀 store의 아이디 전달
        try {
            JSONTask JT = new JSONTask();
            JT.setUpStore(upStore);
            JT.setUser_id(user_id);
            JT.execute("http://13.209.89.187:3443/updateStore");
            Log.e("err","where are you");



        }catch(Exception e){
            e.printStackTrace();
        }


    }


    public void insertCloth(Clothes inCloth, String user_id){ // user_id에 해당하는 매장에 옷 추가(관리자)
        try {
            int id = JSONTask.getInstance().chageStoreID(user_id);
            JSONTask JT = new JSONTask();
            JT.setInCloth(inCloth);
            JT.setUser_id(id+"");
            JT.execute("http://192.168.219.104:3443/insertCloth");// URL변경필수
            Log.e("err","cloth삽입 성공!!");




        }catch(Exception e){
            e.printStackTrace();
        }


    }
    public void deleteCloth(String delClothName){ // user_id에 해당하는 매장에 옷 추가(관리자)
        try {

            JSONTask JT = new JSONTask();
            JT.setDelClothName(delClothName);
            JT.execute("http://192.168.219.104:3443/deleteCloth");// URL변경필수
            Log.e("err","clotht삭제 성공");


        }catch(Exception e){
            e.printStackTrace();
        }


    }

}
