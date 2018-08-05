package com.example.dobitnarae;

public class Store {
    private int id;
    private String name;
    private String address;
    private String introduction;
    private String information;
    private String phoneNumber;
    private String ownerName;
    private double latitude, longitude;

    public Store(String name, String address, String introduction,
                 String information, String phoneNumber, String ownerName,
                 double latitude, double longitude){
        this.name = name;
        this.address = address;
        this.introduction = introduction;
        this.information = information;
        this.phoneNumber = phoneNumber;
        this.ownerName = ownerName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getInformation() {
        return information;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getName() {
        return name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

