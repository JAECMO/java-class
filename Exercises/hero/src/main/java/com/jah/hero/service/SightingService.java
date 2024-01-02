/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.service;

import com.jah.hero.dao.HeroDao;
import com.jah.hero.dao.LocationDao;
import com.jah.hero.dao.SightingDao;
import com.jah.hero.dto.Sighting;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 *
 * @author drjal
 */
@Service
public class SightingService {

    @Autowired
    SightingDao sightingDao;

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    public List<Sighting> menuSelectionAndSightingList(Model model, HttpServletRequest request) {

        List<Sighting> sightings = sightingDao.getAllSightings();

        // Extract unique dates from the list of sightings
        Set<java.util.Date> uniqueDates = sightings.stream()
                .map(Sighting::getDate)
                .collect(Collectors.toSet());

        String dateString = request.getParameter("date");
        String allDates = "All dates";
        model.addAttribute("AllDates", allDates);
        model.addAttribute("uniqueDates", uniqueDates);

        List<Sighting> sightingsByDate;

        if (dateString != null) {
            try {
                Date date = Date.valueOf(dateString);
                sightingsByDate = sightingDao.getAllSightingByDate(date);
                model.addAttribute("selectedSighintId", date);
            } catch (IllegalArgumentException e) {
                sightingsByDate = sightingDao.getAllSightings();
                model.addAttribute("selectedSighintId", uniqueDates);
            }

        } else {
            sightingsByDate = sightingDao.getAllSightings();
        }
        return sightingsByDate;

    }

    public Sighting sightingCreation(Sighting sighting, HttpServletRequest request) {
        String dateString = request.getParameter("date");
        Date date = null;

        if (dateString != null && !dateString.isEmpty()) {
            // Parse the date string into a java.util.Date object
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = dateFormat.parse(dateString);

                // Convert java.util.Date to java.sql.Date
                date = new Date(utilDate.getTime());
            } catch (ParseException | IllegalArgumentException e) {

            }
        }
        String heroId = request.getParameter("heroId");
        String locationId = request.getParameter("locationId");

        if (heroId != null) {
            try {
                sighting.setHero(heroDao.getHeroById(Integer.parseInt(heroId)));
            } catch (NumberFormatException e) {
                sighting.setHero(null);
            }
        } else {
            sighting.setHero(null);
        }

        if (locationId != null) {
            try {
                sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
            } catch (NumberFormatException e) {
                sighting.setLocation(null);
            }
        } else {
            sighting.setLocation(null);
        }

        sighting.setDate(date);

        return sighting;

    }

}
