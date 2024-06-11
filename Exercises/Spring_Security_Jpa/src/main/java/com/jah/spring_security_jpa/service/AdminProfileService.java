/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.service;

import com.jah.spring_security_jpa.dto.AdminPhoto;
import com.jah.spring_security_jpa.models.AdminProfile;
import com.jah.spring_security_jpa.repositories.AdminPhotoRepository;
import com.jah.spring_security_jpa.repositories.AdminProfileRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author drjal
 */
@Service
public class AdminProfileService {

    @Autowired
    private AdminProfileRepository adminProfileRepo;

    @Autowired
    private AdminPhotoRepository adminPhotoRepo;

    public AdminProfile getOrCreateAdminProfile() {
        AdminProfile adminProfile = adminProfileRepo.findFirstByOrderByAdminProfileIdAsc();
        if (adminProfile == null) {
            adminProfile = new AdminProfile();
            adminProfile.setAdminName("_ _ _");
            adminProfile.setBody("");
        }
        return adminProfile;
    }

    public AdminPhoto getExistingAdminPhoto() {
        return adminPhotoRepo.findFirstByOrderByAdminImageIdAsc();
    }

    public byte[] validateAndProcessImage(MultipartFile image) throws IOException {
        if (!image.isEmpty()) {
            if (!(image.getContentType().equals("image/png") || image.getContentType().equals("image/jpeg"))) {
                throw new IllegalArgumentException("Wrong Image type. Choose Png/Jpeg type.");
            }
            if (image.getSize() > 5 * 1024 * 1024) {
                throw new IllegalArgumentException("Image Size exceeds 5 mb");
            }
            return image.getBytes();
        }
        return null;
    }

    public AdminPhoto saveAdminPhoto(byte[] imageData, boolean changeImage, AdminPhoto existingAdminPhoto, AdminPhoto savedAdminPhoto) {
        if (changeImage) {
            if (existingAdminPhoto != null) {
                // If a default image exists, update its data
                existingAdminPhoto.setAdminImage(imageData);
                savedAdminPhoto = existingAdminPhoto;

            } else {
                // If no default image exists, create a new one
                AdminPhoto adminPhoto = new AdminPhoto();
                adminPhoto.setAdminImage(imageData);
                savedAdminPhoto = adminPhoto;

            }
        } else {
            if (existingAdminPhoto != null) {
                // If a default image exists, update its data
                savedAdminPhoto = existingAdminPhoto;

            } else {
                // If no default image exists, create a new one
                AdminPhoto adminPhoto = new AdminPhoto();
                adminPhoto.setAdminImage(imageData);
                savedAdminPhoto = adminPhoto;

            }
        }
        return savedAdminPhoto;
    }

    public void saveAdminProfile(String adminName, String body, AdminPhoto savedAdminPhoto, AdminProfile existingAdminProfile) {

        if (existingAdminProfile != null) {
            existingAdminProfile.setAdminName(adminName);
            existingAdminProfile.setBody(body);
            savedAdminPhoto.setAdminProfile(existingAdminProfile);
            existingAdminProfile.setAdminPhoto(savedAdminPhoto);
            adminProfileRepo.save(existingAdminProfile);
        } else {
            AdminProfile adminProfile = new AdminProfile();
            adminProfile.setAdminName(adminName);
            adminProfile.setBody(body);
            savedAdminPhoto.setAdminProfile(adminProfile);
            adminProfile.setAdminPhoto(savedAdminPhoto);
            adminProfileRepo.save(adminProfile);
        }
    }

}
