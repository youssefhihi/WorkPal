// File: com/workPal/services/serviceImpl/TypeServiceImpl.java
package com.workPal.services.serviceImpl;

import com.workPal.model.Type;
import com.workPal.repositories.interfaces.TypeRepository;
import com.workPal.repositories.repositoryImpl.TypeImpl;
import com.workPal.services.interfaces.TypeService;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;

    public TypeServiceImpl(Connection connection) {
        this.typeRepository = new TypeImpl(connection);
    }

    @Override
    public void addType(Type type) {
        boolean isAdded = typeRepository.addType(type);
        if (isAdded) {
            System.out.println("✅ Type added successfully!");
        } else {
            System.out.println("❗ Failed to add type. An unexpected error occurred.");
        }
    }

    @Override
    public void updateType(Type type) {
        boolean isUpdated = typeRepository.updateType(type);
        if (isUpdated) {
            System.out.println("✅ Type updated successfully!");
        } else {
            System.out.println("❗ Failed to update type. An unexpected error occurred.");
        }
    }

    @Override
    public void deleteType(UUID id) {
        boolean isDeleted = typeRepository.deleteType(id);
        if (isDeleted) {
            System.out.println("✅ Type deleted successfully!");
        } else {
            System.out.println("❗ Failed to delete type. An unexpected error occurred.");
        }
    }

    @Override
    public Optional<Type> getTypeById(UUID id) {
        return typeRepository.getTypeById(id);
    }

    @Override
    public List<Type> getAllTypes() {
        return typeRepository.getAllTypes();
    }
}
