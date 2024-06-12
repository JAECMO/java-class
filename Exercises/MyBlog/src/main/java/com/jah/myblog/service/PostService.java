/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.myblog.service;

import com.jah.myblog.dto.PhotoDTO;
import com.jah.myblog.models.Post;
import com.jah.myblog.models.Tag;
import com.jah.myblog.models.User;
import com.jah.myblog.models.UserPost;
import com.jah.myblog.repositories.PhotoDTORepository;
import com.jah.myblog.repositories.PostRepository;
import com.jah.myblog.repositories.TagRepository;
import com.jah.myblog.repositories.UserPostRepository;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author drjal
 */
@Service
public class PostService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserPostRepository userPostRepository;

    @Autowired
    private PhotoDTORepository photoRepo;

    @Autowired
    private PhotoDTOService photoService;

    Date today = new Date();

    public List<Post> filterPostsForIndex(Integer tagId) {

        List<Post> filteredPosts;
        if (tagId != null) {
            // Retrieve posts associated with the selected tag
            Tag selectedTag = tagRepository.findById(tagId).orElse(null);
            if (selectedTag != null) {
                filteredPosts = selectedTag.getPosts().stream()
                        .filter(post -> post.isActive() && post.isApproved()
                        && isDisplayDateValid(post, today) && isExpiryDateValid(post, today))
                        .peek(post -> {
                            if (post.getCreationDate() == null) {
                                // Set the creationDate if it's the first time displaying the post
                                post.setCreationDate(new Date());
                                // Save the post to update the creationDate
                                postRepository.save(post);
                            }
                        })
                        .sorted((post1, post2) -> post2.getCreationDate().compareTo(post1.getCreationDate()))
                        .collect(Collectors.toList());
            } else {
                filteredPosts = null; // or any default behavior
            }
        } else {

            filteredPosts = postRepository.findAll().stream()
                    .filter(post -> post.isActive() && post.isApproved()
                    && isDisplayDateValid(post, today) && isExpiryDateValid(post, today))
                    .peek(post -> {
                        if (post.getCreationDate() == null) {
                            // Set the creationDate if it's the first time displaying the post
                            post.setCreationDate(new Date());
                            // Save the post to update the creationDate
                            postRepository.save(post);
                        }
                    })
                    // Order posts by most recent creationDate first
                    .sorted((post1, post2) -> post2.getCreationDate().compareTo(post1.getCreationDate()))
                    .collect(Collectors.toList());
        }

        return filteredPosts;
    }

    public Set<Tag> filterAvailableTagsForIndex() {
        Set<Tag> availableTags = new TreeSet<>();
        List<Post> availablePosts = filterAvailablePosts();
        for (Post post : availablePosts) {
            for (Tag tag : post.getTags()) {
                availableTags.add(tag);
            }
        }

        return availableTags;

    }

    private List<Post> filterAvailablePosts() {
        List<Post> availablePosts = postRepository.findAll().stream()
                .filter(post -> post.isActive() && post.isApproved()
                && isDisplayDateValid(post, today) && isExpiryDateValid(post, today)).collect(Collectors.toList());

        return availablePosts;
    }

    public Post getPostById(int postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + postId));
    }

    public List<Integer> getAllTagIdList(Post post) {
        List<Tag> listTagPost = post.getTags();
        List<Integer> listTagId = new ArrayList();

        for (Tag postTag : listTagPost) {
            Integer tagId = postTag.getTagId();
            listTagId.add(tagId);
        }
        return listTagId;
    }

    public boolean isDisplayDateValid(Post post, Date today) {
        Date displayDate = post.getDisplayDate();
        return Optional.ofNullable(displayDate)
                .map(date -> !date.after(today) || date.equals(today))
                .orElse(true);
    }

    public boolean isExpiryDateValid(Post post, Date today) {
        Date expiryDate = post.getExpiryDate();
        return Optional.ofNullable(expiryDate)
                .map(date -> date.after(today))
                .orElse(true);
    }

    private Date parseDate(String dateStr, SimpleDateFormat dateFormat) {
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public void savePost(Post post, PhotoDTO photoDTO, List<String> selectedTags, int userId, String displayDateStr, String expiryDateStr, boolean active, boolean approved) throws IOException {
        User user = userService.getUserById(userId);
        UserPost userPost = new UserPost(user, post);

        List<Tag> tagList = tagService.getTagsById(selectedTags);
        post.setTags(tagList);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date displayDate = parseDate(displayDateStr, dateFormat);
        Date expiryDate = parseDate(expiryDateStr, dateFormat);

        post.setDisplayDate(displayDate);
        post.setExpiryDate(expiryDate);

        post.setActive(active);
        if (active && post.getCreationDate() == null) {
            post.setCreationDate(new Date());
        }
        post.setApproved(approved);

        photoDTO.setPost(post);
        post.setPostImage(photoDTO);

        postRepository.save(post);
        userPostRepository.save(userPost);
        photoRepo.save(photoDTO);
        post.setImageId(photoDTO.getImageId());
        postRepository.save(post);
    }

    public void updatePost(int id, String displayDateStr, String expiryDateStr, List<String> selectedTags, String title, String body, boolean activationStatus, boolean approvedStatus, MultipartFile image, Boolean changeImage) throws Exception {
        Post post = getPostById(id);
        List<Tag> tagList = tagService.getTagsById(selectedTags);

        boolean changesMade = checkAndSetPostChanges(post, displayDateStr, expiryDateStr, tagList, title, body);
        byte[] imageData = image.isEmpty() ? null : image.getBytes();
        boolean imageChanged = isImageDifferent(changeImage, imageData, photoService.getImageByPostId(post.getPostId()));

        if (changeImage) {
            handleImageChange(changeImage, post, image);
        }

        if ((changesMade || imageChanged) && post.getCreationDate() != null) {
            post.setUpdateDate(new Date());
        }

        updatePostDetails(post, activationStatus, approvedStatus);
    }

    private boolean checkAndSetPostChanges(Post post, String displayDateStr, String expiryDateStr, List<Tag> tagList, String title, String body) throws ParseException {
        boolean changesMade = false;

        List<Tag> sortedPostTags = new ArrayList<>(post.getTags());
        List<Tag> sortedTagList = new ArrayList<>(tagList);
        Collections.sort(sortedPostTags);
        Collections.sort(sortedTagList);

        if (!sortedPostTags.equals(sortedTagList)) {
            post.setTags(tagList);
            changesMade = true;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date displayDate = parseDate(displayDateStr, dateFormat);
        Date expiryDate = parseDate(expiryDateStr, dateFormat);
        Date postDisplayDate;
        Date postExpiryDate;

        try {
            postDisplayDate = parseDate(post.getDisplayDate().toString(), dateFormat);
        } catch (Exception ex) {
            postDisplayDate = null;
        }

        try {
            postExpiryDate = parseDate(post.getExpiryDate().toString(), dateFormat);
        } catch (Exception ex) {
            postExpiryDate = null;
        }

        if (!Objects.equals(postDisplayDate, displayDate)) {
            post.setDisplayDate(displayDate);
            changesMade = true;
        }

        if (!Objects.equals(postExpiryDate, expiryDate)) {
            post.setExpiryDate(expiryDate);
            changesMade = true;
        }

        if (!Objects.equals(post.getTitle(), title)) {
            post.setTitle(title);
            changesMade = true;
        }

        if (!Objects.equals(post.getBody(), body)) {
            post.setBody(body);
            changesMade = true;
        }

        return changesMade;
    }

    private void handleImageChange(boolean changeImage, Post post, MultipartFile image) throws IOException {
        String error = validateImage(image);

        if (!error.isEmpty()) {
            throw new IOException(error);
        }

        PhotoDTO existingPhotoDTO = photoRepo.findByPostPostId(post.getPostId());
        byte[] imageData = image.isEmpty() ? null : image.getBytes();

        if (isImageDifferent(changeImage, imageData, photoService.getImageByPostId(post.getPostId()))) {
            existingPhotoDTO.setImage(imageData);
            photoRepo.save(existingPhotoDTO);

            existingPhotoDTO.setPost(post);
            post.setImageId(existingPhotoDTO.getImageId());
            post.setPostImage(existingPhotoDTO);
        }

    }

    private String validateImage(MultipartFile image) {
        if (image.isEmpty()) {
            return "";
        }

        if (!(image.getContentType().equals("image/png") || image.getContentType().equals("image/jpeg"))) {
            return "Wrong Image type. Choose Png/Jpeg type.";
        }

        if (image.getSize() > 5 * 1024 * 1024) {
            return "Image Size exceeds 5 mb";
        }

        return "";
    }

    private boolean isImageDifferent(boolean changeImage, byte[] newImage, byte[] currentImage) {
        if (changeImage == false) {
            return false;
        }

        return !Arrays.equals(newImage, currentImage);
    }

    @Transactional
    public void updatePostDetails(Post post, boolean activationStatus, boolean approvedStatus) {
        post.setActive(activationStatus);
        post.setApproved(approvedStatus);
        postRepository.save(post);
    }

    private void setModelAttribute(HttpSession session, Model model, String attributeName) {
        String attributeValue = (String) session.getAttribute(attributeName);
        if (attributeValue != null) {
            model.addAttribute(attributeName, attributeValue);
            session.removeAttribute(attributeName);
        } else {
            model.addAttribute(attributeName, "");
        }
    }

    public void addPostSuccessAttributes(HttpSession session, Model model) {
        setModelAttribute(session, model, "postAdditionSuccess");
        setModelAttribute(session, model, "postSavedSuccess");
    }
}
