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
import java.util.ArrayList;
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
public class HeroDaoDBTest {

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

    public HeroDaoDBTest() {
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
     * Test of getHeroById method, of class HeroDaoDB.
     */
    @Test
    public void testAddAndGetHero() {
        SuperPower superPower = new SuperPower();
        superPower.setName("Flying");
        superPowerDao.addSuperPower(superPower);

        Hero hero = new Hero();
        hero.setName("SuperMan");
        hero.setDescription("He can fly");
        hero.setIsHero(true);
        hero.setSuperPower(superPower);
        heroDao.addHero(hero);

        Hero fromDao = heroDao.getHeroById(hero.getHeroId());
        assertEquals(hero, fromDao);

    }

//    /**
//     * Test of getAllHeroes method, of class HeroDaoDB.
//     */
    @Test
    public void testGetAllHeroes() {
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

        List<Hero> heroes = heroDao.getAllHeroes();

        assertEquals(2, heroes.size());
        assertTrue(heroes.contains(hero));
        assertTrue(heroes.contains(hero2));

    }

    /**
     * Test of updateHero method, of class HeroDaoDB.
     */
    @Test
    public void testUpdateHero() {
        SuperPower superPower = new SuperPower();
        superPower.setName("Flying");
        superPowerDao.addSuperPower(superPower);

        Hero hero = new Hero();
        hero.setName("SuperMan");
        hero.setDescription("He can fly");
        hero.setIsHero(true);
        hero.setSuperPower(superPower);
        heroDao.addHero(hero);

        Hero fromDao = heroDao.getHeroById(hero.getHeroId());
        assertEquals(hero, fromDao);

        hero.setName("JOJO");
        heroDao.updateHero(hero);

        assertNotEquals(hero, fromDao);

        fromDao = heroDao.getHeroById(hero.getHeroId());

        assertEquals(hero, fromDao);
    }

    /**
     * Test of deleteHero method, of class HeroDaoDB.
     */
    @Test
    public void testDeleteHero() {
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

        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);

        Organization organization = new Organization();
        organization.setName("Alpha");
        organization.setAddressInfo("66 Titus street");
        organization.setContactInfo("Joe Rollo");
        organization.setDescription("Biggest org");
        organization.setIsHero(true);
        organization.setMembers(heroes);
        organizationDao.addOrganization(organization);

        Date date = Date.valueOf("2023-06-06");

        Sighting sighting = new Sighting();
        sighting.setDate(date);
        sighting.setHero(hero);
        sighting.setLocation(location);
        sightingDao.addSighting(sighting);

        Hero fromDao = heroDao.getHeroById(hero.getHeroId());
        assertEquals(hero, fromDao);

        heroDao.deleteHero(hero.getHeroId());

        fromDao = heroDao.getHeroById(hero.getHeroId());
        assertNull(fromDao);
    }

    /**
     * Test of getAllHeroesByLocation method, of class HeroDaoDB.
     */
    @Test
    public void testGetAllHeroesByLocation() {

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

        List<Hero> heroes = heroDao.getAllHeroesByLocation(location);
        assertEquals(2, heroes.size());
        assertTrue(heroes.contains(hero));
        assertTrue(heroes.contains(hero2));
        assertFalse(heroes.contains(hero3));

    }

    /**
     * Test of getAllHeroesForOrganization method, of class HeroDaoDB.
     */
    @Test
    public void testGetAllHeroesForOrganization() {

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

        List<Hero> heroes1 = new ArrayList<>();
        heroes1.add(hero);
        heroes1.add(hero2);

        List<Hero> heroes2 = new ArrayList<>();
        heroes2.add(hero3);

        Organization organization = new Organization();
        organization.setName("Alpha");
        organization.setAddressInfo("66 Titus street");
        organization.setContactInfo("Joe Rollo");
        organization.setDescription("Biggest org");
        organization.setIsHero(true);
        organization.setMembers(heroes1);
        organizationDao.addOrganization(organization);

        Organization organization2 = new Organization();
        organization2.setName("Bate");
        organization2.setAddressInfo("66 Superior stret");
        organization2.setContactInfo("Frank Wick");
        organization2.setDescription("Medium org");
        organization2.setIsHero(true);
        organization2.setMembers(heroes2);
        organizationDao.addOrganization(organization2);

        List<Hero> heroes = heroDao.getAllHeroesForOrganization(organization);
        assertEquals(2, heroes.size());
        assertTrue(heroes.contains(hero));
        assertTrue(heroes.contains(hero2));
        assertFalse(heroes.contains(hero3));

    }

}
