/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.dvdlibrary.dao;

import com.jah.dvdlibrary.dto.DVD;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author drjal
 */
public class DVDLibraryDaoFileImplTest {
    
    DVDLibraryDao testDao;
    
    public DVDLibraryDaoFileImplTest() {
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "testDVDLibrary.txt";
        // Use the FileWriter to quickly blank the file
        new FileWriter(testFile);
        testDao = new DVDLibraryDaoFileImpl(testFile);
    }
    
     @Test
    public void testAddGetDVD() throws Exception {
    //Create our method test inputs
         String testFile = "testDVDLibrary.txt";
        // Use the FileWriter to quickly blank the file
        new FileWriter(testFile);
        testDao = new DVDLibraryDaoFileImpl(testFile);
        //this ensures we are starting with a fresh, empty DAO object
     //Create our method test inputs
    
    String title = "test DVD 1";
    DVD dvd = new DVD(title);
    dvd.setReleaseDate(LocalDate.parse("2000-10-10"));
    dvd.setMpaaRating("R");
    dvd.setDirector("Jo Black");
    dvd.setStudio("The New Studio");
    dvd.setRating(7.3);
    dvd.setNote("Fantastic!!");
    
    //  Add the dvd to the DAO
    testDao.addDVD(title, dvd);
    // Get the student from the DAO
    DVD retrievedDVD = testDao.getDVD(title);

    // Check the data is equal
    assertEquals(dvd.getTitle(),
                retrievedDVD.getTitle(),
                "Checking dvd title.");
    assertEquals(dvd.getReleaseDate(),
                retrievedDVD.getReleaseDate(),
                "Checking the release date.");
    assertEquals(dvd.getMpaaRating(), 
                retrievedDVD.getMpaaRating(),
                "Checking Mpaa rating.");
    assertEquals(dvd.getDirector(), 
                retrievedDVD.getDirector(),
                "Checking director.");
    assertEquals(dvd.getStudio(), 
                retrievedDVD.getStudio(),
                "Checking studio.");
    assertEquals(dvd.getRating(), 
                retrievedDVD.getRating(),
                "Checking rating.");
    assertEquals(dvd.getNote(), 
                retrievedDVD.getNote(),
                "Checking rating.");
}
    
    @Test
    public void testAddGetAllDVDs() throws Exception {
     //Create our method test inputs
         String testFile = "testDVDLibrary.txt";
        // Use the FileWriter to quickly blank the file
        new FileWriter(testFile);
        testDao = new DVDLibraryDaoFileImpl(testFile);
        //this ensures we are starting with a fresh, empty DAO object
     //Create our method test inputs
     
    // Create our first dvd
    String title1 = "test DVD 1";
    DVD dvd1 = new DVD(title1);
    dvd1.setReleaseDate(LocalDate.parse("2000-10-10"));
    dvd1.setMpaaRating("R");
    dvd1.setDirector("Jo Black");
    dvd1.setStudio("The New Studio");
    dvd1.setRating(7.3);
    dvd1.setNote("Fantastic!!");

    // Create our second dvd
    String title2 = "test DVD 2";
    DVD dvd2 = new DVD(title2);
    dvd2.setReleaseDate(LocalDate.parse("1986-06-07"));
    dvd2.setMpaaRating("R");
    dvd2.setDirector("John Green");
    dvd2.setStudio("The Older Studio");
    dvd2.setRating(6.5);
    dvd2.setNote("Wow!!");

    // Add both our dvds to the DAO
    testDao.addDVD(dvd1.getTitle(), dvd1);
    testDao.addDVD(dvd2.getTitle(), dvd2);

    // Retrieve the list of all dvds within the DAO
    List<DVD> allDVDs = testDao.getAllDVDs();

    // First check the general contents of the list
    assertNotNull(allDVDs, "The list of dvds must not be null");
    assertEquals(2, allDVDs.size(),"List of dvds should have 2 dvds.");

    // Then the specifics
    assertTrue(testDao.getAllDVDs().contains(dvd1),
                "The list of dvds should include dvd1.");
    assertTrue(testDao.getAllDVDs().contains(dvd2),
            "The list of dvds should include dvd2.");

}

    @Test
public void testRemoveDVD() throws Exception {
    //Create our method test inputs
         String testFile = "testDVDLibrary.txt";
        // Use the FileWriter to quickly blank the file
        new FileWriter(testFile);
        testDao = new DVDLibraryDaoFileImpl(testFile);
        //this ensures we are starting with a fresh, empty DAO object
     //Create our method test inputs
     
    // Create our first dvd
    String title1 = "test DVD 1";
    DVD dvd1 = new DVD(title1);
    dvd1.setReleaseDate(LocalDate.parse("2000-10-10"));
    dvd1.setMpaaRating("R");
    dvd1.setDirector("Jo Black");
    dvd1.setStudio("The New Studio");
    dvd1.setRating(7.3);
    dvd1.setNote("Fantastic!!");

    // Create our second dvd
    String title2 = "test DVD 2";
    DVD dvd2 = new DVD(title2);
    dvd2.setReleaseDate(LocalDate.parse("1986-06-07"));
    dvd2.setMpaaRating("R");
    dvd2.setDirector("John Green");
    dvd2.setStudio("The Older Studio");
    dvd2.setRating(6.5);
    dvd2.setNote("Wow!!");

    // Add both our dvds to the DAO
    testDao.addDVD(dvd1.getTitle(), dvd1);
    testDao.addDVD(dvd2.getTitle(), dvd2);

    // remove the first dvd - dvd1
    DVD removedDVD = testDao.removeDVD(dvd1.getTitle());

    // Check that the correct object was removed.
    assertEquals(removedDVD, dvd1, "The removed dvd should be dvd1.");

    // Get all the dvds
    List<DVD> allDVDs = testDao.getAllDVDs();

    // First check the general contents of the list
    assertNotNull( allDVDs, "All students list should be not null.");
    assertEquals( 1, allDVDs.size(), "All dvds should only have 1 student.");

    // Then the specifics
    assertFalse( allDVDs.contains(dvd1), "All dvds should NOT include dvd1.");
    assertTrue( allDVDs.contains(dvd2), "All dvds should include dvd2.");    

    // Remove the second dvd2
    removedDVD = testDao.removeDVD(dvd2.getTitle());
    // Check that the correct object was removed.
    assertEquals( removedDVD, dvd2, "The removed dvd should be dvd2.");

    // retrieve all of the dvds again, and check the list.
    allDVDs = testDao.getAllDVDs();

    // Check the contents of the list - it should be empty
    assertTrue( allDVDs.isEmpty(), "The retrieved list of dvds should be empty.");

    // Try to 'get' both students by their old id - they should be null!
    DVD retrievedDVD = testDao.getDVD(dvd1.getTitle());
    assertNull(retrievedDVD, "dvd1 was removed, should be null.");

    retrievedDVD = testDao.getDVD(dvd2.getTitle());
    assertNull(retrievedDVD, "dvd2 was removed, should be null.");

}
}
