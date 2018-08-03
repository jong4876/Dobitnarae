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
    ArrayList<account> accountList = new ArrayList<account>();

    htmlDown task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sql);



        task = new htmlDown();
        txtView = (TextView) findViewById(R.id.txtView);
        task.execute("http://192.168.100.79:3443/");//안종희 내부아이피, 자신의 아이피로 변경 필수

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

            StringBuffer sb = new StringBuffer();


            try{

                // JSONObject root = new JSONObject(str);// 전체 데이터 JSON파싱
                JSONArray ja = new JSONArray(str);
               // txtView.setText(str);
                for(int i=0; i<ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);

                    String ID = jo.getString("ID");
                    String PW = jo.getString("PW");
                    String name = jo.getString("name");
                    String HP = jo.getString("HP");
                    int priv = jo.getInt("priv");


                    account acc = new account(ID, PW, name, HP, priv);

                    accountList.add(acc);//accountList에 차례대로 삽입

                    sb.append(// test용 stringbuffer
                            "ID:" + ID +
                                    "\nPW:" + PW +
                                    "\nname:" + name +
                                    "\nHP:" + HP +
                                    "\npriv:" + priv +
                                    "\n\n"

                    );
                }
                    txtView.setText(sb);

            }catch(JSONException e){
                e.printStackTrace();
            }

        }

    }
}

class account {//account의 sql문 결과를 담기위한 클래스

    String ID;
    String PW;
    String name;
    String HP;
    int priv;


    public account(String ID, String PW, String name, String HP, int priv) {

        this.ID = ID;
        this.PW = PW;
        this.name = name;
        this.HP = HP;
        this.priv = priv;
    }
    public String getID(){
        return ID;
    }

    public String getPW(){
        return PW;
    }
    public String getname(){
        return name;
    }
    public String getHP(){
        return HP;
    }
    public int getpriv(){
        return priv;
    }





}




