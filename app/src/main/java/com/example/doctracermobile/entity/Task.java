package com.example.doctracermobile.entity;

import com.example.doctracermobile.utile.TaskPriority;
import com.example.doctracermobile.utile.TaskStatus;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    private String name; //название задачи
    private String idea; //краткое описание
    private Instant creationDate; //дата создания
    private Instant deadline; //дедлайн
    private Employee responsible; //сотрудник, отвественный за выполнение
    private Employee creator;
    private TaskPriority priority;
    private TaskStatus status;
    private Long id;

    public boolean emptyFieldCheck() {
        if (name.equals("") ||
                idea.equals("") ||
                (creationDate == null) ||
                (deadline == null) ||
                (responsible == null)||
                (priority == null)) {
            return false;
        } else {
            return true;
        }
    }
}
