package com.workPal.services.serviceImpl;

import com.workPal.model.Manager;
import com.workPal.repositories.inteface.ManagerRepository;
import com.workPal.repositories.repositoryImpl.ManagerImpl;
import com.workPal.services.interfaces.ManagerService;
import java.sql.Connection;
import java.util.Map;
import java.util.UUID;

public class ManagerServiceImpl implements ManagerService {
    private final ManagerRepository managerRepository;

    public ManagerServiceImpl(Connection connection) {
        this.managerRepository = new ManagerImpl(connection);
    }

    @Override
    public void addManager(Manager manager) {
        try {
            managerRepository.save(manager);
        } catch (Exception e) {
            System.out.println("Error adding manager: " + e.getMessage());
        }
    }

    @Override
    public void updateManager(Manager manager) {
        try {
            managerRepository.update(manager);
        } catch (Exception e) {
            System.out.println("Error updating manager: " + e.getMessage());
        }
    }

    @Override
    public void deleteManager(UUID id) {
        try {
            managerRepository.delete(id);
        } catch (Exception e) {
            System.out.println("Error deleting manager: " + e.getMessage());
        }
    }

    @Override
    public Manager getManagerByEmail(String email) {
        return managerRepository.findManager(email);
    }

    @Override
    public Map<UUID, Manager> getAllManagers() {
        return managerRepository.getAll();
    }
}