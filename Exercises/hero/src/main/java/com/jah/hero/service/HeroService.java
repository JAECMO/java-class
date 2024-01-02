/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.service;

import com.jah.hero.dao.HeroDao;
import com.jah.hero.dao.LocationDao;
import com.jah.hero.dao.SuperPowerDao;
import com.jah.hero.dto.Hero;
import com.jah.hero.dto.Location;
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
public class HeroService {
     @Autowired
    HeroDao heroDao;

    @Autowired
    SuperPowerDao superPowerDao;

    @Autowired
    LocationDao locationDao;
    
    public List<Hero> menuSelectionAndHeroList(Model model, HttpServletRequest request){
        String locationId = request.getParameter("locationId");
        String allLocations = "All Locations";
        String allLocationsNotSeen = "All Locations & Not Seen (All Heroes)";
        String notSeen = "Not Seen";
        model.addAttribute("NotSeen", notSeen);
        model.addAttribute("AllLocations", allLocations);
        model.addAttribute("AllLocationsNotSeen", allLocationsNotSeen);
        List<Hero> heroesByLocation;
        if (locationId != null) {
            try {
                Integer locationIdInt = Integer.parseInt(locationId);
                Location location = locationDao.getLocationById(Integer.parseInt(locationId));
                heroesByLocation = heroDao.getAllHeroesByLocation(location);
                model.addAttribute("selectedLocationId", locationIdInt);

            } catch (NumberFormatException e) {
                if (locationId.equals(allLocations)) {
                    heroesByLocation = heroDao.getAllHeroesForAllLocations();
                    model.addAttribute("selectedLocationId", allLocations);
                } else if (locationId.equals(allLocationsNotSeen)) {
                    heroesByLocation = heroDao.getAllHeroes();
                    model.addAttribute("selectedLocationId", allLocationsNotSeen);
                } else {
                    heroesByLocation = heroDao.getNotSeenHeroes();
                    model.addAttribute("selectedLocationId", notSeen);
                }
            }

        } else {
            heroesByLocation = heroDao.getAllHeroes();

        }
        
        return heroesByLocation;
    }
    
    public Hero heroCreation(Hero hero, HttpServletRequest request) {

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String superPowerId = request.getParameter("superPowerId");
        String isHero = request.getParameter("isHero");
        Boolean isHeroBool;

        isHeroBool = isHero.equals("Yes");

        if (superPowerId != null) {
            hero.setSuperPower(superPowerDao.getSuperPowerById(Integer.parseInt(superPowerId)));
        }

        hero.setName(name);
        hero.setDescription(description);
        hero.setIsHero(isHeroBool);

        return hero;
    }
    
    public Integer getCurrentId (List<Hero> heroes){
    Integer maxId = null;

        for (Hero hero : heroes) {
            Integer heroId = hero.getHeroId(); // Assuming there's a method to get the ID, replace with the actual method
            if (maxId == null || (heroId != null && heroId > maxId)) {
                maxId = heroId;
            }
        }

        return maxId;
    }
    
    
    
    
    
    
}
