/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.service;

import com.jah.spring_security_jpa.dto.PhotoDTO;
import com.jah.spring_security_jpa.repositories.PhotoDTORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author drjal
 */
@Service
public class PhotoDTOService {

    @Autowired
    private PhotoDTORepository photoDTORepository;

    public byte[] getImageByPostId(int postId) {
        // Find the PhotoDTO by post ID
        PhotoDTO photoDTO = photoDTORepository.findByPostPostId(postId);

        // Check if the PhotoDTO exists
        if (photoDTO != null) {
            // Return the image data
            return photoDTO.getImage();
        } else {
            // Return null if no image is found for the given post ID
            return null;
        }
    }

}
