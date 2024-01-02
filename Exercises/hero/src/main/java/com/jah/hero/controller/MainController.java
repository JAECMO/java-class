/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.controller;

import com.jah.hero.dao.HeroDao;
import com.jah.hero.dao.LocationDao;
import com.jah.hero.dao.SightingDao;
import com.jah.hero.dto.Sighting;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author drjal
 */
@Controller
public class MainController {

    @Autowired
    HeroDao heroDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    LocationDao locationDao;

    @GetMapping("index")
    public String displayLastSightings(Model model) {
        List<Sighting> sightingsAll = sightingDao.getAllSightings();
        // Sort the list in descending order based on the date
        List<Sighting> sightings = sightingsAll.stream()
                .sorted((s1, s2) -> s2.getDate().compareTo(s1.getDate())) // Sort in descending order
                .limit(10) // Limit to the first 10 elements (last 10 sightings)
                .collect(Collectors.toList()); // Collect the results into a list
        model.addAttribute("sightings", sightings);
        return "index";

    }
}
