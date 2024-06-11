/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.service;

import com.jah.spring_security_jpa.models.UserPost;
import com.jah.spring_security_jpa.repositories.UserPostRepository;
import java.util.ArrayList;
import java.util.Date;
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
        return getSortedPosts("post.postId", sortOrder);
    }

    public List<UserPost> getPostsSortedByPostSubmittedDate(String sortOrder) {
        return getFilteredSortedPosts("post.submittedDate", sortOrder);
    }

    public List<UserPost> getPostsSortedByPostCreationDate(String sortOrder) {
        return getFilteredSortedPosts("post.creationDate", sortOrder);
    }

    public List<UserPost> getPostsSortedByPostUpdateDate(String sortOrder) {
        return getFilteredSortedPosts("post.updateDate", sortOrder);
    }

    public List<UserPost> getPostsSortedByPostDisplayDate(String sortOrder) {
        return getFilteredSortedPosts("post.displayDate", sortOrder);
    }

    public List<UserPost> getPostsSortedByPostExpiryDate(String sortOrder) {
        return getFilteredSortedPosts("post.expiryDate", sortOrder);
    }

    private List<UserPost> getSortedPosts(String field, String sortOrder) {
        if (sortOrder == null) {
            return userPostRepository.findAll();
        } else {
            Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(Sort.Direction.ASC, field) : Sort.by(Sort.Direction.DESC, field);
            return userPostRepository.findAll(sort);
        }
    }

    private List<UserPost> getFilteredSortedPosts(String dateField, String sortOrder) {
        List<UserPost> userPostAllNotNull = new ArrayList<>();
        if (sortOrder == null) {
            return userPostRepository.findAll();
        } else {
            Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(Sort.Direction.ASC, dateField) : Sort.by(Sort.Direction.DESC, dateField);
            List<UserPost> userPostAll = userPostRepository.findAll(sort);
            for (UserPost uP : userPostAll) {
                if (getDateFieldValue(uP, dateField) != null) {
                    userPostAllNotNull.add(uP);
                }
            }
        }
        return userPostAllNotNull;
    }

    private Date getDateFieldValue(UserPost userPost, String dateField) {
        switch (dateField) {
            case "post.submittedDate":
                return userPost.getPost().getSubmittedDate();
            case "post.creationDate":
                return userPost.getPost().getCreationDate();
            case "post.updateDate":
                return userPost.getPost().getUpdateDate();
            case "post.displayDate":
                return userPost.getPost().getDisplayDate();
            case "post.expiryDate":
                return userPost.getPost().getExpiryDate();
            default:
                return null;
        }
    }

    //SORT BY YES OR NO
    public List<UserPost> getPostsSortedByPostActive(String sortChoice) {
        if (sortChoice == null || sortChoice.isEmpty()) {
            return userPostRepository.findAll();
        } else if (sortChoice.equalsIgnoreCase("yes")) {
            // Return posts where active is true
            return userPostRepository.findByPostActive(true);
        } else if (sortChoice.equalsIgnoreCase("no")) {
            // Return posts where active is false
            return userPostRepository.findByPostActive(false);
        } else {
            // If the sortChoice is not recognized, return all posts
            return userPostRepository.findAll();
        }
    }

    public List<UserPost> getPostsSortedByPostApproved(String sortChoice) {
        if (sortChoice == null || sortChoice.isEmpty()) {
            return userPostRepository.findAll();
        } else if (sortChoice.equalsIgnoreCase("yes")) {
            // Return posts where active is true
            return userPostRepository.findByPostApproved(true);
        } else if (sortChoice.equalsIgnoreCase("no")) {
            // Return posts where active is false
            return userPostRepository.findByPostApproved(false);
        } else {
            // If the sortChoice is not recognized, return all posts
            return userPostRepository.findAll();
        }
    }

    public List<UserPost> getPostsSortedByUser(String authorId) {
        int authorIdInt = Integer.parseInt(authorId);
        if (authorId == null || authorId.isEmpty()) {
            return userPostRepository.findAll();
        } else {
            // Perform sorting and return the sorted list
            return userPostRepository.findUserPostsByUserUserId(authorIdInt);
        }

    }

    public List<UserPost> getFilteredAndSortedPosts(String filterAttribute, String filterOrder, String filterYesOrNo, String filterAuthor) {
        List<UserPost> userPostList = userPostRepository.findAll();

        if (filterAttribute != null) {
            switch (filterAttribute) {
                case "id":
                    return getPostsSortedByPostPostId(filterOrder);
                case "submittedDate":
                    return getPostsSortedByPostSubmittedDate(filterOrder);
                case "creationDate":
                    return getPostsSortedByPostCreationDate(filterOrder);
                case "updateDate":
                    return getPostsSortedByPostUpdateDate(filterOrder);
                case "displayDate":
                    return getPostsSortedByPostDisplayDate(filterOrder);
                case "expiryDate":
                    return getPostsSortedByPostExpiryDate(filterOrder);
                case "active":
                    return getPostsSortedByPostActive(filterYesOrNo);
                case "approved":
                    return getPostsSortedByPostApproved(filterYesOrNo);
                case "author":
                    return getPostsSortedByUser(filterAuthor);
                default:
                    return userPostList;
            }
        }
        return userPostList;
    }

}
