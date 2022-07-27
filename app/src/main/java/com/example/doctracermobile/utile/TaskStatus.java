package com.example.doctracermobile.utile;

public enum TaskStatus {
    CREATED("ОТКРЫТО"),
    CLOSED("ЗАКРЫТО");

    private String description;

    TaskStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

}
