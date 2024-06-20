/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.myblog.controller;

import com.jah.myblog.dto.AdminPhoto;
import com.jah.myblog.models.AdminProfile;
import com.jah.myblog.models.MyUserDetails;
import com.jah.myblog.models.Tag;
import com.jah.myblog.repositories.AdminPhotoRepository;
import com.jah.myblog.repositories.PostRepository;
import com.jah.myblog.service.AdminProfileService;
import com.jah.myblog.service.ImageService;
import com.jah.myblog.service.MyUserDetailsService;
import com.jah.myblog.service.PostService;
import com.jah.myblog.service.TagService;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author drjal
 */
@Controller
@SessionAttributes("user")
public class AdminController {

    @Autowired
    PostRepository posts;

    @Autowired
    private PostService postService;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    private TagService tagService;

    @Autowired
    private AdminPhotoRepository adminPhotoRepo;

    @Autowired
    ImageService imageService;

    @Autowired
    private AdminProfileService adminProfileService;

    @RequestMapping("/admin")
    public String admin(Model model, HttpSession session) {

        // Get authentication details from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        int userId = userDetails.getUserId();
        List<Tag> tagList = tagService.getAllTags();

        model.addAttribute("userId", userId);
        model.addAttribute("tags", tagList);
        model.addAttribute("isAdmin", true);

        postService.addPostSuccessAttributes(session, model);
        return "admin";
    }

    @RequestMapping("/editAdminProfile")
    public String editAdminProfile(Model model) {
        Boolean changeImage = false;
        AdminProfile adminProfile;

        // Load the default image
        String defaultBase64Image = imageService.getDefaultBase64Image();
        // Load the Admin image
        String base64AdminImage = imageService.getAdminBase64Image(defaultBase64Image);
        //
        adminProfile = adminProfileService.getOrCreateAdminProfile();

        model.addAttribute("adminProfile", adminProfile);
        model.addAttribute("changeImage", changeImage);
        model.addAttribute("base64Image", base64AdminImage);

        return "editAdminProfile";
    }

    @PostMapping("/updateAdminProfile")
    public String updateAdminProfile(Model model, @RequestParam("changeImage") Boolean changeImage, @RequestParam String adminName, @RequestParam String body, @RequestParam("image") MultipartFile image) {

        AdminProfile existingAdminProfile = adminProfileService.getOrCreateAdminProfile();

        AdminPhoto savedAdminPhoto = null;
        AdminPhoto existingAdminPhoto = adminPhotoRepo.findFirstByOrderByAdminImageIdAsc();

        try {

            byte[] imageData;
            imageData = adminProfileService.validateAndProcessImage(image);
            savedAdminPhoto = adminProfileService.saveAdminPhoto(imageData, changeImage, existingAdminPhoto, savedAdminPhoto);

        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "redirect:/errorImage?error=" + ex.getMessage();
        } catch (IOException ex) {
            model.addAttribute("error", "Error occurred while reading image file");
            return "redirect:/errorImage?error=Error occurred while reading image file";
        }

        adminPhotoRepo.save(savedAdminPhoto);
        adminProfileService.saveAdminProfile(adminName, body, savedAdminPhoto, existingAdminProfile);

        return "redirect:/index";

    }
}
