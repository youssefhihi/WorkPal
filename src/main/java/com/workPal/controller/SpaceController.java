package com.workPal.controller;

import com.workPal.connectDB.DatabaseConnection;
import com.workPal.model.Space;
import com.workPal.services.interfaces.SpaceService;
import com.workPal.services.serviceImpl.SpaceServiceImpl;

import java.sql.Connection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SpaceController {
    private static final Connection connection = DatabaseConnection.connect();
    private final SpaceService spaceService = new SpaceServiceImpl(connection);

    public void addSpace(Space space) {
        spaceService.addSpace(space);
    }

    public void updateSpace(Space space) {
        spaceService.updateSpace(space);
    }

    public void deleteSpace(UUID id) {
        spaceService.deleteSpace(id);
    }

    public Optional<Space> getSpaceByName(String name) {
        return spaceService.getSpaceByName(name);
    }

    public Map<UUID, Space> getAllSpaces() {
        return spaceService.getAllSpaces();
    }

    public Map<UUID, Space> searchSpaces(String query) {
        return spaceService.searchSpaces(query);
    }
}
