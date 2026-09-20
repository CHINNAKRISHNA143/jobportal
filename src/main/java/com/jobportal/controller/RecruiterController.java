package com.jobportal.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.entities.Recruiter;
import com.jobportal.enums.Role;
import com.jobportal.service.RecruiterService;
import com.jobportal.util.JwtUtil;

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

    // ✅ Login Recruiter API (Now Returning JWT Token without role will include the role later)
    @PostMapping("/login")
    public ResponseEntity<?> loginRecruiter(@RequestBody Recruiter loginRequest) {
        try {
            Recruiter recruiter = recruiterService.loginRecruiter(loginRequest.getEmail(), loginRequest.getPassword());

            // ✅ Generate JWT Token with role is updated
            String token = jwtUtil.generateToken(recruiter.getEmail(), recruiter.getName(),Role.RECRUITER.name());

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
