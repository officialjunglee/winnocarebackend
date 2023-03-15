package com.project.login.controllers.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmergencyContactResponse {
    private String responseCode;
    private String message;
    private String defultContact;

}
