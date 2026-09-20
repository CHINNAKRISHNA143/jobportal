package com.jobportal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jobportal.entities.JobSeeker;
import com.jobportal.enums.Role;
import com.jobportal.repository.JobSeekerRepository;
import com.jobportal.util.JwtUtil;

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
                // Generate JWT with the role, based on email and name
                return jwtUtil.generateToken(jobSeeker.getEmail(), jobSeeker.getFullName(),Role.JOBSEEKER.name());
            } else {
                throw new RuntimeException("Invalid password for JobSeeker");
            }
        } else {
            throw new RuntimeException("JobSeeker not found");
        }
    }
}
