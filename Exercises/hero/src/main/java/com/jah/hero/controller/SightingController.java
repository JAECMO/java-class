/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.controller;

import com.jah.hero.dao.HeroDao;
import com.jah.hero.dao.LocationDao;
import com.jah.hero.dao.SightingDao;
import com.jah.hero.dto.Hero;
import com.jah.hero.dto.Location;
import com.jah.hero.dto.Sighting;
import com.jah.hero.service.HeroService;
import com.jah.hero.service.SightingService;
import java.io.File;
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
public class SightingController {

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    HeroService heroService;

    @Autowired
    SightingService sightingService;

    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();

//    String errorDate = null;
    @GetMapping("sighting")
    public String displaySighting(Model model, HttpServletRequest request) {
        List<Hero> heroes = heroDao.getAllHeroes();
        List<Location> locations = locationDao.getAllLocations();

        List<Sighting> sightingsByDate = sightingService.menuSelectionAndSightingList(model, request);
        List<Sighting> sightings = sightingDao.getAllSightings();

        model.addAttribute("sightingsByDate", sightingsByDate);
        model.addAttribute("sightings", sightings);

        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);

        if (!violations.isEmpty()) {
            violations.clear();
        }
        model.addAttribute("errors", violations);
        return "sighting";
    }

    @PostMapping("sighting")
    public String displaySightingByDate(Model model, HttpServletRequest request) {
        List<Hero> heroes = heroDao.getAllHeroes();
        List<Location> locations = locationDao.getAllLocations();
        List<Sighting> sightingsByDate = sightingService.menuSelectionAndSightingList(model, request);
        List<Sighting> sightings = sightingDao.getAllSightings();

        model.addAttribute("sightingsByDate", sightingsByDate);
        model.addAttribute("sightings", sightings);

        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);

        model.addAttribute("errors", violations);
        return "sighting";
    }

    @PostMapping("addSighting")
    public String addSighting(Sighting sighting, Model model, HttpServletRequest request) {

        sighting = sightingService.sightingCreation(sighting, request);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        if (violations.isEmpty()) {
            sightingDao.addSighting(sighting);
            return "redirect:/sighting";
        } else {
            List<Sighting> sightingsByDate = sightingService.menuSelectionAndSightingList(model, request);
            List<Hero> heroes = heroDao.getAllHeroes();
            List<Location> locations = locationDao.getAllLocations();
            List<Sighting> sightings = sightingDao.getAllSightings();
            model.addAttribute("sightingsByDate", sightingsByDate);
            model.addAttribute("sightings", sightings);
            model.addAttribute("heroes", heroes);
            model.addAttribute("locations", locations);
            model.addAttribute("errors", violations);
            return "sighting";

        }

    }

//    @GetMapping("sightingDetail")
//    public String sightingDetail(Integer id, Model model) {
//        Sighting sighting = sightingDao.getSighting(id);
//        model.addAttribute("sighting", sighting);
//        
//        
//        
//        return "sightingDetail";
//    }
    
    @GetMapping("sightingDetail")
    public String sightingDetail(Integer id, Model model) {
        Sighting sighting = sightingDao.getSighting(id);

       

        String image = sighting.getHero().getHeroId() + ".jpg";
        String imageDefault = "default.png";
        
        // Check if the image file exists
        String absolutePath = "src\\main\\resources\\static\\images" + "\\" + image;
        File imageFile = new File(absolutePath);

        if (imageFile.exists()) {
            model.addAttribute("image", image);
        } else {
            model.addAttribute("image", imageDefault);
        }

        model.addAttribute("sighting", sighting);
        return "sightingDetail";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingDao.deleteSighting(id);
        return "redirect:/sighting";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sighting = sightingDao.getSighting(id);
        List<Hero> heroes = heroDao.getAllHeroes();
        List<Location> locations = locationDao.getAllLocations();

        try {
            Integer heroIdInt = sighting.getHero().getHeroId();
            model.addAttribute("selectedHeroId", heroIdInt);
        } catch (Exception e) {
        }

        try {
            Integer locationIdInt = sighting.getLocation().getLocationId();
            model.addAttribute("selectedLocationId", locationIdInt);
        } catch (Exception e) {
        }

        model.addAttribute("sighting", sighting);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);

        if (!violations.isEmpty()) {
            violations.clear();
        }
        model.addAttribute("errors", violations);

        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(Model model, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSighting(id);

        sighting = sightingService.sightingCreation(sighting, request);

        try {
            Integer heroIdInt = sighting.getHero().getHeroId();
            model.addAttribute("selectedHeroId", heroIdInt);
        } catch (Exception e) {
        }

        try {
            Integer locationIdInt = sighting.getLocation().getLocationId();
            model.addAttribute("selectedLocationId", locationIdInt);
        } catch (Exception e) {
        }

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        if (violations.isEmpty()) {
            sightingDao.updateSighting(sighting);
            return "redirect:/sighting";
        } else {
            List<Hero> heroes = heroDao.getAllHeroes();
            List<Location> locations = locationDao.getAllLocations();
            model.addAttribute("sighting", sighting);
            model.addAttribute("heroes", heroes);
            model.addAttribute("locations", locations);
            model.addAttribute("errors", violations);
            return "editSighting";

        }

    }
}
