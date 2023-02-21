package com.project.login.controllers.request;
import java.util.Date;

import lombok.Data;

import java.sql.Time;
import java.time.*;

@Data
public class MedicineDetailRequest {

             String medicineName;
            int stock;
             String expiryDate;
             String medStartDate;
             String medEndDate;
             String reminderTime;
             String userName;
}
