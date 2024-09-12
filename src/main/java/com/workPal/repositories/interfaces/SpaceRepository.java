package com.workPal.repositories.interfaces;

import com.workPal.model.Space;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface SpaceRepository {
        Boolean save(Space space);

        Optional<Space> findSpace(String name);
        Map<UUID, Space> getAll();
        Boolean update(Space space);
        Boolean delete(UUID id);
        Map<UUID, Space> searchSpaces(String query);
}
