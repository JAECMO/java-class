/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.controller;

import com.jah.hero.dao.HeroDao;
import com.jah.hero.dao.OrganizationDao;
import com.jah.hero.dto.Hero;
import com.jah.hero.dto.Location;
import com.jah.hero.dto.Organization;
import com.jah.hero.dto.SuperPower;
import com.jah.hero.service.OrganizationService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author drjal
 */
@Controller
public class OrganizationController {

    @Autowired
    HeroDao heroDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    OrganizationService organizationService;

    Set<ConstraintViolation<Organization>> violations = new HashSet<>();

    @GetMapping("organization")
    public String displayOrganization(Model model, HttpServletRequest request) {
        List<Organization> organizationByHero = organizationService.menuSelectionAndOrganizationList(model, request);
        List<Hero> heroes = heroDao.getAllHeroes();

        model.addAttribute("organizationByHero", organizationByHero);
        model.addAttribute("savedHeroes", heroes);
        model.addAttribute("heroes", heroes);

        if (!violations.isEmpty()) {
            violations.clear();
        }
        model.addAttribute("errors", violations);

        return "organization";
    }

    @PostMapping("organization")
    public String displayOrganizationBySuperHero(Model model, HttpServletRequest request) {
        List<Organization> organizationByHero = organizationService.menuSelectionAndOrganizationList(model, request);

        List<Hero> heroes = heroDao.getAllHeroes();

        model.addAttribute("organizationByHero", organizationByHero);
        model.addAttribute("heroes", heroes);
        model.addAttribute("savedHeroes", heroes);

        model.addAttribute("errors", violations);

        return "organization";
    }

    @PostMapping("addOrganization")
    public String addOrganization(Organization organization, Model model, HttpServletRequest request) {
       List<Organization> organizationByHero = organizationService.menuSelectionAndOrganizationList(model, request);


       organization = organizationService.organizationCreation(organization, request);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(organization);
        
          if (violations.isEmpty()) {
            organizationDao.addOrganization(organization);
            return "redirect:/organization";
        } else {

            List<Hero> heroes = heroDao.getAllHeroes();
            model.addAttribute("heroes", heroes);
            model.addAttribute("savedHeroes", heroes);
            model.addAttribute("organizationByHero", organizationByHero);
            model.addAttribute("errors", violations);
            return "organization";
        }
        
    }
    
    @GetMapping("organizationDetail")
    public String organizationDetail(Integer id, Model model) {
        Organization organization = organizationDao.getOrganization(id);
        model.addAttribute("organization", organization);
        return "organizationDetail";
    }
    
    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id) {
        organizationDao.deleteOrganization(id);
        return "redirect:/organization";
    }
    
    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model) {
        Organization organization = organizationDao.getOrganization(id);
        List<Hero> heroes = heroDao.getAllHeroes();
        
        model.addAttribute("organization", organization);
        model.addAttribute("savedHeroes", heroes);

        if (!violations.isEmpty()) {
            violations.clear();
        }
        model.addAttribute("errors", violations);

        return "editOrganization";
    }
   
     @PostMapping("editOrganization")
    public String performEditOrganization(Model model, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organization organization = organizationDao.getOrganization(id);

        organization = organizationService.organizationCreation(organization, request);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(organization);

        if (violations.isEmpty()) {
            organizationDao.updateOrganization(organization);
            return "redirect:/organization";
        } else {
           
            model.addAttribute("organization", organization);
            model.addAttribute("errors", violations);
            return "editOrganization";

        }
    }
    
}
