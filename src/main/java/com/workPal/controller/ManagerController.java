package com.workPal.controller;

import com.workPal.connectDB.DatabaseConnection;
import com.workPal.model.Manager;
import com.workPal.services.interfaces.ManagerService;
import com.workPal.services.interfaces.MemberService;
import com.workPal.services.serviceImpl.ManagerServiceImpl;
import com.workPal.services.serviceImpl.MemberServiceImpl;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ManagerController {

    private static final Connection connection = DatabaseConnection.connect();

    private final ManagerService managerService =new ManagerServiceImpl(connection);

    public void addManager(Manager manager) {
        managerService.addManager(manager);
    }

    public void updateManager(Manager manager) {
        managerService.updateManager(manager);
    }

    public void deleteManager(UUID id) {
        managerService.deleteManager(id);
    }

    public Optional<Manager> getManagerByEmail(String email) {
        return managerService.getManagerByEmail(email);
    }

    public Map<UUID, Manager> getAllManagers() {
        return managerService.getAllManagers();
    }

    public  Map<UUID, Manager> searchManagers(String query){
        return managerService.searchManagers(query);
    }

}
