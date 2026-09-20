package com.jobportal.service;

import com.jobportal.entities.JobSeeker;
import com.jobportal.repository.JobSeekerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final JobSeekerRepository jobSeekerRepository;

    public CustomUserDetailsService(JobSeekerRepository jobSeekerRepository) {
        this.jobSeekerRepository = jobSeekerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<JobSeeker> jobSeeker = jobSeekerRepository.findByEmail(email);
        return jobSeeker.orElseThrow(() ->
                new UsernameNotFoundException("JobSeeker not found with email: " + email));
    }
}
