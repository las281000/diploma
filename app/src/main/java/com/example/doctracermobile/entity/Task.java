package com.example.doctracermobile.entity;

import com.example.doctracermobile.utile.TaskPriority;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private String name; //название задачи
    private String idea; //краткое описание
    private Instant creationDate; //дата создания
    private Instant deadline; //дедлайн
    private Employee responsible; //сотрудник, отвественный за выполнение
    private TaskPriority taskPriority;

    public boolean emptyFieldCheck() {
        if (name.equals("") ||
                idea.equals("") ||
                (creationDate == null) ||
                (deadline == null) ||
                (responsible == null)||
                (taskPriority == null)) {
            return false;
        } else {
            return true;
        }
    }
}
