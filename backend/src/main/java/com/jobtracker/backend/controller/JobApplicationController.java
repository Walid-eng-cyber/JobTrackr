package com.jobtracker.backend.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.jobtracker.backend.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import com.jobtracker.backend.dto.JobApplicationDTO;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;    
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;


@RestController
@RequestMapping("/api/job-applications")
@Tag(name = "JobApplication", description = "APIs for Job Application Management")
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;
    
    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @GetMapping
    @Operation(summary = "Get all job applications", description = "Retrieves a list of all job applications")
    public ResponseEntity<List<JobApplicationDTO>> getAllJobApplications() {
        return ResponseEntity.ok(jobApplicationService.findAllJobApplications());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get job application by ID", description = "Retrieves a job application by ID")
    public ResponseEntity<JobApplicationDTO> getJobApplicationById(@PathVariable UUID id) {
        return ResponseEntity.ok(jobApplicationService.findJobApplicationById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new job application", description = "Creates a new job application with the provided details")
    public ResponseEntity<JobApplicationDTO> createJobApplication(@Valid @RequestBody JobApplicationDTO jobApplicationDTO) {
        JobApplicationDTO createdJobApplication = jobApplicationService.createJobApplication(jobApplicationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJobApplication);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a job application", description = "Updates a job application with the provided details")
    public ResponseEntity<JobApplicationDTO> updateJobApplication(@PathVariable UUID id, @Valid @RequestBody JobApplicationDTO jobApplicationDTO) {
        JobApplicationDTO updatedJobApplication = jobApplicationService.updateJobApplication(id, jobApplicationDTO);
        return ResponseEntity.ok(updatedJobApplication);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a job application", description = "Deletes a job application by ID")
    public ResponseEntity<Void> deleteJobApplication(@PathVariable UUID id) {
        jobApplicationService.deleteJobApplication(id);
        return ResponseEntity.ok().build();
    }
}
