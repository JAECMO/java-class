/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.dao;

import com.jah.hero.dto.Hero;
import com.jah.hero.dto.Location;
import com.jah.hero.dto.Organization;
import com.jah.hero.dto.Sighting;
import com.jah.hero.dto.SuperPower;
import java.sql.Date;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author drjal
 */
@SpringBootTest
public class SightingDaoDBTest {
    
    @Autowired
    HeroDao heroDao;

    @Autowired
    SuperPowerDao superPowerDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SightingDao sightingDao;
    
    public SightingDaoDBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        List<Hero> heroes = heroDao.getAllHeroes();
        for (Hero hero : heroes) {
            heroDao.deleteHero(hero.getHeroId());
        }

        List<SuperPower> superPowers = superPowerDao.getAllSuperPower();
        for (SuperPower superPower : superPowers) {
            superPowerDao.deletesuperPower(superPower.getSuperPowerId());
        }

        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getLocationId());
        }

        List<Organization> organizations = organizationDao.getAllOrganizations();
        for (Organization organization : organizations) {
            organizationDao.deleteOrganization(organization.getOrganizationId());
        }

        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSighting(sighting.getSightingId());
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getSighting method, of class SightingDaoDB.
     */
    @Test
    public void testAddAndGetSightingById() {
        SuperPower superPower = new SuperPower();
        superPower.setName("Flying");
        superPowerDao.addSuperPower(superPower);

        Hero hero = new Hero();
        hero.setName("SuperMan");
        hero.setDescription("He can fly");
        hero.setIsHero(true);
        hero.setSuperPower(superPower);
        heroDao.addHero(hero);

        Location location = new Location();
        location.setName("Montreal");
        location.setAddressInfo("222 Nice street");
        location.setDescription("Nice street");
        location.setLatitude(22.66664444);
        location.setLongitude(122.33339999);
        locationDao.addLocation(location);

        Date date = Date.valueOf("2023-06-06");

        Sighting sighting = new Sighting();
        sighting.setDate(date);
        sighting.setHero(hero);
        sighting.setLocation(location);
        sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSighting(sighting.getSightingId());
        assertEquals(sighting,fromDao);
        
    }
    
    /**
     * Test of getAllSightings method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightings() {
         SuperPower superPower = new SuperPower();
        superPower.setName("Flying");
        superPowerDao.addSuperPower(superPower);

        SuperPower superPower2 = new SuperPower();
        superPower2.setName("Laser");
        superPowerDao.addSuperPower(superPower2);

        Hero hero = new Hero();
        hero.setName("SuperMan");
        hero.setDescription("He can fly");
        hero.setIsHero(true);
        hero.setSuperPower(superPower);
        heroDao.addHero(hero);

        Hero hero2 = new Hero();
        hero2.setName("BatMan");
        hero2.setDescription("He can fight");
        hero2.setIsHero(true);
        hero2.setSuperPower(superPower2);
        heroDao.addHero(hero2);

        Hero hero3 = new Hero();
        hero3.setName("Hulk");
        hero3.setDescription("He is Big");
        hero3.setIsHero(true);
        hero3.setSuperPower(superPower2);
        heroDao.addHero(hero3);

        Location location = new Location();
        location.setName("Montreal");
        location.setAddressInfo("222 Nice street");
        location.setDescription("Nice street");
        location.setLatitude(22.66664444);
        location.setLongitude(122.33339999);
        locationDao.addLocation(location);

        Location location2 = new Location();
        location2.setName("Ottawa");
        location2.setAddressInfo("555 hull street");
        location2.setDescription("Presidential street");
        location2.setLatitude(99.1114444);
        location2.setLongitude(100.33339999);
        locationDao.addLocation(location2);

        Date date = Date.valueOf("2023-06-06");

        Sighting sighting = new Sighting();
        sighting.setDate(date);
        sighting.setHero(hero);
        sighting.setLocation(location);
        sightingDao.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setDate(date);
        sighting2.setHero(hero2);
        sighting2.setLocation(location);
        sightingDao.addSighting(sighting2);

        Sighting sighting3 = new Sighting();
        sighting3.setDate(date);
        sighting3.setHero(hero3);
        sighting3.setLocation(location2);
        sightingDao.addSighting(sighting3);

        List<Sighting> sightings = sightingDao.getAllSightings();
        assertEquals(3, sightings.size());
        assertTrue(sightings.contains(sighting2));
        assertTrue(sightings.contains(sighting3));
        assertTrue(sightings.contains(sighting));
    }

    /**
     * Test of updateSighting method, of class SightingDaoDB.
     */
    @Test
    public void testUpdateSighting() {
        SuperPower superPower = new SuperPower();
        superPower.setName("Flying");
        superPowerDao.addSuperPower(superPower);
        
        SuperPower superPower2 = new SuperPower();
        superPower2.setName("Laser");
        superPowerDao.addSuperPower(superPower2);

        Hero hero = new Hero();
        hero.setName("SuperMan");
        hero.setDescription("He can fly");
        hero.setIsHero(true);
        hero.setSuperPower(superPower);
        heroDao.addHero(hero);
        
        Hero hero2 = new Hero();
        hero2.setName("BatMan");
        hero2.setDescription("He can fight");
        hero2.setIsHero(true);
        hero2.setSuperPower(superPower2);
        heroDao.addHero(hero2);

        Location location = new Location();
        location.setName("Montreal");
        location.setAddressInfo("222 Nice street");
        location.setDescription("Nice street");
        location.setLatitude(22.66664444);
        location.setLongitude(122.33339999);
        locationDao.addLocation(location);

        Date date = Date.valueOf("2023-06-06");

        Sighting sighting = new Sighting();
        sighting.setDate(date);
        sighting.setHero(hero);
        sighting.setLocation(location);
        sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSighting(sighting.getSightingId());
        assertEquals(sighting,fromDao);
        
        sighting.setHero(hero2); 
        sightingDao.updateSighting(sighting);

        assertNotEquals(sighting, fromDao);
        
        fromDao = sightingDao.getSighting(sighting.getSightingId());

        assertEquals(sighting, fromDao);
     
    }

    /**
     * Test of deleteSighting method, of class SightingDaoDB.
     */
    @Test
    public void testDeleteSighting() {
        SuperPower superPower = new SuperPower();
        superPower.setName("Flying");
        superPowerDao.addSuperPower(superPower);

        Hero hero = new Hero();
        hero.setName("SuperMan");
        hero.setDescription("He can fly");
        hero.setIsHero(true);
        hero.setSuperPower(superPower);
        heroDao.addHero(hero);

        Location location = new Location();
        location.setName("Montreal");
        location.setAddressInfo("222 Nice street");
        location.setDescription("Nice street");
        location.setLatitude(22.66664444);
        location.setLongitude(122.33339999);
        locationDao.addLocation(location);

        Date date = Date.valueOf("2023-06-06");

        Sighting sighting = new Sighting();
        sighting.setDate(date);
        sighting.setHero(hero);
        sighting.setLocation(location);
        sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSighting(sighting.getSightingId());
        assertEquals(sighting,fromDao);
        
        sightingDao.deleteSighting(sighting.getSightingId());
        
        fromDao = sightingDao.getSighting(sighting.getSightingId());
        assertNull(fromDao);
    }

    /**
     * Test of getAllSightingByDate method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightingByDate() {
        SuperPower superPower = new SuperPower();
        superPower.setName("Flying");
        superPowerDao.addSuperPower(superPower);
        
        SuperPower superPower2 = new SuperPower();
        superPower2.setName("Laser");
        superPowerDao.addSuperPower(superPower2);

        Hero hero = new Hero();
        hero.setName("SuperMan");
        hero.setDescription("He can fly");
        hero.setIsHero(true);
        hero.setSuperPower(superPower);
        heroDao.addHero(hero);
        
        Hero hero2 = new Hero();
        hero2.setName("BatMan");
        hero2.setDescription("He can fight");
        hero2.setIsHero(true);
        hero2.setSuperPower(superPower2);
        heroDao.addHero(hero2);

        Location location = new Location();
        location.setName("Montreal");
        location.setAddressInfo("222 Nice street");
        location.setDescription("Nice street");
        location.setLatitude(22.66664444);
        location.setLongitude(122.33339999);
        locationDao.addLocation(location);

        Date date = Date.valueOf("2023-06-06");
        Date date2 = Date.valueOf("2023-07-07");
        
        Sighting sighting = new Sighting();
        sighting.setDate(date);
        sighting.setHero(hero);
        sighting.setLocation(location);
        sightingDao.addSighting(sighting);
        
        Sighting sighting2 = new Sighting();
        sighting2.setDate(date2);
        sighting2.setHero(hero);
        sighting2.setLocation(location);
        sightingDao.addSighting(sighting2);
        
        Sighting sighting3 = new Sighting();
        sighting3.setDate(date2);
        sighting3.setHero(hero);
        sighting3.setLocation(location);
        sightingDao.addSighting(sighting3);
        
        List<Sighting> sigthings = sightingDao.getAllSightingByDate(date2);
        assertEquals(2, sigthings.size());
        assertTrue(sigthings.contains(sighting2));
        assertTrue(sigthings.contains(sighting3));
        assertFalse(sigthings.contains(sighting));
       
        
        
    }
    
}
