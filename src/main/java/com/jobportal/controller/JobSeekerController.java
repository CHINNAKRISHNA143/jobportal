package com.jobportal.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.entities.JobSeeker;
import com.jobportal.enums.Role;
import com.jobportal.service.JobseekerService;
import com.jobportal.util.JwtUtil;

@RestController
@RequestMapping("/jobseekers")
public class JobSeekerController {

    @Autowired
    private JobseekerService jobSeekerService;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Register JobSeeker API
    @PostMapping("/register")
    public ResponseEntity<?> registerJobSeeker(@RequestBody JobSeeker jobSeeker) {
        try {
            JobSeeker savedJobSeeker = jobSeekerService.registerJobSeeker(jobSeeker);
            return ResponseEntity.ok(savedJobSeeker);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Login JobSeeker API (Now Returning JWT Token without role)
    @PostMapping("/login")
    public ResponseEntity<?> loginJobSeeker(@RequestBody JobSeeker loginRequest) {
        try {
            JobSeeker jobSeeker = jobSeekerService.loginJobSeeker(loginRequest.getEmail(), loginRequest.getPassword());

            // ✅ Generate JWT Token without role
            String token = jwtUtil.generateToken(jobSeeker.getEmail(), jobSeeker.getFullName(),Role.JOBSEEKER.name());

            // ✅ Prepare Response
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "Login Successful");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
