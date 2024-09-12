// File: com/workPal/repositories/repositoryImpl/TypeRepositoryImpl.java
package com.workPal.repositories.repositoryImpl;

import com.workPal.model.Type;
import com.workPal.repositories.interfaces.TypeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TypeImpl implements TypeRepository {
    private final Connection connection;

    public TypeImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean addType(Type type) {
        String query = "INSERT INTO types (id, name) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, type.getId());
            stmt.setString(2, type.getName());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateType(Type type) {
        String query = "UPDATE types SET name = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, type.getName());
            stmt.setObject(2, type.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteType(UUID id) {
        String query = "DELETE FROM types WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Type> getTypeById(UUID id) {
        String query = "SELECT * FROM types WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Type type = new Type(UUID.fromString(rs.getString("id")), rs.getString("name"));
                return Optional.of(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Type> getAllTypes() {
        List<Type> types = new ArrayList<>();
        String query = "SELECT * FROM types";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Type type = new Type(UUID.fromString(rs.getString("id")), rs.getString("name"));
                types.add(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return types;
    }
}
