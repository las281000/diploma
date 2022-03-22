package com.example.doctracermobile.repository;

import android.util.Log;

import com.example.doctracermobile.entity.User;
import com.example.doctracermobile.request.JointUserCompany;
import com.example.doctracermobile.request.JointEmailToken;
import com.example.doctracermobile.request.UserForRequest;
import com.example.doctracermobile.util.CustomAuthenticator;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.doctracermobile.util.Constants.JSON;

public class UserClient {
    private static final String TAG = "USER_CLIENT"; //Для ошибок
    private static final String URL = "http://checka.ru:3333";
    private static final String signInURL = "/user";
    private static final String verifyURL = "/public/user/activate";
    private static final String updateURL = "/user";

    private String error;

    //Запрос на авторизацию
    public static JointUserCompany signIn(String login, String password) {
        OkHttpClient client = new OkHttpClient.Builder()
                .authenticator(new CustomAuthenticator(login, password))
                .retryOnConnectionFailure(false)
                .build();
        Request signInRequest = new Request.Builder()
                .url(URL + signInURL)
                .build();

        try {
            Response response = client.newCall(signInRequest).execute();

            if (response.code() != 200) {
                return null;
            }
            return new Gson().fromJson(response.body().string(), JointUserCompany.class);

        } catch (IOException | JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static boolean verifyEmail(String code, String email) {
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .build();

        String jsonObject = new Gson().toJson(new JointEmailToken(code, email));

        Log.d("json", jsonObject);
        Request request = new Request.Builder()
                .post(RequestBody.create(JSON, jsonObject))
                .url(URL + verifyURL)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.code() == 200;
        } catch (IOException | JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }

    }

    public static JointUserCompany update(String oldLogin, String oldPassword, UserForRequest newUserData) {
        OkHttpClient client = new OkHttpClient.Builder()
                .authenticator(new CustomAuthenticator(oldLogin, oldPassword))
                .retryOnConnectionFailure(false)
                .build();

        String jsonObject = new Gson().toJson(newUserData); //делаем JSON
        Log.d("json", jsonObject);

        Request updateRequest = new Request.Builder()
                .url(URL + updateURL)
                .patch(RequestBody.create(JSON, jsonObject))
                .build();

        try {
            Response response = client.newCall(updateRequest).execute();
            if (response.code() != 200) {
                return null;
            }
            return new Gson().fromJson(response.body().string(), JointUserCompany.class);

        } catch (IOException | JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }

    }
}
