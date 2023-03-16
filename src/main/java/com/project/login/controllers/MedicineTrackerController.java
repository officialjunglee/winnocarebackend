package com.project.login.controllers;

import com.project.login.controllers.model.User;
import com.project.login.controllers.request.Body;
import com.project.login.controllers.request.MedicineScheduleRequest;
import com.project.login.controllers.response.EmergencyContactResponse;
import com.project.login.controllers.response.MedicineScheduleResponse;
import com.project.login.controllers.response.RegisterResponse;
import com.project.login.repository.MedicineDetailsRepository;
import com.project.login.repository.UserRepository;
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
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class MedicineTrackerController {
    @Autowired
    private MedicineDetailsRepository medicineDetailsRepository;

    @Autowired
    private UserRepository userRepository;


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
        medicineDetails.setActiveStatus(1);

        System.out.println("medine details: "+medicineDetails);
        medicineDetailsRepository.save(medicineDetails);

    return new ResponseEntity<>("User Medication details saved successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/medicine/update",produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> updateMedicine(@RequestBody MedicineDetailRequest medicineDetailRequest){
        MedicineDetails medicineDetails= medicineDetailsRepository.findByUserNameAndMedicineName(medicineDetailRequest.getUserName()
        ,medicineDetailRequest.getMedicineName());
        if(medicineDetails==null){
            return ResponseEntity.badRequest().body("Details Not found");
        }
        medicineDetails.setFrequency(medicineDetailRequest.getFrequency());
        medicineDetails.setExpiryDate(medicineDetailRequest.getExpiryDate());
        medicineDetails.setMedStartDate(medicineDetailRequest.getMedStartDate());
        medicineDetails.setMedEndDate(medicineDetailRequest.getMedEndDate());
        medicineDetails.setStock(medicineDetailRequest.getStock());
        medicineDetails.setTimeOfDay(medicineDetailRequest.getTimeOfDay());
        medicineDetails.setReminderTime(medicineDetailRequest.getReminderTime());
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
                if(medicine.getMedStartDate().isBefore(localDate)&&medicine.getMedEndDate().isAfter(localDate)
                &&medicine.getActiveStatus()==1){
                    medicineScheduleResponse.add(mapToResponse(medicine));
                }
            }
            body.setStatusCode("SUCCESS");
            body.setMessage("Medicine details fetched successfully");
            body.setMedicineScheduleResponse(medicineScheduleResponse);
        }
        else {
            body.setMessage("Medicine details Not Found");
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

    @PostMapping(value = "/medicine/delete",produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> softDeleteMedicineDetails(@RequestParam String userName, @RequestParam String medicineName){
        MedicineDetails medicineDetails=medicineDetailsRepository.findByUserNameAndMedicineName(userName,medicineName);
        if(medicineDetails==null){
            return ResponseEntity.badRequest().body("No medicine Details found!");
        }
        if(medicineDetails.getActiveStatus()!=0){
            medicineDetails.setActiveStatus(0);
        }
        medicineDetailsRepository.save(medicineDetails);
        return ResponseEntity.ok("Operation Successful");
    }
    @GetMapping(value = "/emergency/contact",produces = "application/json", consumes = "application/json")
    public ResponseEntity<EmergencyContactResponse> emergencyRespone(@RequestParam("userName") String userName){
        EmergencyContactResponse emergencyContactResponse=new EmergencyContactResponse();
        Optional<User> user=userRepository.findByUserName(userName);
        if (!userRepository.existsByuserName(userName)) {
            emergencyContactResponse.setResponseCode("FAILURE");
            emergencyContactResponse.setMessage("User not found");
            return ResponseEntity.badRequest().body(emergencyContactResponse);
        }
        String flag=user.get().getDefaultFlag();
        System.out.println("flag value:"+flag);
        String defaultContact;
        if(flag.equalsIgnoreCase("E1")){
            defaultContact=user.get().getEmergencyContact1();
            emergencyContactResponse.setSecondaryContact(user.get().getEmergencyContact2());
        }
        else {
            defaultContact=user.get().getEmergencyContact2();
            emergencyContactResponse.setSecondaryContact(user.get().getEmergencyContact1());
        }
        emergencyContactResponse.setResponseCode("SUCCESS");
        emergencyContactResponse.setMessage("Emergency Contact Found");
        emergencyContactResponse.setDefultContact(defaultContact);
        emergencyContactResponse.setPrimaryDoctorContact(user.get().getDoctorContact1());
        emergencyContactResponse.setSecondaryDoctorContact(user.get().getDoctorContact2());
    return ResponseEntity.ok(emergencyContactResponse);
    }

    @PostMapping(value = "/update/defaultcontact",produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> defaultFlagUpdate(@RequestParam("userName") String userName,
                                                                     @RequestParam("defaultContact") String defaultContact){
        EmergencyContactResponse emergencyContactResponse=new EmergencyContactResponse();
        Optional<User> user=userRepository.findByUserName(userName);
        if (!userRepository.existsByuserName(userName)) {
            emergencyContactResponse.setResponseCode("FAILURE");
            emergencyContactResponse.setMessage("User not found");
            return ResponseEntity.badRequest().body(emergencyContactResponse);
        }
        String flagValue="E1";
        if (defaultContact.equalsIgnoreCase("emergencyContact2")){
            flagValue="E2";
        }
        User user1=user.orElseThrow();
        user1.setDefaultFlag(flagValue);
        userRepository.save(user1);
        emergencyContactResponse.setResponseCode("SUCCESS");
        emergencyContactResponse.setMessage("Default Contact Status Updated");
        return ResponseEntity.ok(emergencyContactResponse);
    }
}
