/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.dvdlibrary.dao;

import com.jah.dvdlibrary.dto.DVD;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    DVD editDVD(String title, DVD dvd)
            throws DVDLibraryDaoException;

    Map<LocalDate, List<DVD>> findDvdLastNYears(int yearsNb)
            throws DVDLibraryDaoException;

    List<DVD> findDvdMpaaRating(String ratingMpaa)
            throws DVDLibraryDaoException;

    Map<String, List<DVD>> findDvdDirector(String directorName)
            throws DVDLibraryDaoException;

    List<DVD> findDvdStudio(String studioName)
            throws DVDLibraryDaoException;

    String findDvdAvgAge()
            throws DVDLibraryDaoException;
    
    List<DVD> findDvdNewest()
            throws DVDLibraryDaoException;
    
    List<DVD> findDvdOldest()
            throws DVDLibraryDaoException;
    
    String findAvgNotes()
            throws DVDLibraryDaoException;
            

    String verifyTitle(String title)
            throws DVDLibraryDaoException;
}
