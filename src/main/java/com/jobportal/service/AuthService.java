package com.jobportal.service;

import com.jobportal.entities.JobSeeker;
import com.jobportal.repository.JobSeekerRepository;
import com.jobportal.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

   
    public String authenticateUser(String email, String password) {
        // Check if the job seeker exists
        Optional<JobSeeker> jobSeekerOpt = jobSeekerRepository.findByEmail(email);
        if (jobSeekerOpt.isPresent()) {
            JobSeeker jobSeeker = jobSeekerOpt.get();

            // Check if password matches
            if (passwordEncoder.matches(password, jobSeeker.getPassword())) {
                // Generate JWT without the role, based on email and name
                return jwtUtil.generateToken(jobSeeker.getEmail(), jobSeeker.getFullName());
            } else {
                throw new RuntimeException("Invalid password for JobSeeker");
            }
        } else {
            throw new RuntimeException("JobSeeker not found");
        }
    }
}
