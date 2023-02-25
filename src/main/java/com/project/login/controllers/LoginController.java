package com.project.login.controllers;

import com.project.login.controllers.model.Role;
import com.project.login.controllers.model.User;
import com.project.login.controllers.request.LoginRequest;
import com.project.login.controllers.request.RegisterRequest;
import com.project.login.controllers.response.LoginResponse;
import com.project.login.controllers.response.RegisterResponse;
import com.project.login.repository.RoleRepository;
import com.project.login.repository.UserRepository;
import com.project.login.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @GetMapping("/message")
    public String test(){
        return "Spring boot";
    }

    @PostMapping(value="/user/login",produces = "application/json",consumes = "application/json")
    public LoginResponse loginv1(@RequestBody LoginRequest request, HttpServletResponse httpServletResponse) throws IOException {
      log.info("login api");
      LoginResponse loginResponse=new LoginResponse();
      try{
          Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName()
                  ,request.getPassword()));
          if(authentication.isAuthenticated()){
              SecurityContextHolder.getContext().setAuthentication(authentication);
              loginResponse.setResponseCode("SUCCESS");
              loginResponse.setMessage(request.getUserName()+" Authenticate Successfully");
              System.out.println("login api:"+ request);
          }
      }
      catch (AuthenticationException exception){
          loginResponse.setResponseCode("FAILURE");
          loginResponse.setMessage("Authentication Failed Incorrect Username or Password");
          httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
      }
      return loginResponse;
    }
@PostMapping(value = "/user/register",produces = "application/json", consumes = "application/json")
    public ResponseEntity<RegisterResponse> registerResponse(@RequestBody RegisterRequest registerRequest){
        log.info("Register api");
    // add check if username exists in Database
    RegisterResponse registerResponse=new RegisterResponse();
    if(userRepository.existsByuserName(registerRequest.getUserName())){
        registerResponse.setResponseCode("FAILURE");
        registerResponse.setMessage("Username is already taken!");
        return ResponseEntity.badRequest().body(registerResponse);
    }

    // create object of the user
    User user = new User();
    
    user.setUserName(registerRequest.getUserName());
    user.setFirstName(registerRequest.getFirstName());
    user.setLastName(registerRequest.getLastName());
    user.setAge(registerRequest.getAge());
    user.setGender(registerRequest.getGender());
    user.setCountry(registerRequest.getCountry());
    user.setEmail(registerRequest.getEmail());
    user.setPhoneNumber(registerRequest.getPhoneNumber());
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    user.setEmergencyContact1(registerRequest.getEmergencyContact1());
    user.setEmergencyContact2(registerRequest.getEmergencyContact2());
    user.setDoctorContact1(registerRequest.getDoctorContact1());
    user.setDoctorContact2(registerRequest.getDoctorContact2());
    System.out.println("username : "+registerRequest.getUserName()+" Password: "+registerRequest.getPassword());
    Role roles = roleRepository.findByRoleName("ROLE_ADMIN").get();
    user.setRoles(Collections.singleton(roles));

    userRepository.save(user);
    registerResponse.setMessage("User registered successfully");
    registerResponse.setResponseCode("SUCCESS");
    registerResponse.setUserName(registerRequest.getUserName());
    registerResponse.setEmergencyContact1(registerRequest.getEmergencyContact1());
    registerResponse.setEmergencyContact2(registerRequest.getEmergencyContact2());
    registerResponse.setDoctorContact1(registerRequest.getDoctorContact1());
    registerResponse.setDoctorContact2(registerRequest.getDoctorContact2());
    return ResponseEntity.ok(registerResponse);
    }
    //end points for forgotpassword
    @PostMapping(value = "/user/forgotpassword",produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> handleForgotPassword(@RequestParam("userName") String userName,@RequestParam("newPassword") String newPassword) {
        Optional<User> user=userRepository.findByUserName(userName);
            if (!userRepository.existsByuserName(userName)) {
            return ResponseEntity.badRequest().body("User not found");
        }
       // User user = userRepository.findByUsername(username);

       userService.updateUserPassword(user.orElseThrow(),newPassword);
            return ResponseEntity.ok("Password reset done successfully");
}
}
