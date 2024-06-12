/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.myblog.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author drjal
 */
@Entity
@SuppressWarnings({"PersistenceUnitPresent", "SerializableClass", "ValidPrimaryTableName"})
@Table(name = "user_post")
public class UserPost {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UserPostId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    
    public UserPost() {
        // Default constructor
    }

    // Constructor to set user and post
    public UserPost(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    public int getUserPostId() {
        return UserPostId;
    }

    public void setUserPostId(int UserPostId) {
        this.UserPostId = UserPostId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
    
    
}
