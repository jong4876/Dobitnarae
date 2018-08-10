package com.example.dobitnarae;

import java.io.Serializable;

public class Store implements Serializable{
    private int id;
    private String name;
    private String admin_id;
    private String address;
    private String tel;
    private String intro;
    private String inform;
    private int sector;
    private double longitude;
    private double latitude;

    public Store(int id, String name, String admin_id, String address, String tel, String intro,
                 String inform, int sector, double longitude, double latitude){
        this.id=id;
        this.name = name;
        this.admin_id= admin_id;
        this.address = address;
        this.tel = tel;
        this.intro = intro;
        this.inform = inform;
        this.sector = sector;
        this.longitude = longitude;
        this.latitude = latitude;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getAdmin_id() {
        return admin_id;
    }
    public String getAddress() {
        return address;
    }
    public String getTel(){
        return tel;
    }
    public String getIntro() {
        return intro;
    }
    public String getInform() {
        return inform;
    }
    public int getSector(){
        return sector;
    }
    public double getLongitude(){
        return longitude;
    }
    public double getLatitude(){
        return latitude;
    }


}