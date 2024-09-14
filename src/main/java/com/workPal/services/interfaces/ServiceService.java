package com.workPal.services.interfaces;

import com.workPal.model.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceService {
    void addService(Service service);
    void updateService(Service service);
    void deleteService(UUID id);
    Optional<Service> getServiceByName(String name);
    List<Service> getAllServices();
}
