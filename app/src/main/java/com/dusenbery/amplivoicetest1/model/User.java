package com.dusenbery.amplivoicetest1.model;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.firestore.IgnoreExtraProperties;

/**
 * User POJO (Plain Old Java Object)
 */
@IgnoreExtraProperties
public class User {
    public static final String FIELD_ID = "userID";
    public static final String FIELD_CREATED_AT = "createdAtDate";
    public static final String FIELD_FIRST_NAME = "firstName";
    public static final String FIELD_LAST_NAME = "lastName";


    private String userID;
    private String createdAtDate;
    private String firstName;
    private String lastName;

    public User() {}

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /*
    public User(String userID, String createdAtDate, String firstName, String lastName) {
        this.userID = userID;
        this.createdAtDate = createdAtDate;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    */

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCreatedAtDate() {
        return createdAtDate;
    }

    public void setCreatedAtDate(String createdAtDate) {
        this.createdAtDate = createdAtDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
