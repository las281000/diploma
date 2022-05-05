package com.example.doctracermobile.repository;

import static com.example.doctracermobile.utile.Constants.JSON;

import android.util.Log;

import com.example.doctracermobile.entity.Project;
import com.example.doctracermobile.entity.User;
import com.example.doctracermobile.request.JointUserProject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProjectClient {
    private static final String TAG = "COMPANY_CLIENT"; //Для ошибок

    private static final String URL = "http://checka.ru:3333";
    private static final String regURL = "/public/company";

    public static boolean register(Project project, User user) {
        JointUserProject jointUserProject = new JointUserProject(
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate(),
                user.getName(),
                user.getSurname(),
                user.getPatronum(),
                user.getPosition(),
                user.getPhone(),
                user.getEmail(),
                user.getPass()
        );
        String jsonObject = new Gson().toJson(jointUserProject);

        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .build();

        Request request = new Request.Builder()
                .post(RequestBody.create(JSON, jsonObject ))
                .url(URL + regURL).build();

        try {
            Response response = client.newCall(request).execute();
            Log.e(TAG, response.toString());
            return response.code() == 200;
        } catch (IOException | JsonSyntaxException e){
            Log.e(TAG, e.getMessage());
            return false;
        }
    }


}
