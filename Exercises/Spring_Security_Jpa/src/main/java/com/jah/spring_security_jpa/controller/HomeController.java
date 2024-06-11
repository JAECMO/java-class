/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.controller;

import com.jah.spring_security_jpa.models.AdminProfile;
import com.jah.spring_security_jpa.models.Post;
import com.jah.spring_security_jpa.models.RegistrationForm;
import com.jah.spring_security_jpa.models.Tag;
import com.jah.spring_security_jpa.repositories.PostRepository;
import com.jah.spring_security_jpa.service.AdminProfileService;
import com.jah.spring_security_jpa.service.ImageService;
import com.jah.spring_security_jpa.service.MyUserDetailsService;
import com.jah.spring_security_jpa.service.PostService;
import com.jah.spring_security_jpa.service.UserService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    PostRepository posts;

    @Autowired
    private PostService postService;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    ImageService imageService;

    @Autowired
    private AdminProfileService adminProfileService;

    @RequestMapping("/index")
    public String home(Model model, @RequestParam(name = "tag", required = false) Integer tagId) {

        List<Post> filteredPosts = postService.filterPostsForIndex(tagId);

        Set<Tag> availableTags = new TreeSet<>();
        availableTags = postService.filterAvailableTagsForIndex();
        String defaultBase64Image = imageService.getDefaultBase64Image();
        Map<Integer, String> postIdWithBase64Image = imageService.getPostIdWithBase64Image(filteredPosts, defaultBase64Image);
        AdminProfile adminProfile = adminProfileService.getOrCreateAdminProfile();
        String base64AdminImage = imageService.getAdminBase64Image(defaultBase64Image);

        // Add attributes to the model
        model.addAttribute("adminProfile", adminProfile);
        model.addAttribute("base64AdminImage", base64AdminImage);
        model.addAttribute("postIdWithBase64Image", postIdWithBase64Image);
        model.addAttribute("allTags", availableTags);
        model.addAttribute("posts", filteredPosts);

        return "index";
    }

    @RequestMapping("/")
    public String home2(Model model) {
        return "redirect:/index";
    }

    @RequestMapping("/readMore")
    public String readMore(Model model, @RequestParam int id) {
        Post post = postService.getPostById(id);
        List<Post> uniquePost = new ArrayList();
        uniquePost.add(post);
        String defaultBase64Image = imageService.getDefaultBase64Image();

        Map<Integer, String> postIdWithBase64Image = imageService.getPostIdWithBase64Image(uniquePost, defaultBase64Image);

        // Add the HashSet to the model
        model.addAttribute("postIdWithBase64Image", postIdWithBase64Image);

        model.addAttribute(post);
        return "readMore";

    }

    @RequestMapping("/access-denied")
    public String access_denied(Model model) {
        String text = "You cannot access this page";
        model.addAttribute("text", text);
        return "access-denied";
    }

    @RequestMapping("/login")
    public String login(Model model, @RequestParam(name = "logout", required = false) String logout) {

        if (logout != null) {
            model.addAttribute("logout", "You have been logged out");
        }

        model.addAttribute("text", "Login");

        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout"; // Redirect to login page after logout
    }

    @GetMapping("/default")
    public String defaultAfterLogin(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin";
        } else {
            return "redirect:/user";
        }
    }

    @RequestMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegistrationForm());

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid RegistrationForm form, BindingResult result, Model model) {
        String registrationSuccess = "";
        if (result.hasErrors()) {

            return "register";
        }
        try {
            userService.registerUser(form);
            registrationSuccess = "User " + form.getUsername() + " was successfully added";

            return "redirect:/userList?registrationSuccess=" + registrationSuccess;
        } catch (Exception ex) {
            model.addAttribute("registrationError", ex.getMessage());
            return "register";
        }
    }

}
