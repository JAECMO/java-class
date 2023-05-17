/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.dvdlibrary.dao;

import com.jah.dvdlibrary.dto.DVD;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author drjal
 */
public interface DVDLibraryDao {
    
    DVD addDVD(String title, DVD dvd)
            throws DVDLibraryDaoException;
    
    List<DVD> getAllDVDs()
            throws DVDLibraryDaoException;
    
    List<String> getAllDVDTitles()
            throws DVDLibraryDaoException;
    
     DVD removeDVD(String title)
            throws DVDLibraryDaoException;
     
     DVD getDVD(String title)
            throws DVDLibraryDaoException;
     
     DVD editDVD(String title, DVD dvd )
            throws DVDLibraryDaoException;
     
     String verifyTitle(String title)
            throws DVDLibraryDaoException;
}
