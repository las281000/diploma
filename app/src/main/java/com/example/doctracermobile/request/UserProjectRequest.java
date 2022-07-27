package com.example.doctracermobile.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProjectRequest {
    private String projectName;
    private String description;
    private String startDate;
    private String endDate;

    private String name;
    private String surname;
    private String patronum;
    private String position;
    private String phoneNumber;
    private String email;
    private String password;

    @Override
    public String toString() {
        return "UserProjectRequest{" +
                ", projectName='" + projectName + '\'' +
                ", description='" + description + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronum='" + patronum + '\'' +
                ", position='" + position + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
