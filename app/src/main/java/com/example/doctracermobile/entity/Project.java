package com.example.doctracermobile.entity;

import java.io.Serializable;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //генерирует конструкторы,геттеры,сеттеры и пр.
@Builder //конструктор для инициализации нескольких полей по цепочке
@AllArgsConstructor //конструктор со всеми полями
@NoArgsConstructor //конструктор по умолчанию
public class Project implements Serializable {

    private String name; //Название проекта
    private String description;
    private Instant startDate;
    private Instant endDate;

    public boolean emptyFieldsCheck(){
        if (name.equals("") ||
                description.equals("") ||
                startDate.equals("") ||
                endDate.equals("")){
            return true;
        }
        return false;
    }

}
