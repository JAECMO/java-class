/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.dto;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 *
 * @author drjal
 */
public class Organization {

    private int organizationId;
    @NotBlank(message = "Name must not be empty.")
    @Size(max = 50, message = "Name must be at most 50 characters.")
    private String name;
    @Size(max = 300, message = "Description must be at most 300 characters.")
    private String description;
    @NotBlank(message = "Address must not be empty.")
    @Size(max = 100, message = "Address must be at most 100 characters.")
    private String addressInfo;
    @NotBlank(message = "Contact must not be empty.")
    @Size(max = 100, message = "Contact must be at most 150 characters.")
    private String contactInfo;
    @NotEmpty(message = "Add at least one Hero/Vilain to the organization")
    private List<Hero> members;
    private boolean isHero;

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<Hero> getMembers() {
        return members;
    }

    public void setMembers(List<Hero> members) {
        this.members = members;
    }

    public boolean isIsHero() {
        return isHero;
    }

    public void setIsHero(boolean isHero) {
        this.isHero = isHero;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + this.organizationId;
        hash = 31 * hash + Objects.hashCode(this.name);
        hash = 31 * hash + Objects.hashCode(this.description);
        hash = 31 * hash + Objects.hashCode(this.addressInfo);
        hash = 31 * hash + Objects.hashCode(this.contactInfo);
        hash = 31 * hash + Objects.hashCode(this.members);
        hash = 31 * hash + (this.isHero ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Organization other = (Organization) obj;
        if (this.organizationId != other.organizationId) {
            return false;
        }
        if (this.isHero != other.isHero) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.addressInfo, other.addressInfo)) {
            return false;
        }
        if (!Objects.equals(this.contactInfo, other.contactInfo)) {
            return false;
        }
        if (!Objects.equals(this.members, other.members)) {
            return false;
        }
        return true;
    }

}
