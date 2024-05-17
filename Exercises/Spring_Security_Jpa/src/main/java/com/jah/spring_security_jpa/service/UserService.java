/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.service;

import com.jah.spring_security_jpa.models.RegistrationForm;
import com.jah.spring_security_jpa.models.User;
import com.jah.spring_security_jpa.repositories.UserRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author drjal
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(RegistrationForm form) {
        User user = new User();
        user.setUserName(form.getUsername());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        // Set other user details as needed
        user.setActive(false);
        user.setRoles("ROLE_USER");
        userRepository.save(user);
    }

    @Transactional
    public void updateUserDetails(User user, boolean activationStatus) {
        // Encode the password before updating
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//encodedPassword);

//        userRepository.updateUserDetails(
//                user.getId(),
//                user.getUserName());
// Ensure that the entity is in a managed state before calling update
        User managedUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + user.getUserId()));

        // Update the managed user
        managedUser.setUserName(user.getUserName());
        managedUser.setActive(activationStatus);
        userRepository.save(managedUser);

    }

    public User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    public User getUserByName(String name) {
        return userRepository.findByUserName(name).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

//    public void updateUserDetails(int userId, String newUsername, String password) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    public boolean verifyPassword(String username, String password) {
        User user = getUserByName(username);

        if (user != null) {
            // Compare the entered password with the stored hashed password
            return passwordEncoder.matches(password, user.getPassword());
        }

        return false; // User not found
    }

    public boolean isActivationPending(String username, String password) {
        User user = getUserByName(username);
        boolean passTest = verifyPassword(username, password);
        if (user != null && !passTest) {
            user = null;
        }
        return user != null && !user.isActive();
    }
}
