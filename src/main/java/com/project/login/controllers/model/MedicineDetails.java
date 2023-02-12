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
            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private long prescriptionID;
            private String Prescription_name;
            private int stock;
            private String expirydate;
            private String medstartdate;
            private String medenddate;
            private String remindertime;
            private String username;
    
}
