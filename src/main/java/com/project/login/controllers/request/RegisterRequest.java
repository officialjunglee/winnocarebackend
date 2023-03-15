package com.project.login.controllers.request;

import lombok.Data;

@Data
public class RegisterRequest {

    String firstName;
    String lastName;
    String email;
    int age;
    String gender;
    String userName;
    String phoneNumber;
    String country;
    String password;
    String emergencyContact1;
    String emergencyContact2;
    String doctorContact1;
    String doctorContact2;

    String defaultFlag;
}
