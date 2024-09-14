package com.workPal.services.interfaces;

import com.workPal.model.Manager;
import com.workPal.model.Space;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface SpaceService {

    void addSpace(Space space);

    void updateSpace(Space space, Manager manager);

    void deleteSpace(Space space, Manager manager);

    Optional<Space> getSpaceByName(String name);

    Map<UUID, Space> getAllSpaces();

    Map<UUID, Space> searchSpaces(String query);

    Map<UUID, Space> getManagerSpaces(Manager manager);
}
