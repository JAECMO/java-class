/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.repositories;

import com.jah.spring_security_jpa.models.Post;
import com.jah.spring_security_jpa.models.User;
import com.jah.spring_security_jpa.models.UserPost;
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
public interface UserPostRepository extends JpaRepository<UserPost, Integer> {
//    List<Post> getPostsSortedByPostPostId(String sortOrder);
//    List<Post> getPostsSortedByPostCreationDate(String sortOrder);
//    getPostsSortedByPostUpdateDate(String sortOrder)
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_post WHERE user_id = :userId", nativeQuery = true)
    void deleteUserPostByUserId(@Param("userId") int userId);
    
    List<UserPost> findUserPostsByUserUserId(int userId);
    User findByPostPostId(int postId);
    List<Post> findPostsBy();
    

}
