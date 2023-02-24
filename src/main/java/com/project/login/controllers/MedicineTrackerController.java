package com.project.login.controllers;

import com.project.login.controllers.request.Body;
import com.project.login.controllers.request.MedicineScheduleRequest;
import com.project.login.controllers.response.MedicineScheduleResponse;
import com.project.login.repository.MedicineDetailsRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.login.controllers.model.MedicineDetails;
import com.project.login.controllers.request.MedicineDetailRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @PostMapping(value = "/user/medicinefrequency",produces = "application/json", consumes = "application/json")
    public Body medicineFrequency(@RequestBody MedicineScheduleRequest medicineScheduleRequest,
                                                     HttpServletResponse httpServletResponse){
        MedicineScheduleResponse medicineScheduleResponse=new MedicineScheduleResponse();
        Body body=new Body();
        MedicineDetails medicineDetails=medicineDetailsRepository.findByUserNameAndMedicineName(medicineScheduleRequest.getUserName(),
                medicineScheduleRequest.getMedicineName());
        System.out.println("Medication: "+medicineDetails);

        if(medicineDetails==null){
            body.setStatusCode("FAILURE");
            body.setMessage("Details Not found");
            body.setMedicineSchedule(medicineScheduleResponse);
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        else {
            body.setStatusCode("SUCCESS");
            body.setMessage("User Details Found");
            medicineScheduleResponse.setFrequency(medicineDetails.getFrequency());
            medicineScheduleResponse.setTimeOfDay(medicineDetails.getTimeOfDay());
            body.setMedicineSchedule(medicineScheduleResponse);

        }
        return body;
    }
    @PostMapping(value = "/user/medicineschedule",produces = "application/json",consumes = "application/json")
    public Body medicineSchedule(@RequestParam String userName){
        LocalDate localDate=LocalDate.now();
        Body body=new Body();
        List<MedicineDetails> medicineDetails=medicineDetailsRepository.findByUserNameAndExpiryDateAfter(userName,localDate);
        System.out.println("Medicine Details: "+medicineDetails);
        List<MedicineScheduleResponse> medicineScheduleResponse=new ArrayList<>();
        if(!medicineDetails.isEmpty()){
            for (MedicineDetails medicine:medicineDetails){
                if(medicine.getMedStartDate().isBefore(localDate)&&medicine.getMedEndDate().isAfter(localDate)){
                    medicineScheduleResponse.add(mapToResponse(medicine));
                }
            }
            body.setStatusCode("SUCCESS");
            body.setMessage("Frequency Fetched successfully");
            body.setMedicineScheduleResponse(medicineScheduleResponse);
        }
        else {
            body.setMessage("Medicine Schedule Not Found");
            body.setStatusCode("FAILURE");
            body.setMedicineScheduleResponse(medicineScheduleResponse);
        }

        return body;
    }
    private MedicineScheduleResponse mapToResponse(MedicineDetails medicine) {
        // map fields from Medicine entity to MedicineResponse object
        return new MedicineScheduleResponse(medicine.getUserName(), medicine.getMedicineName(), medicine.getStock(),
                medicine.getExpiryDate(), medicine.getMedStartDate(), medicine.getMedEndDate(), medicine.getReminderTime(),
                medicine.getFrequency(), medicine.getTimeOfDay());
    }
}
