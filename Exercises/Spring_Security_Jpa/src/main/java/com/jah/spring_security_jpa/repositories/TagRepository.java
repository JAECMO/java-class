/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.repositories;

import com.jah.spring_security_jpa.models.Tag;
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
public interface TagRepository extends JpaRepository<Tag, Integer>  {
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM post_tag WHERE tag_id = :tagId", nativeQuery = true)
    void deletePostTagByTagId(@Param("tagId") int tagId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Tag t WHERE t.tagId = :tagId", nativeQuery = true)
    void deleteTagByTagId(@Param("tagId") int tagId);

}
