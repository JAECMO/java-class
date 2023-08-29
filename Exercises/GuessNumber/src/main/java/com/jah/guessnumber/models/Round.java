/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.guessnumber.models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author drjal
 */
public class Round {
    
    private int id;
    LocalDateTime time;
    int gameId;
    String result;
    String guess;

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode(this.time);
        hash = 47 * hash + this.gameId;
        hash = 47 * hash + Objects.hashCode(this.result);
        hash = 47 * hash + Objects.hashCode(this.guess);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Round other = (Round) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.gameId != other.gameId) {
            return false;
        }
        if (!Objects.equals(this.result, other.result)) {
            return false;
        }
        if (!Objects.equals(this.guess, other.guess)) {
            return false;
        }
        if (!Objects.equals(this.time, other.time)) {
            return false;
        }
        return true;
    }

    
    
    
    
}
