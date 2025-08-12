package com.jobtracker.backend.entity;
// Jakarta Persistence API (JPA) is a specification for persisting, accessing, and managing data between Java objects/classes and a relational database.

import jakarta.persistence.*;
// The @Entity annotation marks the class as an entity, which means it will be mapped to a database table.
import io.swagger.v3.oas.annotations.media.Schema;

// The @Validated annotation is used to enable method validation.
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import lombok.*;
// Lombok is a Java library that automatically generates common methods such as getters, setters, and constructors.

import java.time.LocalDateTime;
// The LocalDateTime class represents a date and time without a time-zone in the ISO-8601 calendar system.

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;



import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User entity represents a user in the system")
// @Entity annotation marks the class as an entity, which means it will be mapped to a database table.
// @Table annotation specifies the database table name, which is the same as the class name in this case.
// @Data annotation is a Lombok annotation that automatically generates getters and setters for the fields.
// @NoArgsConstructor and @AllArgsConstructor are Lombok annotations that automatically generate a no-argument constructor and an all-argument constructor, respectively.

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(
        description = "Unique identifier of the user",
        example = "123e4567-e89b-12d3-a456-426614174000",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID id;
    // The @Id annotation marks the field as the primary key of the entity.
    // The @GeneratedValue annotation specifies how the primary key is generated.
    // The GenerationType.UUID strategy generates a UUID as the primary key.
    // A UUID (Universally Unique Identifier) is a 128-bit number used to identify information in computer systems.
    // It is usually represented as a 32-digit hexadecimal number, separated by hyphens.
    // UUIDs are often used to identify records in a database, as they are unique and can be generated randomly.
    // There are different versions of UUIDs, such as UUIDv4, which is a random UUID.
    // The @GeneratedValue(strategy = GenerationType.UUID) annotation uses the UUIDv4 algorithm to generate a random UUID as the primary key.

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Schema(
        description = "Full name of the user",
        example = "John Doe",
        required = true
    )    
    private String name;
    // The @NotBlank annotation ensures that the field is not null or empty.
    // The @Size annotation ensures that the field is within a specified range of values.
    // The message attribute specifies the error message to be displayed if the validation fails.
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(unique = true , nullable = false)
    @Schema(
        description = "Email address of the user",
        example = "john.doe@example.com",
        required = true
    )
    private String email;
    // The @Email annotation ensures that the field is a valid email address.
    // The message attribute specifies the error message to be displayed if the validation fails.
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Column(nullable = false)
    @JsonIgnore
    @Schema(
        description = "User's password (will be hashed)",
        example = "securePassword123!",
        accessMode = Schema.AccessMode.WRITE_ONLY
    )
    private String password;
    // The @Size annotation ensures that the field is within a specified range of values.
    // The message attribute specifies the error message to be displayed if the validation fails.
    // The @Column annotation specifies the database column name, which is the same as the field name in this case.
    // The @JsonIgnore annotation is used to exclude the field from being serialized when the entity is converted to JSON.



    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Schema(
        description = "List of user roles",
        example = "[\"USER\"]",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    @Column(name = "role")
    // The @Enumerated annotation specifies how the enum values are stored in the database.
    // EnumType.STRING means that the enum values are stored as strings in the database.
    // The @ElementCollection annotation specifies that this field is a collection of elements.
    // The @CollectionTable annotation specifies the name of the table that stores the collection, and the joinColumns attribute specifies the column that links the collection table to the user table.
    // The @Column annotation specifies the name of the column in the collection table that stores the role.
    
    private List<Role> role = new ArrayList<>() ;
    // The @Enumerated annotation specifies how the enum values are stored in the database.

    @CreationTimestamp
    @Column(name = "created_at" , nullable = false , updatable = false)
    @Schema(
        description = "Timestamp when the user was created",
        example = "2025-08-06T10:00:00",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime createdAt;
    
    /**
     * An enum (short for enumeration) is a special type of data that allows us to define a set of named values.
     * In this case, the Role enum defines two named values: ADMIN and USER.
     * The enum is used to store the role of a user in the database.
     * The Role enum is a special type of data that allows us to define a set of named values.
     * It is a more explicit and readable way of storing the role of a user.
     * The Role enum can be used in the code to check the role of a user and to grant access to certain operations.
     * For example, if a user is an ADMIN, they can perform all operations, while a USER can only perform operations on their own data.
     */
    

    public enum Role {
        @Schema(description = "Administrator with full access")
        ADMIN,
        
        @Schema(description = "Regular user with standard permissions")
        USER
    }
  
    

    
}
