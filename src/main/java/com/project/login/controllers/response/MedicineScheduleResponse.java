package com.project.login.controllers.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MedicineScheduleResponse {

    String statusCode;

    String message;
    List<String> frequency;
    List<String> timeOfDay;
}
