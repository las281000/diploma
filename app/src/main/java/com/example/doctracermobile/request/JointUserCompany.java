package com.example.doctracermobile.request;


public class JointUserCompany {
    private String companyName;
    private String type;
    private String country;
    private String address;
    private String inn;
    private String ogrn;

    private String name;        //Имя
    private String surname;     //Фамилия
    private String patronum;    //Отчество
    private String position;    //Должность
    private String phoneNumber;       //Телефон TODO уточнить про название поля (было phone)
    private String email;    //Логин (эл.почта)
    private String password;        //Пароль


    public JointUserCompany(String companyName, String type, String country, String address,
                            String inn, String ogrn, String name, String surname, String patronum,
                            String position, String phoneNumber, String email, String password) {
        this.companyName = companyName;
        this.type = type;
        this.country = country;
        this.address = address;
        this.inn = inn;
        this.ogrn = ogrn;
        this.name = name;
        this.surname = surname;
        this.patronum = patronum;
        this.position = position;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getType() {
        return type;
    }

    public String getCountry() {
        return country;
    }

    public String getAddress() {
        return address;
    }

    public String getInn() {
        return inn;
    }

    public String getOgrn() {
        return ogrn;
    }

    public String getName() {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "JointUserCompany{" +
                "companyName='" + companyName + '\'' +
                ", type='" + type + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", inn='" + inn + '\'' +
                ", ogrn='" + ogrn + '\'' +
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
