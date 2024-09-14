package com.workPal;

import com.workPal.connectDB.DatabaseConnection;
import com.workPal.model.Manager;
import com.workPal.model.User;
import com.workPal.utility.UserUtility;
import com.workPal.view.Auth.Auth;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;


public class Main {
//    public static Connection connection = DatabaseConnection.connect();
    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {
       Auth.runAuth();
//        User manager  = new User();
//        manager.setEmail("hhhhh");
//        StringBuilder keys = new StringBuilder();
//        StringBuilder values = new StringBuilder();
//
//
//        for(Field manage : manager.getClass().getDeclaredFields()){
//            manage.setAccessible(true);
//            System.out.println("here : " + manage);
//        }
//        String name = "youssef hihi";
//        String email = "hihi.allobaba@gmail.com";
//        String password = "1234";
//        String sql = "INSERT INTO admin (name, email, password,role) VALUES (?, ?, ?,?::user_role)";
//        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.setString(1, name);
//        statement.setString(2, email);
//        String hashPassword = UserUtility.hashPassword(password);
//        statement.setString(3, hashPassword);
//        statement.setString(4,"admin");
//        statement.executeUpdate();
    }
}