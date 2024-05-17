/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.controller;

import com.jah.spring_security_jpa.dto.AdminPhoto;
import com.jah.spring_security_jpa.dto.DefaultImage;
import com.jah.spring_security_jpa.dto.PhotoDTO;
import com.jah.spring_security_jpa.dto.TempImage;
import com.jah.spring_security_jpa.models.AdminProfile;
import com.jah.spring_security_jpa.models.MyUserDetails;
import com.jah.spring_security_jpa.models.Post;
import com.jah.spring_security_jpa.models.RegistrationForm;
import com.jah.spring_security_jpa.models.Tag;
import com.jah.spring_security_jpa.models.User;
import com.jah.spring_security_jpa.models.UserPost;
import com.jah.spring_security_jpa.repositories.AdminPhotoRepository;
import com.jah.spring_security_jpa.repositories.AdminProfileRepository;
import com.jah.spring_security_jpa.repositories.DefaultImageRepository;
import com.jah.spring_security_jpa.repositories.PhotoDTORepository;
import com.jah.spring_security_jpa.repositories.PostRepository;
import com.jah.spring_security_jpa.repositories.TagRepository;
import com.jah.spring_security_jpa.repositories.TempImageRepository;
import com.jah.spring_security_jpa.repositories.UserPostRepository;
import com.jah.spring_security_jpa.repositories.UserRepository;
import com.jah.spring_security_jpa.service.MyUserDetailsService;
import com.jah.spring_security_jpa.service.PhotoDTOService;
import com.jah.spring_security_jpa.service.PostService;
import com.jah.spring_security_jpa.service.TagService;
import com.jah.spring_security_jpa.service.UserPostService;
import com.jah.spring_security_jpa.service.UserService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author drjal
 */
//@RestController
@Controller
@SessionAttributes("user")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

