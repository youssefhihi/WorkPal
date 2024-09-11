package com.workPal.controller;

import com.workPal.connectDB.DatabaseConnection;
import com.workPal.model.User;
import com.workPal.services.interfaces.UserService;
import com.workPal.services.serviceImpl.UserServiceImpl;

import java.sql.Connection;
import java.util.Optional;

public class UserController {
    private static final Connection connection = DatabaseConnection.connect();
    private final UserService userService = new UserServiceImpl(connection);



    public Optional<User> login(String email, String password) {
        return userService.login(email, password);
    }

    public void forgetPassword(String email) {
        userService.forgetPassword(email);
    }

    public  Boolean  updatePassword(User user, String newPassword){
        return userService.updatePassword(user,newPassword);
    }

    public Boolean findUserByEmail(String email){
        return  userService.findUserByEmail(email);
    }
}