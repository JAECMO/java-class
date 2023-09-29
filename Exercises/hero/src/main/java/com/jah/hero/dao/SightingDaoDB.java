/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.dao;


import com.jah.hero.dto.Hero;
import com.jah.hero.dto.Location;
import com.jah.hero.dto.Sighting;
import java.sql.Date;
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
public class SightingDaoDB implements SightingDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting getSighting(int id) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM Sighting WHERE id = ?";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), id);
            sighting.setHero(getHeroForSighting(id)); 
            sighting.setLocation(getLocationForSighting(id));
            return sighting;
        } catch(DataAccessException ex) {
            return null;
        }
    }
    
    private Hero getHeroForSighting(int id) {
        final String SELECT_HERO_FOR_SIGHTING = "SELECT h.* FROM Hero h "
                + "JOIN Sighting s ON s.heroId = h.heroId WHERE s.sightingId = ?";
        return jdbc.queryForObject(SELECT_HERO_FOR_SIGHTING, new HeroDaoDB.HeroMapper(), id);
    }
    
    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM Location l "
                + "JOIN Sighting s ON s.locationId = l.locationId WHERE s.sightingId = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationDaoDB.LocationMapper(), id);
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO Sighting(date, heroId, locationId) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                sighting.getDate(),
                sighting.getHero().getHeroId(),
                sighting.getLocation().getLocationId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setSightingId(newId);
        return sighting;
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String GET_ALL_SIGHTINGS = "SELECT * FROM Sighting";
        return jdbc.query(GET_ALL_SIGHTINGS, new SightingDaoDB.SightingMapper());
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE Sighting SET date = ?, heroId = ?, "
                + "locationId = ? WHERE sightingId = ?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getDate(),
                sighting.getHero().getHeroId(),
                sighting.getLocation().getLocationId(),
                sighting.getSightingId());
    }

    @Override
    @Transactional
    public void deleteSighting(int id) {
        final String DELETE_SIGHTING = "DELETE FROM Sighting WHERE sightingId = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }

    @Override
    public List<Sighting> getAllSightingByDate(Date date) {
        final String GET_SIGHTING_BY_DATE = "SELECT * FROM Sighting"
                + "WHERE date = ?";
        return jdbc.query(GET_SIGHTING_BY_DATE, new SightingDaoDB.SightingMapper(), date);
    }
    
    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setSightingId(rs.getInt("sightingId"));
            sighting.setDate(rs.getDate("date"));
            return sighting;
        }
    }
}
