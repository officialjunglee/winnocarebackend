package com.project.login.controllers.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Date;
@Data
@Entity
@Table(name = "medicine_details", uniqueConstraints = {@UniqueConstraint(columnNames = "PrescriptionID")})
public class MedicineDetails {
            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private long prescriptionID;
            @Column(name = "user_name")
            private String userName;

            @Column(name = "medicine_name")
            private String medicineName;

            @Column(name = "stock")
            private Integer stock;

            @Column(name = "expiry_date")
            private String expiryDate;

            @Column(name = "med_start_date")
            private String medStartDate;

            @Column(name = "med_end_date")
            private String medEndDate;

            @Column(name = "reminder_time")
            private String reminderTime;

            @ElementCollection
            @CollectionTable(name = "medicine_details_frequency", joinColumns = @JoinColumn(name = "medication_id"))
            @Column(name = "frequency")
            private List<String> frequency;

            @ElementCollection
            @CollectionTable(name = "medicine_details_time_of_day", joinColumns = @JoinColumn(name = "medication_id"))
            @Column(name = "time_of_day")
            private List<String> timeOfDay;
    
}
