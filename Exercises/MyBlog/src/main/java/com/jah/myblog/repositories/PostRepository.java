/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.myblog.repositories;

import com.jah.myblog.models.Post;
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
