package com.jobportal.service;


import com.jobportal.entities.JobSeeker;
import com.jobportal.repository.JobSeekerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobseekerService {

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Register JobSeeker
    public JobSeeker registerJobSeeker(JobSeeker jobSeeker) {
        Optional<JobSeeker> existing = jobSeekerRepository.findByEmail(jobSeeker.getEmail());
        if (existing.isPresent()) {
            throw new RuntimeException("JobSeeker already registered with this email.");
        }

        // Encode password before saving
        jobSeeker.setPassword(passwordEncoder.encode(jobSeeker.getPassword()));
        return jobSeekerRepository.save(jobSeeker);
    }

    // ✅ Login JobSeeker
    public JobSeeker loginJobSeeker(String email, String rawPassword) {
        Optional<JobSeeker> jobSeekerOpt = jobSeekerRepository.findByEmail(email);
        if (jobSeekerOpt.isEmpty()) {
            throw new RuntimeException("No JobSeeker found with this email.");
        }

        JobSeeker jobSeeker = jobSeekerOpt.get();
        if (!passwordEncoder.matches(rawPassword, jobSeeker.getPassword())) {
            throw new RuntimeException("Invalid password.");
        }

        return jobSeeker;
    }
}
