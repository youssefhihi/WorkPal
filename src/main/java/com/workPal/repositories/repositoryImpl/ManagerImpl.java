package com.workPal.repositories.repositoryImpl;

import com.workPal.model.Manager;
import com.workPal.repositories.inteface.ManagerRepository;
import com.workPal.utility.UserUtility;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ManagerImpl implements ManagerRepository {
    private final Connection connection;

    public ManagerImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Manager manager) {
        try {
            String sql = "INSERT INTO managers (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, manager.getName());
            statement.setString(2, manager.getEmail());
            String hashPassword = UserUtility.hashPassword(manager.getPassword());
            statement.setString(3, hashPassword);
            statement.executeUpdate();
        } catch (SQLException | NoSuchAlgorithmException e) {
            System.out.println("Error in saving manager in ManagerImpl: " + e.getMessage());
        }
    }

    @Override
    public void update(Manager manager) {
        try {
            String sql = "UPDATE managers SET name = ?, email = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, manager.getName());
            statement.setString(2, manager.getEmail());
            statement.setString(3, manager.getId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error in updating manager in ManagerImpl: " + e.getMessage());
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            String sql = "DELETE FROM managers WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error in deleting manager in ManagerImpl: " + e.getMessage());
        }
    }

    @Override
    public Manager findManager(String email) {
        Manager manager = null;
        try {
            String sql = "SELECT * FROM managers WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                manager = new Manager();
                manager.setId(UUID.fromString(rs.getString("id")));
                manager.setName(rs.getString("name"));
                manager.setEmail(rs.getString("email"));
                manager.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error in finding manager in ManagerImpl: " + e.getMessage());
        }
        return manager;
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
}
