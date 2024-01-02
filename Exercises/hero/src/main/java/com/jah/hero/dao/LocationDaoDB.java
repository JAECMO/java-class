/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.dao;

import com.jah.hero.dto.Hero;
import com.jah.hero.dto.Location;
import com.jah.hero.dto.Sighting;
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
public class LocationDaoDB implements LocationDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id) {
        try {
            final String GET_LOCATION_BY_ID = "SELECT * FROM Location WHERE locationId = ?";
            Location location = jdbc.queryForObject(GET_LOCATION_BY_ID, new LocationDaoDB.LocationMapper(), id);
            return location;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String GET_ALL_LOCATIONS = "SELECT * FROM Location";
        return jdbc.query(GET_ALL_LOCATIONS, new LocationDaoDB.LocationMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO Location(name, description, addressInfo,latitude,longitude, locationId) "
                + "VALUES(?,?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddressInfo(),
                location.getLatitude(),
                location.getLongitude(),
                location.getLocationId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setLocationId(newId);
        return location;
    }
    
    private Sighting getSightingForLocation(int id) {
        final String SELECT_SIGHTING_FOR_LOCATION = "SELECT s.* FROM Sighting s "
                + "JOIN Location l ON s.locationId = l.locationId WHERE s.locationId = ?";
        return jdbc.queryForObject(SELECT_SIGHTING_FOR_LOCATION, new SightingDaoDB.SightingMapper(), id);
    }
    
    private void insertSighting(Location location) {
        final String INSERT_SIGHTING = "INSERT INTO "
                + "Sighting(sightingId, date, heroId, locationId) VALUES(?,?,?,?)";
        Sighting sighting = getSightingForLocation(location.getLocationId());
        
            jdbc.update(INSERT_SIGHTING,
                    sighting.getSightingId(),
                    sighting.getDate(), sighting.getHero().getHeroId(), sighting.getLocation().getLocationId()); 
    }

    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE Location SET name = ?, description = ?, "
                + "addressInfo = ?, latitude = ?, longitude = ? WHERE locationId = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddressInfo(),
                location.getLatitude(),
                location.getLongitude(),
                location.getLocationId());
        
//        final String DELETE_SIGHTING= "DELETE FROM Sighting WHERE locationId = ?";
//        jdbc.update(DELETE_SIGHTING, location.getLocationId());
//        insertSighting(location);
    }

    @Override
    @Transactional
    public void deleteLocationById(int id) {

        final String DELETE_SIGHTING = "DELETE FROM Sighting WHERE locationId = ?";
        jdbc.update(DELETE_SIGHTING, id);
        
        final String DELETE_LOCATION = "DELETE FROM Location WHERE locationId = ?";
        jdbc.update(DELETE_LOCATION, id);
    }

    @Override
    public List<Location> getLocationsForHero(Hero hero) {
        int heroId = hero.getHeroId();
        final String GET_LOCATIONS_FOR_HERO = "SELECT l.* FROM Location l "
                + "JOIN Sighting s ON s.locationId = l.locationId WHERE s.heroId = ?";
        return jdbc.query(GET_LOCATIONS_FOR_HERO, new LocationDaoDB.LocationMapper (), heroId);
    }
    
        public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setLocationId(rs.getInt("locationId"));
            location.setName(rs.getString("name"));
            location.setAddressInfo(rs.getString("addressInfo"));
            location.setDescription(rs.getString("description"));
            location.setLatitude(rs.getDouble("latitude"));
            location.setLongitude(rs.getDouble("longitude"));
            return location;
        }
    }
    
    
    
}
