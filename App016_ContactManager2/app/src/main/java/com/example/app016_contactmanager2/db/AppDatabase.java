package com.example.app016_contactmanager2.db;

import androidx.room.RoomDatabase;

import com.example.app016_contactmanager2.db.entities.Contact;

@androidx.room.Database(version = 1, entities = {Contact.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDAO getContactDao();
}
