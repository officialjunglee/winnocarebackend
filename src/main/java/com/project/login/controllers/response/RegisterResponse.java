package com.project.login.controllers.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterResponse {
    private String responseCode;
    private String message;

    private String userName;

    private String emergencyContact1;
    private String emergencyContact2;
    private String doctorContact1;
    private String doctorContact2;
}
