package com.example.doctracermobile.utile;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum TaskPriority {
    HIGH (1, "Высокий"),
    MEDIUM (2, "Средний"),
    LOW (3, "Низкий");

    private final int sortingPriority;
    private final String description;

    @Override
    public String toString() {
        return description;
    }
}
