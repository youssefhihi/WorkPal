package com.workPal.services.interfaces;

import com.workPal.model.User;
import java.util.Optional;

public interface UserService {

    Optional<User> login(String email, String password);

    Boolean forgetPassword(String email);

    Boolean updatePassword(User user, String newPassword);
}