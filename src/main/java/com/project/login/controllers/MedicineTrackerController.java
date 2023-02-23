package com.project.login.controllers;

import com.project.login.controllers.request.MedicineScheduleRequest;
import com.project.login.controllers.response.MedicineScheduleResponse;
import com.project.login.repository.MedicineDetailsRepository;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<?> registerResponse(@RequestBody MedicineDetailRequest medicineDetailRequest){
        log.info("Medicine Details api");

        // create Medicine details object
        MedicineDetails medicineDetails=new MedicineDetails();
        medicineDetails.setUserName(medicineDetailRequest.getUserName());
        medicineDetails.setMedicineName(medicineDetailRequest.getMedicineName());
        medicineDetails.setFrequency(medicineDetailRequest.getFrequency());
        medicineDetails.setExpiryDate(medicineDetailRequest.getExpiryDate());
        medicineDetails.setMedStartDate(medicineDetailRequest.getMedStartDate());
        medicineDetails.setMedEndDate(medicineDetailRequest.getMedEndDate());
        medicineDetails.setStock(medicineDetailRequest.getStock());
        medicineDetails.setTimeOfDay(medicineDetailRequest.getTimeOfDay());
        medicineDetails.setReminderTime(medicineDetailRequest.getReminderTime());

        System.out.println("medine details: "+medicineDetails);
        medicineDetailsRepository.save(medicineDetails);

    return new ResponseEntity<>("User Medication details saved successfully", HttpStatus.OK);
    }
    @PostMapping(value = "/user/medicineschedule",produces = "application/json", consumes = "application/json")
    public MedicineScheduleResponse medicineSchedule(@RequestBody MedicineScheduleRequest medicineScheduleRequest,
                                                     HttpServletResponse httpServletResponse){
        MedicineScheduleResponse medicineScheduleResponse=new MedicineScheduleResponse();

        MedicineDetails medicineDetails=medicineDetailsRepository.findByUserNameAndMedicineName(medicineScheduleRequest.getUserName(),
                medicineScheduleRequest.getMedicineName());
        System.out.println("Medicaiton: "+medicineDetails);

        if(medicineDetails==null){
            medicineScheduleResponse.setStatusCode("FAILURE");
            medicineScheduleResponse.setMessage("Details Not found");
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        else {
            medicineScheduleResponse.setStatusCode("SUCCESS");
            medicineScheduleResponse.setMessage("User Details Found");
            medicineScheduleResponse.setFrequency(medicineDetails.getFrequency());
            medicineScheduleResponse.setTimeOfDay(medicineDetails.getTimeOfDay());
        }
        return medicineScheduleResponse;
    }
}
