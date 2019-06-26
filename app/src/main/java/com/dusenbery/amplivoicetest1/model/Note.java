package com.dusenbery.amplivoicetest1.model;

public class Note {
    private String title;
    private String creationDate;

    public Note() {
        //empty constructor needed
    }

    public Note(String title, String creationDate) {
        this.title = title;
        this.creationDate = creationDate;
    }

    public String getTitle() {
        return title;
    }

    public String getCreationDate() {
        return creationDate;
    }
}