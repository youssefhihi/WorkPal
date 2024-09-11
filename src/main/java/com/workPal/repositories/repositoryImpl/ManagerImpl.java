package com.workPal.repositories.repositoryImpl;

import com.workPal.enums.Role;
import com.workPal.model.Manager;
import com.workPal.repositories.inteface.ManagerRepository;
import com.workPal.utility.UserUtility;

import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ManagerImpl implements ManagerRepository {
    private final Connection connection;

    public ManagerImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Boolean save(Manager manager) {
        try {
            String sql = "INSERT INTO managers (name, email, password,role) VALUES (?, ?, ?,?::user_role)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, manager.getName());
            statement.setString(2, manager.getEmail());
            String hashPassword = UserUtility.hashPassword(manager.getPassword());
            statement.setString(3, hashPassword);
            statement.setString(4, Role.manager.name());
            statement.executeUpdate();
            return true;
        } catch (SQLException | NoSuchAlgorithmException e) {
            System.out.println("Error in saving manager in ManagerImpl: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Boolean update(Manager manager) {
        String passwordKey = manager.getPassword().isEmpty() ? "" : ", password = ?";
        try {
            String sql = "UPDATE managers SET name = ?, email = ?" + passwordKey + " WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, manager.getName());
            statement.setString(2, manager.getEmail());
            if (!passwordKey.isEmpty()) {
                String hashedPassword;
                try {
                    hashedPassword = UserUtility.hashPassword(manager.getPassword());
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException("Error hashing the password: " + e.getMessage(), e);
                }
                statement.setString(3, hashedPassword);
                statement.setObject(4, manager.getId());
            } else {
                statement.setObject(3, manager.getId());
            }
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error in updating manager in ManagerImpl: " + e.getMessage());
        }
        return false;
    }



    @Override
    public Boolean delete(UUID id) {
        try {
            String sql = "DELETE FROM managers WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error in deleting manager in ManagerImpl: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Optional<Manager> findManager(String email) {
        try {
            String sql = "SELECT * FROM managers WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
              Manager  manager = new Manager();
                manager.setId(UUID.fromString(rs.getString("id")));
                manager.setName(rs.getString("name"));
                manager.setEmail(rs.getString("email"));
                manager.setPassword(rs.getString("password"));
               return Optional.of(manager);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error in finding manager in ManagerImpl: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Map<UUID, Manager> getAll() {
        Map<UUID, Manager> managers = new HashMap<>();
        try {
            String sql = "SELECT * FROM managers";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Manager manager = new Manager();
                manager.setId(UUID.fromString(rs.getString("id")));
                manager.setName(rs.getString("name"));
                manager.setEmail(rs.getString("email"));
                manager.setPassword(rs.getString("password"));
                managers.put(manager.getId(), manager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return managers;
    }

    @Override
    public Map<UUID, Manager> searchManagers(String query) {
        Map<UUID, Manager> result = new HashMap<>();

        try{
            String sql = "SELECT * FROM managers WHERE name ILIKE ? OR email ILIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + query + "%");
            statement.setString(2, "%" + query + "%");

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Manager manager = new Manager();
                    manager.setId(UUID.fromString(rs.getString("id")));
                    manager.setName(rs.getString("name"));
                    manager.setEmail(rs.getString("email"));
                    result.put(manager.getId(), manager);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in searching managers: " + e.getMessage());
        }

        return result;
    }

}
