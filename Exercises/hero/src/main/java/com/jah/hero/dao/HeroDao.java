/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.dao;

import com.jah.hero.dto.Hero;
import com.jah.hero.dto.Location;
import com.jah.hero.dto.Organization;
import java.util.List;

/**
 *
 * @author drjal
 */
public interface HeroDao {

    Hero getHeroById(int id);

    List<Hero> getAllHeroes();

    Hero addHero(Hero hero);

    void updateHero(Hero hero);

    void deleteHero(int id);

    List<Hero> getAllHeroesByLocation(Location location);

    List<Hero> getAllHeroesForOrganization(Organization organization);
    
    List<Hero> getAllHeroesForAllLocations();
    
    List<Hero> getNotSeenHeroes();

}
