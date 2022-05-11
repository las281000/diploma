package com.example.doctracermobile.entity;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private String name;
    private String idea;
    private Instant creationDate;
    private Instant deadline;
}
