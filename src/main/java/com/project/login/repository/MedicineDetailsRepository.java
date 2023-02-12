package com.project.login.repository;

import com.project.login.controllers.model.MedicineDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineDetailsRepository extends JpaRepository<MedicineDetails,Long> {
}
