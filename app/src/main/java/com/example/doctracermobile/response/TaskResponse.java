package com.example.doctracermobile.response;

import com.example.doctracermobile.entity.Employee;
import com.example.doctracermobile.utile.TaskPriority;
import com.example.doctracermobile.utile.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
        private String name; //название задачи
        private String idea; //краткое описание
        private String creation; //дата создания
        private String deadline; //дедлайн
        private Employee responsible; //сотрудник, отвественный за выполнение
        private Employee creator;
        private TaskPriority priority;
        private TaskStatus status;
        private Long id;
}
