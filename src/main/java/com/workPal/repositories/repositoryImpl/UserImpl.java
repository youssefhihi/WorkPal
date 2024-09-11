package com.workPal.repositories.repositoryImpl;

import com.workPal.enums.Role;
import com.workPal.model.User;
import com.workPal.repositories.inteface.UserRepository;
import com.workPal.utility.Mails.JMailer;
import com.workPal.utility.Mails.MailMsg;
import com.workPal.utility.UserUtility;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class UserImpl implements UserRepository {

    private final Connection connection;

    public UserImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Optional<User> findUserByEmail(String email) {
        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("email"), rs.getString("password"));
                user.setId((UUID) rs.getObject("id"));
                user.setRole(Role.valueOf(rs.getString("role")));
                return Optional.of(user);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Boolean updatePassword(User user, String newPassword){
        Optional<User> userOptional = findUserByEmail(user.getEmail());
        try{
            if (userOptional.isPresent()) {
                User userfound = userOptional.get();
                String hashedPassword = UserUtility.hashPassword(user.getPassword());
                if(userfound.getPassword().equals(hashedPassword)){
                   String hashNewPassword  = UserUtility.hashPassword(newPassword);
                   String sql = "UPDATE users SET password = ? WHERE email = ?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, hashNewPassword);
                    statement.setString(2, user.getEmail());
                    statement.executeUpdate();
                    System.out.println("✅ Password updated successfully!");
                    return true;
                } else {
                    System.out.println("❌ Current password is incorrect.");
                }
            } else {
                System.out.println("User not found.");
            }
        } catch (Exception e) {
            System.out.println("an error occurred during updating password " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean forgetPassword(String email){
        try {
            Optional<User> userOptional = findUserByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String randomStr = UserUtility.generateRandomString();
                String hashNewPassword = UserUtility.hashPassword(randomStr);
                String sql = "UPDATE users SET password = ? WHERE email = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, hashNewPassword);
                statement.setString(2, user.getEmail());
                statement.executeUpdate();
                new JMailer().sendEmail("reset Password Email", MailMsg.resetPassword(randomStr,user.getName()), user.getEmail());
                System.out.println("📨 A password reset code has been sent to your email: " + email);
                System.out.println("🔄 Please check your email and use the code to Login and updated after.");
                return true;
            } else {
                System.out.println("❌ No account found with the given email. Please try again or register a new account.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("an error occurred during forget password " + e.getMessage());
        }
        return false;
    }

    @Override
    public Optional<User> login(String email, String password) {
        try {
            Optional<User> userOptional = findUserByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String storedPassword = user.getPassword();
                String hashedPassword = UserUtility.hashPassword(password);
                if (hashedPassword.equals(storedPassword)) {
                    return  userOptional ;
                }else{
                    System.out.println("❌ Password incorrect. Please try again.");
                }
            } else {
                System.out.println("❌ User not found with the given email.");
            }
        } catch (Exception e) {
            System.out.println("⚠️ An error occurred during login " + e.getMessage());
        }

        return Optional.empty();
    }

}
