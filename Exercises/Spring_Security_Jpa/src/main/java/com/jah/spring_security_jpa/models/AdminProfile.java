/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.models;

import com.jah.spring_security_jpa.dto.AdminPhoto;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author drjal
 */
@Entity
@Table(name = "adminProfile")
@SuppressWarnings({"PersistenceUnitPresent", "SerializableClass", "ValidPrimaryTableName"})
public class AdminProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminProfileId;
    
    private String adminName;
    @Column(columnDefinition = "TEXT", length = 1000)
    private String body;
    
    @OneToOne(mappedBy = "adminProfile", cascade = CascadeType.ALL)
    private AdminPhoto adminPhoto;

    public int getAdminProfileId() {
        return adminProfileId;
    }

    public void setAdminProfileId(int adminProfileId) {
        this.adminProfileId = adminProfileId;
    }



    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }



    public AdminPhoto getAdminPhoto() {
        return adminPhoto;
    }

    public void setAdminPhoto(AdminPhoto adminPhoto) {
        this.adminPhoto = adminPhoto;
    }
    
    
}
