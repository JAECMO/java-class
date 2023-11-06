/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.dao;

import com.jah.hero.dao.HeroDaoDB.HeroMapper;
import com.jah.hero.dto.Hero;
import com.jah.hero.dto.Organization;
import com.jah.hero.dto.SuperPower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author drjal
 */

@Repository
public class OrganizationDaoDB implements OrganizationDao {
    
    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    HeroDaoDB heroDaoDB;

    @Override
    public Organization getOrganization(int id) {
        try {
            final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM Organization WHERE organizationId = ?";
            Organization organization = jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationDaoDB.OrganizationMapper(), id);
            List<Hero> heroes = getHeroesForOrganization(id);
           heroDaoDB.associateSuperPower(heroes); 
            organization.setMembers(heroes);
            
            return organization;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private List<Hero> getHeroesForOrganization(int id) {
        final String SELECT_HEROES_FOR_ORGANIZATION = "SELECT h.* FROM Hero h "
                + "JOIN HeroOrganization ho ON ho.heroId = h.heroId WHERE ho.organizationId = ?";
        return jdbc.query(SELECT_HEROES_FOR_ORGANIZATION, new HeroMapper(), id);
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
        final String INSERT_ORGANIZATION = "INSERT INTO Organization(name, description, addressInfo, contactInfo, isHero) "
                + "VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getAddressInfo(),
                organization.getContactInfo(),
                organization.isIsHero());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setOrganizationId(newId);
        insertHeroOrganization(organization);
        return organization;
    }

    private void insertHeroOrganization(Organization organization) {
        final String INSERT_HERO_ORGANIZATION = "INSERT INTO "
                + "HeroOrganization(heroId, organizationId) VALUES(?,?)";
        for (Hero hero : organization.getMembers()) {
            jdbc.update(INSERT_HERO_ORGANIZATION,
                    hero.getHeroId(),
                    organization.getOrganizationId());
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String GET_ALL_ORGANIZATIONS = "SELECT * FROM Organization";
        
        List<Organization> organizations = jdbc.query(GET_ALL_ORGANIZATIONS, new OrganizationDaoDB.OrganizationMapper());
        
        associateSuperPowerToHeroes(organizations);
        
        return organizations;
    }

    @Override
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORGANIZATION = "UPDATE Organization SET name = ?, description = ?, "
                + "addressInfo = ?, contactInfo = ?, isHero = ? WHERE organizationId = ?";
        jdbc.update(UPDATE_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getAddressInfo(),
                organization.getContactInfo(),
                organization.isIsHero(),
                organization.getOrganizationId());

        final String DELETE_HERO_ORGANIZATION = "DELETE FROM HeroOrganization WHERE organizationId = ?";
        jdbc.update(DELETE_HERO_ORGANIZATION, organization.getOrganizationId());
        insertHeroOrganization(organization);
    }

    @Override
    @Transactional
    public void deleteOrganization(int id) {
        final String DELETE_HERO_ORGANIZATION = "DELETE FROM HeroOrganization WHERE organizationId = ?";
        jdbc.update(DELETE_HERO_ORGANIZATION, id);

        final String DELETE_ORGANIZATION = "DELETE FROM Organization WHERE organizationId = ?";
        jdbc.update(DELETE_ORGANIZATION, id);
    }

    @Override
    public List<Organization> getAllOrganizationsForHero(Hero hero) {
        int heroId = hero.getHeroId();
        final String GET_ORGANIZATION_FOR_HERO = "SELECT o.* FROM Organization o "
                + "JOIN HeroOrganization ho ON ho.organizationId = o.organizationId WHERE ho.heroId = ?";
        List<Organization> organizations = jdbc.query(GET_ORGANIZATION_FOR_HERO, new OrganizationDaoDB.OrganizationMapper(), heroId);

        associateSuperPowerToHeroes(organizations);
        return organizations;
    }
    
    
     private void associateSuperPowerToHeroes(List<Organization> organizations){
        for(Organization organization : organizations){
            List<Hero> heroes = getHeroesForOrganization(organization.getOrganizationId());
            heroDaoDB.associateSuperPower(heroes);
            organization.setMembers(heroes);
        }
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setOrganizationId(rs.getInt("organizationId"));
            organization.setName(rs.getString("name"));
            organization.setDescription(rs.getString("description"));
            organization.setAddressInfo(rs.getString("addressInfo"));
            organization.setContactInfo(rs.getString("contactInfo"));
            organization.setIsHero(rs.getBoolean("isHero"));

            return organization;
        }
    }

}
