/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.myblog.controller;

import com.jah.myblog.dto.DefaultImage;
import com.jah.myblog.repositories.PostRepository;
import com.jah.myblog.service.AdminProfileService;
import com.jah.myblog.service.DefaultImageService;
import com.jah.myblog.service.ImageService;
import com.jah.myblog.service.MyUserDetailsService;
import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DefaultImageController {

    @Autowired
    PostRepository posts;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    ImageService imageService;

    @Autowired
    private AdminProfileService adminProfileService;

    @Autowired
    private DefaultImageService defaultImageService;

    @RequestMapping("/defaultImage")
    public String defaultImage(Model model) {
        Boolean changeImage = false;

        // Load the default image
        String base64Image = defaultImageService.getDefaultBase64Image();

        // Add the HashSet to the model
        model.addAttribute("base64Image", base64Image);
        model.addAttribute("changeImage", changeImage);

        return "defaultImage";
    }

    @PostMapping("/addDefaultImage")
    public String addDefaultImage(Model model, @RequestParam("image") MultipartFile image, @RequestParam("changeImage") Boolean changeImage) {

        String base64Image = "";
        try {
            byte[] imageData;
            imageData = adminProfileService.validateAndProcessImage(image);
            DefaultImage savedDefaultImage = defaultImageService.addOrUpdateDefaultImage(imageData, changeImage);
            base64Image = Base64.getEncoder().encodeToString(savedDefaultImage.getDefaultImage());
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "redirect:/errorImage?error=" + ex.getMessage();
        } catch (IOException ex) {
            model.addAttribute("error", "Error occurred while reading image file");
            return "redirect:/errorImage?error=Error occurred while reading image file";
        } catch (Exception ex) {
        }
        model.addAttribute("base64Image", base64Image);
        model.addAttribute("changeImage", false);

        return "defaultImage";
    }

    @RequestMapping("/errorImage")
    public String errorImage(Model model, @RequestParam(required = false) String error) {

        model.addAttribute("error", error);
        return "errorImage";

    }

}
