package com.project.login.repository;

import com.project.login.controllers.model.MedicineDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface MedicineDetailsRepository extends JpaRepository<MedicineDetails,Long> {

    MedicineDetails findByUserNameAndMedicineNameAndActiveStatus(String userName, String medicineName,int activeStatus);
    Boolean existsByUserNameAndMedicineNameAndActiveStatus(String userName, String medicineName,int activeStatus);
    List<MedicineDetails> findByUserNameAndExpiryDateAfterAndActiveStatus(String userName, LocalDate expiryDate, int activeStatus);
    List<MedicineDetails> findByUserNameAndExpiryDateBefore(String userName, LocalDate expiryDate);

}
