package com.project.login.controllers.request;
import java.util.Date;

import lombok.Data;

import java.sql.Time;
import java.time.*;

@Data
public class MedicineDetailRequest {

             String Prescription_name;
            int stock;
             String expirydate;
             String medstartdate;
             String medenddate;
             String remindertime;
             String username;
}
