package com.example.doctracermobile.repository;

import static com.example.doctracermobile.utile.Constants.JSON;

import android.util.Log;

import com.example.doctracermobile.entity.Employee;
import com.example.doctracermobile.entity.User;
import com.example.doctracermobile.request.JointEmailToken;
import com.example.doctracermobile.request.JointUserProject;
import com.example.doctracermobile.request.UserForRequest;
import com.example.doctracermobile.utile.CustomAuthenticator;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserClient {
    private static final String TAG = "USER_CLIENT"; //Для ошибок
    private static final String URL = "http://checka.ru:3333";
    private static final String signInURL = "/user";
    private static final String verifyURL = "/public/user/activate";
    private static final String updateURL = "/user";
    private static final String registerURL = "/user";
    private static final String getEmployeeURL = "/user/employees";

    private String error;

    //Запрос на авторизацию
    public static JointUserProject signIn(String login, String password) {
        OkHttpClient client = new OkHttpClient.Builder()
                .authenticator(new CustomAuthenticator(login, password))
                .retryOnConnectionFailure(false)
                .build();
        Request signInRequest = new Request.Builder()
                .url(URL + signInURL)
                .build();

        try {
            Response response = client.newCall(signInRequest).execute();
            Log.e(TAG, response.toString());
            if (response.code() != 200) {
                return null;
            }
            return new Gson().fromJson(response.body().string(), JointUserProject.class);

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

    public static JointUserProject update(String oldLogin, String oldPassword, UserForRequest newUserData) {
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
            return new Gson().fromJson(response.body().string(), JointUserProject.class);

        } catch (IOException | JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }

    }

    //для регистрации раба
    public static boolean register(User employee, String login, String password) {
        OkHttpClient client = new OkHttpClient.Builder()
                .authenticator(new CustomAuthenticator(login, password))
                .retryOnConnectionFailure(false)
                .build();

        String jsonObject = new Gson().toJson(employee); //делаем JSON
        Log.d("json", jsonObject);

        Request registerRequest = new Request.Builder()
                .url(URL + registerURL)
                .post(RequestBody.create(JSON, jsonObject))
                .build();

        try {
            Response response = client.newCall(registerRequest).execute();
            Log.e("TRY_" + TAG, response.body().string());
            if (response.code() != 200) {
                return false;
            }
            return true;

        } catch (IOException | JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    public static ArrayList<Employee> getEmployees(String login, String password) {
        OkHttpClient client = new OkHttpClient.Builder()
                .authenticator(new CustomAuthenticator(login, password))
                .retryOnConnectionFailure(false)
                .build();

        Request employeesRequest = new Request.Builder()
                .url(URL + getEmployeeURL)
                .build();

        try {
            Response response = client.newCall(employeesRequest).execute();
            Log.e(TAG, response.toString());
            if (response.code() != 200) {
                return null;
            }
            String responseJSON = response.body().string().replace("{\"employees\":", "");
            responseJSON = responseJSON.substring(0,responseJSON.length()-1);
            ArrayList list = new ArrayList<> (Arrays.asList(new Gson().fromJson(responseJSON, Employee[].class)));
            return list;
        } catch (IOException | JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }
}
