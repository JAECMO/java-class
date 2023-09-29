/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.dao;

import com.jah.hero.dto.Hero;
import com.jah.hero.dto.Organization;
import java.util.List;

/**
 *
 * @author drjal
 */
public interface OrganizationDao {

    Organization getOrganization(int id);

    Organization addOrganization(Organization organization);

    List<Organization> getAllOrganizations();

    void updateOrganization(Organization organization);

    void deleteOrganization(int id);

    List<Organization> getAllOrganizationsForHero(Hero hero);

}
