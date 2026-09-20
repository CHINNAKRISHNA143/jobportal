package com.jobportal.controller;

import com.jobportal.entities.Recruiter;
import com.jobportal.service.RecruiterService;
import com.jobportal.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/recruiters")
public class RecruiterController {

    @Autowired
    private RecruiterService recruiterService;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Register Recruiter API
    @PostMapping("/register")
    public ResponseEntity<?> registerRecruiter(@RequestBody Recruiter recruiter) {
        try {
            Recruiter savedRecruiter = recruiterService.registerRecruiter(recruiter);
            return ResponseEntity.ok(savedRecruiter);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Login Recruiter API (Now Returning JWT Token without role)
    @PostMapping("/login")
    public ResponseEntity<?> loginRecruiter(@RequestBody Recruiter loginRequest) {
        try {
            Recruiter recruiter = recruiterService.loginRecruiter(loginRequest.getEmail(), loginRequest.getPassword());

            // ✅ Generate JWT Token without role
            String token = jwtUtil.generateToken(recruiter.getEmail(), recruiter.getName());

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
