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
    public JobApplication createJobApplication(JobApplication jobApplication) {
        return jobApplicationRepositry.save(jobApplication);
    }
    @Transactional
    public JobApplication updateJobApplication(JobApplication jobApplication) {
        return jobApplicationRepositry.save(jobApplication);
    }
    @Transactional
    public void deleteJobApplication(UUID id) {
        jobApplicationRepositry.deleteById(id);
    }
    //Helpers
    private JobApplicationDTO convertToJobApplicationDTO(JobApplication jobApplication) {
        JobApplicationDTO jobApplicationDTO = new JobApplicationDTO();
        jobApplicationDTO.setId(jobApplication.getId());
        jobApplicationDTO.setTitle(jobApplication.getTitle());
        jobApplicationDTO.setCompany(jobApplication.getCompany());
        jobApplicationDTO.setCreatedAt(jobApplication.getCreatedAt().toString());  // Convert to String
        jobApplicationDTO.setUpdatedAt(jobApplication.getUpdatedAt().toString());  // Convert to String
        return jobApplicationDTO;
    }
}
