package com.example.doctracermobile.entity;

import com.example.doctracermobile.util.CustomAuthenticator;

import java.io.Serializable;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Company implements Serializable {


    private String name;
    private String type;
    private String country;
    private String address;
    private String inn;
    private String ogrn;

    public Company(String name,
                   String type,
                   String country,
                   String address,
                   String inn,
                   String ogrn){
        this.name = name;
        this.type = type;
        this.country = country;
        this.address = address;
        this.inn = inn;
        this.ogrn = ogrn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }


    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

}
