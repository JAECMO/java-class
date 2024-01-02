/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.service;

import com.jah.hero.dao.HeroDao;
import com.jah.hero.dao.OrganizationDao;
import com.jah.hero.dto.Hero;
import com.jah.hero.dto.Organization;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 *
 * @author drjal
 */
@Service
public class OrganizationService {

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    HeroDao heroDao;

    public List<Organization> menuSelectionAndOrganizationList(Model model, HttpServletRequest request) {
        String heroId = request.getParameter("heroId");
        String allHeroes = "All Heroes";
        model.addAttribute("AllHeroes", allHeroes);

        List<Organization> organizationByHero = new ArrayList();

        if (heroId != null) {
            try {
                Integer heroIdInt = Integer.parseInt(heroId);
                Hero hero = heroDao.getHeroById(Integer.parseInt(heroId));
                organizationByHero = organizationDao.getAllOrganizationsForHero(hero);
                model.addAttribute("selectedOrganizationId", heroIdInt);
            } catch (NumberFormatException e) {
                organizationByHero = organizationDao.getAllOrganizations();
                model.addAttribute("selectedOrganizationId", allHeroes);
            }

        } else {
            organizationByHero = organizationDao.getAllOrganizations();
        }
        return organizationByHero;
    }

    public Organization organizationCreation(Organization organization, HttpServletRequest request) {

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String addressInfo = request.getParameter("addressInfo");
        String contactInfo = request.getParameter("contactInfo");
        String isHero = request.getParameter("isHero");
        String[] heroIds = request.getParameterValues("heroId");

        Boolean isHeroBool;

        isHeroBool = isHero.equals("Yes");

        List<Hero> heroes = new ArrayList<>();
        if (heroIds != null) {
            for (String heroId : heroIds) {
                heroes.add(heroDao.getHeroById(Integer.parseInt(heroId)));
            }
        }

        organization.setName(name);
        organization.setDescription(description);
        organization.setAddressInfo(addressInfo);
        organization.setContactInfo(contactInfo);
        organization.setIsHero(isHeroBool);
        organization.setMembers(heroes);

        return organization;
    }
}
