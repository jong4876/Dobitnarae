package com.example.dobitnarae;

public class StoreTmp{//cloth의 sql문 결과를 담기위한 클래스


    String store_ID;
    String name;
    int location;
    String explain;



    public StoreTmp(String store_ID, String name, int location, String explain) {

        this.store_ID = store_ID;
        this.name = name;
        this.location = location;
        this.explain = explain;

    }

    public String getstore_ID (){
        return store_ID ;
    }
    public String getname(){
        return name;
    }
    public int getlocation(){
        return location;
    }

    public String getexplain (){
        return explain ;
    }






}