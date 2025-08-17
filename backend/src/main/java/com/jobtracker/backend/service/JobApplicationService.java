package com.jobtracker.backend.service;

import com.jobtracker.backend.dto.JobApplicationDTO;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.jobtracker.backend.repository.JobApplicationRepositry;
import org.springframework.transaction.annotation.Transactional;
import com.jobtracker.backend.entity.JobApplication;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobApplicationService {
    //We need repository to interact with the database. 
    //We can use the repository to create, read, update and delete data from the database.

    private final JobApplicationRepositry jobApplicationRepositry;
    
    @Transactional(readOnly = true)
    public List<JobApplicationDTO> findAllJobApplications() {
        return jobApplicationRepositry.findAll().stream().map(this::convertToJobApplicationDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public JobApplicationDTO findJobApplicationById(UUID id) {
        return jobApplicationRepositry.findById(id).map(this::convertToJobApplicationDTO).orElse(null);
    }
    @Transactional
    public JobApplicationDTO createJobApplication(JobApplicationDTO jobApplicationDTO) {
        JobApplication jobApplication = convertToJobApplication(jobApplicationDTO);
        // Set any additional fields or validations here
        JobApplication savedApplication = jobApplicationRepositry.save(jobApplication);
        return convertToJobApplicationDTO(savedApplication);
    }
    @Transactional
public JobApplicationDTO updateJobApplication(UUID id, JobApplicationDTO jobApplicationDTO) {
    return jobApplicationRepositry.findById(id)
        .map(existingJobApplication -> {
            existingJobApplication.setTitle(jobApplicationDTO.getTitle());
            existingJobApplication.setCompany(jobApplicationDTO.getCompany());
            existingJobApplication.setLocation(jobApplicationDTO.getLocation());
            existingJobApplication.setDescription(jobApplicationDTO.getDescription());
            
            JobApplication updatedApplication = jobApplicationRepositry.save(existingJobApplication);
            return convertToJobApplicationDTO(updatedApplication);
        })
        .orElse(null);  // Returns null if not found
}

@Transactional
public boolean deleteJobApplication(UUID id) {
    return jobApplicationRepositry.findById(id)
        .map(jobApplication -> {
            jobApplicationRepositry.delete(jobApplication);
            return true;  // Deletion was successful
        })
        .orElse(false);  // Job application didn't exist, return false
}
    //Helpers
    private JobApplicationDTO convertToJobApplicationDTO(JobApplication jobApplication) {
        JobApplicationDTO jobApplicationDTO = new JobApplicationDTO();
        jobApplicationDTO.setId(jobApplication.getId());
        jobApplicationDTO.setTitle(jobApplication.getTitle());
        jobApplicationDTO.setCompany(jobApplication.getCompany());
        jobApplicationDTO.setLocation(jobApplication.getLocation());
        jobApplicationDTO.setDescription(jobApplication.getDescription());
        jobApplicationDTO.setStatus(jobApplication.getStatus());
        
        // Add null checks for timestamps
        if (jobApplication.getCreatedAt() != null) {
            jobApplicationDTO.setCreatedAt(jobApplication.getCreatedAt().toString());
        }
        if (jobApplication.getUpdatedAt() != null) {
            jobApplicationDTO.setUpdatedAt(jobApplication.getUpdatedAt().toString());
        }
        
        // Set userId if user is not null
        if (jobApplication.getUser() != null) {
            jobApplicationDTO.setUserId(jobApplication.getUser().getId());
        }
        
        return jobApplicationDTO;
    }
    private JobApplication convertToJobApplication(JobApplicationDTO dto) {
        JobApplication jobApplication = new JobApplication();
        jobApplication.setTitle(dto.getTitle());
        jobApplication.setCompany(dto.getCompany());
        jobApplication.setLocation(dto.getLocation());
        jobApplication.setDescription(dto.getDescription());
        jobApplication.setStatus(dto.getStatus());
        // Note: createdAt and updatedAt are automatically managed by JPA
        return jobApplication;
    }
}