//      @GetMapping("index")
//    public String home() {
//        return ("<h1>Welcome</h1>");
//    }
//    @Autowired 
//    RegistrationService registrationService;
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PostRepository posts;

    @Autowired
    private TagRepository tags;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostService postService;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PhotoDTORepository photoRepo;

    @Autowired
    private DefaultImageRepository defaultImageRepo;

    @Autowired
    private PhotoDTOService photoService;
    
    @Autowired
    private AdminProfileRepository adminProfileRepo;
    
    @Autowired
    private AdminPhotoRepository adminPhotoRepo;
    
    @Autowired
    private UserPostRepository userPostRepository;
    
    @Autowired
    private UserPostService userPostService;
    
    @Autowired
    private TempImageRepository tempImageRepo;
    

    Set<ConstraintViolation<RegistrationForm>> violations = new HashSet<>();
    Set<ConstraintViolation<Post>> violationsP = new HashSet<>();

    @RequestMapping("/index")
    public String home(Model model, @RequestParam(name = "tag", required = false) Integer tagId) {
        String text = "HELLO";
        Date today = new Date();

        List<Post> filteredPosts;

        if (tagId != null) {
            // Retrieve posts associated with the selected tag
            Tag selectedTag = tagRepository.findById(tagId).orElse(null);
            if (selectedTag != null) {
                filteredPosts = selectedTag.getPosts().stream()
                        .filter(post -> post.isActive() && post.isApproved()
                        && postService.isDisplayDateValid(post, today) && postService.isExpiryDateValid(post, today))
                        .peek(post -> {
                            if (post.getCreationDate() == null) {
                                // Set the creationDate if it's the first time displaying the post
                                post.setCreationDate(new Date());
                                // Save the post to update the creationDate
                                posts.save(post);
                            }
                        })
                        .sorted((post1, post2) -> post2.getCreationDate().compareTo(post1.getCreationDate()))
                        .collect(Collectors.toList());
            } else {
                filteredPosts = null; // or any default behavior
            }
        } else {
            // If no tag selected, retrieve all posts
//            filteredPosts = posts.findAll().stream()
//                    .filter(post -> post.isActive() && post.isApproved()
//                    && postService.isDisplayDateValid(post, today) && postService.isExpiryDateValid(post, today))
//                    .peek(post -> {
//                        if (post.getCreationDate() == null) {
//                            // Set the creationDate if it's the first time displaying the post
//                            post.setCreationDate(new Date());
//                            // Save the post to update the creationDate
//                            posts.save(post);
//                        }
//                    })
//                    .collect(Collectors.toList());
filteredPosts = posts.findAll().stream()
        .filter(post -> post.isActive() && post.isApproved()
                && postService.isDisplayDateValid(post, today) && postService.isExpiryDateValid(post, today))
        .peek(post -> {
            if (post.getCreationDate() == null) {
                // Set the creationDate if it's the first time displaying the post
                post.setCreationDate(new Date());
                // Save the post to update the creationDate
                posts.save(post);
            }
        })
        // Order posts by most recent creationDate first
        .sorted((post1, post2) -> post2.getCreationDate().compareTo(post1.getCreationDate()))
        .collect(Collectors.toList());
        }

        // Retrieve all tags
        // List<Tag> allTags = tagRepository.findAll();
        Set<Tag> availableTags = new TreeSet<>();
        List<Post> availablePosts = posts.findAll().stream()
                .filter(post -> post.isActive() && post.isApproved()
                && postService.isDisplayDateValid(post, today) && postService.isExpiryDateValid(post, today)).collect(Collectors.toList());

        for (Post post : availablePosts) {
            for (Tag tag : post.getTags()) {
                availableTags.add(tag);
            }
        }
        
       
        

        HashMap<Integer, String> postIdWithBase64Image = new HashMap<>();

// Load the default image
        DefaultImage defaultImage = defaultImageRepo.findFirstByOrderByDefaultImageIdAsc();
        String defaultBase64Image = "";
        try {
            byte[] imageDefaultData = defaultImage.getDefaultImage();
            defaultBase64Image = imageDefaultData != null ? Base64.getEncoder().encodeToString(imageDefaultData) : "";
        } catch (Exception ex) {

        }

        for (Post post : filteredPosts) {
            // Retrieve PhotoDTO for the current post ID
            PhotoDTO photoDTO;

            photoDTO = photoRepo.findById(post.getImageId()).orElse(null);

            // Default base64 image in case photoDTO is null
            String base64Image = defaultBase64Image;
            if (photoDTO != null) {
                // Convert image data to base64
                byte[] imageData = photoDTO.getImage();
                base64Image = imageData != null ? Base64.getEncoder().encodeToString(imageData) : defaultBase64Image;
            }
            // Store post ID and base64 image in the HashMap
            postIdWithBase64Image.put(post.getPostId(), base64Image);
        }
        
        AdminProfile adminProfile = adminProfileRepo.findFirstByOrderByAdminProfileIdAsc();
        AdminPhoto adminPhoto;
        adminPhoto = adminPhotoRepo.findFirstByOrderByAdminImageIdAsc();
        String base64AdminImage = defaultBase64Image;
        if (adminPhoto != null) {
            
                // Convert image data to base64
                byte[] imageData = adminPhoto.getAdminImage();
                base64AdminImage = imageData != null ? Base64.getEncoder().encodeToString(imageData) : defaultBase64Image;
            }
        //        logger.info("adminProfile text: {}", adminProfile);
            if (adminProfile == null) {
            AdminProfile newAdminProfile = new AdminProfile();
            newAdminProfile.setAdminName("_ _ _");
            newAdminProfile.setBody("");
            model.addAttribute("adminProfile", newAdminProfile);
        } else {
            model.addAttribute("adminProfile", adminProfile);
        }

            model.addAttribute("base64AdminImage", base64AdminImage);
// Add the HashSet to the model
        model.addAttribute("postIdWithBase64Image", postIdWithBase64Image);
        
        // Add attributes to the model
        model.addAttribute("allTags", availableTags);
        model.addAttribute("text", text);
//        model.addAttribute("photoDTO", photoDTO);
        model.addAttribute("posts", filteredPosts);
        return "index";
    }

    @RequestMapping("/")
    public String home2(Model model) {
//        String text = "HELLO";
//        model.addAttribute("text", text);
//        model.addAttribute("posts", posts.findAll().stream()
//                .filter(Post::isActive)
//                .collect(Collectors.toList()));
        return "redirect:/index";
    }
    
    
    @RequestMapping("/readMore")
    public String readMore(Model model, @RequestParam int id) {
        Post post = postService.getPostById(id);
                HashMap<Integer, String> postIdWithBase64Image = new HashMap<>();

// Load the default image
        DefaultImage defaultImage = defaultImageRepo.findFirstByOrderByDefaultImageIdAsc();
        String defaultBase64Image = "";
        try {
            byte[] imageDefaultData = defaultImage.getDefaultImage();
            defaultBase64Image = imageDefaultData != null ? Base64.getEncoder().encodeToString(imageDefaultData) : "";
        } catch (Exception ex) {

        }

       
            // Retrieve PhotoDTO for the current post ID
            PhotoDTO photoDTO;

            photoDTO = photoRepo.findById(post.getImageId()).orElse(null);

            // Default base64 image in case photoDTO is null
            String base64Image = defaultBase64Image;
            if (photoDTO != null) {
                // Convert image data to base64
                byte[] imageData = photoDTO.getImage();
                base64Image = imageData != null ? Base64.getEncoder().encodeToString(imageData) : defaultBase64Image;
            }
            // Store post ID and base64 image in the HashMap
            postIdWithBase64Image.put(post.getPostId(), base64Image);
        

// Add the HashSet to the model
        model.addAttribute("postIdWithBase64Image", postIdWithBase64Image);

        model.addAttribute(post);
        return "readMore";

    }

    @RequestMapping("/user")
    public String user(Model model, Authentication authentication) {
        List<Tag> tagList = tagService.getAllTags();

        // Check if the user has the ROLE_ADMIN
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        int userId = userDetails.getUserId();
        // Get the username of the currently logged-in user
//        String username = authentication.getName();
        model.addAttribute("userId", userId);
        model.addAttribute("tags", tagList);

// Add a flag indicating whether the user is an admin
        model.addAttribute("isAdmin", isAdmin);

        return "user";
    }


    @RequestMapping("/admin")
    public String admin(Model model) {
        String text = "HELLO Admin";
        // Get authentication details from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        int userId = userDetails.getUserId();
        List<Tag> tagList = tagService.getAllTags();
//         model.addAttribute("error", error);
        model.addAttribute("userId", userId);
        model.addAttribute("text", text);
        model.addAttribute("tags", tagList);
        model.addAttribute("isAdmin", true);
        return "admin";
    }
    
    @RequestMapping("/editAdminProfile")
    public String editAdminProfile(Model model) {
        Boolean changeImage = false;
        AdminProfile adminProfile;
       
        try {

            adminProfile = adminProfileRepo.findFirstByOrderByAdminProfileIdAsc();
        } catch (Exception ex) {
            adminProfile = null;
        }
          if (adminProfile == null) {
            AdminProfile newAdminProfile = new AdminProfile();
            newAdminProfile.setAdminName("");
            newAdminProfile.setBody("");
            model.addAttribute("adminProfile", newAdminProfile);
        } else {
            model.addAttribute("adminProfile", adminProfile);
        }
        //    logger.info("adminProfile NAme: {}", adminProfile.getAdminName());
        model.addAttribute("changeImage", changeImage);


        return "editAdminProfile";
    }
    
    
    @PostMapping("/updateAdminProfile")
    public String updateAdminProfile(Model model,@RequestParam("changeImage") Boolean changeImage,@RequestParam String adminName, @RequestParam String body,@RequestParam("image") MultipartFile image) {
        
        AdminProfile existingAdminProfile;        
        
        try {
            existingAdminProfile = adminProfileRepo.findFirstByOrderByAdminProfileIdAsc();
        } catch (Exception ex) {
            existingAdminProfile = null;
        }
        AdminPhoto savedAdminPhoto;
        AdminPhoto existingAdminPhoto = adminPhotoRepo.findFirstByOrderByAdminImageIdAsc();
       
        byte[] imageData = null;
        
                     logger.info("Change Image: {}", changeImage);
        if (changeImage) {
          String error = "";
       
//        AdminPhoto existingAdminPhoto = adminPhotoRepo.findFirstByOrderByAdminImageIdAsc();
        
        // Check if the image file is not empty
        if (!image.isEmpty()) {
            logger.info(" in !image.isEmpty() ");
            // Check if the image file is either PNG or JPEG
            if (!(image.getContentType().equals("image/png") || image.getContentType().equals("image/jpeg"))) {
                error = "Wrong Image type. Choose Png/Jpeg type.";
//                    bindingResult.rejectValue("image", "invalidFormat", "Only PNG or JPEG images are allowed.");
            }
            // Check if the image file size is within the allowed limit (5MB)
            if (image.getSize() > 5 * 1024 * 1024) {
                logger.info(" image.getSize() > 5 * 1024 * 1024 ");
                error = "Image Size exceeds 5 mb";
//                     bindingResult.rejectValue("image", "sizeExceeded", "Image size exceeds the allowed limit (5MB).");
            }
        }

        try {
            imageData = image.getBytes();
//                logger.info("Length of the byte array: {}", imageData.length);

        } catch (IOException ex) {
            error = "Error occurred while reading image file";
//                // Log the error
//                logger.error("Error occurred while reading image file: {}", ex.getMessage());
//                // Add error message to binding result
//                bindingResult.rejectValue("image", "fileReadError", "Error occurred while reading image file.");
        }

        if (!"".equals(error)) {
            // Pass the validation errors to the view
            model.addAttribute("error", error);

            return "redirect:/errorImage?error=" + error;
//            return "admin";
        }
        if (imageData.length == 0) {
            imageData = null;
        }
        
             if (existingAdminPhoto != null) {
            // If a default image exists, update its data
            existingAdminPhoto.setAdminImage(imageData);
            savedAdminPhoto = existingAdminPhoto;
           
        } else {
            // If no default image exists, create a new one
            AdminPhoto adminPhoto = new AdminPhoto();
            adminPhoto.setAdminImage(imageData);
            savedAdminPhoto = adminPhoto;

        }
        }else{
                  String error = "";
       
//        AdminPhoto existingAdminPhoto = adminPhotoRepo.findFirstByOrderByAdminImageIdAsc();
        
        // Check if the image file is not empty
        if (!image.isEmpty()) {
            logger.info(" in !image.isEmpty() ");
            // Check if the image file is either PNG or JPEG
            if (!(image.getContentType().equals("image/png") || image.getContentType().equals("image/jpeg"))) {
                error = "Wrong Image type. Choose Png/Jpeg type.";
//                    bindingResult.rejectValue("image", "invalidFormat", "Only PNG or JPEG images are allowed.");
            }
            // Check if the image file size is within the allowed limit (5MB)
            if (image.getSize() > 5 * 1024 * 1024) {
                logger.info(" image.getSize() > 5 * 1024 * 1024 ");
                error = "Image Size exceeds 5 mb";
//                     bindingResult.rejectValue("image", "sizeExceeded", "Image size exceeds the allowed limit (5MB).");
            }
        }

        try {
            imageData = image.getBytes();
//                logger.info("Length of the byte array: {}", imageData.length);

        } catch (IOException ex) {
            error = "Error occurred while reading image file";
//                // Log the error
//                logger.error("Error occurred while reading image file: {}", ex.getMessage());
//                // Add error message to binding result
//                bindingResult.rejectValue("image", "fileReadError", "Error occurred while reading image file.");
        }

        if (!"".equals(error)) {
            // Pass the validation errors to the view
            model.addAttribute("error", error);

            return "redirect:/errorImage?error=" + error;
//            return "admin";
        }
        if (imageData.length == 0) {
            imageData = null;
        }
        
             if (existingAdminPhoto != null) {
            // If a default image exists, update its data

            savedAdminPhoto = existingAdminPhoto;
           
        } else {
            // If no default image exists, create a new one
            AdminPhoto adminPhoto = new AdminPhoto();
            adminPhoto.setAdminImage(imageData);
            savedAdminPhoto = adminPhoto;

        }
        
        }

 
    

         adminPhotoRepo.save(savedAdminPhoto);
        
        
        if (existingAdminProfile != null) {
            existingAdminProfile.setAdminName(adminName);
            existingAdminProfile.setBody(body);
            savedAdminPhoto.setAdminProfile(existingAdminProfile);
            existingAdminProfile.setAdminPhoto(savedAdminPhoto);
            adminProfileRepo.save(existingAdminProfile);
        } else {
            AdminProfile adminProfile = new AdminProfile();
            adminProfile.setAdminName(adminName);
            adminProfile.setBody(body);
            savedAdminPhoto.setAdminProfile(adminProfile);
            adminProfile.setAdminPhoto(savedAdminPhoto);
            adminProfileRepo.save(adminProfile);
        }

        return "redirect:/index";
    }

    @RequestMapping("/editPosts")
    public String editPosts(@RequestParam(required = false) String filterAttribute, @RequestParam(required = false) String filterOrder, @RequestParam(required = false) String filterYesOrNo, @RequestParam(required = false) String filterAuthor, Model model) {
        List<UserPost> userPostList = userPostRepository.findAll();
//        for(UserPost userPost :userPostList){
//        userPost.getPost().getPostId();
//        }
//        List<Post> postList = posts.findAll();
        List<User> userList = userService.getAllUsers();
        try {
            String selectedAuthorName = userService.getUserById(Integer.parseInt(filterAuthor)).getUserName();
            model.addAttribute("selectedAuthorName", selectedAuthorName);
        } catch (NumberFormatException e) {
        }

        if (null != filterAttribute) {
            switch (filterAttribute) {
                case "id":
//                    postList = postService.getPostsSortedByPostId(filterOrder);
                    userPostList = userPostService.getPostsSortedByPostPostId(filterOrder);
                    break;
                case "creationDate":
//                    postList = postService.getPostsSortedByCreationDate(filterOrder);
                    userPostList = userPostService.getPostsSortedByPostCreationDate(filterOrder);
                    break;
                case "updateDate":
                    userPostList = userPostService.getPostsSortedByPostUpdateDate(filterOrder);
                    break;
                case "displayDate":
                    userPostList = userPostService.getPostsSortedByPostDisplayDate(filterOrder);
                    break;
                case "expiryDate":
                    userPostList = userPostService.getPostsSortedByPostExpiryDate(filterOrder);
                    break;
                case "active":
                    userPostList = userPostService.getPostsSortedByPostActive(filterYesOrNo);
                    break;
                case "approved":
                    userPostList = userPostService.getPostsSortedByPostApproved(filterYesOrNo);
                    break;
                case "author":
                    userPostList = userPostService.getPostsSortedByUser(filterAuthor);
                default:
                    break;
            }
        }
        model.addAttribute("authorList", userList);
        model.addAttribute("filterAttribute", filterAttribute);
        model.addAttribute("filterOrder", filterOrder);
        model.addAttribute("filterYesOrNo", filterYesOrNo);
        model.addAttribute("filterAuthor", filterAuthor);

        model.addAttribute("userPostList", userPostList);

        return "editPosts";
    }

    @RequestMapping("/defaultImage")
    public String defaultImage(Model model) {
    Boolean changeImage = false;

// Load the default image
        DefaultImage defaultImage = defaultImageRepo.findFirstByOrderByDefaultImageIdAsc();
        String defaultBase64Image = "";
        try {
            byte[] imageDefaultData = defaultImage.getDefaultImage();
            defaultBase64Image = imageDefaultData != null ? Base64.getEncoder().encodeToString(imageDefaultData) : "";
        } catch (Exception ex) {

        }
        


            String base64Image = defaultBase64Image;

            
        // Add the HashSet to the model
        model.addAttribute("base64Image", base64Image);
        model.addAttribute("changeImage",changeImage);

        return "defaultImage";
    }

    @PostMapping("/addDefaultImage")
    public String addDefaultImage(Model model, @RequestParam("image") MultipartFile image,@RequestParam("changeImage") Boolean changeImage) {
        String error = "";
        byte[] imageData = null;
//        DefaultImage defaultImage = new DefaultImage();
        // Check if a default image already exists in the database

        DefaultImage existingDefaultImage = defaultImageRepo.findFirstByOrderByDefaultImageIdAsc();
        // Check if the image file is not empty
        if (!image.isEmpty()) {
            logger.info(" in !image.isEmpty() ");
            // Check if the image file is either PNG or JPEG
            if (!(image.getContentType().equals("image/png") || image.getContentType().equals("image/jpeg"))) {
                error = "Wrong Image type. Choose Png/Jpeg type.";
//                    bindingResult.rejectValue("image", "invalidFormat", "Only PNG or JPEG images are allowed.");
            }
            // Check if the image file size is within the allowed limit (5MB)
            if (image.getSize() > 5 * 1024 * 1024) {
                logger.info(" image.getSize() > 5 * 1024 * 1024 ");
                error = "Image Size exceeds 5 mb";
//                     bindingResult.rejectValue("image", "sizeExceeded", "Image size exceeds the allowed limit (5MB).");
            }
        }

        try {
            imageData = image.getBytes();
//            logger.info("Length of the byte array: {}", imageData.length);
//            defaultImage.setDefaultImage(imageData);
        } catch (IOException ex) {
            error = "Error occurred while reading image file";
//                // Log the error
//                logger.error("Error occurred while reading image file: {}", ex.getMessage());
//                // Add error message to binding result
//                bindingResult.rejectValue("image", "fileReadError", "Error occurred while reading image file.");
        }

        if (!"".equals(error)) {
            // Pass the validation errors to the view
            model.addAttribute("error", error);

            return "redirect:/errorImage?error=" + error;
//            return "admin";
        }
if(changeImage){
        if (existingDefaultImage != null) {
            // If a default image exists, update its data
            existingDefaultImage.setDefaultImage(imageData);
            defaultImageRepo.save(existingDefaultImage);
        } else {
            // If no default image exists, create a new one
            DefaultImage defaultImage = new DefaultImage();
            defaultImage.setDefaultImage(imageData);
            defaultImageRepo.save(defaultImage);
        }
}

        logger.info("PASSED RETURN ERRORIMAGE");
//        defaultImageRepo.save(defaultImage);

  // Reload the saved image and add it to the model
        changeImage = false;
        DefaultImage savedDefaultImage = defaultImageRepo.findFirstByOrderByDefaultImageIdAsc();
        String base64SavedImage = Base64.getEncoder().encodeToString(savedDefaultImage.getDefaultImage());
        model.addAttribute("base64Image", base64SavedImage);
        model.addAttribute("changeImage", changeImage);

        return "defaultImage";
    }

    @RequestMapping("/errorImage")
    public String errorImage(Model model, @RequestParam(required = false) String error) {

        model.addAttribute("error", error);
        return "errorImage";

    }

    @PostMapping("/filteredPosts")
    public String filterPosts(@RequestParam(required = false) String filterAttribute, @RequestParam(required = false) String filterOrder, @RequestParam(required = false) String filterYesOrNo, @RequestParam(required = false) String filterAuthor, Model model) {
        model.addAttribute("filterAttribute", filterAttribute);
        model.addAttribute("filterOrder", filterOrder);
        model.addAttribute("filterYesOrNo", filterYesOrNo);
        model.addAttribute("filterAuthor", filterAuthor);
        if (filterOrder != "") {
            // Redirect to avoid resubmission
            return "redirect:/editPosts?filterAttribute=" + filterAttribute + "&filterOrder=" + filterOrder;
        } else if (filterYesOrNo != "") {
            return "redirect:/editPosts?filterAttribute=" + filterAttribute + "&filterYesOrNo=" + filterYesOrNo;
        } else if (filterAuthor != "") {
            return "redirect:/editPosts?filterAttribute=" + filterAttribute + "&filterAuthor=" + filterAuthor;
        } else {
            return "editPosts";

        }
    }

    @PostMapping("/addPost")
    public String addPost(Model model, Post post, @RequestParam("image") MultipartFile image, @RequestParam int id, @RequestParam List<String> selectedTags, @RequestParam String displayDateStr, @RequestParam String expiryDateStr, @RequestParam(required = false, defaultValue = "true") boolean active, @RequestParam(required = false, defaultValue = "true") boolean approved, Authentication authentication) {

        String error = "";
        PhotoDTO photoDTO = new PhotoDTO();
        byte[] imageData = null;
        // Check if the image file is not empty
        if (!image.isEmpty()) {
            logger.info(" in !image.isEmpty() ");
            // Check if the image file is either PNG or JPEG
            if (!(image.getContentType().equals("image/png") || image.getContentType().equals("image/jpeg"))) {
                error = "Wrong Image type. Choose Png/Jpeg type.";
//                    bindingResult.rejectValue("image", "invalidFormat", "Only PNG or JPEG images are allowed.");
            }
            // Check if the image file size is within the allowed limit (5MB)
            if (image.getSize() > 5 * 1024 * 1024) {
                logger.info(" image.getSize() > 5 * 1024 * 1024 ");
                error = "Image Size exceeds 5 mb";
//                     bindingResult.rejectValue("image", "sizeExceeded", "Image size exceeds the allowed limit (5MB).");
            }
        }

        try {
            imageData = image.getBytes();
//                logger.info("Length of the byte array: {}", imageData.length);

        } catch (IOException ex) {
            error = "Error occurred while reading image file";
//                // Log the error
//                logger.error("Error occurred while reading image file: {}", ex.getMessage());
//                // Add error message to binding result
//                bindingResult.rejectValue("image", "fileReadError", "Error occurred while reading image file.");
        }

        if (!"".equals(error)) {
            // Pass the validation errors to the view
            model.addAttribute("error", error);

            return "redirect:/errorImage?error=" + error;
//            return "admin";
        }

        logger.info("PASSED RETURN ERRORIMAGE");

        if (imageData.length == 0) {
            imageData = null;
        }
        photoDTO.setImage(imageData);
        

        photoDTO.setPost(post);
        post.setImageId(photoDTO.getImageId());
        post.setPostImage(photoDTO);
        // Set active based on the value passed from the form
        post.setActive(active);

        post.setApproved(approved);

        User user = userService.getUserById(id);
//        post.setUser(user);

        UserPost userPost = new UserPost(user, post);
       


        List<Tag> tagList = tagService.getTagsById(selectedTags);
        post.setTags(tagList);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date displayDate = parseDate(displayDateStr, dateFormat);
        Date expiryDate = parseDate(expiryDateStr, dateFormat);

        post.setDisplayDate(displayDate);
        post.setExpiryDate(expiryDate);
        // Check if the user has the ROLE_ADMIN
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            post.setActive(false);
        }
        posts.save(post);
        userPostRepository.save(userPost);
        photoRepo.save(photoDTO);
        return "redirect:/index";

    }

    private Date parseDate(String dateStr, SimpleDateFormat dateFormat) {
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) { // Handle the exception based on your requirements
            // Handle the exception based on your requirements
            return null;
        }
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

        userService.registerUser(form);
        registrationSuccess = "User " + form.getUsername() + " was successfully added";
