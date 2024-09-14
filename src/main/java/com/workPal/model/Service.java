package com.workPal.model;

import java.util.UUID;

public class Service {
    UUID id;
    String name;
    Integer price;

    public Service(String name, Integer price) {
        this.name = name;
        this.price = price;
    }
    public Service(){}

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
