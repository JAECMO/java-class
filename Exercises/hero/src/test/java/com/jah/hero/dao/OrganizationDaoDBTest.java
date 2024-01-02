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
public class OrganizationDaoDBTest {
    
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
    
    public OrganizationDaoDBTest() {
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
//
//    /**
//     * Test of getOrganization method, of class OrganizationDaoDB.
//     */
    @Test
    public void testAddAndGetOrganizationById() {
        
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
        
        List<Hero> heroesq = new ArrayList<>();
        heroesq.add(hero);
        heroesq.add(hero2);
        
        Organization organization = new Organization();
        organization.setAddressInfo("125 Hull Street");
        organization.setContactInfo("Obama");
        organization.setDescription("Biggest org");
        organization.setIsHero(true);
        organization.setName("Alpha");
        organization.setMembers(heroesq);
        organizationDao.addOrganization(organization);
        
        Organization fromDao = organizationDao.getOrganization(organization.getOrganizationId());
        assertEquals(organization,fromDao);
    }
//
//
//    /**
//     * Test of getAllOrganizations method, of class OrganizationDaoDB.
//     */
    @Test
    public void testGetAllOrganizations() {
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
        
        List<Hero> heroes1 = new ArrayList<>();
        heroes1.add(hero);
        heroes1.add(hero2);
        
        List<Hero> heroes2 = new ArrayList<>();
        heroes2.add(hero);
        
        Organization organization1 = new Organization();
        organization1.setAddressInfo("125 Hull Street");
        organization1.setContactInfo("Obama");
        organization1.setDescription("Biggest org");
        organization1.setIsHero(true);
        organization1.setName("Alpha");
        organization1.setMembers(heroes1);
        organizationDao.addOrganization(organization1);
        
        
        Organization organization2 = new Organization();
        organization2.setAddressInfo("2000 Joy Street");
        organization2.setContactInfo("Bush");
        organization2.setDescription("Medium org");
        organization2.setIsHero(true);
        organization2.setName("Beta");
        organization2.setMembers(heroes2);
        organizationDao.addOrganization(organization2);
        
        List<Organization> organizations = organizationDao.getAllOrganizations();

        assertEquals(2, organizations.size());
        assertEquals(heroes1,organizations.get(0).getMembers());
        assertTrue(organizations.contains(organization1));
        assertTrue(organizations.contains(organization2));
        
    }
//
//    /**
//     * Test of updateOrganization method, of class OrganizationDaoDB.
//     */
    @Test
    public void testUpdateOrganization() {
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
        
        List<Hero> heroes1 = new ArrayList<>();
        heroes1.add(hero);
        heroes1.add(hero2);
        
        List<Hero> heroes2 = new ArrayList<>();
        heroes2.add(hero);
        
        Organization organization1 = new Organization();
        organization1.setAddressInfo("125 Hull Street");
        organization1.setContactInfo("Obama");
        organization1.setDescription("Biggest org");
        organization1.setIsHero(true);
        organization1.setName("Alpha");
        organization1.setMembers(heroes1);
        organizationDao.addOrganization(organization1);
        
        Organization fromDao = organizationDao.getOrganization(organization1.getOrganizationId());
        assertEquals(organization1, fromDao);
        
        organization1.setName("New Alpha"); 
        organization1.setMembers(heroes2);
        organizationDao.updateOrganization(organization1);

        assertNotEquals(organization1, fromDao);
        
         fromDao = organizationDao.getOrganization(organization1.getOrganizationId());

        assertEquals(organization1, fromDao);
        
    }
//
//    /**
//     * Test of deleteOrganization method, of class OrganizationDaoDB.
//     */
    @Test
    public void testDeleteOrganization() {
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
        
        List<Hero> heroes1 = new ArrayList<>();
        heroes1.add(hero);
        heroes1.add(hero2);
        
        Organization organization1 = new Organization();
        organization1.setAddressInfo("125 Hull Street");
        organization1.setContactInfo("Obama");
        organization1.setDescription("Biggest org");
        organization1.setIsHero(true);
        organization1.setName("Alpha");
        organization1.setMembers(heroes1);
        organizationDao.addOrganization(organization1);
        
        Organization fromDao = organizationDao.getOrganization(organization1.getOrganizationId());
        assertEquals(organization1, fromDao);

        organizationDao.deleteOrganization(organization1.getOrganizationId());

        fromDao = organizationDao.getOrganization(organization1.getOrganizationId());
        assertNull(fromDao);
        
    }
//
//    /**
//     * Test of getAllOrganizationsForHero method, of class OrganizationDaoDB.
//     */
    @Test
    public void testGetAllOrganizationsForHero() {
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
        
        List<Hero> heroes1 = new ArrayList<>();
        heroes1.add(hero);
        heroes1.add(hero2);
        
        List<Hero> heroes2 = new ArrayList<>();
        heroes2.add(hero);
        
        List<Hero> heroes3 = new ArrayList<>();
        heroes2.add(hero2);
        
        Organization organization1 = new Organization();
        organization1.setAddressInfo("125 Hull Street");
        organization1.setContactInfo("Obama");
        organization1.setDescription("Biggest org");
        organization1.setIsHero(true);
        organization1.setName("Alpha");
        organization1.setMembers(heroes1);
        organizationDao.addOrganization(organization1);
        
        
        Organization organization2 = new Organization();
        organization2.setAddressInfo("2000 Joy Street");
        organization2.setContactInfo("Bush");
        organization2.setDescription("Medium org");
        organization2.setIsHero(true);
        organization2.setName("Beta");
        organization2.setMembers(heroes2);
        organizationDao.addOrganization(organization2);
        
        Organization organization3 = new Organization();
        organization3.setAddressInfo("One Street");
        organization3.setContactInfo("Xi");
        organization3.setDescription("Small org");
        organization3.setIsHero(false);
        organization3.setName("Gamma");
        organization3.setMembers(heroes3);
        organizationDao.addOrganization(organization3);
        
        List<Organization> organizations = organizationDao.getAllOrganizationsForHero(hero);
        assertEquals(2, organizations.size());
        assertTrue(organizations.contains(organization1));
        assertTrue(organizations.contains(organization2));
        assertFalse(organizations.contains(organization3));
    }
    

    
}
