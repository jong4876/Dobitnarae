package com.example.dobitnarae;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class clothActivity extends AppCompatActivity {

    TextView txtView;
    String user_id;
    ArrayList<cloth> clothList = new ArrayList<cloth>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth);
        Intent intent = getIntent();
        user_id = intent.getExtras().getString("ID");
        Toast.makeText(getApplicationContext(),user_id+"님 안녕하세요!",Toast.LENGTH_LONG).show();

        txtView = (TextView) findViewById(R.id.txtView);
        new JSONTask().execute("http://192.168.43.77:3443/cloth");//AsyncTask 시작시킴


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

        protected void onPostExecute(String str) {


            StringBuffer sb = new StringBuffer();


            try{

                // JSONObject root = new JSONObject(str);// 전체 데이터 JSON파싱
                JSONArray ja = new JSONArray(str);
                // txtView.setText(str);
                for(int i=0; i<ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);

                    String cloth_ID = jo.getString("cloth_ID");
                    String store_ID = jo.getString("store_ID");
                    int type = jo.getInt("type");
                    String img = jo.getString("img");
                    String name = jo.getString("name");
                    String size = jo.getString("size");
                    int price = jo.getInt("price");
                    int count = jo.getInt("count");


                    cloth tmp = new cloth(cloth_ID, store_ID, type, img, name, size, price, count);
                    clothList.add(tmp);//accountList에 차례대로 삽입

                    sb.append(// test용 stringbuffer
                            "cloth_ID:" + cloth_ID +
                                    "\nstore_ID:" + store_ID  +
                                    "\ntype :" + type  +
                                    "\nimg:" + img +
                                    "\nname:" + name +
                                    "\nsize:" + size +
                                    "\nprice:" + price +
                                    "\ncount:" + count +
                                    "\n\n"

                    );
                }
                txtView.setText(sb);
                //txtView.setText(clothList.get(1).getname());

            }catch(JSONException e){
                e.printStackTrace();
            }


        }
    }
}
class cloth{//cloth의 sql문 결과를 담기위한 클래스

    String cloth_ID;
    String store_ID;
    int type;
    String img;
    String name;
    String size;
    int price;
    int count;


    public cloth(String cloth_ID, String store_ID ,int type, String img, String name, String size,int price, int count) {

        this.cloth_ID = cloth_ID;
        this.store_ID = store_ID;
        this.name = name;
        this.type = type;
        this.img = img;
        this.size = size;
        this.price = price;
        this.count = count;
    }
    public String getcloth_ID(){
        return cloth_ID;
    }

    public String getstore_ID (){
        return store_ID ;
    }
    public String getname(){
        return name;
    }
    public int gettype(){
        return type;
    }
    public String getimg (){
        return img ;
    }

    public String getsize (){
        return size ;
    }
    public int getprice(){
        return price;
    }

    public int getcount (){
        return count;
    }





}






