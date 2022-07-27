package com.example.doctracermobile.repository;

import static com.example.doctracermobile.utile.Constants.JSON;

import android.util.Log;

import com.example.doctracermobile.entity.Task;
import com.example.doctracermobile.request.TaskRequest;
import com.example.doctracermobile.response.TaskResponse;
import com.example.doctracermobile.utile.CustomAuthenticator;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaskClient {
    private static final String TAG = "TASK_CLIENT";

    private static final String URL = "http://checka.ru:3333";
    private static final String createURL = "/task";
    private static final String assignedURL = "/task/assigned";
    private static final String createdURL = "/task/created";
    private static final String closedURL = "/task/closed";
    private static final String closeURL = "/close";

    //создание новой задачи
    public static boolean create(Task task, String login, String password) {
        TaskRequest taskRequest = new TaskRequest(task.getName(),
                task.getIdea(),
                task.getCreationDate().toString(),
                task.getDeadline().toString(),
                task.getResponsible().getEmail(),
                task.getPriority());

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

    public static List<Task> getAssignedTasks(String login, String password) {
        OkHttpClient client = new OkHttpClient.Builder()
                .authenticator(new CustomAuthenticator(login, password))
                .retryOnConnectionFailure(false)
                .build();

        Request assignedRequest = new Request.Builder()
                .url(URL + assignedURL)
                .build();

        try {
            Response response = client.newCall(assignedRequest).execute();
            Log.e(TAG, response.toString());
            if (response.code() != 200) {
                return null;
            }

            String responseJSON = response.body().string().replace("{\"tasks\":", "");
            responseJSON = responseJSON.substring(0, responseJSON.length() - 1);
            Log.e(TAG+"_RESPONSE",responseJSON);

            List<Task> list = Arrays.stream(new Gson().fromJson(responseJSON, TaskResponse[].class))
                    .map(TaskClient::toTask)
                    .sorted(Comparator.comparingInt((Task task) -> task.getPriority().getSortingPriority())
                            .thenComparing(Task::getDeadline))
                    .collect(Collectors.toList());


            return list;
        } catch (IOException | JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static List<Task> getCreatedTasks(String login, String password) {
        OkHttpClient client = new OkHttpClient.Builder()
                .authenticator(new CustomAuthenticator(login, password))
                .retryOnConnectionFailure(false)
                .build();

        Request assignedRequest = new Request.Builder()
                .url(URL + createdURL)
                .build();

        try {
            Response response = client.newCall(assignedRequest).execute();
            Log.e(TAG, response.toString());
            if (response.code() != 200) {
                return null;
            }
            String responseJSON = response.body().string().replace("{\"tasks\":", "");
            responseJSON = responseJSON.substring(0, responseJSON.length() - 1);
            Log.e(TAG + "_RESPONSE", responseJSON);

            List<Task> list = Arrays.stream(new Gson().fromJson(responseJSON, TaskResponse[].class))
                    .map(TaskClient::toTask)
                    .sorted(Comparator.comparingInt((Task task) -> task.getPriority().getSortingPriority())
                            .thenComparing(Task::getDeadline))
                    .collect(Collectors.toList());

            return list;
        } catch (IOException | JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static List<Task> getClosedTasks(String login, String password) {
        OkHttpClient client = new OkHttpClient.Builder()
                .authenticator(new CustomAuthenticator(login, password))
                .retryOnConnectionFailure(false)
                .build();

        Request assignedRequest = new Request.Builder()
                .url(URL + closedURL)
                .build();

        try {
            Response response = client.newCall(assignedRequest).execute();
            Log.e(TAG, response.toString());
            if (response.code() != 200) {
                return null;
            }
            String responseJSON = response.body().string().replace("{\"tasks\":", "");
            responseJSON = responseJSON.substring(0, responseJSON.length() - 1);
            Log.e(TAG + "_RESPONSE", responseJSON);

            List<Task> list = Arrays.stream(new Gson().fromJson(responseJSON, TaskResponse[].class))
                    .map(TaskClient::toTask)
                    .sorted(Comparator.comparingInt((Task task) -> task.getPriority().getSortingPriority())
                            .thenComparing(Task::getDeadline))
                    .collect(Collectors.toList());

            return list;
        } catch (IOException | JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static boolean close (Long id, String login, String password){
        OkHttpClient client = new OkHttpClient.Builder()
                .authenticator(new CustomAuthenticator(login, password))
                .retryOnConnectionFailure(false)
                .build();

        String jsonObject = new Gson().toJson(id); //делаем JSON
        Log.e("json", jsonObject);

        Request closeRequest = new Request.Builder()
                .url(URL + "/task/" + id + closeURL)
                .post(RequestBody.create(JSON, jsonObject))
                .build();

        try {
            Response response = client.newCall(closeRequest).execute();
            Log.e(TAG, response.toString());
            if (response.code() != 200) {
                return false;
            }
            return true;

        } catch (IOException | JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    private static Task toTask(TaskResponse taskResponse) {
        return new Task(taskResponse.getName(),
                taskResponse.getIdea(),
                Instant.parse(taskResponse.getCreation()),
                Instant.parse(taskResponse.getDeadline()),
                taskResponse.getResponsible(),
                taskResponse.getCreator(),
                taskResponse.getPriority(),
                taskResponse.getStatus(),
                taskResponse.getId());
    }

}
