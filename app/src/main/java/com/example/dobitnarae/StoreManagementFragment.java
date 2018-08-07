package com.example.dobitnarae;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.util.Collections;

public class StoreManagementFragment extends Fragment {
    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;
    String user_id= "S-5";
    ArrayList<StoreTmp> storeList = new ArrayList<StoreTmp>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_management_store, container, false);

        mListView = (ListView) rootView.findViewById(R.id.listView);

        mAdapter = new ListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);

        Toast.makeText(getContext(), user_id + "님 안녕하세요!", Toast.LENGTH_LONG).show();
        getStoreAll("http://192.168.43.77:3443/store");//AsyncTask 시작시킴




        return rootView;
    }

    private class ViewHolder {
        public TextView mText;

        public TextView mContext;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<StoreInfoData> mListData = new ArrayList<StoreInfoData>();

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_info_store, null);

                holder.mText = (TextView) convertView.findViewById(R.id.mText);
                holder.mContext = (TextView) convertView.findViewById(R.id.mContext);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            StoreInfoData mData = mListData.get(position);


            holder.mText.setText(mData.mText);
            holder.mContext.setText(mData.mContext);

            return convertView;
        }

        public void addItem(String mText, String mContext){
            StoreInfoData addInfo = null;
            addInfo = new StoreInfoData();
            addInfo.mText = mText;
            addInfo.mContext = mContext;

            mListData.add(addInfo);
        }

        public void remove(int position){
            mListData.remove(position);
            dataChange();
        }

        public void dataChange(){
            mAdapter.notifyDataSetChanged();
        }
    }

    public void getStoreAll(String URL){
        new JSONTask().execute(URL);//AsyncTask 시작시킴
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
                mAdapter.addItem("아이디",storeList.get(0).getstore_ID());
                mAdapter.addItem("이름", storeList.get(0).getname());
                mAdapter.addItem("위치", ""+storeList.get(0).getlocation());
                mAdapter.addItem("설명", storeList.get(0).getexplain());
                mAdapter.notifyDataSetChanged();




            }catch(JSONException e){
                e.printStackTrace();
            }

        }
    }
}


