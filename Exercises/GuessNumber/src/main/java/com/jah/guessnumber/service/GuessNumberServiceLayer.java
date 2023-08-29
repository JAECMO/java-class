/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.guessnumber.service;

import com.jah.guessnumber.models.Game;
import com.jah.guessnumber.models.Round;
import java.util.List;

/**
 *
 * @author drjal
 */
public interface GuessNumberServiceLayer {
    int[] strToIntArray(String answer, String separator);
    String intArrayToStr(int[] intArray,String operator);
    String result(Round round,Game game);
    String strAnswer();
    Game gameReturnHiddenAns(int gameId);
    List<Game> listGameReturnHiddenAns();
   
}
