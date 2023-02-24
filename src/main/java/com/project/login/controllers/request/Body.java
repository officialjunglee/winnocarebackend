package com.project.login.controllers.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.login.controllers.response.MedicineScheduleResponse;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Body {

    private String statusCode;
    private String message;
    private List<MedicineScheduleResponse> medicineScheduleResponse;

    private MedicineScheduleResponse medicineSchedule;
}
