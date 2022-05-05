package com.example.doctracermobile.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JointUserProject {
    private String projectName;
    private String description;
    private String startDate;
    private String endDate;

    private String name;        //Имя
    private String surname;     //Фамилия
    private String patronum;    //Отчество
    private String position;    //Должность
    private String phoneNumber;       //Телефон TODO уточнить про название поля (было phone)
    private String email;    //Логин (эл.почта)
    private String password;        //Пароль

    @Override
    public String toString() {
        return "JointUserProject{" +
                ", projectName='" + projectName + '\'' +
                ", description='" + description + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronum='" + patronum + '\'' +
                ", position='" + position + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
