package com.example.dobitnarae;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;




public class SQLActivity extends AppCompatActivity {

    ImageView imView;
    TextView txtView;
    Bitmap bmImg;

    htmlDown task;
    ArrayList<ListItem> listItem = new ArrayList<ListItem>();
    ListItem Item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sql);



        task = new htmlDown();
        txtView = (TextView) findViewById(R.id.txtView);
        task.execute("http://192.168.219.201:3443/");//안종희 내부아이피, mygrape.ownip.net로 변경 필

    }


    private class htmlDown extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            try {
                // 연결 url 설정
                URL url = new URL(urls[0]);
                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 연결되었으면.
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    // 연결되었음 코드가 리턴되면.
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for (; ; ) {
                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                            String line = br.readLine();
                            if (line == null) break;
                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return jsonHtml.toString();

        }

        protected void onPostExecute(String str) {

            String ID;
            String PW;
            String name;
            String HP;
            String priv="asd";



            try{//??파싱이 안됨, 전달받은 데이터가 온전한 JSON 데이터가 아닐수 있음

                JSONObject root = new JSONObject(str);// 전체 데이터 JSON파싱
                JSONArray ja = root.getJSONArray("results");

                for(int i=0; i<ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);

                    ID = jo.getString("ID");
                    PW = jo.getString("PW");
                    name = jo.getString("name");
                    HP = jo.getString("HP");
                    priv = jo.getString("priv");

                    listItem.add(new ListItem(ID,PW,name, HP, priv));
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
           // txtView.setText("ID :"+listItem.get(0).getData(0));
         //   txtView.setText("ID :"+listItem.get(0).getData(0)+"\nPW:"+ listItem.get(0).getData(1)+"\nname:"+listItem.get(0).getData(2)+"\nname:"+listItem.get(0).getData(2)
           //         +"\nHP:"+listItem.get(0).getData(3)+"\npriv:"+listItem.get(0).getData(4));
        }

    }
}

class ListItem {//sql문 결과를 담기위한 클래스

    private String[] mData;

    public ListItem(String[] data) {


        mData = data;
    }

    public ListItem(String ID, String PW, String name, String HP, String priv) {

        mData = new String[4];
        mData[0] = ID;
        mData[1] = PW;
        mData[2] = name;
        mData[3] = HP;
        mData[4] = priv;


    }

    public String[] getData() {
        return mData;
    }

    public String getData(int index) {
        return mData[index];
    }

    public void setData(String[] data) {
        mData = data;
    }

}




