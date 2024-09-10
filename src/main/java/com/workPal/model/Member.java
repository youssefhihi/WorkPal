package com.workPal.model;
import  com.workPal.model.User;
public class Member extends User{
    private String phoneNumber;

    public Member(String name, String email, String password, String phoneNumber){
        super(name,email,password);
        this.phoneNumber = phoneNumber;

    }
    public  Member (){}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
