package com.example.doctracermobile.repository;

import static com.example.doctracermobile.utile.Constants.APP_PREFERENCES_LOGIN;
import static com.example.doctracermobile.utile.Constants.APP_PREFERENCES_PASSWORD;

import android.content.SharedPreferences;

public class Preferences {

    public static void savePreferences(SharedPreferences pref, String login, String password) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(APP_PREFERENCES_LOGIN, login);
        editor.putString(APP_PREFERENCES_PASSWORD, password);
        editor.apply();
    }

    public static String getLogin(SharedPreferences pref){
        if (pref.contains(APP_PREFERENCES_LOGIN)){
            return pref.getString(APP_PREFERENCES_LOGIN, "");
        } else {
            return null;
        }
    }

    public static String getPassword(SharedPreferences pref){
        if (pref.contains(APP_PREFERENCES_PASSWORD)){
            return pref.getString(APP_PREFERENCES_PASSWORD, "");
        } else {
            return null;
        }
    }

    public static boolean removePassword(SharedPreferences pref){
        SharedPreferences.Editor editor = pref.edit();
        if (pref.contains(APP_PREFERENCES_PASSWORD)) {
            editor.remove(APP_PREFERENCES_PASSWORD);
            editor.apply();

            return true;
        } else{
            return false;
        }
    }

}
