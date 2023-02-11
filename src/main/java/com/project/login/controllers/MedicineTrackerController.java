package com.project.login.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import com.project.login.controllers.model.MedicineDetails;
import com.project.login.controllers.request.MedicineDetailRequest;
import com.project.login.repository.MedicineDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Collections;
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class MedicineTrackerController {

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
    

    MedicineDetailsRepository.save(meddetails);

    return new ResponseEntity<>("User Medication details saved successfully", HttpStatus.OK);
    }
}
