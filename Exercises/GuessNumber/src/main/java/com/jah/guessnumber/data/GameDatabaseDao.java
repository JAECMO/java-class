/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.guessnumber.data;

import com.jah.guessnumber.models.Game;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
public class GameDatabaseDao implements GameDao {
   
    @Autowired
    private final JdbcTemplate jdbcTemplate;
    

    @Autowired
    public GameDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Game addGame(Game game) {

        final String sql = "INSERT INTO game(answer) VALUES(?);";
      GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                sql, 
                Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, game.getAnswer());
            return statement;

        }, keyHolder);

        game.setId(keyHolder.getKey().intValue());

        return game;
    }

    @Override
    public Game getGameById(int id) {
        try {
            final String SELECT_GAME_BY_ID = "SELECT * FROM game WHERE id = ?";
            return jdbcTemplate.queryForObject(SELECT_GAME_BY_ID, new GameMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Game> getAllGames() {
        final String sql = "SELECT * FROM game";
        return jdbcTemplate.query(sql, new GameMapper());
    }

    @Override
    public boolean updateGame(Game game) {

        final String sql = "UPDATE game SET "
                + "answer = ?, "
                + "finished = ? "
                + "WHERE id= ?;";

        return jdbcTemplate.update(sql,
                game.getAnswer(),
                game.isFinished(),
                game.getId()) > 0;
      }

    @Override
    public void deleteGameById(int id) {
        final String sqlRoundGameId = "DELETE FROM round WHERE gameId = ?";
        final String sqlGameId = "DELETE FROM game WHERE id = ?";
        
        jdbcTemplate.update(sqlRoundGameId, id);
        jdbcTemplate.update(sqlGameId, id);
    }
    
 
    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setId(rs.getInt("id"));
            game.setAnswer(rs.getString("answer"));
            game.setFinished(rs.getBoolean("finished"));
            return game;
        }
    }
    
}