//        return "redirect:/login?registrationSuccess";
//          return "redirect:/userList";
//        model.addAttribute("registrationSuccess", registrationSuccess);
        return "redirect:/userList?registrationSuccess=" + registrationSuccess;
    }


    @RequestMapping("/updateUser")
    public String showUserUpdateForm(int id, Model model) {
        String role;
        // Fetch the user details by ID and pass them to the update form
        User user = userService.getUserById(id);
        role = user.getRoles();
        model.addAttribute("role",role);

        model.addAttribute("user", user);
        return "updateUser";
    }

    @PostMapping("/updateUser")
    public String updateUserDetails(@RequestParam int id, @RequestParam String username, @RequestParam String password, @RequestParam boolean activationStatus) {
        // Retrieve user by ID and update details
        User user = userService.getUserById(id);
        user.setUserName(username);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(password);
//        user.setPassword(encodedPassword);
if (!password.isEmpty()){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
}
        userService.updateUserDetails(user, activationStatus);
        return "redirect:/userList";
    }

    @RequestMapping("/updatePost")
    public String showPostUpdateForm(int id, Model model) {
        String textDisplayDate = "Display Date";
        String textExpiryDate = "Expiry Date";
        Boolean changeImage = false;

        // Fetch the post details by ID and pass them to the update form
        Post post = postService.getPostById(id);

        List<Tag> tagList = tagService.getAllTags();
        List<Integer> postTagIdList = postService.getAllTagIdList(post);

//        if (post.getDisplayDate() == null) {
//        } else if (post.getDisplayDate().before(new Date())) {
//            textDisplayDate = "Display Date" + " (was :" + post.getDisplayDate().toString() + ")";
//        }
//        if (post.getExpiryDate() == null) {
//        } else if (post.getExpiryDate().before(new Date())) {
//            textExpiryDate = "Expiry Date" + " (was :" + post.getExpiryDate().toString() + ")";
//        }
//        model.addAttribute("textDisplayDate", textDisplayDate);
//        model.addAttribute("textExpiryDate", textExpiryDate);
   HashMap<Integer, String> postIdWithBase64Image = new HashMap<>();

// Load the default image
        DefaultImage defaultImage = defaultImageRepo.findFirstByOrderByDefaultImageIdAsc();
        String defaultBase64Image = "";
        try {
            byte[] imageDefaultData = defaultImage.getDefaultImage();
            defaultBase64Image = imageDefaultData != null ? Base64.getEncoder().encodeToString(imageDefaultData) : "";
        } catch (Exception ex) {

        }
        
         PhotoDTO photoDTO;

            photoDTO = photoRepo.findById(post.getImageId()).orElse(null);

            // Default base64 image in case photoDTO is null
            String base64Image = defaultBase64Image;
            if (photoDTO != null) {
                // Convert image data to base64
                byte[] imageData = photoDTO.getImage();
                base64Image = imageData != null ? Base64.getEncoder().encodeToString(imageData) : defaultBase64Image;
            }
            // Store post ID and base64 image in the HashMap
            postIdWithBase64Image.put(post.getPostId(), base64Image);
            
        // Add the HashSet to the model
        model.addAttribute("postIdWithBase64Image", postIdWithBase64Image);
        
        model.addAttribute("postTagIdList", postTagIdList);
        model.addAttribute("tags", tagList);
        model.addAttribute("changeImage", changeImage);
        model.addAttribute("post", post);
        return "updatePost";
    }

