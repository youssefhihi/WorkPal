package com.workPal.services.interfaces;

import com.workPal.model.Manager;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ManagerService {

    void addManager(Manager manager);

    void updateManager(Manager manager);

    void deleteManager(UUID id);

    Optional<Manager> getManagerByEmail(String email);

    Map<UUID, Manager> getAllManagers();

    Map<UUID, Manager> searchManagers(String query);

}
