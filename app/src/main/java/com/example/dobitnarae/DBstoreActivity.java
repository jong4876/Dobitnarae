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
    String user_id;
   ArrayList<StoreTmp> storeList = new ArrayList<StoreTmp>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbstore);

        Intent intent = getIntent();
        user_id = intent.getExtras().getString("ID");
        Toast.makeText(getApplicationContext(), user_id + "님 안녕하세요!", Toast.LENGTH_LONG).show();

        txtView = (TextView) findViewById(R.id.txtView);
        new JSONTask().execute("http://192.168.43.77:3443/store");//AsyncTask 시작시킴

    }

    public class JSONTask extends AsyncTask<String, String, String> {


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

                // JSONObject root = new JSONObject(str);// 전체 데이터 JSON파싱
                JSONArray ja = new JSONArray(str);
                // txtView.setText(str);
                for(int i=0; i<ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);
                    String store_ID = jo.getString("store_ID");
                    String name = jo.getString("name");
                    int location = jo.getInt("location");
                    String explain = jo.getString("explain");


                    StoreTmp tmp = new StoreTmp(store_ID, name, location, explain);
                    storeList.add(tmp);//accountList에 차례대로 삽입

                    sb.append(// test용 stringbuffer
                            "매장아이디: " + store_ID+
                                    "\n\n매장명: " + name  +
                                    "\n\n매장위치: " + location  +
                                    "\n\n매장설명: " + explain +
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






