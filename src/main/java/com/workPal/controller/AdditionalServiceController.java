package com.workPal.controller;

import com.workPal.connectDB.DatabaseConnection;
import com.workPal.model.Service;
import com.workPal.services.interfaces.ServiceService;
import com.workPal.services.serviceImpl.ServiceServiceImpl;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AdditionalServiceController {

    private static final Connection connection = DatabaseConnection.connect();
    private final ServiceService service = new ServiceServiceImpl(connection);

    public void addService(Service additionalService) {
        service.addService(additionalService);
    }

    public void updateService(Service additionalService) {
        service.updateService(additionalService);
    }

    public void deleteService(UUID id) {
        service.deleteService(id);
    }

    public Optional<Service> getServiceByName(String name) {
        return service.getServiceByName(name);
    }

    public List<Service> getAllServices() {
        return service.getAllServices();
    }

}
