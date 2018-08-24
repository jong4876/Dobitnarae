package com.example.dobitnarae;

public class Account {
    private String id;
    private String name;
    private String phone;
    private int privilege;

    public Account() {
        setClientAccount();
    }
    // 임시 계정 정보 설정
    // 지울것임 setAdminAccount, setClientAccount
    public void setAdminAccount(){
        this.id = "jong4876";
        this.name = "원할머니";
        this.phone = "01022223333";
        this.privilege = Constant.ADMIN;
    }

    public void setClientAccount(){
        this.id = "Kuril951";
        this.name = "홍길동";
        this.phone = "01012345678";
        this.privilege = Constant.CLIENT;
    }

    public Account(String id, String name, String tel, int privilege) {
        this.id = id;
        this.name = name;
        this.phone = tel;
        this.privilege = privilege;
    }

    public static Account getInstance(){
        return Singleton.instance;
    }

    private static class Singleton{
        private static final Account instance = new Account();
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

    public String getPhone() {
        return phone;
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

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
