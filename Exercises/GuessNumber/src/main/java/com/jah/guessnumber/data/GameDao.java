/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.guessnumber.data;

import com.jah.guessnumber.models.Game;
import java.util.List;

/**
 *
 * @author drjal
 */
public interface GameDao {
    Game addGame(Game game);
    Game getGameById (int id);
    List<Game> getAllGames();
    boolean updateGame(Game game);
    void deleteGameById(int id);
}
