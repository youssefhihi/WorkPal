package com.workPal.services.interfaces;

import com.workPal.model.Manager;
import java.util.Map;
import java.util.UUID;

public interface ManagerService {

    void addManager(Manager manager);

    void updateManager(Manager manager);

    void deleteManager(UUID id);

    Manager getManagerByEmail(String email);

    Map<UUID, Manager> getAllManagers();
}
