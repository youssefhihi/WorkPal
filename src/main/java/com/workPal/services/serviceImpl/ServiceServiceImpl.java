package com.workPal.services.serviceImpl;

import com.workPal.model.Service;
import com.workPal.repositories.interfaces.ServiceRepository;
import com.workPal.repositories.repositoryImpl.ServiceImpl;
import com.workPal.services.interfaces.ServiceService;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository additionalServiceRepository;

    public ServiceServiceImpl(Connection connection) {
        this.additionalServiceRepository = new ServiceImpl(connection);
    }

    @Override
    public void addService(Service service) {
        boolean isAdded = additionalServiceRepository.save(service);
        if (isAdded) {
            System.out.println("✅ Service added successfully!");
        } else {
            System.out.println("❗ Failed to add service. An unexpected error occurred.");
        }
    }

    @Override
    public void updateService(Service service) {
        boolean isUpdated = additionalServiceRepository.update(service);
        if (isUpdated) {
            System.out.println("✅ Service updated successfully!");
        } else {
            System.out.println("❗ Failed to update service. An unexpected error occurred.");
        }
    }

    @Override
    public void deleteService(UUID id) {
        boolean isDeleted = additionalServiceRepository.delete(id);
        if (isDeleted) {
            System.out.println("✅ Service deleted successfully!");
        } else {
            System.out.println("❗ Failed to delete service. An unexpected error occurred.");
        }
    }

    @Override
    public Optional<Service> getServiceByName(String name) {
        return additionalServiceRepository.findByName(name);
    }

    @Override
    public List<Service> getAllServices() {
        return additionalServiceRepository.getAll();
    }
}
