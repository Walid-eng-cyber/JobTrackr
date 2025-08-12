package com.jobtracker.backend.service;
import com.jobtracker.backend.dto.UserDTO;
import com.jobtracker.backend.entity.User;
import com.jobtracker.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// Collectors is a final class that extends Object, and contains various useful methods for reducing and collecting the elements of a Stream.

@Service
@RequiredArgsConstructor
public class UserService {
    // we need a UserRepository to interact with the database.
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    /**
     * This method retrieves all users from the database and returns them as a
     * list of UserDTO objects.
     * 
     * It uses the Spring Data JPA method findAll() to retrieve all users from the
     * database and then uses the Stream API to convert the list of User objects to
     * a list of UserDTO objects.
     * 
     * The map() method is used to call the convertToDTO() method on each User object
     * in the list. This method takes a User object and returns a UserDTO object with
     * the same data.
     * 
     * The collect() method is then used to collect the results of the map() method
     * into a list of UserDTO objects.
     */
    // We convert the User objects to UserDTO objects when retrieving them from the
    // database because we don't want to expose the password field in the User
    // object. The UserDTO object does not contain the password field, so if we
    // return a list of UserDTO objects, the clients will not have access to the
    // password field. This is a security measure to prevent unauthorized access
    // to the passwords of the users.
    // The @Transactional annotation is a Spring annotation that marks a method as
    // transactional. When a method annotated with @Transactional is called, Spring
    // will automatically create a transaction and commit it when the method
    // completes successfully. If the method throws an exception, Spring will
    // automatically rollback the transaction.

    // The readOnly attribute of the @Transactional annotation is a boolean that
    // indicates whether the method is allowed to write to the database. If the
    // attribute is set to true, the method is only allowed to read from the
    // database and any attempt to write to the database will result in an
    // exception. If the attribute is set to false (its default value), the method
    // is allowed to write to the database.

    // In the context of the findAllUsers() method, the @Transactional annotation
    // is used to make sure that the retrieval of the users is done in a
    // transactional way. This means that if the method throws an exception, the
    // transaction will be rolled back and the users will not be retrieved.


    @Transactional(readOnly = true)
    public List<UserDTO> findAllUsers(){
        return userRepository.findAll().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
    }



    /**
     * This method retrieves a user by its ID from the database.
     * 
     * It uses the Spring Data JPA method findById() to retrieve the user from the
     * database and then uses the Optional class to handle the case where the user
     * is not found.
     * 
     * The map() method is used to call the convertToDTO() method on the User object
     * to convert it to a UserDTO object.
     * 
     * The orElseThrow() method is used to throw a RuntimeException if the user is
     * not found.
     */
        
    // The findById() method is used to retrieve a user by its ID from the database.
    // This method is not defined in the UserRepository interface, but it is
    // available in the JpaRepository interface which is extended by the
    // UserRepository interface.

    @Transactional(readOnly = true)
    public UserDTO findUserById(UUID id){
        return userRepository.findById(id)
        .map(this::convertToDTO)
        .orElseThrow(() -> new RuntimeException("User not found"));
    }
    

    
    /**
     * This method creates a new user in the database.
     * 
     * The method takes a UserDTO object as a parameter which contains the
     * information of the user to be created. The method also takes a password
     * string parameter which is the password of the user to be created.
     * 
     * The method first creates a new User object and sets its fields based on the
     * information in the UserDTO object. The password of the user is then
     * encrypted using the password encoder and set in the User object.
     * 
     * If the roles of the user are not provided in the UserDTO object, the method
     * sets the default role of the user as USER.
     * 
     * The method then calls the save() method of the UserRepository to save the
     * User object to the database.
     * 
     * The method finally returns the created User object as a UserDTO object.
     */
    
    /**
     * theory
     * This method creates a new user in the database.
     * 
     * The client sends a UserDTO object as a parameter which contains the
     * information of the user to be created.
     * 
     * The method first converts the UserDTO object to a User entity using the
     * convertToUser() method.
     * 
     * The method then calls the save() method of the UserRepository to save the
     * User entity to the database.
     * 
     * The method finally returns the created User entity as a UserDTO object by
     * calling the convertToDTO() method.
     */

    @Transactional
    public UserDTO createUser(UserDTO userDTO, String password) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(password));
        
        // Set default role if none provided
        if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
            user.setRole(java.util.Collections.singletonList(User.Role.USER));
        } else {
            // Convert List<String> to List<Role>
            List<User.Role> roles = userDTO.getRoles().stream()
                .map(String::toUpperCase)
                .map(User.Role::valueOf)
                .collect(java.util.stream.Collectors.toList());
            user.setRole(roles);
        }
        
        // Save the user to get the generated ID and timestamps
        User savedUser = userRepository.save(user);
        
        // Convert the saved user back to DTO
        UserDTO savedUserDTO = convertToDTO(savedUser);
        
        // Set the roles in the DTO
        if (savedUser.getRole() != null) {
            List<String> roleNames = savedUser.getRole().stream()
                .map(Enum::name)
                .collect(java.util.stream.Collectors.toList());
            savedUserDTO.setRoles(roleNames);
        }
        
        if (savedUser.getCreatedAt() != null) {
            savedUserDTO.setCreatedAt(savedUser.getCreatedAt().toString());
        }
        
        return savedUserDTO;
    }


    /**
     * This method updates an existing user in the database.
     * 
     * The method takes a UserDTO object as a parameter which contains the
     * information of the user to be updated.
     * 
     * The method first finds the user by its ID using the findById() method of the
     * UserRepository.
     * 
     * The method then updates the user's name and email using the information in the
     * UserDTO object.
     * 
     * The method finally returns the updated User object as a UserDTO object.
     */
    @Transactional
    public UserDTO updateUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return convertToDTO(userRepository.save(user));
    }

    /**
     * This method deletes an existing user from the database.
     * 
     * The method takes a UUID parameter which is the ID of the user to be deleted.
     * 
     * The method first checks if the user exists in the database using the
     * existsById() method of the UserRepository.
     * 
     * If the user does not exist, the method throws a RuntimeException with a
     * message indicating that the user was not found.
     * 
     * If the user exists, the method calls the deleteById() method of the
     * UserRepository to delete the user from the database.
     */
    @Transactional
    public void deleteUser(UUID id) {
        if(!userRepository.existsById(id)){
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
    //Helpers
    /**
     * This method converts a User object to a UserDTO object.
     * 
     * It takes a User object as input and returns a UserDTO object with the same data.
     * 
     * The UserDTO object does not contain the password field, so if we return a list of
     * UserDTO objects, the clients will not have access to the password field. This is a
     * security measure to prevent unauthorized access to the passwords of the users.
     */

    private UserDTO convertToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
    // Convert List<Role> to List<String>
    if (user.getRole() != null) {
        List<String> roleNames = user.getRole().stream()
            .map(Enum::name)
            .collect(Collectors.toList());
        userDTO.setRoles(roleNames);
    }
    
    if (user.getCreatedAt() != null) {
        userDTO.setCreatedAt(user.getCreatedAt().toString());
    }
    
        return userDTO;
    }
}
