// File: com/workPal/repositories/interfaces/TypeRepository.java
package com.workPal.repositories.interfaces;

import com.workPal.model.Type;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TypeRepository {
    boolean addType(Type type);

    boolean updateType(Type type);

    boolean deleteType(UUID id);

    Optional<Type> getTypeById(UUID id);

    List<Type> getAllTypes();
}
