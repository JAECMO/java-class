/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.models;

import com.jah.spring_security_jpa.dto.PhotoDTO;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author drjal
 */
@Entity
@Table(name = "post")
@SuppressWarnings({"PersistenceUnitPresent", "SerializableClass"})
public class Post {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;
    
    private String title;
    @Column(columnDefinition = "TEXT", length = 1000)
    private String body;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date displayDate;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updateDate;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date expiryDate;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date submittedDate;
    private int imageId;   
    private boolean approved = true;
    private boolean active = true;
  
    
    @ManyToMany
    @JoinTable(name = "post_tag",
            joinColumns = {@JoinColumn(name = "postId")},
            inverseJoinColumns = {@JoinColumn(name = "tagId")})
    private List<Tag> tags;
    
        

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private PhotoDTO postImage; 


    public PhotoDTO getPostImage() {
        return postImage;
    }

    public void setPostImage(PhotoDTO postImage) {
        this.postImage = postImage;
    }

    

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    
    public Date getSubmittedDate() {
        return submittedDate;
    }
   
    public void setSubmittedDate(Date submittedDate) {    
        this.submittedDate = submittedDate;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
    
     
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    

    public Date getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(Date displayDate) {
        this.displayDate = displayDate;
    }



    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
        

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    
}
