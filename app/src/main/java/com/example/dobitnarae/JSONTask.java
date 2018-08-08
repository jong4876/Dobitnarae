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
    ArrayList<Store> storeList = new ArrayList<Store>();
    String user_id="jong4876";
    Store store;
    String tmpStr;



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

    protected void onPostExecute(String str) {// 정작 실행되는 메서드

        StringBuffer sb = new StringBuffer();

        try{
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


        }catch(JSONException e){
            e.printStackTrace();
        }

    }
    public static ArrayList<Store> getStoreAll(String str){ // JSON.HTML넣어서 사용
        ArrayList<Store> storeList = new ArrayList<Store>();
        Store store;

        StringBuffer sb = new StringBuffer();

        try{
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


                store = new Store(id, name, admin_id,tel,intro, inform, address, sector);
                storeList.add(store);//accountList 차례대로 삽입

            }


        }catch(JSONException e){
            e.printStackTrace();
        }


        return storeList;
    }








}
