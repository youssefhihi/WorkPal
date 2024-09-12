package com.workPal.repositories.interfaces;
import com.workPal.model.Manager;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ManagerRepository {

    Boolean save(Manager manager);

    Optional<Manager> findManager(String email);

    Map<UUID, Manager> getAll();

    Boolean update(Manager manager);

    Boolean delete(UUID id);

    Map<UUID, Manager> searchManagers(String query);
}