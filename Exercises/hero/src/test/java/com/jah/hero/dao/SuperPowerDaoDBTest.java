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
public class SuperPowerDaoDBTest {
    
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
    
    public SuperPowerDaoDBTest() {
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
     * Test of getSuperPowerById method, of class SuperPowerDaoDB.
     */
    @Test
    public void testAddAndGetSuperPowerById() {
        SuperPower superPower = new SuperPower();
        superPower.setName("Flying");
        superPowerDao.addSuperPower(superPower);
        
        SuperPower fromDao = superPowerDao.getSuperPowerById(superPower.getSuperPowerId());
        assertEquals(superPower, fromDao);
    }

    /**
     * Test of getAllSuperPower method, of class SuperPowerDaoDB.
     */
    @Test
    public void testGetAllSuperPower() {
        SuperPower superPower = new SuperPower();
        superPower.setName("Flying");
        superPowerDao.addSuperPower(superPower);

        SuperPower superPower2 = new SuperPower();
        superPower2.setName("Laser");
        superPowerDao.addSuperPower(superPower2);
        
        List<SuperPower> superPowers = superPowerDao.getAllSuperPower();

        assertEquals(2, superPowers.size());
        assertTrue(superPowers.contains(superPower));
        assertTrue(superPowers.contains(superPower2));
    }

    /**
     * Test of updateSuperPower method, of class SuperPowerDaoDB.
     */
    @Test
    public void testUpdateSuperPower() {
        SuperPower superPower = new SuperPower();
        superPower.setName("Flying");
        superPowerDao.addSuperPower(superPower);
        
        SuperPower fromDao = superPowerDao.getSuperPowerById(superPower.getSuperPowerId());
        assertEquals(superPower, fromDao);

        superPower.setName("Laser");
        superPowerDao.updateSuperPower(superPower);

        assertNotEquals(superPower, fromDao);

        fromDao = superPowerDao.getSuperPowerById(superPower.getSuperPowerId());

        assertEquals(superPower, fromDao);
    }

    /**
     * Test of deletesuperPower method, of class SuperPowerDaoDB.
     */
    @Test
    public void testDeletesuperPower() {
        SuperPower superPower = new SuperPower();
        superPower.setName("Flying");
        superPowerDao.addSuperPower(superPower);

        Hero hero = new Hero();
        hero.setName("SuperMan");
        hero.setDescription("He can fly");
        hero.setIsHero(true);
        hero.setSuperPower(superPower);
        heroDao.addHero(hero);
        
        SuperPower fromDao = superPowerDao.getSuperPowerById(superPower.getSuperPowerId());
        assertEquals(superPower, fromDao);

        superPowerDao.deletesuperPower(superPower.getSuperPowerId());

        fromDao = superPowerDao.getSuperPowerById(superPower.getSuperPowerId());
        assertNull(fromDao);
    }
    
}
