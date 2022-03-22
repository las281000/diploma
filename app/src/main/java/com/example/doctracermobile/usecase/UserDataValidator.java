package com.example.doctracermobile.usecase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.lanwen.verbalregex.VerbalExpression;

public class UserDataValidator extends AbstractValidator{

    public static String passwordCheck(String password){
        String warning = "";
        int len = password.length();

        if ((len < 6)||(len > 32)){
            warning="Пароль должен содержать от 6 до 32 знаков.";
        }

        String regEx = "[^\'\\\"\\{\\}\\[\\]\\(\\)\\<\\>.;\\\\\\/\\s]" + "{" + Integer.toString(len) + "}";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(password);

        if(!matcher.matches()){
            if (!warning.equals("")){
                warning+="\n";
            }
            warning+="Запрещены: { } [ ] < > ( ) \u002F \\ ' \" . ; и пробелы.";
        }

        if (warning.equals("")){
            return null;
        }
        else return warning;
    }

    public static String phoneCheck(String phone) {
        VerbalExpression verRegEx = VerbalExpression.regex()
                .startOfLine()
                .then("+7")
                .space()
                .digit().count(3)
                .space()
                .digit().count(3)
                .add("-")
                .digit().count(2)
                .add("-")
                .digit().count(2)
                .build();

        if (!verRegEx.testExact(phone)){
            return "Номер телефона должен иметь форму +7 XXX XXX-XX-XX";
        }
        else return null;
    }

}
