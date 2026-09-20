package com.jobportal.service;

import com.jobportal.entities.Recruiter;
import com.jobportal.repository.RecruiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterService {

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Register Recruiter
    public Recruiter registerRecruiter(Recruiter recruiter) {
        // Check if the recruiter with the provided email already exists
        Optional<Recruiter> existing = recruiterRepository.findByEmail(recruiter.getEmail());
        if (existing.isPresent()) {
            throw new RuntimeException("Recruiter already registered with this email.");
        }

        // Encode password before saving
        recruiter.setPassword(passwordEncoder.encode(recruiter.getPassword()));
        return recruiterRepository.save(recruiter);
    }

    // ✅ Login Recruiter
    public Recruiter loginRecruiter(String email, String rawPassword) {
        Optional<Recruiter> recruiterOpt = recruiterRepository.findByEmail(email);
        if (recruiterOpt.isEmpty()) {
            throw new RuntimeException("No Recruiter found with this email.");
        }

        Recruiter recruiter = recruiterOpt.get();
        if (!passwordEncoder.matches(rawPassword, recruiter.getPassword())) {
            throw new RuntimeException("Invalid password.");
        }

        return recruiter;
    }
}
