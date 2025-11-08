package com.orderservice.controller;

import com.netflix.discovery.converters.Auto;
import com.orderservice.configurations.JwtUtil;
import com.orderservice.model.ApplicationUser;
import com.orderservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@AllArgsConstructor
public class AuthController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> signUp(@RequestBody ApplicationUser applicationUser){

        try{
            applicationUser.setPassword(passwordEncoder.encode(applicationUser.getPassword()));
            userRepository.save(applicationUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("Registered");
        }catch (Exception e){
            log.error("Could NOT Save User : "+e);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> signin(@RequestBody ApplicationUser applicationUser){
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    applicationUser.getUsername(),
                    applicationUser.getPassword()));
        }catch (Exception e){
            log.error("Could not Authenticate user : "+e);
        }
        String token = jwtUtil.createToken(applicationUser.getUsername());

        return ResponseEntity.ok().body(Map.of("token",token));
    }



}
