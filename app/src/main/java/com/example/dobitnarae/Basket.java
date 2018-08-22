package com.example.dobitnarae;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class Basket {
    private static Basket instance = new Basket();
    private ArrayList<BasketItem> basket;
    private int selectedStoreID;
    private String rentalDate;  // 형태  yyyy-mm-dd hh:mm:ss

    private Basket(){
        basket = new ArrayList<>();
        selectedStoreID = -1;
        rentalDate = "2018-08-13";
    }

    public static synchronized Basket getInstance(){
        return instance;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    public int getClothesCnt(){
        return basket.size();
    }

    public int getSelectedStoreID() {
        return selectedStoreID;
    }

    public ArrayList<BasketItem> getBasket() {
        return basket;
    }

    private void clearBasket(){
        basket.clear();
    }

    public void addClothes(final Context context, BasketItem item){
        /*
         * 1. 선택된 가게가 없을때
         *   - 담기
         * 2. 선택된 가게가 있을때
         *   - 담을려고 하는 옷의 가게와 동일한경우
         *     - 담기
         *   - 다른 경우
         *     - client에게 기존 목록 지울껀지 묻고 담기
         *
         */
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        date.setTimeZone(TimeZone.getTimeZone("GMT+9"));
        rentalDate = date.format(today);
        Log.e("Sdfsdf", rentalDate);
        if(selectedStoreID == -1){
            basket.add(item);
            selectedStoreID = item.getClothes().getStore_id();
        }
        else {
            if(selectedStoreID == item.getClothes().getStore_id())
                basket.add(item);
            else {
                showAlert(context, item);
            }
        }
    }

    private void showAlert(Context context, final BasketItem item){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("장바구니에는 한 매장의 한복만 담을수 있습니다\n" +
                "장바구니를 비우고 이 한복을 담으시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearBasket();
                        basket.add(item);
                        selectedStoreID = item.getClothes().getStore_id();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    public void deleteClothes(int position)
    {
        basket.remove(position);
    }

    public int getTotalPrice()
    {
        int price = 0;
        for(BasketItem b : basket){
            price += b.getClothes().getPrice() * b.getCnt();
        }
        return price;
    }

    public int getTotalClothesCnt()
    {
        int cnt = 0;
        for(BasketItem b : basket)
            cnt += b.getCnt();
        return cnt;
    }
}