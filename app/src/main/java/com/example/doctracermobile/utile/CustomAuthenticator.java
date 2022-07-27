package com.example.doctracermobile.utile;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

//Помещает в заголовок логин и пароль
public class CustomAuthenticator implements Authenticator {

    private final String email;
    private final String password;

    public CustomAuthenticator(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        String credential = Credentials.basic(email, password);
        return response.request().newBuilder().header("Authorization", credential).build();
    }
}