//    @PostMapping("/updatePost")
//    public String updatePostDetails(@RequestParam int id, @RequestParam String displayDateStr, @RequestParam String expiryDateStr, @RequestParam List<String> selectedTags, @RequestParam String title, @RequestParam String body, @RequestParam boolean activationStatus) {
//        // Retrieve user by ID and update details
//        Post post = postService.getPostById(id);
//        List<Tag> tagList = tagService.getTagsById(selectedTags);
//        post.setTags(tagList);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date displayDate = parseDate(displayDateStr, dateFormat);
////        Date displayDateTemp = parseDate(displayDateStr, dateFormat);
//        Date expiryDate = parseDate(expiryDateStr, dateFormat);
//        if (displayDate == null) {
//            displayDate = new Date();
//        }
//
//        post.setTitle(title);
//        post.setBody(body);
//        post.setDisplayDate(displayDate);
//        post.setExpiryDate(expiryDate);
//        post.setUpdateDate(new Date());
//        postService.updatePostDetails(post, activationStatus);
//        return "redirect:/editPosts";
//    }
    @PostMapping("/updatePost")
    public String updatePostDetails(@RequestParam int id, @RequestParam String displayDateStr, @RequestParam String expiryDateStr, @RequestParam List<String> selectedTags, @RequestParam String title, @RequestParam String body, @RequestParam boolean activationStatus, @RequestParam(required = false, defaultValue = "true") boolean approvedStatus, @RequestParam("image") MultipartFile image, @RequestParam("changeImage") Boolean changeImage) {
        // Retrieve user by ID and update details
        Post post = postService.getPostById(id);
        List<Tag> tagList = tagService.getTagsById(selectedTags);

        // Check if any changes were made to the post
        boolean changesMade = false;
//        logger.info("Chnages made: {}", changesMade);
        // Check if tags have changed
        List<Tag> sortedPostTags = new ArrayList<>(post.getTags());
        List<Tag> sortedTagList = new ArrayList<>(tagList);
        Collections.sort(sortedPostTags);
        Collections.sort(sortedTagList);

        if (!sortedPostTags.equals(sortedTagList)) {
            post.setTags(tagList);
            changesMade = true;
        }
        logger.info("Changes made: {}", changesMade);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date displayDate = parseDate(displayDateStr, dateFormat);
        logger.info("Display date: {}", displayDate);

        Date expiryDate = parseDate(expiryDateStr, dateFormat);

        // Check if display date has changed
        if (displayDate == null) {
            logger.info("HELLOO");
        } else if (post.getDisplayDate() == null || post.getDisplayDate().before(displayDate) || post.getDisplayDate().after(displayDate)) {
            post.setDisplayDate(displayDate);
            changesMade = true;
        }
        // Check if expiry date has changed
        if (expiryDate == null) {
            logger.info("HELLOO");
        } else if (post.getExpiryDate() == null || post.getExpiryDate().before(expiryDate) || post.getExpiryDate().after(expiryDate)) {
            post.setExpiryDate(expiryDate);
            changesMade = true;
        }

        // Check if title has changed
        if (!Objects.equals(post.getTitle(), title)) {
            post.setTitle(title);
            changesMade = true;
        }

        // Check if body has changed
        if (!Objects.equals(post.getBody(), body)) {
            post.setBody(body);
            changesMade = true;
        }

       if (changeImage) {
        String error = "";
        PhotoDTO existingPhotoDTO = photoRepo.findByPostPostId(post.getPostId());
        byte[] imageData = null;
        // Check if the image file is not empty
        if (!image.isEmpty()) {
            logger.info(" in !image.isEmpty() ");
            // Check if the image file is either PNG or JPEG
            if (!(image.getContentType().equals("image/png") || image.getContentType().equals("image/jpeg"))) {
                error = "Wrong Image type. Choose Png/Jpeg type.";
//                    bindingResult.rejectValue("image", "invalidFormat", "Only PNG or JPEG images are allowed.");
            }
            // Check if the image file size is within the allowed limit (5MB)
            if (image.getSize() > 5 * 1024 * 1024) {
                logger.info(" image.getSize() > 5 * 1024 * 1024 ");
                error = "Image Size exceeds 5 mb";
//                     bindingResult.rejectValue("image", "sizeExceeded", "Image size exceeds the allowed limit (5MB).");
            }
        }

        try {
            imageData = image.getBytes();
//            logger.info("FIRST Length of the byte array: {}", imageData.length);
//            logger.info("image data: {}", imageData);
            // photoDTO.setImage(imageData);
        } catch (IOException ex) {
            error = "Error occurred while reading image file";
//                // Log the error
//                logger.error("Error occurred while reading image file: {}", ex.getMessage());
//                // Add error message to binding result
//                bindingResult.rejectValue("image", "fileReadError", "Error occurred while reading image file.");
        }

        if (!"".equals(error)) {
            // Pass the validation errors to the view
//        model.addAttribute("error", error);

            return "redirect:/errorImage?error=" + error;
//            return "admin";
        }

        //PhotoDTO currentImage = photoRepo.findById(post.getImageId());
        byte[] currentImage = photoService.getImageByPostId(post.getPostId());
        
 try {
            if (imageData.length == 0) {
                imageData = null;
            }

        } catch (Exception e) {
            imageData = null;
        }
//        try {
        if (imageData != null && currentImage != null) {
            // Compare the lengths of the binary data
            if ((imageData.length != currentImage.length)) {
                // Images are not identical
                 logger.info("FIRST");
                changesMade = true;

            } else {
                // Compare the binary data byte by byte

                for (int i = 0; i < imageData.length; i++) {
                    if (imageData[i] != currentImage[i]) {
                         logger.info("SECOND");
                        changesMade = true;
                        break;
                    }
                }
            }
        }

        if (imageData != null && currentImage == null) {
             logger.info("THIRD");
            changesMade = true;
        }

        if (imageData == null && currentImage != null) {
                logger.info("FOURTH");
            changesMade = true;
        }
       

        existingPhotoDTO.setImage(imageData);
        photoRepo.save(existingPhotoDTO);

        existingPhotoDTO.setPost(post);
        post.setImageId(existingPhotoDTO.getImageId());
        post.setPostImage(existingPhotoDTO);


            }//if changeImage
        logger.info("Changes made: {}", changesMade);
        // Assign update date if changes were made
        if (changesMade) {
            post.setUpdateDate(new Date());
        }

        postService.updatePostDetails(post, activationStatus, approvedStatus);

        return "redirect:/index";
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

    @RequestMapping("/tags")
    public String tags(Model model) {
        // Fetch the list of all tags
        List<Tag> tagList = tagService.getAllTags();

//        // Sort the userList based on the 'active' status
//        Collections.sort(tagList, Comparator.comparing(User::isActive).reversed());
        model.addAttribute("tags", tagList);
        return "tags";
    }

    @PostMapping("/addTags")
    public String addTags(Tag tag, Model model) {
        tags.save(tag);
        return "redirect:/tags";
    }
    
    @RequestMapping("/updateTag")
    public String updateTag(int id, Model model) {
        // Fetch the user details by ID and pass them to the update form
        Tag tag = tagService.getTagById(id);
        model.addAttribute("tag", tag);
        return "updateTag";

    }
    
    @PostMapping("/updateTag")
    public String updateTag(@RequestParam int id, @RequestParam String name){
         Tag tag = tagService.getTagById(id);
        tag.setName(name);
        tagRepository.save(tag);
        return "redirect:/tags";
    }
    
    @Transactional
    @RequestMapping("/removeTag")
    public String removeTag(@RequestParam int id) {
        tagRepository.deleteById(id);
        tagRepository.deletePostTagByTagId(id);
        
        return "redirect:/tags";
    }
    
    @Transactional
    @RequestMapping("/removePost")
    public String removePost(@RequestParam int id) {
        posts.deletePostTagByPostId(id);
        posts.deletePostByPostId(id);
        posts.deleteUserPostByPostId(id);
        
        return "redirect:/editPosts";
    }

    
     @Transactional
    @RequestMapping("/removeUser")
    public String removeUser(@RequestParam int id) {
        String error;
        User user;
 
             user =  userRepository.findById(id).get();

         if ("ROLE_ADMIN".equals(user.getRoles())) {
             error = "Admin cannot be deleted";
             return "redirect:/errorImage?error=" + error;
         }
        
         try {
             userPostRepository.findUserPostsByUserUserId(id);
         } catch (Exception ex) {
             userPostRepository.deleteUserPostByUserId(id);
         }
        userRepository.deleteUserByUserId(id);
        
        return "redirect:/userList";
    }
    
}
