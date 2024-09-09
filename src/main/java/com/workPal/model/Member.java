package com.workPal.model;
import  com.workPal.model.User;
public class Member extends User{
    private String phoneNumber;

    public Member(int id, String name, String email, String password, String phoneNumber){
        super(id,name,email,password);
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
