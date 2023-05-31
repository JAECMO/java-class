/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.dvdlibrary.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author drjal
 */
public class DVD {

    private String title;
    private LocalDate releaseDate;
    private String mpaaRating;
    private String director;
    private String studio;
    private double rating;
    private String note;

    public DVD(String title) {
        this.title = title;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.title);
        hash = 37 * hash + Objects.hashCode(this.releaseDate);
        hash = 37 * hash + Objects.hashCode(this.mpaaRating);
        hash = 37 * hash + Objects.hashCode(this.director);
        hash = 37 * hash + Objects.hashCode(this.studio);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.rating) ^ (Double.doubleToLongBits(this.rating) >>> 32));
        hash = 37 * hash + Objects.hashCode(this.note);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DVD other = (DVD) obj;
        if (Double.doubleToLongBits(this.rating) != Double.doubleToLongBits(other.rating)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.mpaaRating, other.mpaaRating)) {
            return false;
        }
        if (!Objects.equals(this.director, other.director)) {
            return false;
        }
        if (!Objects.equals(this.studio, other.studio)) {
            return false;
        }
        if (!Objects.equals(this.note, other.note)) {
            return false;
        }
        if (!Objects.equals(this.releaseDate, other.releaseDate)) {
            return false;
        }
        return true;
    }
    
    
    
    //no set title because title is the key of our map

    public String getTitle() {
        return title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {

        if (note.isBlank() || note.isEmpty() ) {
            this.note = "\"No reviews\"";
        } else {
            this.note = note;
        }
     
    }

    @Override
    public String toString() {
        return "DVD{" + "title=" + title + ", releaseDate=" + releaseDate + ", mpaaRating=" + mpaaRating + ", director=" + director + ", studio=" + studio + ", rating=" + rating + ", note=" + note + '}';
    }
    

}
