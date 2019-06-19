package com.dusenbery.amplivoicetest1.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

/**
 * User POJO (Plain Old Java Object)
 */
@IgnoreExtraProperties
public class User {
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_CREATED_AT = "creationDate";
    public static final String FIELD_FIRST_NAME = "firstName";
    public static final String FIELD_LAST_NAME = "lastName";


    private String email;
    private String creationDate;
    private String firstName;
    private String lastName;

    public User() {}

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
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
