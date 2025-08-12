package com.jobtracker.backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import com.jobtracker.backend.dto.UserDTO;
import com.jobtracker.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

/**
 * The objective of this controller is to provide REST APIs
 * for managing Users. 
 * This includes getting all users, getting a user by ID, creating a user, 
 * updating a user, and deleting a user.
 * 
 * The controller delegates the actual business logic to the UserService.
 */

/**
 * @RestController - This annotation marks this class as a web request handler.
 * It is a convenience annotation that is itself annotated with @Controller and @ResponseBody.
 * It is used to define a class as a web request handler, which is a class that handles web requests.
 * 
 * @RequestMapping("/api/users") - This annotation maps all the methods in this class
 * to the "/api/users" endpoint.
 * 
 * @Tag(name = "User", description = "APIs for User Management") - This annotation is used
 * to group related operations together. In this case, it groups all the operations related
 * to user management under the name "User". The description provides more information about
 * what the operations in this group do.
 */

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "APIs for User Management")
public class UserController {
    /**
    * The UserService variable is used to delegate the actual business logic to the service layer.
    * The service layer encapsulates the business logic of the application and provides an interface
    * for the controller to interact with the business logic.
    * 
    * The UserService is injected into the UserController through the constructor.
    * This is a best practice as it allows the controller to be loosely coupled with the service layer.
    * 
    * By using constructor injection, the controller does not need to know how to create an instance
    * of the UserService. This makes the code more modular and easier to test.
*/
   private final UserService userService;

   public UserController(UserService userService) {
      this.userService = userService;
   }
   /**
    * @GetMapping - This annotation maps the getAllUsers() method to the "/api/users" endpoint.
    * 
    * @Operation - This annotation is used to document the getAllUsers() method.
    * It provides a summary and description of the method.
    * 
    * @return - This annotation is used to document the return type of the method.
    * It provides information about the type of data that the method returns.
    */
   @GetMapping
   @Operation(summary = "Get all users" , description="Retrieves a list of all users")
   
   /**
    * The getAllUsers() method is used to retrieve a list of all users from the database.
    * 
    * It delegates the actual business logic to the UserService, which encapsulates the
    * business logic of the application and provides an interface for the controller
    * to interact with the business logic.
    * 
    * The UserService uses the UserRepository to retrieve a list of all users from the
    * database. The list of users is then converted to a list of UserDTO objects, which
    * are returned to the controller.
    * 
    * The controller then returns the list of UserDTO objects as a ResponseEntity.
    * 
    * The ResponseEntity is a Spring class that is used to represent the entire HTTP
    * response. It contains the status code, headers and body of the response.
    * 
    * The status code is set to 200 OK, which means that the request was successful.
    * The body of the response is the list of UserDTO objects.
    */

   public ResponseEntity<List<UserDTO>> getAllUsers() {
      return ResponseEntity.ok(userService.findAllUsers());
   }

   /**
    * @GetMapping - This annotation maps the getUserById() method to the "/api/users/{id}" endpoint.
    * 
    * @Operation - This annotation is used to document the getUserById() method.
    * It provides a summary and description of the method.
    * 
    * @return - This annotation is used to document the return type of the method.
    * It provides information about the type of data that the method returns.
    */
   @GetMapping("/{id}")
   @Operation(summary = "Get user by ID" , description="Retrieves a user by ID")
   public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
      return ResponseEntity.ok(userService.findUserById(id));
   }

   /**
    * @PostMapping - This annotation maps the createUser() method to the "/api/users" endpoint.
    * 
    * @Operation - This annotation is used to document the createUser() method.
    * It provides a summary and description of the method.
    * 
    * @return - This annotation is used to document the return type of the method.
    * It provides information about the type of data that the method returns.
    */
    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details")
    public ResponseEntity<UserDTO> createUser(
            // The @Valid annotation is used to enable validation of the UserDTO object.
            // It is placed on the method parameter and is used to specify that the
            // UserDTO object should be validated before the method is invoked.
            // The validation is performed by the Spring framework and uses the
            // Bean Validation API (JSR-303) to validate the object.
            // The validation is based on the constraints that are specified on the
            // UserDTO class using annotations such as @NotBlank, @Size, @Email etc.
            @Valid @RequestBody UserDTO userDTO,

            // The @RequestParam annotation is used to inject the value of a request parameter into the method.
            // In this case, the value of the "password" request parameter is injected into the method.
            // The "required = false" attribute specifies that the request parameter is optional.
            // If the request parameter is not provided, the value of the parameter will be null.
            @RequestParam(required = false) String password) {
        UserDTO createdUser = userService.createUser(userDTO, password);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * @PutMapping - This annotation maps the updateUser() method to the "/api/users/{id}" endpoint.
     * 
     * @Operation - This annotation is used to document the updateUser() method.
     * It provides a summary and description of the method.
     * 
     * @return - This annotation is used to document the return type of the method.
     * It provides information about the type of data that the method returns.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Updates a user with the provided details")
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id, @Valid @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        UserDTO updatedUser = userService.updateUser(userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * @DeleteMapping - This annotation maps the deleteUser() method to the "/api/users/{id}" endpoint.
     * 
     * @Operation - This annotation is used to document the deleteUser() method.
     * It provides a summary and description of the method.
     * 
     * @return - This annotation is used to document the return type of the method.
     * It provides information about the type of data that the method returns.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Deletes a user with the provided ID")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
