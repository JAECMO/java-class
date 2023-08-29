/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.guessnumber.controllers;

import com.jah.guessnumber.data.GameDao;
import com.jah.guessnumber.data.RoundDao;
import com.jah.guessnumber.models.Game;
import com.jah.guessnumber.models.Round;
import com.jah.guessnumber.service.GuessNumberServiceLayer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author drjal
 */
@RestController
@RequestMapping("/guessnumber")
public class GuessNumberController {
    
    private final GameDao gameDao;
    private final RoundDao roundDao;
    private final GuessNumberServiceLayer service;

   
    public GuessNumberController(GameDao gameDao, RoundDao roundDao, GuessNumberServiceLayer service ) {
        this.gameDao = gameDao;
        this.roundDao = roundDao;
        this.service = service;
    }
    
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int begin() {
        Game game = new Game();
        game.setAnswer(service.strAnswer());
        return gameDao.addGame(game).getId();
    }
    
    @PostMapping("/guess")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createRound(@RequestBody Round round) {
        Game game = gameDao.getGameById(round.getGameId());
        service.result(round, game);
        round = roundDao.addRound(round);

        if (round == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(round);
    }
    
     @GetMapping("/game")
    public List<Game> all() {
        return service.listGameReturnHiddenAns();
    }
    
     @GetMapping("/game/{gameId}")
    public ResponseEntity<Game> findGameById(@PathVariable int gameId) {
        Game result = service.gameReturnHiddenAns(gameId);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
    
      @GetMapping("/rounds/{gameId}")
    public ResponseEntity<List<Round>> findRoundsById(@PathVariable int gameId) {
        List<Round> result = roundDao.getAllRoundsByGameId(gameId);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
}
