package com.workPal.services.serviceImpl;

import com.workPal.model.Manager;
import com.workPal.repositories.inteface.ManagerRepository;
import com.workPal.repositories.repositoryImpl.ManagerImpl;
import com.workPal.services.interfaces.ManagerService;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ManagerServiceImpl implements ManagerService {
    private final ManagerRepository managerRepository;

    public ManagerServiceImpl(Connection connection) {
        this.managerRepository = new ManagerImpl(connection);
    }

    @Override
    public void addManager(Manager manager) {
        Optional<Manager> existingManager = managerRepository.findManager(manager.getEmail());
        if (existingManager.isEmpty()) {
            boolean isAdded = managerRepository.save(manager);
            if (isAdded) {
                System.out.println("✅ Manager added successfully!");
            } else {
                System.out.println("❗ Failed to add manager. An unexpected error occurred.");
            }
        } else {
            System.out.println("❗ Failed to add manager. The email is already in use.");
        }
    }


    @Override
    public void updateManager(Manager manager){
            Boolean isUpdated = managerRepository.update(manager);
            if (isUpdated) {
                System.out.println("✅ Manager Updated successfully!");
            } else {
                System.out.println("❗ Failed to update manager. An unexpected error occurred.");
            }
    }

    @Override
    public void deleteManager(UUID id) {
         Boolean isDeleted =   managerRepository.delete(id);
        if (isDeleted) {
            System.out.println("✅ Manager Deleted successfully!");
        } else {
            System.out.println("❗ Failed to Deleted manager. An unexpected error occurred.");
        }
    }

    @Override
    public Optional<Manager> getManagerByEmail(String email) {
        return managerRepository.findManager(email);

    }

    @Override
    public Map<UUID, Manager> getAllManagers() {
        return managerRepository.getAll();
    }

    @Override
    public Map<UUID, Manager> searchManagers(String query){
        return  managerRepository.searchManagers(query);

    }

}