/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.repositories;

import com.jah.spring_security_jpa.dto.TempImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author drjal
 */
@Repository
public interface TempImageRepository extends JpaRepository<TempImage, Integer> {
    public TempImage findFirstByOrderByTempImageIdAsc();
}
