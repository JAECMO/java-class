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
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Scanner;
import java.util.stream.Collectors;

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
    
    @Override
    public Map<LocalDate, List<DVD>> findDvdLastNYears(int yearsNb) throws DVDLibraryDaoException {
        loadDVDList();
        LocalDate today = LocalDate.now();
        int releaseYearLimit = today.getYear() - yearsNb;
        List<DVD> dvdsList = new ArrayList(dvds.values());
        Map<LocalDate, List<DVD>> dvdByYear = dvdsList.stream().filter((p) -> p.getReleaseDate().getYear() >= releaseYearLimit)
                .collect(Collectors.groupingBy((p) -> p.getReleaseDate()));
        return dvdByYear;
    }
    
    @Override
    public List<DVD> findDvdMpaaRating(String ratingMpaa) throws DVDLibraryDaoException {
        loadDVDList();
        List<DVD> dvdsList = new ArrayList(dvds.values());
        return dvdsList.stream().
                filter((d) -> d.getMpaaRating().equals(ratingMpaa))
                .collect(Collectors.toList());
    }
   
    @Override
    public Map<String, List<DVD>> findDvdDirector(String directorName) throws DVDLibraryDaoException {
        loadDVDList();
        List<DVD> dvdsList = new ArrayList(dvds.values());
        Map<String, List<DVD>> dvdByDirector = dvdsList.stream().
                filter((d) -> d.getDirector().equals(directorName)).
                collect(Collectors.groupingBy((p) -> p.getMpaaRating()));
        return dvdByDirector;
    }
    
    @Override
    public List<DVD> findDvdStudio(String studioName) throws DVDLibraryDaoException {
        loadDVDList();
        List<DVD> dvdsList = new ArrayList(dvds.values());
        return dvdsList.stream().
                filter((d) -> d.getStudio().equals(studioName))
                .collect(Collectors.toList());
    }
    
    @Override
    public String findDvdAvgAge() throws DVDLibraryDaoException {
        String avgAge;
        LocalDate today = LocalDate.now();
        loadDVDList();
        List<DVD> dvdsList = new ArrayList(dvds.values());
        Double avgAgeDouble = dvdsList.stream().
                mapToDouble((d) -> ChronoUnit.MONTHS.between(d.getReleaseDate(), today) / 12.0)
                .average().orElse(Double.NaN);

        if (avgAgeDouble.isNaN()) {
            avgAge = "The collection is Empty";
        } else {
            avgAge = "The Average age is " + String.format("%.2f", avgAgeDouble) + " years";
        }

        return avgAge;
    }
    
    @Override
    public List<DVD> findDvdNewest() throws DVDLibraryDaoException {

        String newestDvd;
        LocalDate today = LocalDate.now();

        loadDVDList();
        List<DVD> dvdsList = new ArrayList(dvds.values());
        List<String> datesListStr = dvdsList.stream().
                map((d) -> d.getReleaseDate().toString())
                .collect(Collectors.toList());

        List<LocalDate> datesList = datesListStr.stream()
                .map((d) -> LocalDate.parse(d))
                .collect(Collectors.toList());

        Optional<LocalDate> mostRecent = datesList.stream()
                .max(LocalDate::compareTo);

        if (mostRecent.isPresent()) {
            newestDvd = "" + mostRecent.get();
        } else {
            newestDvd = today.toString();
        }

        List<DVD> dvdNewestList = dvdsList.stream().
                filter((d) -> d.getReleaseDate().equals(LocalDate.parse(newestDvd)))
                .collect(Collectors.toList());

        return dvdNewestList;
    }
    
    @Override
    public List<DVD> findDvdOldest() throws DVDLibraryDaoException {

        String oldestDvd;
        LocalDate today = LocalDate.now();

        loadDVDList();
        List<DVD> dvdsList = new ArrayList(dvds.values());
        List<String> datesListStr = dvdsList.stream().
                map((d) -> d.getReleaseDate().toString())
                .collect(Collectors.toList());

        List<LocalDate> datesList = datesListStr.stream()
                .map((d) -> LocalDate.parse(d))
                .collect(Collectors.toList());

        Optional<LocalDate> lessRecent = datesList.stream()
                .min(LocalDate::compareTo);

        if (lessRecent.isPresent()) {
            oldestDvd = "" + lessRecent.get();
        } else {
            oldestDvd = today.toString();
        }

        List<DVD> dvdNewestList = dvdsList.stream().
                filter((d) -> d.getReleaseDate().equals(LocalDate.parse(oldestDvd)))
                .collect(Collectors.toList());

        return dvdNewestList;
    }
    
    @Override
    public String findAvgNotes() throws DVDLibraryDaoException {
        String avgNotes;
        loadDVDList();
        List<DVD> dvdsList = new ArrayList(dvds.values());
        List<DVD> dvdsNoteList = dvdsList.stream().
                filter((d) -> !d.getNote().equals("\"No reviews\""))
                .collect(Collectors.toList());

        Double dvdsListSize = dvdsList.size() * 1.0;
        Double dvdsNoteListSize = dvdsNoteList.size() * 1.0;

        Double avgNotesDouble = dvdsNoteListSize / dvdsListSize;

        if (avgNotesDouble.isNaN()) {
            avgNotes = "The collection is Empty";
        } else {
            avgNotes = "The Average number of notes is " + String.format("%.2f", avgNotesDouble) + " per DVD";
        }

        return avgNotes;

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
