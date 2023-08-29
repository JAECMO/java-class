/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.guessnumber.data;

import com.jah.guessnumber.models.Game;
import com.jah.guessnumber.models.Round;
import java.util.List;

/**
 *
 * @author drjal
 */
public interface RoundDao {
    Round addRound(Round round);
    Round getRoundById(int roundId);
    List<Round> getAllRoundsByGameId(int gameId);
    List<Round> getAllRounds();
    void deleteRoundById(int id);
    boolean updateRound(Round round);
}
