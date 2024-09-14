package com.workPal.repositories.interfaces;

import com.workPal.model.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository {
    boolean save(Service additionalService);
    boolean update(Service additionalService);
    boolean delete(UUID id);
    Optional<Service> findByName(String name) ;
    List<Service> getAll();
}
