package com.example.dobitnarae;

public class Account {
    private String id;
    private String name;
    private String tel;
    private int privilege;

    public Account(String id, String name, String tel, int privilege) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.privilege = privilege;
    }

    public String getName() {
        return name;
    }

    public int getPrivilege() {
        return privilege;
    }

    public String getId() {
        return id;
    }

    public String getTel() {
        return tel;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
