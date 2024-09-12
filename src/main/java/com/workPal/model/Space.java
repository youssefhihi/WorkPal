package com.workPal.model;

import java.util.UUID;

public class Space {
    private UUID id;
    private String name;
    private String description;
    private  Type type;
    private String location;
    private Integer capacity;


    public Space(String name, String description, Integer capacity, String location,  Type type) {
        this.capacity = capacity;
        this.description = description;
        this.name = name;
        this.type = type;
        this.location = location;
    }

    public Space(){}

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }


}


