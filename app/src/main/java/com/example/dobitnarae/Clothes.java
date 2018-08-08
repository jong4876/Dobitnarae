package com.example.dobitnarae;

import java.io.Serializable;

public class Clothes implements Serializable{
    private int id;
    private int image;
    private String name;
    private int price;
    private int category;

    int getImage() {
        return this.image;
    }
    String getName() {
        return this.name;
    }
    int getPrice() { return  this.price; }
    int getId() { return this.id; }
    int getCategory() { return this.category; }

    Clothes(int id, int image, String name, int price, int category) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.category = category;
    }
}