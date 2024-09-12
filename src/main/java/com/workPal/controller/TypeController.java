// File: com/workPal/controller/TypeController.java
package com.workPal.controller;

import com.workPal.connectDB.DatabaseConnection;
import com.workPal.model.Type;
import com.workPal.services.interfaces.TypeService;
import com.workPal.services.serviceImpl.TypeServiceImpl;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TypeController {
    private static final Connection connection = DatabaseConnection.connect();
    private final TypeService typeService = new TypeServiceImpl(connection);

    public void addType(Type type) {
        typeService.addType(type);
    }

    public void updateType(Type type) {
        typeService.updateType(type);
    }

    public void deleteType(UUID id) {
        typeService.deleteType(id);
    }

    public Optional<Type> getTypeById(UUID id) {
        return typeService.getTypeById(id);
    }

    public List<Type> getAllTypes() {
        return typeService.getAllTypes();
    }
}
