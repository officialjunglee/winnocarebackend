package com.project.login.controllers.request;

import lombok.Data;

@Data
public class RegisterRequest {

    String firstname;
    String lastname;
    String email;
    int age;
    String username;
//    String firstName;
//
//    String lastName;

    String password;
    int emergencycontact1;
    int emergencycontact2;
}
