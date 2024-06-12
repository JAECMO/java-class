/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.myblog.models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author drjal
 */
@Entity
@SuppressWarnings({"PersistenceUnitPresent", "SerializableClass", "ValidPrimaryTableName"})
public class Tag implements Comparable<Tag> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagId;
   
    private String name;
    
    @ManyToMany(mappedBy = "tags")
    private List<Post> posts;

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
    
     @Override
    public int compareTo(Tag other) {
        return Integer.compare(this.tagId, other.tagId);
    }
}
