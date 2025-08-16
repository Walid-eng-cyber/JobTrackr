package com.jobtracker.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import com.jobtracker.backend.entity.JobApplication;
import org.springframework.stereotype.Repository;
import java.util.Optional;


/**
 * This is the interface for the JobApplicationRepository. 
 * It extends the JpaRepository to provide CRUD operations for JobApplications.
 * 
 * The interface also provides two custom methods, findByTitle and existsByTitle, 
 * which are useful for checking if a job application with a given title already exists in the database.
 * 
 * The @Repository annotation is used to indicate that this is a Spring Data JPA repository.
 * 
 * The interface is annotated with @Repository to indicate that this is a Spring Data JPA repository.
 */

@Repository
public interface JobApplicationRepositry extends JpaRepository<JobApplication, UUID> {
    Optional<JobApplication> findByTitle(String title);
    boolean existsByTitle(String title);
}
