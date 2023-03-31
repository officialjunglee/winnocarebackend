package com.project.login.service;

import com.project.login.controllers.model.MedicineDetails;
import com.project.login.controllers.model.User;
import com.project.login.repository.MedicineDetailsRepository;
import com.project.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DeleteService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MedicineDetailsRepository medicineDetailsRepository;
    @Scheduled(cron = "*/10 * * * * *")
    public void softDeleteFromDB(){
        List<User> userList=userRepository.findAll();
        for(User user:userList){
            LocalDate localDate=LocalDate.now();
//            List<MedicineDetails> medicineDetails=medicineDetailsRepository.findByUserNameAndExpiryDateBefore(user.getUserName(), localDate);
//            if(medicineDetails!=null){
//                for(MedicineDetails medicineDetails1:medicineDetails){
//                    medicineDetails1.setActiveStatus(0);
//                    System.out.println(medicineDetails);
//                }
//            }
        }
    }
}
