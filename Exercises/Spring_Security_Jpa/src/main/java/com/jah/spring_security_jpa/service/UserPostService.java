/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.service;

import com.jah.spring_security_jpa.models.Post;
import com.jah.spring_security_jpa.models.UserPost;
import com.jah.spring_security_jpa.repositories.UserPostRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author drjal
 */


@Service
public class UserPostService {
    
    @Autowired
UserPostRepository userPostRepository;
    
    public List<UserPost> getPostsSortedByPostPostId(String sortOrder) {
         if(null == sortOrder){
         return userPostRepository.findAll();
         }else{
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(Sort.Direction.ASC, "post.postId") : Sort.by(Sort.Direction.DESC, "post.postId");
        return userPostRepository.findAll(sort);
         }
    }
    
   public List<UserPost> getPostsSortedByPostCreationDate(String sortOrder) {
       if(null == sortOrder){
           return userPostRepository.findAll();
       }else{
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(Sort.Direction.ASC, "post.creationDate") : Sort.by(Sort.Direction.DESC, "post.creationDate");
        return userPostRepository.findAll(sort);
       }
    }
//   
   public List<UserPost> getPostsSortedByPostUpdateDate(String sortOrder) {
       if(null == sortOrder){
           return userPostRepository.findAll();
       }else{
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(Sort.Direction.ASC, "post.updateDate") : Sort.by(Sort.Direction.DESC, "post.updateDate");
        return userPostRepository.findAll(sort);
       }
    }
//   
   public List<UserPost> getPostsSortedByPostDisplayDate(String sortOrder) {
       if(null == sortOrder){
           return userPostRepository.findAll();
       }else{
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(Sort.Direction.ASC, "post.displayDate") : Sort.by(Sort.Direction.DESC, "post.displayDate");
        return userPostRepository.findAll(sort);
       }
    }
//   
   public List<UserPost> getPostsSortedByPostExpiryDate(String sortOrder) {
       if(null == sortOrder){
           return userPostRepository.findAll();
       }else{
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(Sort.Direction.ASC, "post.expiryDate") : Sort.by(Sort.Direction.DESC, "post.expiryDate");
        return userPostRepository.findAll(sort);
       }
    }
//   
//   //SORT BY YES OR NO
   public List<UserPost> getPostsSortedByPostActive(String sortChoice) {
    if (sortChoice == null || sortChoice.isEmpty()) {
        return userPostRepository.findAll();
    } else {
        // Define a Sort object based on the sortChoice
        Sort sort;
        if (sortChoice.equalsIgnoreCase("yes")) {
            sort = Sort.by(Sort.Direction.DESC, "post.active"); // Sort in descending order for "yes"
        } else {
            sort = Sort.by(Sort.Direction.ASC, "post.active"); // Sort in ascending order for "no"
        }
        // Perform sorting and return the sorted list
        return userPostRepository.findAll(sort);
    }
}
//   
    public List<UserPost> getPostsSortedByPostApproved(String sortChoice) {
    if (sortChoice == null || sortChoice.isEmpty()) {
        return userPostRepository.findAll();
    } else {
        // Define a Sort object based on the sortChoice
        Sort sort;
        if (sortChoice.equalsIgnoreCase("yes")) {
            sort = Sort.by(Sort.Direction.DESC, "post.approved"); // Sort in descending order for "yes"
        } else {
            sort = Sort.by(Sort.Direction.ASC, "post.approved"); // Sort in ascending order for "no"
        }
        // Perform sorting and return the sorted list
        return userPostRepository.findAll(sort);
    }
}
//    
    public List<UserPost> getPostsSortedByUser(String authorId) {
        int authorIdInt = Integer.parseInt(authorId);

//        List<Post> allPosts = postRepository.findAll();
//        List<Post> sortedByAuthorPosts ;
//       // List<Integer> allPostIdByUser = getAllPostIdList(userRepository.findById(authorIdInt).get());
//        User user = userRepository.findById(authorIdInt).get();
//        
//
//       
//
//        try {
//           sortedByAuthorPosts = user.getPosts();
//            return sortedByAuthorPosts;
//        } catch (NumberFormatException e) {
//            return allPosts;
//        }
//            return allPostsByUser;
            
             if (authorId == null || authorId.isEmpty()) {
        return userPostRepository.findAll();
    } else {
              // Perform sorting and return the sorted list
        return userPostRepository.findUserPostsByUserUserId(authorIdInt);
        }

    }

    
}
