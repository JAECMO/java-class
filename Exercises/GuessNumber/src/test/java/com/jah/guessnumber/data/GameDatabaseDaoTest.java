/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.guessnumber.data;

import com.jah.guessnumber.TestApplicationConfiguration;
import com.jah.guessnumber.models.Game;
import com.jah.guessnumber.models.Round;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author drjal
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GameDatabaseDaoTest {
    
    @Autowired
    GameDao gameDao;
    
    @Autowired
    RoundDao roundDao;
    
    
    
    public GameDatabaseDaoTest() {
    }
    
  
    
    @Before
    public void setUp() {
        List<Game> games = gameDao.getAllGames();
        for (Game game : games) {
            gameDao.deleteGameById(game.getId());
        }

        List<Round> rounds = roundDao.getAllRounds();
        for (Round round : rounds) {
            roundDao.deleteRoundById(round.getId());
        }

    }
    
    /**
     * Test of addGame and getGameById method, of class GameDatabaseDao.
     */
    @Test
    public void testAddGetGame() {
        Game game = new Game();
        game.setAnswer("1111");
        game = gameDao.addGame(game);
        Game fromDao = gameDao.getGameById(game.getId());
        assertEquals(game, fromDao);
    }

    /**
     * Test of getAllGames method, of class GameDatabaseDao.
     */
    @Test
    public void testGetAllGames() {
        Game game = new Game();
        game.setAnswer("2365");
        game = gameDao.addGame(game);
        
        Game game2 = new Game();
        game2.setAnswer("9999");
        game2 = gameDao.addGame(game2);
        
        List<Game> games = gameDao.getAllGames();
       
        assertEquals(2, games.size());
        assertTrue(games.contains(game));
        assertTrue(games.contains(game2));
    }

    /**
     * Test of updateGame method, of class GameDatabaseDao.
     */
    @Test
    public void testUpdateGame() {
        Game game = new Game();
        game.setAnswer("7777");
        game = gameDao.addGame(game);
        
        Game fromDao = gameDao.getGameById(game.getId());
        
        assertEquals(game, fromDao);
        
        game.setAnswer("8888");
        
        gameDao.updateGame(game);
        
        assertNotEquals(game, fromDao);
        
        fromDao = gameDao.getGameById(game.getId());
        
        assertEquals(game, fromDao);
    }
    
    
    
    /**
     * Test of deleteGameById method, of class GameDatabaseDao.
     */
    @Test
    public void testDeleteGameById() {
        Game game = new Game();
        game.setAnswer("3566");
        game = gameDao.addGame(game);
        
        Round round = new Round();
        round.setGameId(game.getId());
        round.setGuess("6653");
        round.setResult("e:0:p:0");
        round.setTime(LocalDateTime.now());
        round = roundDao.addRound(round);
 
        
        gameDao.deleteGameById(game.getId());
        
        Game fromDao = gameDao.getGameById(game.getId());
        
        assertNull(fromDao);
    }
    
}
