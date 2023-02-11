package com.project.login.controllers.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Set;
import java.util.Date;
@Data
@Entity
@Table(name = "medicine_details", uniqueConstraints = {@UniqueConstraint(columnNames = "PrescriptionID")})
public class MedicineDetails {
            private int prescriptionID;
            private String Prescription_name;
            private int stock;
            private Date expirydate;
            private Date medstartdate;
            private Date medenddate;
            private Time remindertime;
            private String username;
    
}
