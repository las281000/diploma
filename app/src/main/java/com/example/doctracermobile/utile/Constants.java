package com.example.doctracermobile.utile;

import okhttp3.MediaType;

public class Constants {

    private Constants() {
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static final String APP_PREFERENCES = "authorization";
    public static final String APP_PREFERENCES_LOGIN = "login";
    public static final String APP_PREFERENCES_PASSWORD = "password";
}
