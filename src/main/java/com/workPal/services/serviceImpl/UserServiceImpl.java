package com.workPal.services.serviceImpl;

import com.workPal.model.User;
import com.workPal.repositories.interfaces.UserRepository;
import com.workPal.repositories.repositoryImpl.UserImpl;
import com.workPal.services.interfaces.UserService;

import java.sql.Connection;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(Connection connection) {
        this.userRepository = new UserImpl(connection);
    }

    @Override
    public Optional<User> login(String email, String password) {
        return userRepository.login(email, password);
    }

    @Override
    public Boolean forgetPassword(String email) {
       return userRepository.forgetPassword(email);
    }

    @Override
    public  Boolean  updatePassword(User user, String newPassword){
        return userRepository.updatePassword(user,newPassword);
    }

    @Override
    public Boolean findUserByEmail(String email){
        Optional<User> isFound = userRepository.findUserByEmail(email);
        return isFound.isPresent();
    }



}