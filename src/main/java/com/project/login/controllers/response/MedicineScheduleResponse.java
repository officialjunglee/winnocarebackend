package com.project.login.controllers.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicineScheduleResponse {

    private String statusCode;

    private String message;
    private String userName;
    private String medicineName;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int stock;
    private LocalDate expiryDate;
    private LocalDate medStartDate;
    private LocalDate medEndDate;
    private String reminderTime;
    private List<String> frequency;
    private List<String> timeOfDay;

    private String morning;
    private String afternoon;
    private String evening;
    private String night;

    public MedicineScheduleResponse(String userName, String medicineName, Integer stock, LocalDate expiryDate,
                                    LocalDate medStartDate, LocalDate medEndDate,
                                    String reminderTime, List<String> frequency, List<String> timeOfDay, String morning,
                                    String afternoon, String evening, String night) {
        this.userName=userName;
        this.medicineName=medicineName;
        this.stock=stock;
        this.expiryDate=expiryDate;
        this.medStartDate=medStartDate;
        this.medEndDate=medEndDate;
        this.reminderTime=reminderTime;
        this.frequency=frequency;
        this.timeOfDay=timeOfDay;
        this.morning=morning;
        this.afternoon=afternoon;
        this.evening=evening;
        this.night=night;
    }

    public MedicineScheduleResponse() {

    }
}
