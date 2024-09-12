// File: com/workPal/services/interfaces/TypeService.java
package com.workPal.services.interfaces;

import com.workPal.model.Type;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface for Type service, defining methods for CRUD operations on Type entities.
 */
public interface TypeService {

    /**
     * Adds a new Type.
     *
     * @param type The Type object to be added.
     */
    void addType(Type type);

    /**
     * Updates an existing Type.
     *
     * @param type The Type object with updated information.
     */
    void updateType(Type type);

    /**
     * Deletes a Type by its ID.
     *
     * @param id The UUID of the Type to be deleted.
     */
    void deleteType(UUID id);

    /**
     * Retrieves a Type by its ID.
     *
     * @param id The UUID of the Type to retrieve.
     * @return An Optional containing the Type if found, or empty if not.
     */
    Optional<Type> getTypeById(UUID id);

    /**
     * Retrieves all Types.
     *
     * @return A list of all Types.
     */
    List<Type> getAllTypes();
}
