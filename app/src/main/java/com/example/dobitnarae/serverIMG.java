package com.example.dobitnarae;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;



public  class serverIMG  extends AsyncTask<String, Integer,Bitmap>{// 서버에 이미지를 bitmap형식으로 뿌리기

    Bitmap bmImg;


    @Override
    protected Bitmap doInBackground(String... urls) {
        // TODO Auto-generated method stub
        try{
            URL myFileUrl = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            bmImg = BitmapFactory.decodeStream(is);


        }catch(IOException e){
            e.printStackTrace();
        }
        return bmImg;
    }

    public static Bitmap getStoreImage(int clothID){

        String str = ""+clothID;
        Bitmap BM = null;
        try {
            serverIMG SI = new serverIMG();
            BM = SI.execute("http://13.209.89.187:3443/store/" + str+".jpg").get();


        }catch(Exception E){
            E.printStackTrace();
        }
        return BM;
    }
    public static Bitmap getClothImage(int clothID){

        String str = ""+clothID;
        Bitmap BM = null;
        try {
            serverIMG SI = new serverIMG();
            BM = SI.execute("http://13.209.89.187:3443/cloth/" + str+".jpg").get();
        }catch(Exception E){
            E.printStackTrace();
        }
        return BM;
    }



}

