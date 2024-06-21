package com.example.app015_contactmanager_room.db.entity;

import android.database.Cursor;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "contacts")
public class Contact {
    // Variables // DB Constraints
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contact_id")
    private long id;
    @ColumnInfo(name = "contact_name")
    private String name;
    @ColumnInfo(name = "contact_email")
    private String email;
    @ColumnInfo(name = "contact_number")
    private String number;

    // Constructors
    @Ignore
    public Contact() {}
    public Contact(long id, String name, String email, String number) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.number = number;
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
}
