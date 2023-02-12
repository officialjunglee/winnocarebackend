package com.project.login.controllers;

import com.project.login.controllers.model.Role;
import com.project.login.controllers.model.User;
import com.project.login.controllers.request.LoginRequest;
import com.project.login.controllers.request.RegisterRequest;
import com.project.login.controllers.response.LoginResponse;
import com.project.login.repository.RoleRepository;
import com.project.login.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

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

    @PostMapping(value="/user/login",produces = "application/json",consumes = "application/json")
    public LoginResponse loginv1(@RequestBody LoginRequest request){
      log.info("login api");
      LoginResponse loginResponse=new LoginResponse();
      Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName()
      ,request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      loginResponse.setResponseCode("SUCCESS");
      loginResponse.setMessage(request.getUserName());
      System.out.println("login api:"+ request);
      return loginResponse;
    }
@PostMapping(value = "/user/register",produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> registerResponse(@RequestBody RegisterRequest registerRequest){
        log.info("Register api");
    // add check for username exists in a DB
    if(userRepository.existsByusername(registerRequest.getUsername())){
        return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
    }

    // create user object
    User user = new User();
    
    user.setUsername(registerRequest.getUsername());
    user.setFirstname(registerRequest.getFirstname());
    user.setLastname(registerRequest.getLastname());
    user.setAge(registerRequest.getAge());
    user.setEmail(registerRequest.getEmail());
    user.setEmergencycontact1(registerRequest.getEmergencycontact1());
    user.setEmergencycontact2(registerRequest.getEmergencycontact2());
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    System.out.println("username : "+registerRequest.getUsername()+" Password: "+registerRequest.getPassword());
    Role roles = roleRepository.findByRoleName("ROLE_ADMIN").get();
    user.setRoles(Collections.singleton(roles));

    userRepository.save(user);

    return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
