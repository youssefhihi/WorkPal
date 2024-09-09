package com.workPal.services.serviceImpl;

import com.workPal.model.User;
import com.workPal.repositories.inteface.UserRepository;
import com.workPal.services.interfaces.UserService;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> login(String email, String password) {
        return userRepository.login(email, password);
    }

    @Override
    public void forgetPassword(String email) {
        userRepository.forgetPassword(email);
    }

    @Override
    public boolean validateToken(String email, String token) {
        return userRepository.validateToken(email, token);
    }

    @Override
    public boolean resetPassword(String email, String newPassword, String token) {
        return userRepository.resetPassword(email, newPassword, token);
    }
}