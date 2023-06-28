/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.dvdlibrary.ui;

import com.jah.dvdlibrary.dao.DVDLibraryDaoException;
import com.jah.dvdlibrary.dto.DVD;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        io.print("7. Advanced search");
        io.print("8. Exit");

        return io.readInt("Please select from the above choices.", 1, 8);
    }
     
      public int printMenuAndGetSelectionAdvancedSearch() {
        io.print("Main Menu");
        io.print("1. Find all movies released in the last N years");
        io.print("2. Find all the movies with a given MPAA rating");
        io.print("3. Find all the movies by a given director");
        io.print("4. Find all the movies released by a particular studio");
        io.print("5. Find the average age of the movies in the collection");
        io.print("6. Find the newest movie in your collection");
        io.print("7. Find the oldest movie in your collection");
        io.print("8. Find the average number of notes associated with movies in your collection");
        io.print("9. Exit");

        return io.readInt("Please select from the above choices.", 1, 9);
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
   
    public DVD getUpdatedDVDInfo(DVD dvd)  {
        
        if (dvd != null){

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
        }
        
        return dvd;
     }
     
     public String getTitle() {
        return io.readString("Please enter the title of the DVD.");
    }
     
     public int getNumberOfYears(){
         return io.readInt("Please enter the number of years.");
     }
     
      public String getMPAArating(){
         return io.readMpaaRating("Please enter the MPAA rating (G,PG,PG-13,R,NC-17):");
     }
      
     public String getDirectorName() {
        String directorName;
        directorName = io.readString("Please enter the director name.");
        while (directorName.isEmpty() || directorName.isBlank()) {
            directorName = io.readString("There was no entry." + "\n" + "Please enter the director name.");
        }
        io.print("\n");
        return directorName;
    }

    public String getStudioName() {
        String studioName;
        studioName = io.readString("Please enter the studio name.");
        while (studioName.isEmpty() || studioName.isBlank()) {
            studioName = io.readString("There was no entry." + "\n" + "Please enter the studio name.");
        }
        io.print("\n");
        return studioName;
    }
     
     public void displayCreateDVDBanner() {
        io.print("=== Create DVD ===");
    }

    public void displayCreateSuccessBanner() {
        io.readString(
                "DVD successfully created.  Please hit enter to continue");
    }
    
     public void displayDisplayAllBanner() {
        io.print("=== Display All DVDs ===");
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
      
    public void displayOnlyDVDList(List<DVD> dvdList) {

        if (!dvdList.isEmpty()) {
            for (DVD currentDVD : dvdList) {
                String dvdInfo = String.format("%s" + "\n%s," + " %s," + " %s," + " %s," + " %s" + ", %s\n",
                        currentDVD.getTitle(),
                        currentDVD.getReleaseDate(),
                        currentDVD.getMpaaRating(),
                        currentDVD.getDirector(),
                        currentDVD.getStudio(),
                        currentDVD.getRating(),
                        currentDVD.getNote());
                io.print(dvdInfo);
            }
        } else {
            io.print("There are no DVDs corresponding to your selection");
        }
        io.print("\n");
    }
      
    
    public void displayMapDvdLastNYears(Map<LocalDate, List<DVD>> dvdsByYear) {
        if (!dvdsByYear.isEmpty()) {
            io.print("===============================");
            io.print("Release Date       Title");
            io.print("===============================");
            dvdsByYear.forEach((releaseDate, dvdList) -> {
                String formattedDate = releaseDate.toString();
                dvdList.forEach(dvd -> {
                    String dvdInfo = String.format("%s" + "%s," + " %s," + " %s," + " %s," + " %s" + ", %s\n",
                            dvd.getTitle(),
                            dvd.getReleaseDate(),
                            dvd.getMpaaRating(),
                            dvd.getDirector(),
                            dvd.getStudio(),
                            dvd.getRating(),
                            dvd.getNote());

                    String paddedDate = String.format("%-15s", formattedDate);
                    io.print(paddedDate + " " + dvdInfo);
                });
            });
        } else {
            io.print("There are no DVDs corresponding to this period");
        }
        io.print("\n");

    }
   
    public void displayMapDvdDirector(Map<String, List<DVD>> dvdsByDirector) {

        if (!dvdsByDirector.isEmpty()) {
            dvdsByDirector.forEach((ratingMpaa, dvdList) -> {
                io.print("MPAA Rating as " + ratingMpaa);
                displayOnlyDVDList(dvdList);
            });
        } else {
            io.print("No DVD corresponds to the selected director");
            io.print("\n");
        }
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
    
    public void displayEditDVDSuccessBanner(String title , DVD updatedDVD) {   
         if (updatedDVD != null) {
            io.print("DVD with title " + title + " was successfully edited");
        } else {
            io.print("No such DVD");
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

    public void displayAdvancedSearchBanner() {
        io.print("=== Advanced Search ===");
    }

    public void displayFindDvdLastYearBanner() {
        io.print("=== Search DVDs from Last N years ===");
    }

    public void displayFindDvdMpaaRatingBanner() {
        io.print("=== Search DVDs by MPAA rating  ===");
    }
    
    public void displayDVDAvgAgeBanner() {
        io.print("=== Average age of movies in the collection  ===");
        
    }
    
     public void displayDVDAvgAge(String avgAge) {
        io.print(avgAge);
        io.print("\n");
        
    }
     
     public void displayDVDAvgNotesBanner() {
        io.print("=== Average number of notes per DVD in the collection  ===");
        
    }
     
     public void displayDVDAvgNotes(String avgNotes) {
        io.print(avgNotes);
        io.print("\n");
        
    }

    public void displayDVDListMpaaBanner(String ratingMpaa) {
        io.print("=== List of DVDs with MPAA rating as " + ratingMpaa + " ===");
    }
    
    public void displayDVDListStudioBanner(String studioName){
         io.print("=== List of DVDs released by " + studioName + " ===");
    }

    public void displayFindDvdDirectorBanner() {
        io.print("=== Search DVDs by director  ===");
    }
    
    public void displayFindDvdStudioBanner() {
        io.print("=== Search DVDs by studio  ===");
    }
    
    public void displayMapDvdDirectorBanner(String directorName) {
        io.print("=== List of DVDs by " + directorName + " ===");
        io.print("\n");
    }
    
     public void displayDvdNewestBanner() {
        io.print("=== List of most recent DVDs  ===");
    }
     
    public void displayDvdOldestBanner() {
        io.print("=== List of oldest DVDs  ===");
    }

    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }
    
}
