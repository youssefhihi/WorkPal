package com.workPal.repositories.repositoryImpl;

import com.workPal.model.Service;
import com.workPal.repositories.interfaces.ServiceRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ServiceImpl implements ServiceRepository {
    private final Connection connection;

    public ServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Service additionalService) {
        String query = "INSERT INTO services (id, name, price) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, additionalService.getId());
            statement.setString(2, additionalService.getName());
            statement.setInt(3, additionalService.getPrice());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Service additionalService) {
        String query = "UPDATE services SET name = ?, price = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, additionalService.getName());
            statement.setInt(2, additionalService.getPrice());
            statement.setObject(3, additionalService.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(UUID id) {
        String query = "DELETE FROM services WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Service> findByName(String name) {
        String query = "SELECT * FROM additional_services WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Service service = new Service();
                       service.setId(UUID.fromString(resultSet.getString("id")));
                        service.setName(resultSet.getString("name"));
                        service.setPrice(resultSet.getInt("price"));

                return Optional.of(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Service> getAll() {
        String query = "SELECT * FROM services";
        List<Service> services = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Service service = new Service(
                        resultSet.getString("name"),
                        resultSet.getInt("price")
                );
                service.setId(UUID.fromString(resultSet.getString("id")));
                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }
}
