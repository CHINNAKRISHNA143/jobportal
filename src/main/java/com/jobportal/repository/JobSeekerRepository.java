package com.jobportal.repository;

import com.jobportal.entities.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, Integer> {
    Optional<JobSeeker> findByEmail(String email); // Used for login
}
