/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.repositories;

import com.jah.spring_security_jpa.models.Post;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author drjal
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
List<Post> getPostsSortedByCreationDate(String sortOrder);
List<Post> getPostsSortedByUpdateDate(String sortOrder);
List<Post> getPostsSortedByDisplayDate(String sortOrder);
List<Post> getPostsSortedByExpiryDate(String sortOrder);
List<Post> getPostsSortedByActive(String sortChoice);
List<Post> getPostsSortedByApproved(String sortChoice);
List<Post> getPostsSortedByPostId(String sortOrder);

    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM post_tag WHERE post_id = :postId", nativeQuery = true)
    void deletePostTagByPostId(@Param("postId") int postId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM post WHERE post_id = :postId", nativeQuery = true)
    void deletePostByPostId(@Param("postId") int postId);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_post WHERE post_id = :postId", nativeQuery = true)
    void deleteUserPostByPostId(@Param("postId") int postId);
    
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE post SET id = 0 WHERE id = :userId", nativeQuery = true)
    void updateUserInPostsToZero(@Param("userId") int userId);
    
}
