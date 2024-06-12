/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.myblog.dto;

import com.jah.myblog.models.Post;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author drjal
 */
@Entity
@Table(name = "image")
@SuppressWarnings({"PersistenceUnitPresent", "SerializableClass"})
public class PhotoDTO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;
    
    
    @Lob
    private byte[] image;
    
    
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;


    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}
