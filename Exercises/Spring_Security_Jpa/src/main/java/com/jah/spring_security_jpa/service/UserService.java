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
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    public boolean usernameExists(String username) {
        Optional<User> user = userRepository.findByUserName(username);
        return user.isPresent();
    }

    @Transactional
    public void registerUser(RegistrationForm form) throws Exception {
        if (usernameExists(form.getUsername())) {
            throw new Exception("Username already exists");
        }

        User user = new User();
        user.setUserName(form.getUsername());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        // Set other user details as needed
        user.setActive(false);
        user.setRoles("ROLE_USER");
        userRepository.save(user);

    }

    @Transactional
    public void updateUserDetails(User user, boolean activationStatus, String password) {
        user.setActive(activationStatus);
        if (!password.isEmpty()) {
            BCryptPasswordEncoder bCpasswordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = bCpasswordEncoder.encode(password);
            user.setPassword(encodedPassword);
        }
        userRepository.save(user);

    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByName(String name) {
        return userRepository.findByUserName(name).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

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
