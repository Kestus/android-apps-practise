package com.example.app015_contactmanager.db.entity;

import android.annotation.SuppressLint;
import android.database.Cursor;

public class Contact {
    // Database Constants
    public static final String TABLE_NAME = "contacts";
    public static final String COLUMN_ID = "contact_id";
    public static final String COLUMN_NAME = "contact_name";
    public static final String COLUMN_EMAIL = "contact_email";
    public static final String COLUMN_NUMBER = "contact_number";

    // Variables
    private long id;
    private String name;
    private String email;
    private String number;

    // Constructors
    public Contact() {}
    public Contact(long id, String name, String email, String number) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.number = number;
    }

    public Contact(Cursor cursor) {
         this.id = cursor.getLong(cursor.getColumnIndexOrThrow(Contact.COLUMN_ID));
         this.name = cursor.getString(cursor.getColumnIndexOrThrow(Contact.COLUMN_NAME));
         this.email = cursor.getString(cursor.getColumnIndexOrThrow(Contact.COLUMN_EMAIL));
         this.number = cursor.getString(cursor.getColumnIndexOrThrow(Contact.COLUMN_NUMBER));
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    // SQL Query: Create Table
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NAME + " TEXT, "
                    + COLUMN_EMAIL + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
                    + COLUMN_NUMBER + " TEXT "
                    + ")";
}
