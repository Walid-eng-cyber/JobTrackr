package com.jobtracker.backend.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "job_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "JobApplication entity represents a job application in the system")
public class JobApplication {
    
    // The @Id annotation marks the field as the primary key of the entity.
    // The @GeneratedValue annotation specifies how the primary key is generated.
    // The GenerationType.UUID strategy generates a UUID as the primary key.
    // A UUID (Universally Unique Identifier) is a 128-bit number used to identify information in computer systems.
    // It is usually represented as a 32-digit hexadecimal number, separated by hyphens.
    // UUIDs are often used to identify records in a database, as they are unique and can be generated randomly.
    // There are different versions of UUIDs, such as UUIDv4, which is a random UUID.
    // The @GeneratedValue(strategy = GenerationType.UUID) annotation uses the UUIDv4 algorithm to generate a random UUID as the primary key.
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(
        description = "Unique identifier of the job application",
        example = "123e4567-e89b-12d3-a456-426614174000",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID id;

    // The @NotBlank annotation ensures that the field is not null or empty.
    // The @Size annotation ensures that the field is within a specified range of values.
    // The message attribute specifies the error message to be displayed if the validation fails.
    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    @Schema(
        description = "Title of the job application",
        example = "Software Engineer",
        required = true
    )
    private String title;

    // The @NotBlank annotation ensures that the field is not null or empty.
    // The @Size annotation ensures that the field is within a specified range of values.
    // The message attribute specifies the error message to be displayed if the validation fails.
    @NotBlank(message = "Company is required")
    @Size(min = 2, max = 100, message = "Company must be between 2 and 100 characters")
    @Schema(
        description = "Company of the job application",
        example = "Tech Corp",
        required = true
    )
    private String company;

    // The @NotBlank annotation ensures that the field is not null or empty.
    // The @Size annotation ensures that the field is within a specified range of values.
    // The message attribute specifies the error message to be displayed if the validation fails.
    @NotBlank(message = "Location is required")
    @Size(min = 2, max = 100, message = "Location must be between 2 and 100 characters")
    @Schema(
        description = "Location of the job application",
        example = "New York, NY",
        required = true
    )
    private String location;

    // The @NotBlank annotation ensures that the field is not null or empty.
    // The @Size annotation ensures that the field is within a specified range of values.
    // The message attribute specifies the error message to be displayed if the validation fails.
    @NotBlank(message = "Description is required")
    @Size(min = 2, max = 100, message = "Description must be between 2 and 100 characters")
    @Schema(
        description = "Description of the job application",
        example = "Software Engineer",
        required = true
    )
    private String description;

    // The @NotBlank annotation ensures that the field is not null or empty.
    // The @Size annotation ensures that the field is within a specified range of values.
    // The message attribute specifies the error message to be displayed if the validation fails.
    @NotBlank(message = "Status is required")
    @Size(min = 2, max = 100, message = "Status must be between 2 and 100 characters")
    @Schema(
        description = "Status of the job application",
        example = "Software Engineer",
        required = true
    )
    private String status;

    // The @CreationTimestamp annotation is used to automatically set the created_at column
    // to the current timestamp when the job application is created.    
    @CreationTimestamp
    @Column(name = "created_at" , nullable = false , updatable = false)
    @Schema(
        description = "Timestamp when the job application was created",
        example = "2025-08-06T10:00:00",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime createdAt;

    // The @UpdateTimestamp annotation is used to automatically update the updated_at column
    // with the current timestamp every time the job application is updated.
    @UpdateTimestamp
    @Column(name = "updated_at" , nullable = false)
    @Schema(
        description = "Timestamp when the job application was updated",
        example = "2025-08-06T10:00:00",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime updatedAt;


    // The @ManyToOne annotation is used to define a many-to-one relationship between the JobApplication
    // entity and the User entity. The @JoinColumn annotation is used to specify the column name in the
    // job_applications table that is used to store the foreign key to the users table. The
    // @Schema annotation is used to provide a description of the user field in the API documentation.

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Schema(
        description = "User who created the job application",
        example = "123e4567-e89b-12d3-a456-426614174000",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private User user;


}
