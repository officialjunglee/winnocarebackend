package com.project.login.controllers.request;

import lombok.Data;

@Data
public class RegisterRequest {

    String firstName;
    String lastName;
    String email;
    int age;
    String gender;
    String username;
    String phoneNumber;
    String country;
    String password;
}
