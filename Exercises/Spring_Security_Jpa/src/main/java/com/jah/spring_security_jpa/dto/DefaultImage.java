/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author drjal
 */
@Entity
@Table(name = "defaultImage")
@SuppressWarnings({"PersistenceUnitPresent", "SerializableClass"})
public class DefaultImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int defaultImageId;
    
    
    @Lob
    private byte[] defaultImage;

    
    public int getDefaultImageId() {
        return defaultImageId;
    }

    public void setDefaultImageId(int defaultImageId) {
        this.defaultImageId = defaultImageId;
    }

    public byte[] getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(byte[] defaultImage) {
        this.defaultImage = defaultImage;
    }

    

}
