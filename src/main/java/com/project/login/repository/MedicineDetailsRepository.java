package com.project.login.repository;

import com.project.login.controllers.model.MedicineDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicineDetailsRepository extends JpaRepository<MedicineDetails,Long> {

    MedicineDetails findByUserNameAndMedicineName(String userName, String medicineName);
}
