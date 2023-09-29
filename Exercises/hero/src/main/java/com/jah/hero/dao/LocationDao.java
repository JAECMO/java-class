/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.dao;

import com.jah.hero.dto.Hero;
import com.jah.hero.dto.Location;
import java.util.List;

/**
 *
 * @author drjal
 */
public interface LocationDao {

    Location getLocationById(int id);

    List<Location> getAllLocations();

    Location addLocation(Location location);

    void updateLocation(Location location);

    void deleteLocationById(int id);

    List<Location> getLocationsForHero(Hero hero);

}
