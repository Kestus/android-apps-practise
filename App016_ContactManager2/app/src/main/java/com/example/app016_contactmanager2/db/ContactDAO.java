package com.example.app016_contactmanager2.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.app016_contactmanager2.db.entities.Contact;

import java.util.List;

@Dao
public interface ContactDAO {

    @Insert
    long insertContact(Contact contact);

    @Delete
    void deleteContact(Contact contact);

    @Update
    void updateContact(Contact contact);

    @Query("SELECT * FROM contact_table ORDER BY id DESC")
    List<Contact> getAllContacts();

    @Query("SELECT * FROM contact_table WHERE id == :contactID")
    Contact getContact(long contactID);
}
