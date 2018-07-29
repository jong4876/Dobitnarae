package com.example.dobitnarae;

public class Clothes {
    int image;
    String name;
    int price;

    int getImage() {
        return this.image;
    }
    String getName() {
        return this.name;
    }
    int getPrice() { return  this.price; }

    Clothes(int image, String name, int price) {
        this.image = image;
        this.name = name;
        this.price = price;
    }
}