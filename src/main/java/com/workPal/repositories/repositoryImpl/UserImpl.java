package com.workPal.repositories.repositoryImpl;

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

public class UserImpl implements UserRepository {

    private final Connection connection;

    public UserImpl(Connection connection) {
        this.connection = connection;
    }

    private Optional<User> findUserByEmail(String email) {
        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"));
                return Optional.of(user);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
    @Override
    public void forgetPassword(String email){
        try {
            Optional<User> userOptional = findUserByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String randomStr = UserUtility.generateRandomString();
                String sql = "INSERT INTO password_reset_tokens (email, token) VALUES (?, ?)";;
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, user.getEmail());
                statement.setString(2,randomStr);
                statement.executeUpdate();
                new JMailer().sendEmail("reset Password Email", MailMsg.resetPassword(randomStr,user.getName()), user.getEmail());
            } else {
                System.out.println("User not found with the given email.");
            }
        } catch (Exception e) {
            System.out.println("an error occurred during forget password " + e.getMessage());
        }
    }
    @Override
    public boolean validateToken(String email, String token) {
        try {
            String sql = "SELECT * FROM password_reset_tokens WHERE email = ? AND token = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, token);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("an error occurred during validate token " + e.getMessage());
            return false;
        }
    }
    @Override
    public boolean resetPassword(String email, String newPassword, String token) {
        if (validateToken(email, token)) {
            try {

                String hashedPassword = UserUtility.hashPassword(newPassword);

                String updateSql = "UPDATE users SET password = ? WHERE email = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setString(1, hashedPassword);
                updateStatement.setString(2, email);
                updateStatement.executeUpdate();

                String deleteSql = "DELETE FROM password_reset_tokens WHERE email = ?";
                PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
                deleteStatement.setString(1, email);
                deleteStatement.executeUpdate();

                System.out.println("Password updated successfully.");
                return true;
            } catch (SQLException | NoSuchAlgorithmException e) {
                System.out.println("an error occurred during reset password " + e.getMessage());
            }
        } else {
            System.out.println("Invalid or expired token.");
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
                    System.out.println("password incorrect");
                }
            } else {
                System.out.println("User not found with the given email.");
            }
        } catch (Exception e) {
            System.out.println("an error occurred during login " + e.getMessage());
        }

        return Optional.empty();
    }

}
