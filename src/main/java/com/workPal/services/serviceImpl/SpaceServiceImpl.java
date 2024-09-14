package com.workPal.services.serviceImpl;

import com.workPal.model.Manager;
import com.workPal.model.Space;
import com.workPal.repositories.interfaces.SpaceRepository;
import com.workPal.repositories.repositoryImpl.SpaceImpl;
import com.workPal.services.interfaces.SpaceService;

import java.sql.Connection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SpaceServiceImpl implements SpaceService {
    private final SpaceRepository spaceRepository;

    public SpaceServiceImpl(Connection connection) {
        this.spaceRepository = new SpaceImpl(connection);
    }

    @Override
    public void addSpace(Space space) {
        Optional<Space> existingSpace = spaceRepository.findSpace(space.getName());
        if (existingSpace.isEmpty()) {
            boolean isAdded = spaceRepository.save(space);
            if (isAdded) {
                System.out.println("✅ Space added successfully!");
            } else {
                System.out.println("❗ Failed to add space. An unexpected error occurred.");
            }
        } else {
            System.out.println("❗ Failed to add space. A space with this name already exists.");
        }
    }

    @Override
    public void updateSpace(Space space,Manager manager) {
        if(space.getManager().getId().equals(manager.getId())){
            boolean isUpdated = spaceRepository.update(space);
            if (isUpdated) {
                System.out.println("✅ Space updated successfully!");
            } else {
                System.out.println("❗ Failed to update space. An unexpected error occurred.");
            }
        }else{
            System.out.println("403, you don't have access to modify this space");
        }
    }

    @Override
    public void deleteSpace(Space space,Manager manager) {
        if(space.getManager().getId().equals(manager.getId())){
            boolean isDeleted = spaceRepository.delete(space.getId());
            if (isDeleted) {
                System.out.println("✅ Space deleted successfully!");
            } else {
                System.out.println("❗ Failed to delete space. An unexpected error occurred.");
            }
        }else{
            System.out.println("403, you don't have access to modify this space");
        }
    }

    @Override
   public Map<UUID, Space> getManagerSpaces(Manager manager){
        return spaceRepository.getManagerSpaces(manager);
    }

    @Override
    public Optional<Space> getSpaceByName(String name) {
        return spaceRepository.findSpace(name);
    }

    @Override
    public Map<UUID, Space> getAllSpaces() {
        return spaceRepository.getAll();
    }

    @Override
    public Map<UUID, Space> searchSpaces(String query) {
        return spaceRepository.searchSpaces(query);
    }

}
