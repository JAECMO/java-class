/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.myblog.dto;

import com.jah.myblog.models.AdminProfile;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

/**
 *
 * @author drjal
 */
@Entity
@SuppressWarnings({"PersistenceUnitPresent", "SerializableClass"})
public class AdminPhoto {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminImageId;
    
    
    @Lob
    private byte[] adminImage;
    
     @OneToOne
    @JoinColumn(name = "admin_profile_Id")
     private AdminProfile adminProfile;
    
    public int getAdminImageId() {
        return adminImageId;
    }

    public void setAdminImageId(int adminImageId) {
        this.adminImageId = adminImageId;
    }

    public byte[] getAdminImage() {
        return adminImage;
    }

    public void setAdminImage(byte[] adminImage) {
        this.adminImage = adminImage;
    }

    public AdminProfile getAdminProfile() {
        return adminProfile;
    }

    public void setAdminProfile(AdminProfile adminProfile) {
        this.adminProfile = adminProfile;
    }
    
    
    
}
