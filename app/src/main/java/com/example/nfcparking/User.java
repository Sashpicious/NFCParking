package com.example.nfcparking;

public class User {

    public String name, useremail, phone, numberPlate, card, userType, time;
    public int status;

    public User(String name, String useremail, String phone, String numberPlate, String card,int status, String userType, String time) {

        this.name = name;
        this.useremail = useremail;
        this.phone = phone;
        this.numberPlate = numberPlate;
        this.card = card;
        this.status = status;
        this.userType = userType;
        this.time = time;
    }




//    public String getFirstName() {
//        return name;
//    }
//
//    public String getLastName() {
//        return useremail;
//    }
}
