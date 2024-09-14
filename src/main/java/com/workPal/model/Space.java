package com.workPal.model;

import java.util.Map;
import java.util.UUID;

public class Space {
    private UUID id;
    private String name;
    private String description;
    private  Type type;
    private String location;
    private Integer capacity;
    private Manager manager;
    private Map<UUID, Service> services;

    public Space(String name, String description, Type type, String location, Integer capacity, Manager manager, Map<UUID, Service> services) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.location = location;
        this.capacity = capacity;
        this.manager = manager;
        this.services = services;
    }
    public  Space(){}

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Map<UUID, Service> getServices() {
        return services;
    }

    public void setServices(Map<UUID, Service> services) {
        this.services = services;
    }
}


