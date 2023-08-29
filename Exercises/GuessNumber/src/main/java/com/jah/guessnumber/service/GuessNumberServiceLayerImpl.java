/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.guessnumber.service;

import com.jah.guessnumber.data.GameDao;
import com.jah.guessnumber.data.RoundDao;
import com.jah.guessnumber.models.Game;
import com.jah.guessnumber.models.Round;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author drjal
 */
@Service
public class GuessNumberServiceLayerImpl implements GuessNumberServiceLayer {
    
    @Autowired
    private GameDao gameDao;
    
    @Autowired
    private RoundDao roundDao;

    @Override
    public int[] strToIntArray(String answer, String separator) {
         String[] numberStrings = answer.split(separator);
        int[] intArray = new int[numberStrings.length];
        
        for (int i = 0; i < numberStrings.length; i++) {
            intArray[i] = Integer.parseInt(numberStrings[i].trim());
        }
        
        return intArray;
    }

   
    @Override
    public String strAnswer() {
        List<Integer> digits = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            digits.add(i);
        }

        Collections.shuffle(digits);

        int[] randomDigits = new int[4];
        for (int i = 0; i < 4; i++) {
            randomDigits[i] = digits.get(i);
        }

        String answer = intArrayToStr(randomDigits, "");
        return answer;
    }

    @Override
    public String intArrayToStr(int[] array, String separator) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);

            if (i < array.length - 1) {
                builder.append(separator);
            }
        }

        return builder.toString();
    }

    @Override
    public String result(Round round, Game game) {
     
        String result;
        if (round.getResult() == null) {
            result = "e:0:p:0";
            round.setResult(result);
        } else {
            result = round.getResult();
        }
        int exactMatchNb = exactMatch(round.getGuess(), game.getAnswer());
        int partialMatchNb = partialMatch(round.getGuess(), game.getAnswer());

        if (exactMatchNb > 0) {
            result = addMatchToParam(round.getResult(), "e", exactMatchNb);
            round.setResult(result);
            if (exactMatchNb == 4) {
                game.setFinished(true);
                gameDao.updateGame(game);
            }
        } 
        
        if (partialMatchNb > 0) {
            result = addMatchToParam(round.getResult(), "p", partialMatchNb);
            round.setResult(result);
        }
        return result;
    }
    
     
    private int exactMatch(String guess, String answer) {
        
        int exactMatch = 0;
        if (guess.length() != 4 || answer.length() != 4) {
            throw new IllegalArgumentException("Both input strings must have exactly 4 characters.");
        }

        for (int i = 0; i < 4; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                exactMatch++;
            }
        }

        return exactMatch;
    }

    
    private  int partialMatch(String guess, String answer) {
        if (guess.length() != 4 || answer.length() != 4) {
            throw new IllegalArgumentException("Both input strings must have exactly 4 characters.");
        }

        int count = 0;
        boolean[] answerUsed = new boolean[4]; // To track which positions in 'answer' have been used

        for (int i = 0; i < 4; i++) {
            char guessChar = guess.charAt(i);

            if (guessChar != answer.charAt(i)) {
                for (int j = 0; j < 4; j++) {
                    if (!answerUsed[j] && guessChar == answer.charAt(j)) {
                        count++;
                        answerUsed[j] = true;
                        break;
                    }
                }
            }
        }

        return count;
    }

   private  String addMatchToParam(String input, String param,int nb) {
        String[] segments = input.split(":");
        for (int i = 0; i < segments.length; i++) {
            if (segments[i].equals(param)) {
                int value = Integer.parseInt(segments[i + 1]);
                value += nb;
                segments[i + 1] = String.valueOf(value);
                break; 
            }
        }
        
        return String.join(":", segments);
    }

    @Override
    public Game gameReturnHiddenAns(int gameId) {
        Game game = gameDao.getGameById(gameId);
        Game gameHidden = new Game();
        if (!game.isFinished()) {
            gameHidden.setId(gameId);
            gameHidden.setAnswer("Hidden");
            gameHidden.setFinished(false);
            return gameHidden;
        } else {
            return game;
        }
    }

    @Override
    public List<Game> listGameReturnHiddenAns() {
        List<Game> allGames = gameDao.getAllGames();
        List<Game> allGamesHidden = new ArrayList<>();
        for (Game game : allGames) {
            if (!game.isFinished()) {
                game.setAnswer("Hidden");
            }
            allGamesHidden.add(game);
        }

        return allGamesHidden;
    }

    

}
