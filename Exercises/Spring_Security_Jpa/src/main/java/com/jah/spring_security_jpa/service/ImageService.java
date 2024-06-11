/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.service;

import com.jah.spring_security_jpa.dto.AdminPhoto;
import com.jah.spring_security_jpa.dto.DefaultImage;
import com.jah.spring_security_jpa.dto.PhotoDTO;
import com.jah.spring_security_jpa.models.Post;
import com.jah.spring_security_jpa.repositories.AdminPhotoRepository;
import com.jah.spring_security_jpa.repositories.DefaultImageRepository;
import com.jah.spring_security_jpa.repositories.PhotoDTORepository;
import com.jah.spring_security_jpa.repositories.PostRepository;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author drjal
 */
@Service
public class ImageService {

    @Autowired
    PostRepository posts;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    private PhotoDTORepository photoRepo;

    @Autowired
    private DefaultImageRepository defaultImageRepo;

    @Autowired
    private AdminPhotoRepository adminPhotoRepo;

    @Autowired
    ImageService imageService;

    public String getDefaultBase64Image() {
        DefaultImage defaultImage = defaultImageRepo.findFirstByOrderByDefaultImageIdAsc();
        return encodeImageToBase64(defaultImage != null ? defaultImage.getDefaultImage() : null);
    }

    public String getBase64ImageForPost(Integer imageId, String defaultBase64Image) {
        PhotoDTO photoDTO = photoRepo.findById(imageId).orElse(null);
        return encodeImageToBase64(photoDTO != null ? photoDTO.getImage() : null, defaultBase64Image);
    }

    public String getAdminBase64Image(String defaultBase64Image) {
        AdminPhoto adminPhoto = adminPhotoRepo.findFirstByOrderByAdminImageIdAsc();
        return encodeImageToBase64(adminPhoto != null ? adminPhoto.getAdminImage() : null, defaultBase64Image);
    }

    private String encodeImageToBase64(byte[] imageData) {
        return imageData != null ? Base64.getEncoder().encodeToString(imageData) : "";
    }

    private String encodeImageToBase64(byte[] imageData, String defaultBase64Image) {
        return imageData != null ? Base64.getEncoder().encodeToString(imageData) : defaultBase64Image;
    }

    public Map<Integer, String> getPostIdWithBase64Image(List<Post> filteredPosts, String defaultBase64Image) {
        Map<Integer, String> postIdWithBase64Image = new HashMap<>();
        for (Post post : filteredPosts) {
            String base64Image = getBase64ImageForPost(post.getImageId(), defaultBase64Image);
            postIdWithBase64Image.put(post.getPostId(), base64Image);
        }
        return postIdWithBase64Image;
    }

}
