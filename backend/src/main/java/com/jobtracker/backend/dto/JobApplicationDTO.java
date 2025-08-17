package com.jobtracker.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
/**
 * This class is a Data Transfer Object (DTO) for job application operations.
 * It is used to transfer data between the service layer and the controller layer.
 * It contains the data that is relevant for the job application operations.
 */

@Data
@Schema(description = "Data Transfer Object for JobApplication operations")
public class JobApplicationDTO {
    
    @Schema(description = "Unique identifier of the job application", example = "123e4567-e89b-12d3-a456-426614174000", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;
    
    @Schema(description = "Title of the job application", example = "Software Engineer", required = true)
    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    private String title;
    
    @Schema(description = "Company of the job application", example = "Tech Corp", required = true)
    @NotBlank(message = "Company is required")
    @Size(min = 2, max = 100, message = "Company must be between 2 and 100 characters")
    private String company;
    
    @Schema(description = "Location of the job application", example = "New York, NY", required = true)
    @NotBlank(message = "Location is required")
    @Size(min = 2, max = 100, message = "Location must be between 2 and 100 characters")
    private String location;
    
    @Schema(description = "Description of the job application", example = "Software Engineer", required = true)
    @NotBlank(message = "Description is required")
    @Size(min = 2, max = 100, message = "Description must be between 2 and 100 characters")
    private String description;
    
    @Schema(description = "Status of the job application", example = "Software Engineer", required = true)
    @NotBlank(message = "Status is required")
    @Size(min = 2, max = 100, message = "Status must be between 2 and 100 characters")
    private String status;
    
    @Schema(description = "Timestamp when the job application was created", example = "2025-08-06T10:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private String createdAt;

    @Schema(description = "Timestamp when the job application was updated", example = "2025-08-06T10:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private String updatedAt;
    
    @Schema(description = "User ID of the job application", example = "123e4567-e89b-12d3-a456-426614174000", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID userId;

    //setter for created at and updatedat
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

 

}
