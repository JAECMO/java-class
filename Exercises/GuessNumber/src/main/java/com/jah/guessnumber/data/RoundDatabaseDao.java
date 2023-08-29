/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.guessnumber.data;

import com.jah.guessnumber.models.Game;
import com.jah.guessnumber.models.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author drjal
 */
@Repository
@Profile("database")
public class RoundDatabaseDao implements RoundDao {

    private final JdbcTemplate jdbcTemplate;
    

    @Autowired
    public RoundDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Round addRound(Round round) {
        
        final String sql = "INSERT INTO round(gameId,result, guess) VALUES(?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                sql, 
                Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, round.getGameId());
            statement.setString(2, round.getResult());
            statement.setString(3, round.getGuess());
            return statement;

        }, keyHolder);
        
  
            round.setId(keyHolder.getKey().intValue());
            round.setTime(getRoundById(keyHolder.getKey().intValue()).getTime());
    
            
      
        return round;
    }
    
  @Override  
    public Round getRoundById(int roundId){
         final String sql = "SELECT * "
                + "FROM round WHERE id = ?";

        try{
            return jdbcTemplate.queryForObject(sql, new RoundMapper(), roundId);
        }catch(DataAccessException ex){
            return null;
        }
    
    }

    @Override
    public List<Round> getAllRoundsByGameId(int gameId) {
        final String sql = "SELECT * "
                + "FROM round WHERE gameId = ? ORDER BY time";
         try{
            return jdbcTemplate.query(sql, new RoundMapper(),gameId);
        }catch(DataAccessException ex){
            return null;
        }
    }
     @Override
    public boolean updateRound(Round round) {
        final String sql = "UPDATE round SET "
                + "time = ?, "
                + "guess = ?, "
                + "result = ? "
                + "WHERE id = ?;";

        return jdbcTemplate.update(sql,
                round.getTime(),
                round.getGuess(),
                round.getResult(),
                round.getId()) > 0;
    }

    @Override
    public void deleteRoundById(int id) {
        final String sqlRoundId = "DELETE FROM round WHERE id = ?";
        jdbcTemplate.update(sqlRoundId, id);
    }

    @Override
    public List<Round> getAllRounds() {
        final String sql = "SELECT * FROM round";
        return jdbcTemplate.query(sql, new RoundDatabaseDao.RoundMapper());
    }


     private static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setId(rs.getInt("id"));
            round.setTime(rs.getTimestamp("time").toLocalDateTime());
            round.setGuess(rs.getString("guess"));
            round.setResult(rs.getString("result"));
            round.setGameId(rs.getInt("gameId"));
            return round;
        }
    }
    
}
