package com.example.doctracermobile.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    private String name;        //Имя
    private String surname;     //Фамилия
    private String patronum;    //Отчество
    private String position;    //Должность
    private String phone;       //Телефон
    private String email;    //Логин (эл.почта)

    @Override
    public String toString(){
        return surname + " " + name.charAt(0)+ "." + patronum.charAt(0)+"., " + position.toLowerCase();
    }
}
