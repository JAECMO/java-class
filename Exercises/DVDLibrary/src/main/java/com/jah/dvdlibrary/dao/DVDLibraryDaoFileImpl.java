/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.dvdlibrary.dao;

import com.jah.dvdlibrary.dto.DVD;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author drjal
 */
public class DVDLibraryDaoFileImpl implements DVDLibraryDao {
    
    private final String DVD_LIST_FILE;

    public DVDLibraryDaoFileImpl() {
        DVD_LIST_FILE = "DVDLibrary.txt";
    }

    public DVDLibraryDaoFileImpl(String DVDLibraryTextFile) {
        DVD_LIST_FILE = DVDLibraryTextFile;
    }
    
    
    public static final String DELIMITER = "::";
    
    private Map<String, DVD> dvds = new HashMap<>();

    @Override
    public DVD addDVD(String title, DVD dvd) throws DVDLibraryDaoException {
        loadDVDList();
        DVD newDVD = dvds.put(title, dvd);
        writeDVDList();
        return newDVD;
    
    }
    
    @Override
    public DVD removeDVD(String title) throws DVDLibraryDaoException {
        loadDVDList();
        DVD removeDVD = dvds.remove(title);
        writeDVDList();
        return removeDVD;
    }
    
    @Override
    public DVD getDVD(String title) throws DVDLibraryDaoException {
         loadDVDList();
        return dvds.get(title);
    }
    
    @Override
    public DVD editDVD(String title, DVD dvd) throws DVDLibraryDaoException {
        loadDVDList();
        DVD updatedDVD = dvds.replace(title, dvd);
        writeDVDList();
        return updatedDVD;
    }

    @Override
    public List<DVD> getAllDVDs() throws DVDLibraryDaoException {
         loadDVDList();
        return new ArrayList(dvds.values());
    }
    
      @Override
    public List<String> getAllDVDTitles() throws DVDLibraryDaoException {
        loadDVDList();
         return new ArrayList(dvds.keySet());
    }
    
    
    private DVD unmarshallAddress(String dvdAsText) {

        String[] dvdTokens = dvdAsText.split(DELIMITER);

        String title = dvdTokens[0];

        DVD dvdFromFile = new DVD(title);

        dvdFromFile.setReleaseDate(LocalDate.parse(dvdTokens[1]));

        dvdFromFile.setMpaaRating(dvdTokens[2]);

        dvdFromFile.setDirector(dvdTokens[3]);

        dvdFromFile.setStudio(dvdTokens[4]);

        dvdFromFile.setRating(Double.parseDouble(dvdTokens[5]));

        dvdFromFile.setNote(dvdTokens[6]);

        return dvdFromFile;

    }

    private void loadDVDList() throws DVDLibraryDaoException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(DVD_LIST_FILE)));
        } catch (FileNotFoundException e) {
            throw new DVDLibraryDaoException(
                    "-_- Could not load address list data into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentDVD holds the most recent DVD unmarshalled
        DVD currentDVD;
 
            while (scanner.hasNextLine()) {
                // get the next line in the file
                currentLine = scanner.nextLine();
                // unmarshall the line into an Address
                currentDVD = unmarshallAddress(currentLine);

                dvds.put(currentDVD.getTitle(), currentDVD);
            }
     
        // close scanner
        scanner.close();
    }

    private String marshallAddress(DVD aDVD) {

        String dvdAsText = aDVD.getTitle() + DELIMITER;

        dvdAsText += aDVD.getReleaseDate() + DELIMITER;

        dvdAsText += aDVD.getMpaaRating() + DELIMITER;

        dvdAsText += aDVD.getDirector() + DELIMITER;

        dvdAsText += aDVD.getStudio() + DELIMITER;

        dvdAsText += aDVD.getRating() + DELIMITER;
        
        dvdAsText += aDVD.getNote();

        return dvdAsText;
    }

    private void writeDVDList() throws DVDLibraryDaoException {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(DVD_LIST_FILE));
        } catch (IOException e) {
            throw new DVDLibraryDaoException(
                    "Could not save address data.", e);
        }

        String dvdAsText;
        List<DVD> dvdList = this.getAllDVDs();
        for (DVD currentDVD: dvdList) {

            dvdAsText = marshallAddress(currentDVD);

            out.println(dvdAsText);

            out.flush();
        }
        // Clean up
        out.close();
    }

    @Override
    public String verifyTitle(String title) throws DVDLibraryDaoException {
      loadDVDList();
        String str = title;

        for (String key : dvds.keySet()) {
            if (key.toLowerCase().equals(title.toLowerCase())) {
                str = key;
                break;
            }
        }//for
        return str;
    }
}
