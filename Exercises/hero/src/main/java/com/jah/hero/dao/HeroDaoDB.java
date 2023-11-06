/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.dao;

import com.jah.hero.dao.SuperPowerDaoDB.SuperPowerMapper;
import com.jah.hero.dto.Hero;
import com.jah.hero.dto.Location;
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
public class HeroDaoDB implements HeroDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Hero getHeroById(int id) {
        try {
            final String GET_HERO_BY_ID = "SELECT * FROM Hero WHERE heroId = ?";
            Hero hero = jdbc.queryForObject(GET_HERO_BY_ID, new HeroMapper(), id);
            hero.setSuperPower(getSuperPowerForHero(id));
            return hero;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private SuperPower getSuperPowerForHero(int id) {
        final String SELECT_SUPER_POWER_FOR_HERO = "SELECT s.* FROM SuperPower s "
                + "JOIN Hero h ON h.superPowerId = s.superPowerId WHERE h.heroId = ?";
        return jdbc.queryForObject(SELECT_SUPER_POWER_FOR_HERO, new SuperPowerMapper(), id);
    }

    @Override
    public List<Hero> getAllHeroes() {
        final String GET_ALL_HEROES = "SELECT * FROM Hero";
        List<Hero> heroes = jdbc.query(GET_ALL_HEROES, new HeroMapper());
        associateSuperPower(heroes);
        return heroes;

    }

    public void associateSuperPower(List<Hero> heroes) {
        for (Hero hero : heroes) {
            hero.setSuperPower(getSuperPowerForHero(hero.getHeroId()));
        }
    }

    @Override
    @Transactional
    public Hero addHero(Hero hero) {
        final String INSERT_HERO = "INSERT INTO Hero(name, description, isHero, superPowerId) "
                + "VALUES(?,?,?,?)";
        jdbc.update(INSERT_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.isIsHero(),
                hero.getSuperPower().getSuperPowerId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setHeroId(newId);
        return hero;
    }

    @Override
    public void updateHero(Hero hero) {
        final String UPDATE_HERO = "UPDATE Hero SET name = ?, description = ?, "
                + "isHero = ?, superPowerId = ? WHERE heroId = ?";
        jdbc.update(UPDATE_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.isIsHero(),
                hero.getSuperPower().getSuperPowerId(),
                hero.getHeroId());
    }

    @Override
    @Transactional
    public void deleteHero(int id) {
        final String DELETE_HERO_ORGANIZATION = "DELETE FROM HeroOrganization WHERE heroId = ?";
        jdbc.update(DELETE_HERO_ORGANIZATION, id);

        final String DELETE_SIGHTING = "DELETE FROM Sighting WHERE heroId = ?";
        jdbc.update(DELETE_SIGHTING, id);

        final String DELETE_HERO = "DELETE FROM Hero WHERE heroId = ?";
        jdbc.update(DELETE_HERO, id);
    }

    @Override
    public List<Hero> getAllHeroesByLocation(Location location) {
        int locationId = location.getLocationId();
        final String GET_HEROES_FOR_LOCATION = "SELECT h.* FROM Hero h "
                + "JOIN Sighting s ON s.heroId = h.heroId WHERE s.locationId= ?";
        List<Hero> heroes = jdbc.query(GET_HEROES_FOR_LOCATION, new HeroDaoDB.HeroMapper(), locationId);
        associateSuperPower(heroes);
        return heroes;
    }

    @Override
    public List<Hero> getAllHeroesForOrganization(Organization organization) {
        int organizationId = organization.getOrganizationId();
        final String GET_HEROES_FOR_ORGANIZATION = "SELECT h.* FROM Hero h "
                + "JOIN HeroOrganization ho ON ho.heroId = h.heroId WHERE ho.organizationId = ?";
        List<Hero> heroes = jdbc.query(GET_HEROES_FOR_ORGANIZATION, new HeroDaoDB.HeroMapper(), organizationId);
        associateSuperPower(heroes);
        return heroes;

    }

    public static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {
            Hero hero = new Hero();
            hero.setHeroId(rs.getInt("heroId"));
            hero.setName(rs.getString("name"));
            hero.setDescription(rs.getString("description"));
            hero.setIsHero(rs.getBoolean("isHero"));
            return hero;
        }
    }

}
