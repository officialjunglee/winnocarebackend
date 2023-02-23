package com.project.login.controllers.request;
import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

import java.sql.Time;
import java.time.*;
import java.util.List;

@Data
public class MedicineDetailRequest {
             String userName;
             String medicineName;
             int stock;
             String expiryDate;
             String medStartDate;
             String medEndDate;
             String reminderTime;
             List<String> frequency;
             List<String> timeOfDay;
}
