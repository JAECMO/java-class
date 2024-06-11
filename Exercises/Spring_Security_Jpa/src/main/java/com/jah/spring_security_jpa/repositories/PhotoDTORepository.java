/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.repositories;

import com.jah.spring_security_jpa.dto.PhotoDTO;
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
public interface PhotoDTORepository extends JpaRepository<PhotoDTO, Integer>{
    // Method to find a PhotoDTO by post ID
    PhotoDTO findByPostPostId(int postId);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM image WHERE post_id = :postId", nativeQuery = true)
    void deleteImagetByPostId(@Param("postId") int postId);   
}
