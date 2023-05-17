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
import java.util.List;

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
        view.displayEditDVDSuccessBanner(title);
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

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

   
    
}
