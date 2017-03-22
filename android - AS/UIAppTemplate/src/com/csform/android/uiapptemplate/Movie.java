package com.csform.android.uiapptemplate;

/**
 * Created by Krishna on 22/02/17.
 */

public class Movie {
    private String title, genre, year,url,academy;

    public Movie() {
    }

    public Movie(String title, String genre, String year, String url, String academy) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.url = url;
        this.academy = academy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String name) {
        this.url = name;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String name) {
        this.academy = name;
    }



}