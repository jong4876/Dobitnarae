package com.example.dobitnarae;

public class Clothes {
    private int cloth_id;
    private int store_id;
    private int category;
    private String name;
    private String intro;
    private int price;
    private int count;
    private int sex;

    int getCloth_id() { return this.cloth_id; }
    int getStore_id() { return this.store_id; }
    int getCategory(){ return this.category; }
    String getName() {
        return this.name;
    }
    String getIntro(){return this.intro;}
    int getPrice() { return  this.price; }
    int getCount() { return this.count; }
    int getSex() { return this.sex; }

    Clothes(int cloth_id,int store_id, int category, String name, String intro, int price, int count, int sex) {
        this.cloth_id = cloth_id;
        this.store_id = store_id;
        this.category = category;
        this.name = name;
        this.intro = intro;
        this.price = price;
        this.count = count;
        this.sex = sex;
    }
}
