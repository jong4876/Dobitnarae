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

    public Store(int id, String name, String admin_id, String tel, String intro, String inform,
                 String address, int sector, double latitude, double longitude){
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


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setTel(String tel){
        this.tel = tel;
    }
    public void setIntro(String intro) {
        this.intro = intro;
    }
    public void setInform(String inform) {
        this.inform = inform;
    }
    public void setSector(int sector){
        this.sector = sector;
    }
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }



}