/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.dvdlibrary.ui;

import com.jah.dvdlibrary.dto.DVD;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author drjal
 */
public class DVDView {
    
     private UserIO io;
    
    public DVDView(UserIO io) {
    this.io = io;
}
     public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. Add DVD");
        io.print("2. Delete DVD");
        io.print("3. Edit DVD");
        io.print("4. List All DVDs");
        io.print("5. View Info about a DVD");
        io.print("6. Search for a DVD by Title");
        io.print("7. Exit");

        return io.readInt("Please select from the above choices.", 1, 7);
    }
     
     public DVD getNewDVDInfo() {
        
        String title = io.readString("Please enter DVD title:");
        LocalDate releaseDate = io.readDate("Please enter the release date (yyyy-MM-dd):");
        String mpaaRating = io.readMpaaRating("Please enter the MPAA rating (G,PG,PG-13,R,NC-17):");
        String director = io.readString("Please enter the director name:");
        String studio = io.readString("Please enter the studio name:");
        Double rating = io.readDouble("Please enter the rating out of 10:",0,10);
        String note = io.readString("Please enter a note if you desire or click enter to continue:");
        
        DVD currentDVD = new DVD(title);
        currentDVD.setReleaseDate(releaseDate);
        currentDVD.setMpaaRating(mpaaRating);
        currentDVD.setDirector(director);
        currentDVD.setStudio(studio);
        currentDVD.setRating(rating);
        currentDVD.setNote(note);
        
        return currentDVD;
    }
   
    public DVD getUpdatedDVDInfo(DVD dvd) {

        String menu = "Select from the following numbers the info to edit:\n"+ "1. release date\n" + "2. mpaaRating\n" + "3. director name\n" + "4. studio name\n" + "5. rating\n" + "6. note";
        
        boolean keepGoing = true;

        while (keepGoing) {
                int choice = io.readInt(menu, 1, 6);
            switch (choice) {
                case 1:
                    LocalDate updatedDate = io.readDate("Enter the new realease date");
                    dvd.setReleaseDate(updatedDate);
                    break;
                case 2:
                    String mpaaUpdated = io.readMpaaRating("Enter the new MPAA rating (G,PG,PG-13,R,NC-17):");
                    dvd.setMpaaRating(mpaaUpdated);
                    break;
                case 3:
                    String directorName = io.readString("Enter the new director name:");
                    dvd.setDirector(directorName);
                    break;
                case 4:
                    String studioName = io.readString("Enter the studio name:");
                    dvd.setStudio(studioName);
                    break;
                case 5:
                    Double rating = io.readDouble("Enter the rating out of 10:", 0, 10);
                    dvd.setRating(rating);
                    break;
                case 6:
                    String note = io.readString("Enter the new review:");
                    dvd.setNote(note);
                    break;
                    
            }//switch
               String prompt = "Do you want to edit more info? Type YES or NO" ;
               
               keepGoing = io.readboolean(prompt);

        }//while
        
        return dvd;
     }
     
     public String getTitle() {
        return io.readString("Please enter the title of the DVD.");
    }
     
     public void displayCreateDVDBanner() {
        io.print("=== Create DVD ===");
    }

    public void displayCreateSuccessBanner() {
        io.readString(
                "DVD successfully created.  Please hit enter to continue");
    }
    
     public void displayDisplayAllBanner() {
        io.print("=== Display All Addresses ===");
    }
     
      public void displayDVDList(List<DVD> dvdList) {
        for (DVD currentDVD : dvdList) {
            String dvdInfo = String.format("%s" +"\n%s," + " %s," + " %s," + " %s," + " %s" +", %s\n",
                    currentDVD.getTitle(),
                    currentDVD.getReleaseDate(),
                    currentDVD.getMpaaRating(),
                    currentDVD.getDirector(),
                    currentDVD.getStudio(),
                    currentDVD.getRating(),
                    currentDVD.getNote());
            io.print(dvdInfo);
        }
        io.readString("Please hit enter to continue.");
    }
      
      public void displayDisplayDVDBanner() {
        io.print("=== Display DVD ===");
    }
      
       public void displayDVDTitleListBanner() {
        io.print("=== Display DVDs by titles ===");
    }
       
       public String displayDVDTitleList(List<String> dvdTitleList) {
        int i = 1;
        for (String title : dvdTitleList) {
            io.print(i + ". " + title);
            i++;
        }
        int intChoice = io.readInt("\nSelect the dvd to view from " + "1 to " + (i-1), 1, i-1);
        String chosenTitle = dvdTitleList.get(intChoice-1);
        
       return chosenTitle;
    }
        
     
    
     public void displayDVD(DVD dvd) {
        if (dvd != null) {
            io.print("");
            io.print(dvd.getTitle());
            io.print(dvd.getReleaseDate().toString());
            io.print(dvd.getMpaaRating());
            io.print(dvd.getDirector());
            io.print(dvd.getStudio());
            io.print(Double.toString(dvd.getRating()));
            io.print(dvd.getNote());
            io.print("");
        } else {
            io.print("No such dvd.");
        }
        io.readString("Please hit enter to continue.");
    }
      
       public void displayRemoveDVDBanner() {
        io.print("=== Remove DVD ===");
    }

    public void displayRemoveResult(DVD dvdRecord) {
        if (dvdRecord != null) {
            io.print("DVD successfully removed.");
        } else {
            io.print("No such DVD");
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayEditDVDBanner() {
        io.print("=== Edit DVD ===");
    }
    
    public void displayEditDVDSuccessBanner(String title) {
        io.print("DVD with title " + title + " was successfully edited");
    }
    
    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
    
    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

     public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }
    
}
