package com.example.app015_contactmanager_room.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.app015_contactmanager_room.db.entity.Contact;

import java.util.List;

@Dao
public interface ContactDAO {

    @Insert
    long addContact(Contact contact);

    @Update
    void updateContact(Contact contact);

    @Delete
    void deleteContact(Contact contact);

    @Query("SELECT * FROM contacts")
    List<Contact> getContacts();

    @Query("SELECT * FROM contacts WHERE contact_id == :contactId")
    Contact getContact(long contactId);
}
