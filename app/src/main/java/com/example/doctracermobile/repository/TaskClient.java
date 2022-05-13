package com.example.doctracermobile.repository;

import static com.example.doctracermobile.utile.Constants.JSON;

import android.util.Log;

import com.example.doctracermobile.entity.Task;
import com.example.doctracermobile.request.TaskRequest;
import com.example.doctracermobile.utile.CustomAuthenticator;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaskClient {
    private static final String TAG = "TASK_CLIENT";

    private static final String URL = "http://checka.ru:3333";
    private static final String createURL = "/task";

    //создание новой задачи
    public static boolean create(Task task, String login, String password) {
        TaskRequest taskRequest = new TaskRequest(task.getName(),
                task.getIdea(),
                task.getCreationDate().toString(),
                task.getDeadline().toString(),
                task.getResponsible());

        OkHttpClient client = new OkHttpClient.Builder()
                .authenticator(new CustomAuthenticator(login, password))
                .retryOnConnectionFailure(false)
                .build();

        String jsonObject = new Gson().toJson(taskRequest); //делаем JSON
        Log.e("json", jsonObject);

        Request registerRequest = new Request.Builder()
                .url(URL + createURL)
                .post(RequestBody.create(JSON, jsonObject))
                .build();

        try {
            Response response = client.newCall(registerRequest).execute();
            Log.e(TAG, response.body().string());
            if (response.code() != 200) {
                return false;
            }
            return true;

        } catch (IOException | JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

}
