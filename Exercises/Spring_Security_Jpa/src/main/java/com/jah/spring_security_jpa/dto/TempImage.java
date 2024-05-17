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
@Table(name = "tempImage")
@SuppressWarnings({"PersistenceUnitPresent", "SerializableClass"})
public class TempImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tempImageId;
    
    @Lob
    private byte[] tempImage;

    
    public int getTempImageId() {
        return tempImageId;
    }

    public void setTempImageId(int tempImageId) {
        this.tempImageId = tempImageId;
    }

    public byte[] getTempImage() {
        return tempImage;
    }

    public void setTempImage(byte[] tempImage) {
        this.tempImage = tempImage;
    }
    
    
    
}
