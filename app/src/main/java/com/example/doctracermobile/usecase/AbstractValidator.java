package com.example.doctracermobile.usecase;

public abstract class AbstractValidator {
    public static boolean capitalLetterCheck(String str){
        if (Character.isUpperCase(str.charAt(0))){
            return true;
        }
        else return false;
    }
}
