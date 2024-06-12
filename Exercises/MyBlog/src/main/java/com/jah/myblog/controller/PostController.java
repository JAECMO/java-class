/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.myblog.controller;

import com.jah.myblog.dto.PhotoDTO;
import com.jah.myblog.models.Post;
import com.jah.myblog.models.Tag;
import com.jah.myblog.models.User;
import com.jah.myblog.models.UserPost;
import com.jah.myblog.repositories.PhotoDTORepository;
import com.jah.myblog.repositories.PostRepository;
import com.jah.myblog.repositories.UserPostRepository;
import com.jah.myblog.repositories.UserRepository;
import com.jah.myblog.service.AdminProfileService;
import com.jah.myblog.service.ImageService;
import com.jah.myblog.service.MyUserDetailsService;
import com.jah.myblog.service.PostService;
import com.jah.myblog.service.TagService;
import com.jah.myblog.service.UserPostService;
import com.jah.myblog.service.UserService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
public class PostController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PostRepository posts;

    @Autowired
    private PostService postService;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PhotoDTORepository photoRepo;

    @Autowired
    private UserPostRepository userPostRepository;

    @Autowired
    private UserPostService userPostService;

    @Autowired
    ImageService imageService;

    @Autowired
    private AdminProfileService adminProfileService;

    @RequestMapping("/editPosts")
    public String editPosts(@RequestParam(required = false) String filterAttribute, @RequestParam(required = false) String filterOrder, @RequestParam(required = false) String filterYesOrNo, @RequestParam(required = false) String filterAuthor, Model model, @RequestParam(name = "postSavedSuccess", required = false, defaultValue = "") String postSavedSuccess) {

        List<UserPost> userPostList = userPostService.getFilteredAndSortedPosts(filterAttribute, filterOrder, filterYesOrNo, filterAuthor);
        List<UserPost> userPostListById;
        List<User> userList = userService.getAllUsers();
        List<User> authorList = new ArrayList();

        for (User user : userList) {

            userPostListById = userPostRepository.findUserPostsByUserUserId(user.getUserId());
            if (!userPostListById.isEmpty()) {
                authorList.add(user);
            }

        }

        try {
            String selectedAuthorName = userService.getUserById(Integer.parseInt(filterAuthor)).getUserName();
            model.addAttribute("selectedAuthorName", selectedAuthorName);
        } catch (NumberFormatException e) {
        }

        model.addAttribute("authorList", authorList);
        model.addAttribute("filterAttribute", filterAttribute);
        model.addAttribute("filterOrder", filterOrder);
        model.addAttribute("filterYesOrNo", filterYesOrNo);
        model.addAttribute("filterAuthor", filterAuthor);
        model.addAttribute("userPostList", userPostList);
        model.addAttribute("postSavedSuccess", postSavedSuccess);

        return "editPosts";
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
    public String addPost(Model model, Post post, @RequestParam("image") MultipartFile image, @RequestParam int id, @RequestParam List<String> selectedTags, @RequestParam String displayDateStr, @RequestParam String expiryDateStr, @RequestParam(required = false, defaultValue = "true") boolean active, @RequestParam(required = false, defaultValue = "true") boolean approved, Authentication authentication, HttpSession session) {
        String postAdditionSuccess;
        String postSavedSuccess;
        String postSubmittedSuccess;

        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        try {

            byte[] imageData = adminProfileService.validateAndProcessImage(image);
            PhotoDTO photoDTO = new PhotoDTO();
            photoDTO.setImage(imageData);
            // Check if the user has the ROLE_ADMIN

            if (!isAdmin) {
                post.setSubmittedDate(new Date());
                post.setActive(false);
            }

            postService.savePost(post, photoDTO, selectedTags, id, displayDateStr, expiryDateStr, active, approved);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "redirect:/errorImage?error=" + ex.getMessage();
        } catch (IOException ex) {
            model.addAttribute("error", "Error occurred while reading image file");
            return "redirect:/errorImage?error=Error occurred while reading image file";
        }
        if (isAdmin) {
            if (active) {
                postAdditionSuccess = "Post with Id:" + post.getPostId() + " was successfully added";
                session.setAttribute("postAdditionSuccess", postAdditionSuccess);
            } else {
                postSavedSuccess = "Post with Id:" + post.getPostId() + " was successfully saved";
                session.setAttribute("postSavedSuccess", postSavedSuccess);
            }
        } else {
            postSubmittedSuccess = "Post with Id:" + post.getPostId() + " was successfully submitted to Admin";
            session.setAttribute("postSubmittedSuccess", postSubmittedSuccess);
            return "redirect:/user";
        }

        return "redirect:/admin";

    }

    @RequestMapping("/updatePost")
    public String showPostUpdateForm(int id, Model model) {
        Boolean changeImage = false;

        // Fetch the post details by ID and pass them to the update form
        Post post = postService.getPostById(id);

        List<Tag> tagList = tagService.getAllTags();
        List<Integer> postTagIdList = postService.getAllTagIdList(post);

        HashMap<Integer, String> postIdWithBase64Image = new HashMap<>();

        // Load the default image
        String defaultBase64Image = imageService.getDefaultBase64Image();
        String base64Image = imageService.getBase64ImageForPost(post.getImageId(), defaultBase64Image);

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

    @PostMapping("/updatePost")
    public String updatePostDetails(@RequestParam int id, @RequestParam String displayDateStr, @RequestParam String expiryDateStr, @RequestParam List<String> selectedTags, @RequestParam String title, @RequestParam String body, @RequestParam boolean activationStatus, @RequestParam(required = false, defaultValue = "true") boolean approvedStatus, @RequestParam("image") MultipartFile image, @RequestParam("changeImage") Boolean changeImage) throws Exception {
        try {
            postService.updatePost(id, displayDateStr, expiryDateStr, selectedTags, title, body, activationStatus, approvedStatus, image, changeImage);
            return "redirect:/index";
        } catch (IOException e) {
            return "redirect:/errorImage?error=" + e.getMessage();
        }

    }

    @Transactional
    @RequestMapping("/removePost")
    public String removePost(@RequestParam int id) {
        User user = userPostRepository.findUserByPostId(id);
        Integer userId = userPostRepository.findUserByPostId(id).getUserId();
        posts.deletePostTagByPostId(id);
        photoRepo.deleteImagetByPostId(id);
        posts.deletePostByPostId(id);
        posts.deleteUserPostByPostId(id);

        List<UserPost> userPostsByUser = userPostRepository.findUserPostsByUserUserId(userId);
        if (userPostsByUser.isEmpty() && user.isDeleted()) {
            userRepository.deleteUserByUserId(userId);
        }

        return "redirect:/editPosts";
    }

}
