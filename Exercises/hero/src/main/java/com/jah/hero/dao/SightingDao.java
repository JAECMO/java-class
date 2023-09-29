/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.dao;

import com.jah.hero.dto.Sighting;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author drjal
 */
public interface SightingDao {

    Sighting getSighting(int id);

    Sighting addSighting(Sighting sighting);

    List<Sighting> getAllSightings();

    void updateSighting(Sighting sighting);

    void deleteSighting(int id);

    
    List<Sighting> getAllSightingByDate(Date date);
}
