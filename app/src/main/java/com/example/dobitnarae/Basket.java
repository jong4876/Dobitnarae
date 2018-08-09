package com.example.dobitnarae;

public class Basket {
    private String userID;
    private String clothID;

    public Basket(String userID, String clothID) {
        this.userID = userID;
        this.clothID = clothID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getClothID() {
        return clothID;
    }

    public void setClothID(String clothID) {
        this.clothID = clothID;
    }
}
