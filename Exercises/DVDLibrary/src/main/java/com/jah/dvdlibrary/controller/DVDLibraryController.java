/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.dvdlibrary.controller;

import com.jah.dvdlibrary.dao.DVDLibraryDao;
import com.jah.dvdlibrary.dao.DVDLibraryDaoException;
import com.jah.dvdlibrary.dto.DVD;
import com.jah.dvdlibrary.ui.DVDView;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author drjal
 */
public class DVDLibraryController {
    
    private DVDView view;
    private DVDLibraryDao dao;

    public DVDLibraryController(DVDLibraryDao dao, DVDView view) {
        this.dao = dao;
        this.view = view;
}
    
    
       public void run() {
    boolean keepGoing = true;
    int menuSelection = 0;
    try {
        while (keepGoing) {

            menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    addDVD();
                    break;
                case 2:
                    removeDVD();
                    break;
                case 3:
                    editDVD();
                    break;
                case 4:
                    listDVDs();
                    break;
                case 5:
                    viewDVD();
                    break;
                case 6:
                    searchDVDByTitle();
                    break;
                case 7:
                    findDVDs();
                    break;
                case 8:
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
            }

        }//while
        exitMessage();
    } catch (DVDLibraryDaoException e) {
        view.displayErrorMessage(e.getMessage());
    }//catch
}//run()

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }
    
    private int getMenuSelectAdvancedSearch(){
        return view.printMenuAndGetSelectionAdvancedSearch();
    }
    
    private void addDVD() throws DVDLibraryDaoException {
        view.displayCreateDVDBanner();
        DVD newDVD = view.getNewDVDInfo();
        dao.addDVD(newDVD.getTitle(), newDVD);
        view.displayCreateSuccessBanner();
    }

    private void removeDVD() throws DVDLibraryDaoException {
        view.displayRemoveDVDBanner();
        String title = view.getTitle();
        title = dao.verifyTitle(title);
        DVD removedDVD = dao.removeDVD(title);
        view.displayRemoveResult(removedDVD);
    }

    private void editDVD() throws DVDLibraryDaoException{
        view.displayEditDVDBanner();
        String title = view.getTitle();
        DVD dvd = dao.getDVD(title);
        //view.displayDVD(dvd); 
        DVD updatedDVD = view.getUpdatedDVDInfo(dvd);
        dao.editDVD(title, updatedDVD);
        view.displayEditDVDSuccessBanner(title, updatedDVD);
    }

    private void listDVDs() throws DVDLibraryDaoException {
        view.displayDisplayAllBanner();
        List<DVD> dvdList = dao.getAllDVDs();
        view.displayDVDList(dvdList);
    }


    private void viewDVD() throws DVDLibraryDaoException {
        view.displayDisplayDVDBanner();
        String title = view.getTitle();
        title = dao.verifyTitle(title);
        DVD dvd = dao.getDVD(title);
        view.displayDVD(dvd);
    }

    private void searchDVDByTitle() throws DVDLibraryDaoException {
        view.displayDVDTitleListBanner();
        List<String> dvdTitleList = dao.getAllDVDTitles();
        String chosenTitle = view.displayDVDTitleList(dvdTitleList);
        DVD dvd = dao.getDVD(chosenTitle);
        view.displayDVD(dvd);
    }

    private void findDVDs() throws DVDLibraryDaoException {
        view.displayAdvancedSearchBanner();
        boolean keepGoing = true;
        int menuSelectAdvancedSearch = 0;
        try {
            while (keepGoing) {

                menuSelectAdvancedSearch = getMenuSelectAdvancedSearch();

                switch (menuSelectAdvancedSearch) {
                    case 1:
                        findDvdLastNYears();
                        break;
                    case 2:
                        findDvdMpaaRating();
                        break;
                    case 3:
                        findDvdDirector();
                        break;
                    case 4:
                        findDvdStudio();
                        break;
                    case 5:
                        findDvdAvgAge();
                        break;
                    case 6:
                        findDvdNewest();
                        break;
                    case 7:
                        findDvdOldest();
                        break;
                    case 8:
                        findDvdAvgNotes();
                        break;
                    case 9:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            }//while
         
        } catch (DVDLibraryDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }
        
    }

    

    private void findDvdLastNYears() throws DVDLibraryDaoException {
        view.displayFindDvdLastYearBanner();
        int yearsNb = view.getNumberOfYears();
        Map<LocalDate, List<DVD>> dvdsByYear = dao.findDvdLastNYears(yearsNb);
        view.displayMapDvdLastNYears(dvdsByYear);
        
        
    }

    private void findDvdMpaaRating() throws DVDLibraryDaoException {
        view.displayFindDvdMpaaRatingBanner();
        String ratingMpaa = view.getMPAArating();
        List<DVD> dvdsByMpaa = dao.findDvdMpaaRating(ratingMpaa);
        view.displayDVDListMpaaBanner(ratingMpaa);
        view.displayOnlyDVDList(dvdsByMpaa);
    }

    private void findDvdDirector() throws DVDLibraryDaoException {
        view.displayFindDvdDirectorBanner();
        String directorName = view.getDirectorName();
        Map<String, List<DVD>> dvdsByDirector = dao.findDvdDirector(directorName);
        view.displayMapDvdDirectorBanner(directorName);
        view.displayMapDvdDirector(dvdsByDirector);
    }

    private void findDvdStudio() throws DVDLibraryDaoException {
        view.displayFindDvdStudioBanner();
        String studioName = view.getStudioName();
        List<DVD> dvdsByStudio = dao.findDvdStudio(studioName);
        view.displayDVDListStudioBanner(studioName);
        view.displayOnlyDVDList(dvdsByStudio);
    }
    
    private void findDvdAvgAge() throws DVDLibraryDaoException { 
        view.displayDVDAvgAgeBanner();
        String avgAge = dao.findDvdAvgAge();
        view.displayDVDAvgAge(avgAge);   
    }
    
    private void findDvdNewest() throws DVDLibraryDaoException {
        List<DVD> dvdsNewestList = dao.findDvdNewest();
        view.displayDvdNewestBanner();
        view.displayOnlyDVDList(dvdsNewestList);
    }
    
     private void findDvdOldest() throws DVDLibraryDaoException {
        List<DVD> dvdsOldestList = dao.findDvdOldest();
        view.displayDvdOldestBanner();
        view.displayOnlyDVDList(dvdsOldestList);
    }
     
     private void findDvdAvgNotes() throws DVDLibraryDaoException {
        String avgDvdNotes = dao.findAvgNotes();
        view.displayDVDAvgNotesBanner();
        view.displayDVDAvgNotes(avgDvdNotes);
    }

    
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

}
