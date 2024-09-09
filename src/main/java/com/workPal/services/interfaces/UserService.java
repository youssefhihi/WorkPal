package com.workPal.services.interfaces;

import com.workPal.model.User;
import java.util.Optional;

public interface UserService {

    Optional<User> login(String email, String password);

    void forgetPassword(String email);

    boolean validateToken(String email, String token);

    boolean resetPassword(String email, String newPassword, String token);
}