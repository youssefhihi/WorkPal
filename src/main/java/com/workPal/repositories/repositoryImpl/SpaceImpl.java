package com.workPal.repositories.repositoryImpl;

import com.workPal.model.Space;
import com.workPal.model.Type;
import com.workPal.repositories.interfaces.SpaceRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SpaceImpl implements SpaceRepository {
    private final Connection connection;

    public SpaceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Boolean save(Space space) {
        try {
            String sql = "INSERT INTO spaces (name, description, type_id, location, capacity) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, space.getName());
            statement.setString(2, space.getDescription());
            statement.setObject(3, space.getType().getId());
            statement.setString(4, space.getLocation());
            statement.setInt(5, space.getCapacity());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error in saving space in SpaceImpl: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Optional<Space> findSpace(String name) {
        try {
            String sql = """
                    SELECT s.*, t.id AS type_id, t.name AS type_name
                    FROM spaces s
                    JOIN types t ON s.type_id = t.id
                    WHERE s.name = ?
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Space space = mapResultSetToSpace(rs);
                return Optional.of(space);
            }
        } catch (SQLException e) {
            System.out.println("Error in finding space in SpaceImpl: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Map<UUID, Space> getAll() {
        Map<UUID, Space> spaces = new HashMap<>();
        try {
            String sql = """
                    SELECT s.*, t.id AS type_id, t.name AS type_name
                    FROM spaces s
                    JOIN types t ON s.type_id = t.id
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Space space = mapResultSetToSpace(rs);
                spaces.put(space.getId(), space);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spaces;
    }

    @Override
    public Boolean update(Space space) {
        try {
            String sql = "UPDATE spaces SET name = ?, description = ?, type_id = ?, location = ?, capacity = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, space.getName());
            statement.setString(2, space.getDescription());
            statement.setObject(3, space.getType().getId());
            statement.setString(4, space.getLocation());
            statement.setInt(5, space.getCapacity());
            statement.setObject(6, space.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error in updating space in SpaceImpl: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Boolean delete(UUID id) {
        try {
            String sql = "DELETE FROM spaces WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error in deleting space in SpaceImpl: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Map<UUID, Space> searchSpaces(String query) {
        Map<UUID, Space> result = new HashMap<>();
        try {
            String sql = """
                    SELECT s.*, t.id AS type_id, t.name AS type_name
                    FROM spaces s
                    JOIN types t ON s.type_id = t.id
                    WHERE s.name ILIKE ? OR s.description ILIKE ?
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + query + "%");
            statement.setString(2, "%" + query + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Space space = mapResultSetToSpace(rs);
                result.put(space.getId(), space);
            }
        } catch (SQLException e) {
            System.out.println("Error in searching spaces: " + e.getMessage());
        }
        return result;
    }

    private Space mapResultSetToSpace(ResultSet rs) throws SQLException {
        Space space = new Space();
        space.setId(UUID.fromString(rs.getString("id")));
        space.setName(rs.getString("name"));
        space.setDescription(rs.getString("description"));

        // Map the type from the result set
        Type type = new Type();
        type.setId(UUID.fromString(rs.getString("type_id")));
        type.setName(rs.getString("type_name"));

        space.setType(type);
        space.setLocation(rs.getString("location"));
        space.setCapacity(rs.getInt("capacity"));
        return space;
    }
}

