package com.example.doctracermobile.request;

import com.example.doctracermobile.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskRequest {
    String name;
    String idea;
    String creation;
    String deadline;
    Employee responsible;
}
