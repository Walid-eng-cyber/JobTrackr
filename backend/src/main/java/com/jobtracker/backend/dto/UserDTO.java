// src/main/java/com/jobtracker/backend/dto/UserDTO.java
package com.jobtracker.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.UUID;

import javax.management.relation.Role;

@Data
@Schema(description = "Data Transfer Object for User operations")
public class UserDTO {
    
    @Schema(description = "User ID", example = "123e4567-e89b-12d3-a456-426614174000", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Schema(description = "Full name of the user", example = "John Doe", required = true)
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Email address of the user", example = "john.doe@example.com", required = true)
    private String email;
    
    @Schema(description = "List of user roles", example = "[\"USER\"]", accessMode = Schema.AccessMode.READ_ONLY)
    private List<String> roles;
    
    @Schema(description = "Timestamp when the user was created", example = "2025-08-06T10:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private String createdAt;
}