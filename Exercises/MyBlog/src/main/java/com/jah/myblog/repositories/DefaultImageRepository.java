/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.myblog.repositories;

import com.jah.myblog.dto.DefaultImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author drjal
 */
@Repository
public interface DefaultImageRepository extends JpaRepository<DefaultImage, Integer> {
    public DefaultImage findFirstByOrderByDefaultImageIdAsc();    
}
