package com.example.dobitnarae;

import java.io.Serializable;

public class OrderInfoData implements Serializable{
    private String orderNo;
    private String orderName;
    private String orderBasket;
    private String orderDate;
    private int orderPrice;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderBasket() {
        return orderBasket;
    }

    public void setOrderBasket(String orderBasket) {
        this.orderBasket = orderBasket;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }
}
