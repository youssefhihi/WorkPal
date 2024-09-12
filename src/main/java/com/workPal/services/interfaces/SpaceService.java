package com.workPal.services.interfaces;

import com.workPal.model.Space;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface SpaceService {

    void addSpace(Space space);

    void updateSpace(Space space);

    void deleteSpace(UUID id);

    Optional<Space> getSpaceByName(String name);

    Map<UUID, Space> getAllSpaces();

    Map<UUID, Space> searchSpaces(String query);
}
