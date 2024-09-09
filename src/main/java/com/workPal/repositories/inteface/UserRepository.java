package com.workPal.repositories.inteface;

import com.workPal.model.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> login(String email, String password);
    void forgetPassword(String email);
    boolean validateToken(String email, String token);
    boolean resetPassword(String email, String newPassword, String token);
}
