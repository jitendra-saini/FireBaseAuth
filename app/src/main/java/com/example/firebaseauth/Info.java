package com.example.firebaseauth;

public class Info  {

    String adress;
    String phone;

    public Info() {
    }

    public Info(String adress, String phone) {
        this.adress = adress;
        this.phone = phone;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public String getPhone() {
        return phone;
    }
}
