package com.workPal.repositories.inteface;

import com.workPal.model.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> login(String email, String password);
    boolean forgetPassword(String email);
    Boolean updatePassword(User user, String newPassword);
    Optional<User> findUserByEmail(String email);
}
