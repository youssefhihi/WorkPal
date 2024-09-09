package com.workPal.controller;

import com.workPal.model.Manager;
import com.workPal.services.interfaces.ManagerService;

import java.util.Map;

public class ManagerController {
    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    public void addManager(Manager manager) {
        managerService.addManager(manager);
    }

    public void updateManager(Manager manager) {
        managerService.updateManager(manager);
    }

    public void deleteManager(String email) {
        managerService.deleteManager(email);
    }

    public Manager getManagerByEmail(String email) {
        return managerService.getManagerByEmail(email);
    }

    public Map<Integer, Manager> getAllManagers() {
        return managerService.getAllManagers();
    }
}
