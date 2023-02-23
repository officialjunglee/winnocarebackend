package com.project.login.controllers.request;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "medicine_details")
public class MedicineScheduleRequest {

    String userName;
    String medicineName;
}
