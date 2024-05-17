/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.service;

import com.jah.spring_security_jpa.models.Post;
import com.jah.spring_security_jpa.models.Tag;
import com.jah.spring_security_jpa.models.User;
import com.jah.spring_security_jpa.repositories.PostRepository;
import com.jah.spring_security_jpa.repositories.UserPostRepository;
import com.jah.spring_security_jpa.repositories.UserRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author drjal
 */
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserPostRepository userPostRepository;


    public Post getPostById(int postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + postId));
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    
     public List<Post> getPostsSortedByPostId(String sortOrder) {
         if(null == sortOrder){
         return postRepository.findAll();
         }else{
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(Sort.Direction.ASC, "postId") : Sort.by(Sort.Direction.DESC, "postId");
        return postRepository.findAll(sort);
         }
    }
    
   public List<Post> getPostsSortedByCreationDate(String sortOrder) {
       if(null == sortOrder){
           return postRepository.findAll();
       }else{
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(Sort.Direction.ASC, "creationDate") : Sort.by(Sort.Direction.DESC, "creationDate");
        return postRepository.findAll(sort);
       }
    }
   
   public List<Post> getPostsSortedByUpdateDate(String sortOrder) {
       if(null == sortOrder){
           return postRepository.findAll();
       }else{
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(Sort.Direction.ASC, "updateDate") : Sort.by(Sort.Direction.DESC, "updateDate");
        return postRepository.findAll(sort);
       }
    }
   
   public List<Post> getPostsSortedByDisplayDate(String sortOrder) {
       if(null == sortOrder){
           return postRepository.findAll();
       }else{
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(Sort.Direction.ASC, "displayDate") : Sort.by(Sort.Direction.DESC, "displayDate");
        return postRepository.findAll(sort);
       }
    }
   
   public List<Post> getPostsSortedByExpiryDate(String sortOrder) {
       if(null == sortOrder){
           return postRepository.findAll();
       }else{
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(Sort.Direction.ASC, "expiryDate") : Sort.by(Sort.Direction.DESC, "expiryDate");
        return postRepository.findAll(sort);
       }
    }
   
   //SORT BY YES OR NO
   public List<Post> getPostsSortedByActive(String sortChoice) {
    if (sortChoice == null || sortChoice.isEmpty()) {
        return postRepository.findAll();
    } else {
        // Define a Sort object based on the sortChoice
        Sort sort;
        if (sortChoice.equalsIgnoreCase("yes")) {
            sort = Sort.by(Sort.Direction.DESC, "active"); // Sort in descending order for "yes"
        } else {
            sort = Sort.by(Sort.Direction.ASC, "active"); // Sort in ascending order for "no"
        }
        // Perform sorting and return the sorted list
        return postRepository.findAll(sort);
    }
}
   
    public List<Post> getPostsSortedByApproved(String sortChoice) {
    if (sortChoice == null || sortChoice.isEmpty()) {
        return postRepository.findAll();
    } else {
        // Define a Sort object based on the sortChoice
        Sort sort;
        if (sortChoice.equalsIgnoreCase("yes")) {
            sort = Sort.by(Sort.Direction.DESC, "approved"); // Sort in descending order for "yes"
        } else {
            sort = Sort.by(Sort.Direction.ASC, "approved"); // Sort in ascending order for "no"
        }
        // Perform sorting and return the sorted list
        return postRepository.findAll(sort);
    }
}
//    
//    public List<Post> getPostsSortedByUser(String authorId) {
//        int authorIdInt = Integer.parseInt(authorId);
//        List<Post> allPostsByUser = userPostRepository.findPostsByUserUserId(authorIdInt);
////        List<Post> allPosts = postRepository.findAll();
////        List<Post> sortedByAuthorPosts ;
////       // List<Integer> allPostIdByUser = getAllPostIdList(userRepository.findById(authorIdInt).get());
////        User user = userRepository.findById(authorIdInt).get();
////        
////
////       
////
////        try {
////           sortedByAuthorPosts = user.getPosts();
////            return sortedByAuthorPosts;
////        } catch (NumberFormatException e) {
////            return allPosts;
////        }
//            return allPostsByUser;
//
//    }

    
    public List<Integer> getAllTagIdList(Post post) {
        List<Tag> listTagPost = post.getTags();
        List<Integer> listTagId = new ArrayList();

        for (Tag postTag : listTagPost) {
            Integer tagId = postTag.getTagId();
            listTagId.add(tagId);
        }
        return listTagId;
    }
    
//    public List<Integer> getAllPostIdList(User user) {
//        List<Post> listPostUser = user.getPosts();
//        List<Integer> listPostId = new ArrayList();
//
//        for (Post userPost : listPostUser) {
//            Integer postId = userPost.getPostId();
//            listPostId.add(postId);
//        }
//        return listPostId;
//    }

    @Transactional
    public void updatePostDetails(Post post, boolean activationStatus, boolean approvedStatus) {

        Post managedPost = postRepository.findById(post.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + post.getPostId()));

        // Update the managed user
        managedPost.setTitle(post.getTitle());
        managedPost.setActive(activationStatus);
        managedPost.setApproved(approvedStatus);
        managedPost.setBody(post.getBody());
        postRepository.save(managedPost);

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
}
