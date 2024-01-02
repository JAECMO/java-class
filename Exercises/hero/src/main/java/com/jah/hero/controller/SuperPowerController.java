/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.controller;

import com.jah.hero.dao.SuperPowerDao;
import com.jah.hero.dto.SuperPower;
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
public class SuperPowerController {
    
    @Autowired
    SuperPowerDao superPowerDao;
    
    Set<ConstraintViolation<SuperPower>> violations = new HashSet<>();
    
     @GetMapping("superPower")
    public String displaySuperPower(Model model) {
        List<SuperPower> superPowers = superPowerDao.getAllSuperPower();
        model.addAttribute("superPowers", superPowers);
        if (!violations.isEmpty()) {
            violations.clear();

        }
        model.addAttribute("errors", violations);
        return "superPower";
    }
    
    @PostMapping("addSuperPower")
    public String addSuperPower(SuperPower superPower,Model model,HttpServletRequest request) {
        
        String name = request.getParameter("name");
     
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superPower);
      
        if (violations.isEmpty()) {
            superPower.setName(name);
            model.addAttribute("hasErrors", false);
            superPowerDao.addSuperPower(superPower);
            return "redirect:/superPower";
        } else {
            List<SuperPower> superPowers = superPowerDao.getAllSuperPower();
            model.addAttribute("superPowers", superPowers);
            model.addAttribute("hasErrors", true);
            model.addAttribute("errors", violations);
            return "superPower";
        }
        
    }
    
    @GetMapping("deleteSuperPower")
    public String deleteSuperPower(Integer id) {
        superPowerDao.deletesuperPower(id);
        return "redirect:/superPower";
    }
    
    
    @GetMapping("editSuperPower")
    public String editSuperPower(Integer id, Model model) {
       
        SuperPower superPower = superPowerDao.getSuperPowerById(id);
        
        model.addAttribute("superPower", superPower);
        model.addAttribute("name", superPower.getName());
        
        if(!violations.isEmpty()){
             violations.clear();
        }
        model.addAttribute("errors", violations);
        
        return "editSuperPower";
    }
    
       @PostMapping("editSuperPower")
    public String performEditStudent(Model model, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        SuperPower superPower = superPowerDao.getSuperPowerById(id);

        String name = request.getParameter("name");

        superPower.setName(name);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superPower);

        if (violations.isEmpty()) {
            superPowerDao.updateSuperPower(superPower);
            return "redirect:/superPower";
        } else {
            model.addAttribute("superPower",superPower);
            model.addAttribute("name", name);
            model.addAttribute("errors", violations);
            return "editSuperPower";

        }
    }
    
}
