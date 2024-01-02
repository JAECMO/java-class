/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.controller;

import com.jah.hero.dao.HeroDao;
import com.jah.hero.dao.LocationDao;
import com.jah.hero.dto.Hero;
import com.jah.hero.dto.Location;
import com.jah.hero.service.LocationService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author drjal
 */
@Controller
public class LocationController {

    @Autowired
    LocationService locationService;

    @Autowired
    LocationDao locationDao;

    @Autowired
    HeroDao heroDao;

    Set<ConstraintViolation<Location>> violations = new HashSet<>();

    String errorLat = null;
    String errorLon = null;

    @GetMapping("location")
    public String displayLocation(Model model, HttpServletRequest request) {
        List<Location> locationsByHero = locationService.menuSelectionAndLocationList(model, request);

        List<Hero> heroes = heroDao.getAllHeroes();

        model.addAttribute("locationsByHero", locationsByHero);
        model.addAttribute("heroes", heroes);

        if (!violations.isEmpty() || errorLat != null || errorLon != null) {
            violations.clear();
            errorLat = null;
            errorLon = null;
        }
        model.addAttribute("errors", violations);
        model.addAttribute("errorLat", errorLat);
        model.addAttribute("errorLon", errorLon);
        return "location";
    }

    @PostMapping("location")
    public String displayLocationBySuperHero(Model model, HttpServletRequest request) {
        List<Location> locationsByHero = locationService.menuSelectionAndLocationList(model, request);

        List<Hero> heroes = heroDao.getAllHeroes();

        model.addAttribute("locationsByHero", locationsByHero);
        model.addAttribute("heroes", heroes);

        model.addAttribute("errors", violations);
        return "location";
    }

    @PostMapping("addLocation")
    public String addLocation(@ModelAttribute("location") @Valid Location location,
            BindingResult bindingResult, Model model, HttpServletRequest request) {

        violations.clear();

        String heroId = request.getParameter("heroId");
        String allHeroes = "All Heroes";
        model.addAttribute("AllHeroes", allHeroes);

        List<Location> locationsByHero = new ArrayList();

        if (heroId != null) {
            try {
                Integer heroIdInt = Integer.parseInt(heroId);
                Hero hero = heroDao.getHeroById(Integer.parseInt(heroId));
                locationsByHero = locationDao.getLocationsForHero(hero);
                model.addAttribute("selectedLocationId", heroIdInt);
            } catch (NumberFormatException e) {
                locationsByHero = locationDao.getAllLocations();
                model.addAttribute("selectedLocationId", allHeroes);
            }

        } else {
            locationsByHero = locationDao.getAllLocations();
        }

        location = locationService.locationAndErrorsCreation(location, request).getLocation();
        errorLat = locationService.locationAndErrorsCreation(location, request).getErrorLat();
        errorLon = locationService.locationAndErrorsCreation(location, request).getErrorLon();

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);

        if (bindingResult.hasErrors() || !violations.isEmpty() || errorLat != null || errorLon != null) {
            List<Hero> heroes = heroDao.getAllHeroes();
            model.addAttribute("errors", violations);
            model.addAttribute("errorLat", errorLat);
            model.addAttribute("errorLon", errorLon);
            model.addAttribute("locationsByHero", locationsByHero);
            model.addAttribute("heroes", heroes);
            return "location";
        } else {
            locationDao.addLocation(location);
            return "redirect:/location";
        }

    }

    @GetMapping("locationDetail")
    public String locationDetail(Integer id, Model model) {
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);
        return "locationDetail";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id) {
        locationDao.deleteLocationById(id);
        return "redirect:/location";
    }

    @GetMapping("editLocation")
    public String editLocation(Integer id, Model model) {
        Location location = locationDao.getLocationById(id);

        model.addAttribute("location", location);
        model.addAttribute("latitude", location.getLatitude());
        model.addAttribute("longitude", location.getLongitude());

        if (!violations.isEmpty() || errorLat != null || errorLon != null) {
            violations.clear();
            errorLat = null;
            errorLon = null;
        }
        model.addAttribute("errors", violations);
        model.addAttribute("errorLat", errorLat);
        model.addAttribute("errorLon", errorLon);

        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(@ModelAttribute("location") @Valid Location location,
            BindingResult bindingResult, Model model, HttpServletRequest request) {

        int id = Integer.parseInt(request.getParameter("id"));
        location = locationDao.getLocationById(id);

        location = locationService.locationAndErrorsCreation(location, request).getLocation();
        errorLat = locationService.locationAndErrorsCreation(location, request).getErrorLat();
        errorLon = locationService.locationAndErrorsCreation(location, request).getErrorLon();

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);

        if (bindingResult.hasErrors() || !violations.isEmpty() || errorLat != null || errorLon != null) {
            model.addAttribute("errors", violations);
            model.addAttribute("errorLat", errorLat);
            model.addAttribute("errorLon", errorLon);
            model.addAttribute("location", location);
            if (errorLat != null) {

                if (errorLat.equals("Latitude cannot be empty")) {
                    model.addAttribute("latitude", "");
                } else if (errorLat.equals("Invalid Latitude format")) {
                    String inputLat = request.getParameter("latitude");
                    model.addAttribute("latitude", inputLat);
                }

            } else {
                model.addAttribute("latitude", location.getLatitude());
            }

            if (errorLon != null) {

                if (errorLon.equals("Longitude cannot be empty")) {
                    model.addAttribute("longitude", "");
                } else if (errorLon.equals("Invalid Longitude format")) {
                    String inputLon = request.getParameter("longitude");
                    model.addAttribute("longitude", inputLon);
                }

            } else {
                model.addAttribute("longitude", location.getLongitude());
            }

            return "editLocation";
        } else {

            locationDao.updateLocation(location);
            return "redirect:/location";
        }
    }

}
