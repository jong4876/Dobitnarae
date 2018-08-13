package com.example.dobitnarae;

import java.io.Serializable;

public class Clothes implements Serializable{
    private int cloth_id;
    private int store_id;
    private int category;
    private String name;
    private String intro;
    private int price;
    private int count;
    private int sex;

    public Clothes(){ }

    public int getCloth_id() { return this.cloth_id; }
    public int getStore_id() { return this.store_id; }
    public int getCategory() {
        return category;
    }
    public String getName() { return this.name; }
    public String getIntro(){return this.intro;}
    public int getPrice() { return  this.price; }
    public int getCount() { return this.count; }
    public int getSex() { return this.sex; }

    Clothes(int cloth_id, int store_id, int category, String name, String intro, int price, int count, int sex) {
        this.cloth_id = cloth_id;
        this.store_id = store_id;
        this.category = category;
        this.name = name;
        this.intro = intro;
        this.price = price;
        this.count = count;
        this.sex = sex;
    }

    public void setCloth_id(int cloth_id) {
        this.cloth_id = cloth_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}