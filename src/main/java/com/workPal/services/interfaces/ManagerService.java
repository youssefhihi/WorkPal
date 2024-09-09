package com.workPal.services.interfaces;

import com.workPal.model.Manager;
import java.util.Map;

public interface ManagerService {

    void addManager(Manager manager);

    void updateManager(Manager manager);

    void deleteManager(String email);

    Manager getManagerByEmail(String email);

    Map<Integer, Manager> getAllManagers();
}
