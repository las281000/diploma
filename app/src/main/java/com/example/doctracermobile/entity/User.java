package com.example.doctracermobile.entity;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L; //Чтоб передавать в интентах

    private String name;        //Имя
    private String surname;     //Фамилия
    private String patronum;    //Отчество
    private String position;    //Должность
    private String phone;       //Телефон
    private String email;    //Логин (эл.почта)
    private String pass;        //Пароль

    public User (String name,
                 String surname,
                 String patronum,
                 String position,
                 String phone,
                 String email,
                 String pass){

        this.name = name;
        this.surname = surname;
        this.patronum = patronum;
        this.position = position;
        this.phone = phone;
        this.email = email;
        this.pass = pass;
    }

    public String getName(){
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getPatronum() {
        return patronum;
    }
    public String getPosition() {
        return position;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }
    public String getPass() {
        return pass;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setPatronum(String patronum) {
        this.patronum = patronum;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }

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
