package com.example.app015_contactmanager_room.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.app015_contactmanager_room.db.entity.Contact;

@Database(version = 1, entities = {Contact.class})
public abstract class ContactsAppDatabase extends RoomDatabase {
    // Link the DAO with DB
    public abstract ContactDAO getContactDAO();
}
