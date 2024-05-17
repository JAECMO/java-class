/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.service;

import com.jah.hero.dao.HeroDao;
import com.jah.hero.dao.LocationDao;
import com.jah.hero.dto.Hero;
import com.jah.hero.dto.Location;
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
public class LocationService {

    @Autowired
    LocationDao locationDao;

    @Autowired
    HeroDao heroDao;

    public List<Location> menuSelectionAndLocationList(Model model, HttpServletRequest request) {
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
        return locationsByHero;
    }

    public LocationStrings locationAndErrorsCreation(Location location, HttpServletRequest request) {
        String name = request.getParameter("name");
        String addressInfo = request.getParameter("addressInfo");
        String description = request.getParameter("description");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        String errorLat = null;
        String errorLon = null;
        double latDouble = 0;
        double lonDouble = 0;

        if (latitude.isEmpty()) {
            errorLat = "Latitude cannot be empty";
        } else {
            try {
                latDouble = Double.parseDouble(latitude);
            } catch (NumberFormatException e) {
                errorLat = "Invalid Latitude format";
            }
        }

        if (longitude.isEmpty()) {
            errorLon = "Longitude cannot be empty";
        } else {
            try {
                lonDouble = Double.parseDouble(longitude);
            } catch (NumberFormatException e) {
                errorLon = "Invalid Longitude format";
            }
        }

        location.setName(name);
        location.setAddressInfo(addressInfo);
        location.setDescription(description);
        location.setLatitude(latDouble);
        location.setLongitude(lonDouble);

        LocationStrings locationStrings = new LocationStrings(location, errorLat, errorLon);
        return locationStrings;

    }

    public class LocationStrings {

        private Location location;
        private String errorLat;
        private String errorLon;

        public LocationStrings(Location location, String errorLat, String errorLon) {
            this.location = location;
            this.errorLat = errorLat;
            this.errorLon = errorLon;
        }

        public Location getLocation() {
            return location;
        }

        public String getErrorLat() {
            return errorLat;
        }

        public String getErrorLon() {
            return errorLon;
        }

    }

}
