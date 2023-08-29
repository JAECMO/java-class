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
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
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
public class RoundDatabaseDaoTest {
    
    @Autowired
    GameDao gameDao;
    
    @Autowired
    RoundDao roundDao;
    
    
    
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
     * Test of addRound and getRoundById method, of class RoundDatabaseDao.
     */
    @Test
    public void testAddGetRound() {
        Game game = new Game();
        game.setAnswer("4559");
        game = gameDao.addGame(game);

        Round round = new Round();
        round.setGameId(game.getId());
        round.setGuess("6554");
        round.setResult("e:0:p:0");
        
        round = roundDao.addRound(round);
        Round fromDao = roundDao.getRoundById(round.getId());
        assertEquals(round, fromDao);

    }

    /**
     * Test of getAllRoundsByGameId method, of class RoundDatabaseDao.
     */
    @Test
    public void testGetAllRoundsByGameId() {
        
        Game game = new Game();
        game.setAnswer("4559");
        game = gameDao.addGame(game);

        Round round = new Round();
        round.setGameId(game.getId());
        round.setGuess("6554");
        round.setResult("e:0:p:0");
        
        round = roundDao.addRound(round);
        
        Round round2 = new Round();
        round2.setGameId(game.getId());
        round2.setGuess("3332");
        round2.setResult("e:0:p:0");
        
        round2 = roundDao.addRound(round2);
        
        List<Round> rounds = roundDao.getAllRounds();
       
        assertEquals(2, rounds.size());
        assertTrue(rounds.contains(round));
        assertTrue(rounds.contains(round2));
        
    }
    
    @Test
    public void testUpdateRound() {
        Game game = new Game();
        game.setAnswer("7777");
        game = gameDao.addGame(game);

        Round round = new Round();
        round.setGameId(game.getId());
        round.setGuess("5043");
        round.setResult("e:0:p:0");

        round = roundDao.addRound(round);

        Round fromDao = roundDao.getRoundById(round.getId());

        assertEquals(round, fromDao);

        round.setGuess("5338");

        roundDao.updateRound(round);

        assertNotEquals(round, fromDao);

        fromDao = roundDao.getRoundById(round.getId());

        assertEquals(round, fromDao);

    }
    
    @Test
    public void testDeleteRoundById() {
        Game game = new Game();
        game.setAnswer("3567");
        game = gameDao.addGame(game);

        Round round = new Round();
        round.setGameId(game.getId());
        round.setGuess("8668");
        round.setResult("e:0:p:0");

        round = roundDao.addRound(round);
 
        
        roundDao.deleteRoundById(round.getId());
        
        Round fromDao = roundDao.getRoundById(round.getId());
        
        assertNull(fromDao);
    }
}
