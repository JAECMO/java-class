/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;


/**
 *
 * @author drjal
 */
@Entity
@SuppressWarnings({"PersistenceUnitPresent", "SerializableClass", "ValidPrimaryTableName"})
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    
//    @Column(unique = true)
    private String userName;
    private String password;
    private boolean active;
    private String roles;
    
    
//    @ManyToMany
//    @JoinTable(name = "user_post",
//            joinColumns = {@JoinColumn(name = "userId")},
//            inverseJoinColumns = {@JoinColumn(name = "postId")})
//    private List<Post> posts;

//    @ManyToMany
//    @JoinTable(name = "user_post",
//            joinColumns = {@JoinColumn(name = "userId")},
//            inverseJoinColumns = {@JoinColumn(name = "postId")})
//    private List<Post> posts;
//
//    public List<Post> getPosts() {
//        return posts;
//    }
//
//    public void setPosts(List<Post> posts) {
//        this.posts = posts;
//    }

//    public List<Post> getPosts() {
//        return posts;
//    }
//
//    public void setPosts(List<Post> posts) {
//        this.posts = posts;
//    }


    
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
    
    
    
}
