package com.example.dobitnarae;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Order implements Comparable<Order>{
    private static ArrayList<Order> ncinstance = new ArrayList<Order>();
    private static ArrayList<Order> ocinstance = new ArrayList<Order>();
    private static Order instance = new Order();
    private int orderNo;
    private String userID;
    private String adminID;
    private int acceptStatus;
    private String orderDate;

    public static synchronized Order getInstance(){
        return instance;
    }

    public static synchronized ArrayList<Order> getncInstanceList(){
        return ncinstance;
    }

    public static synchronized ArrayList<Order> getocInstanceList(){
        return ocinstance;
    }

    public Order(){
    }

    public Order(int orderNo, String userID, String adminID, int acceptStatus, String orderDate) {
        this.orderNo = orderNo;
        this.userID = userID;
        this.adminID = adminID;
        this.acceptStatus = acceptStatus;
        this.orderDate = orderDate;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public int getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(int acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    // 내림차순정렬
    @Override
    public int compareTo(@NonNull Order o) {
        if(this.orderNo > o.orderNo) {
            return -1;
        } else if(this.orderNo == o.orderNo) {
            return 0;
        } else {
            return 1;
        }
    }
}