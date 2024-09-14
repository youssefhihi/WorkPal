package com.workPal.repositories.repositoryImpl;

import com.workPal.model.Manager;
import com.workPal.model.Service;
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
            // Save space data
            String sql = "INSERT INTO spaces (name, description, type_id, location, capacity,manager_id) VALUES (?, ?, ?, ?, ?,?) RETURNING id";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, space.getName());
            statement.setString(2, space.getDescription());
            statement.setObject(3, space.getType().getId());
            statement.setString(4, space.getLocation());
            statement.setInt(5, space.getCapacity());
            statement.setObject(6, space.getManager().getId());

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                UUID spaceId = UUID.fromString(rs.getString("id"));
                space.setId(spaceId);
            } else {
                return false;
            }

            String pivotSql = "INSERT INTO space_service (space_id, service_id) VALUES (?, ?)";
            PreparedStatement pivotStatement = connection.prepareStatement(pivotSql);

            for (UUID serviceId : space.getServices().keySet()) {
                pivotStatement.setObject(1, space.getId());
                pivotStatement.setObject(2, serviceId);
                pivotStatement.addBatch();
            }

            pivotStatement.executeBatch();

            return true;

        } catch (SQLException e) {
            System.out.println("Error in saving space with services in SpaceImpl: " + e.getMessage());
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
                space.setServices(getServicesForSpace(space.getId()));
                return Optional.of(space);
            }
        } catch (SQLException e) {
            System.out.println("Error in finding space in SpaceImpl: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Space> findSpaceByID(UUID id) {
        try {
            String sql = """
                SELECT s.*, t.id AS type_id, t.name AS type_name
                FROM spaces s
                JOIN types t ON s.type_id = t.id
                WHERE s.id = ?
                """;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Space space = mapResultSetToSpace(rs);

                // Retrieve associated services for this space
                space.setServices(getServicesForSpace(space.getId()));

                return Optional.of(space);
            }
        } catch (SQLException e) {
            System.out.println("Error in finding space by ID in SpaceImpl: " + e.getMessage());
        }
        return Optional.empty();
    }


    @Override
    public Map<UUID, Space> getAll() {
        Map<UUID, Space> spaces = new HashMap<>();
        try {
            String sql = """
                SELECT s.*, t.id AS type_id, t.name AS type_name
                managers.name AS manager_name , managers.email
                FROM spaces s
                JOIN types t ON s.type_id = t.id
                JOIN managers s.manager_id = managers.id
                """;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Space space = mapResultSetToSpace(rs);
                // Retrieve associated services for this space
                space.setServices(getServicesForSpace(space.getId()));
                spaces.put(space.getId(), space);
            }
        } catch (SQLException e) {
            System.out.println("Error in getting all spaces: " + e.getMessage());
        }
        return spaces;
    }

    @Override
    public Map<UUID, Space> getManagerSpaces(Manager manager) {
        Map<UUID, Space> spaces = new HashMap<>();
        try {
            String sql = """
                SELECT s.*, t.id AS type_id, t.name AS type_name
                managers.name AS manager_name , managers.email
                FROM spaces s
                JOIN types t ON s.type_id = t.id
                JOIN managers s.manager_id = managers.id WHERE manager_id = ?
                """;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, manager.getId());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Space space = mapResultSetToSpace(rs);
                // Retrieve associated services for this space
                space.setServices(getServicesForSpace(space.getId()));
                spaces.put(space.getId(), space);
            }
        } catch (SQLException e) {
            System.out.println("Error in getting all spaces: " + e.getMessage());
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

            clearServicesForSpace(space.getId());

            insertServicesForSpace(space);

            return true;
        } catch (SQLException e) {
            System.out.println("Error in updating space in SpaceImpl: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Boolean delete(UUID id) {
        try {
            clearServicesForSpace(id);

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
                managers.name AS manager_name , managers.email
                FROM spaces s
                JOIN types t ON s.type_id = t.id
                JOIN managers s.manager_id = managers.id
                WHERE s.name ILIKE ? OR s.description ILIKE ?
                """;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + query + "%");
            statement.setString(2, "%" + query + "%");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Space space = mapResultSetToSpace(rs);
                // Retrieve associated services for this space
                space.setServices(getServicesForSpace(space.getId()));
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
        space.setLocation(rs.getString("location"));
        space.setCapacity(rs.getInt("capacity"));

        Type type = new Type();
        type.setId(UUID.fromString(rs.getString("type_id")));
        type.setName(rs.getString("type_name"));

        Manager manager = new Manager();
        manager.setName("manager_name");
        manager.setEmail("email");

        space.setType(type);
        space.setManager(manager);

        return space;
    }





    private void insertServicesForSpace(Space space) throws SQLException {
        String pivotSql = "INSERT INTO space_service (space_id, service_id) VALUES (?, ?)";
        PreparedStatement pivotStatement = connection.prepareStatement(pivotSql);

        for (UUID serviceId : space.getServices().keySet()) {
            pivotStatement.setObject(1, space.getId());
            pivotStatement.setObject(2, serviceId);
            pivotStatement.addBatch();
        }

        // Execute the batch insert for space_service table
        pivotStatement.executeBatch();
    }
    private void clearServicesForSpace(UUID spaceId) throws SQLException {
        String sql = "DELETE FROM space_service WHERE space_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setObject(1, spaceId);
        statement.executeUpdate();
    }

    private Map<UUID, Service> getServicesForSpace(UUID spaceId) throws SQLException {
        Map<UUID, Service> services = new HashMap<>();

        String sql = """
            SELECT asv.*
            FROM space_service ss
            JOIN services asv ON ss.service_id = asv.id
            WHERE ss.space_id = ?
            """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setObject(1, spaceId);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Service service = new Service();
            service.setId(UUID.fromString(rs.getString("id")));
            service.setName(rs.getString("name"));
            services.put(service.getId(), service);
        }

        return services;
    }


}

