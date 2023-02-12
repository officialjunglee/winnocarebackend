package com.project.login.controllers;

import com.project.login.repository.MedicineDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.project.login.controllers.model.MedicineDetails;
import com.project.login.controllers.request.MedicineDetailRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class MedicineTrackerController {
    @Autowired
    private MedicineDetailsRepository medicineDetailsRepository;


@PostMapping(value = "/user/medicinedetails",produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> registerResponse(@RequestBody MedicineDetailRequest medRequest){
        log.info("Medicine Details api");
    // add check for username exists in a DB
    

    // create user object
    MedicineDetails meddetails = new MedicineDetails();
    meddetails.setPrescription_name(medRequest.getPrescription_name());
    meddetails.setExpirydate(medRequest.getExpirydate());
    meddetails.setStock(medRequest.getStock());
    meddetails.setMedenddate(medRequest.getMedenddate());
    meddetails.setMedstartdate(medRequest.getMedstartdate());
    meddetails.setRemindertime(medRequest.getRemindertime());
    medRequest.setUsername(medRequest.getUsername());
    

    medicineDetailsRepository.save(meddetails);

    return new ResponseEntity<>("User Medication details saved successfully", HttpStatus.OK);
    }
}
