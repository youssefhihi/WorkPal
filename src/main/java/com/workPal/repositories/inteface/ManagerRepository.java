package com.workPal.repositories.inteface;
import com.workPal.model.Manager;
import java.util.Map;
import java.util.UUID;

public interface ManagerRepository {

    void save(Manager manager);
    Manager findManager(String email);
    Map<UUID, Manager> getAll();
    void update(Manager manager);
    void delete(UUID id);
}
