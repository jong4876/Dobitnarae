package com.example.dobitnarae;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;

public class Clothes implements Serializable, Parcelable{
    private int cloth_id;
    private int store_id;
    private int type;
    private String name;
    private String intro;
    private int price;
    private int count;
    private int sex;

    int getCloth_id() { return this.cloth_id; }
    int getStore_id() { return this.store_id; }
    int getType(){ return this.type; }
    String getName() {
        return this.name;
    }
    String getIntro(){return this.intro;}
    int getPrice() { return  this.price; }
    int getCount() { return this.count; }
    int getSex() { return this.sex; }

    public Clothes() {
    }

    public Clothes(Parcel in) {
        readFromParcel(in);
    }

    public void setCloth_id(int cloth_id) {
        this.cloth_id = cloth_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public void setType(int type) {
        this.type = type;
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

    Clothes(int cloth_id, int store_id, int type, String name, String intro, int price, int count, int sex) {
        this.cloth_id = cloth_id;
        this.store_id = store_id;
        this.type = type;
        this.name = name;
        this.intro = intro;
        this.price = price;
        this.count = count;
        this.sex = sex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.cloth_id);
        dest.writeInt(this.store_id);
        dest.writeInt(this.type);
        dest.writeString(this.name);
        dest.writeString(this.intro);
        dest.writeInt(this.price);
        dest.writeInt(this.count);
        dest.writeInt(this.sex);
    }

    public void readFromParcel(Parcel in) {
        this.cloth_id = in.readInt();
        this.store_id = in.readInt();
        this.type = in.readInt();
        this.name = in.readString();
        this.intro = in.readString();
        this.price = in.readInt();
        this.count = in.readInt();
        this.sex = in.readInt();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Clothes createFromParcel(Parcel in) {
            return new Clothes(in);
        }

        public Clothes[] newArray(int size) {
            return new Clothes[size];
        }
    };
}