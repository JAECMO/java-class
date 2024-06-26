/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.controller;

import com.jah.hero.dao.SightingDao;
import com.jah.hero.dto.Sighting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author drjal
 */
@Controller
public class MapController { //shows errors in tests only with the MapController, but app still run perfectly

    @Autowired
    SightingDao sightingDao;

    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;

    

    @GetMapping("/map")
    public String showMap(Integer id, Model model) {
        Sighting sighting = sightingDao.getSighting(id);
        model.addAttribute("sighting", sighting);
        model.addAttribute("apiKey", googleMapsApiKey);
        return "map";
    }

}
