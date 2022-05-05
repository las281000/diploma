package com.example.doctracermobile.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = 1L; //Чтоб передавать в интентах

    private String name;        //Имя
    private String surname;     //Фамилия
    private String patronum;    //Отчество
    private String position;    //Должность
    private String phone;       //Телефон
    private String email;    //Логин (эл.почта)
    private String pass;        //Пароль

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronum='" + patronum + '\'' +
                ", position='" + position + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }

   public boolean emptyFieldCheck(){
        if (this.getName().equals("") ||
                this.getSurname().equals("") ||
                this.getPatronum().equals("") ||
                this.getPosition().equals("") ||
                this.getPhone().equals("+7") ||
                this.getPhone().equals("") ||
                this.getEmail().equals("") ||
                this.getPass().equals("")) {
            return true;
        }
        return false;
    }
}
