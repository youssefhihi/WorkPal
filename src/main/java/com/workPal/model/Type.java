package com.workPal.model;

import java.util.UUID;

public class Type {

        private UUID id;
        private String name;

        public Type(UUID id, String name) {
            this.id = id;
            this.name = name;
        }

        public Type() {}

        // Getters and Setters
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

}


