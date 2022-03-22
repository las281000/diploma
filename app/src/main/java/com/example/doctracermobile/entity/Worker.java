package com.example.doctracermobile.entity;

public class Worker {
    private String name;        //Имя
    private String surname;     //Фамилия
    private String patronum;    //Отчество
    private String position;    //Должность
    private String phone;       //Телефон
    private String logEmail;    //Логин (эл.почта)

    public Worker (String name,
                 String surname,
                 String patronum,
                 String position,
                 String phone,
                 String logEmail){

        this.name = name;
        this.surname = surname;
        this.patronum = patronum;
        this.position = position;
        this.phone = phone;
        this.logEmail = logEmail;
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
    public String getLogEmail() {
        return logEmail;
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
    public void setLogEmail(String logEmail) {
        this.logEmail = logEmail;
    }

    @Override
    public String toString(){
        return surname + " " + name.charAt(0)+ "." + patronum.charAt(0)+"., " + position.toLowerCase();
    }
}
