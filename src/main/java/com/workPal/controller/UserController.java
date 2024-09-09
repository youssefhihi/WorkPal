package com.workPal.controller;

import com.workPal.model.User;
import com.workPal.services.interfaces.UserService;

import java.util.Optional;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public Optional<User> login(String email, String password) {
        return userService.login(email, password);
    }

    public void forgetPassword(String email) {
        userService.forgetPassword(email);
    }

    public boolean validateToken(String email, String token) {
        return userService.validateToken(email, token);
    }

    public boolean resetPassword(String email, String newPassword, String token) {
        return userService.resetPassword(email, newPassword, token);
    }
}