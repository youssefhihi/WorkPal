package com.workPal.repositories.inteface;
import com.workPal.model.Manager;
import java.util.Map;

public interface ManagerRepository {

    void save(Manager manager);
    Manager findManager(String email);
    Map<Integer, Manager> getAll();
    void update(Manager manager);
    void delete(String email);
}
