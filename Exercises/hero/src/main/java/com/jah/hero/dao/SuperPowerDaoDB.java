/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.dao;

import com.jah.hero.dto.Location;
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
public class SuperPowerDaoDB implements SuperPowerDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public SuperPower getSuperPowerById(int id) {
  try {
            final String GET_SUPERPOWER_BY_ID = "SELECT * FROM SuperPower WHERE superPowerId = ?";
            SuperPower superPower = jdbc.queryForObject(GET_SUPERPOWER_BY_ID, new SuperPowerDaoDB.SuperPowerMapper(), id);
            return superPower;
        } catch (DataAccessException ex) {
            return null;
        }    }

    @Override
    public List<SuperPower> getAllSuperPower() {
        final String GET_ALL_SUPERPOWERS = "SELECT * FROM SuperPower";
        return jdbc.query(GET_ALL_SUPERPOWERS, new SuperPowerDaoDB.SuperPowerMapper());
    }

    @Override
    @Transactional
    public SuperPower addSuperPower(SuperPower superPower) {
        final String INSERT_SUPERPOWER = "INSERT INTO SuperPower(name) "
                + "VALUES(?)";
        jdbc.update(INSERT_SUPERPOWER,
                superPower.getName());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superPower.setSuperPowerId(newId);
        return superPower;
    }

    @Override
    public void updateSuperPower(SuperPower superPower) {
        final String UPDATE_SUPER_POWER = "UPDATE SuperPower SET name = ? "
                + "WHERE superPowerId = ?";
        jdbc.update(UPDATE_SUPER_POWER,
                superPower.getName(),
                superPower.getSuperPowerId());
    }

    @Override
    @Transactional
    public void deletesuperPower(int id) {
        final String DELETE_HERO = "DELETE FROM Hero WHERE superPowerId = ?";
        jdbc.update(DELETE_HERO, id);

        final String DELETE_SUPER_POWER = "DELETE FROM SuperPower WHERE superPowerId = ?";
        jdbc.update(DELETE_SUPER_POWER, id);
    }

    public static final class SuperPowerMapper implements RowMapper<SuperPower> {

        @Override
        public SuperPower mapRow(ResultSet rs, int index) throws SQLException {
            SuperPower superPower = new SuperPower();
            superPower.setSuperPowerId(rs.getInt("superPowerId"));
            superPower.setName(rs.getString("name"));
            return superPower;
        }
    }
}
