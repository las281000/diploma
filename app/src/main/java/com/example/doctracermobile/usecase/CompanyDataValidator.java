package com.example.doctracermobile.usecase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompanyDataValidator extends AbstractValidator {
    public static final int PE = 0;
    public static final int LE = 1;

    //Проверка длины ИНН
    public static String innCheck(String inn, String legalForm) {
        int length; //выбираем длину ИНН
        switch (legalForm) {
            case "ИП":
                length = 12;
                break;
            default:
                length = 10;
        }

        String regEx = "[1234567890]{" + String.valueOf(length) + "}"; //только length цифр
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(inn);

        if (!matcher.matches()) {
            return "ИНН для данной ОФП должен состоять из " + String.valueOf(length) + " цифр!";
        } else return null;
    }

    public static String ogrnCheck(String ogrn) {
        String regEx = "[1234567890]{13}"; //только 13 цифр
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(ogrn);

        if (!matcher.matches()) {
            return "ОГРН должен состоять из 13 цифр!";
        } else {
            return null;
        }
    }
}
