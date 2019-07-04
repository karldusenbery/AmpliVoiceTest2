package com.dusenbery.amplivoicetest1.model;

import android.util.Log;

public class Note {
    private String title;
    private String creationDate;

    public Note() {
        //empty constructor
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