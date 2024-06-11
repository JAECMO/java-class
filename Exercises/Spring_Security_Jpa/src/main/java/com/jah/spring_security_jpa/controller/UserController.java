/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.controller;

import com.jah.spring_security_jpa.models.MyUserDetails;
import com.jah.spring_security_jpa.models.Tag;
import com.jah.spring_security_jpa.models.User;
import com.jah.spring_security_jpa.models.UserPost;
import com.jah.spring_security_jpa.repositories.PostRepository;
import com.jah.spring_security_jpa.repositories.UserPostRepository;
import com.jah.spring_security_jpa.repositories.UserRepository;
import com.jah.spring_security_jpa.service.ImageService;
import com.jah.spring_security_jpa.service.MyUserDetailsService;
import com.jah.spring_security_jpa.service.TagService;
import com.jah.spring_security_jpa.service.UserService;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author drjal
 */
@Controller
@SessionAttributes("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PostRepository posts;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserPostRepository userPostRepository;

    @Autowired
    ImageService imageService;

    @RequestMapping("/user")
    public String user(Model model, Authentication authentication, HttpSession session) {
        List<Tag> tagList = tagService.getAllTags();

        // Check if the user has the ROLE_ADMIN
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        int userId = userDetails.getUserId();
        model.addAttribute("userId", userId);
        model.addAttribute("tags", tagList);
        model.addAttribute("isAdmin", isAdmin);

        String postSubmittedSuccess = (String) session.getAttribute("postSubmittedSuccess");
        if (postSubmittedSuccess != null) {
            model.addAttribute("postSubmittedSuccess", postSubmittedSuccess);
            session.removeAttribute("postSubmittedSuccess");
        } else {
            model.addAttribute("postSubmittedSuccess", "");
        }

        return "user";
    }

    @RequestMapping("/updateUser")
    public String showUserUpdateForm(int id, Model model) {
        String role;
        // Fetch the user details by ID and pass them to the update form
        User user = userService.getUserById(id);
        role = user.getRoles();

        model.addAttribute("role", role);
        model.addAttribute("user", user);
        return "updateUser";
    }

    @PostMapping("/updateUser")
    public String updateUserDetails(@RequestParam int id, @RequestParam String username, @RequestParam String password, @RequestParam boolean activationStatus) {
        // Retrieve user by ID and update details
        User user = userService.getUserById(id);
        if (user != null) {
            user.setUserName(username);
            userService.updateUserDetails(user, activationStatus, password);
        }
        return "redirect:/userList";
    }

    @RequestMapping("/userList")
    public String userList(@RequestParam(name = "registrationSuccess", required = false) String registrationSuccess, Model model) {
        // Fetch the list of all users
        List<User> userList = userService.getAllUsers();

        // Sort the userList based on the 'active' status
        Collections.sort(userList, Comparator.comparing(User::isActive).reversed());

        model.addAttribute("users", userList);
        // Add the registrationSuccess message to the model if it's not null or empty
        if (registrationSuccess != null && !registrationSuccess.isEmpty()) {
            model.addAttribute("registrationSuccess", registrationSuccess);
        }
        return "userList";
    }

    @Transactional
    @RequestMapping("/removeUser")
    public String removeUser(@RequestParam int id) {
        List<UserPost> userPostsByUser = userPostRepository.findUserPostsByUserUserId(id);
        String error;
        User user;

        user = userRepository.findById(id).get();

        if ("ROLE_ADMIN".equals(user.getRoles())) {
            error = "Admin cannot be deleted";
            return "redirect:/errorImage?error=" + error;
        }

        if (!userPostsByUser.isEmpty()) {
            user.setDeleted(true);
            user.setPassword("");
            user.setActive(false);
            userRepository.save(user);
        } else {
            userRepository.deleteUserByUserId(id);
        }

        return "redirect:/userList";
    }

}
