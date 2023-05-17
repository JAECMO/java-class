/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.dvdlibrary.dao;

/**
 *
 * @author drjal
 */
public class DVDLibraryDaoException extends Exception {
    
     public DVDLibraryDaoException(String message) {
        super(message);
    }
    
    public DVDLibraryDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
