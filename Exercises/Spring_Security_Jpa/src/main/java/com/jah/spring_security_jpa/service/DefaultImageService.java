/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.service;

import com.jah.spring_security_jpa.dto.DefaultImage;
import com.jah.spring_security_jpa.repositories.DefaultImageRepository;
import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author drjal
 */
@Service
public class DefaultImageService {

    @Autowired
    private DefaultImageRepository defaultImageRepo;

    public String getDefaultBase64Image() {
        DefaultImage defaultImage = defaultImageRepo.findFirstByOrderByDefaultImageIdAsc();
        String defaultBase64Image = "";
        try {
            byte[] imageDefaultData = defaultImage.getDefaultImage();
            defaultBase64Image = imageDefaultData != null ? Base64.getEncoder().encodeToString(imageDefaultData) : "";
        } catch (Exception ex) {
            // Log the exception if necessary
        }
        return defaultBase64Image;
    }

    public DefaultImage addOrUpdateDefaultImage(byte[] imageData, boolean changeImage) throws IOException {

        DefaultImage existingDefaultImage = defaultImageRepo.findFirstByOrderByDefaultImageIdAsc();

        if (changeImage) {
            if (existingDefaultImage != null) {
                existingDefaultImage.setDefaultImage(imageData);
                defaultImageRepo.save(existingDefaultImage);
            } else {
                DefaultImage defaultImage = new DefaultImage();
                defaultImage.setDefaultImage(imageData);
                defaultImageRepo.save(defaultImage);
            }
        }

        DefaultImage savedDefaultImage = defaultImageRepo.findFirstByOrderByDefaultImageIdAsc();
        return savedDefaultImage;
    }
}
