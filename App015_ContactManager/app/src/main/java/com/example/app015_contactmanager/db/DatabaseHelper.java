package com.example.app015_contactmanager.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.app015_contactmanager.db.entity.Contact;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contact_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contact.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contact.TABLE_NAME);
        onCreate(db);
    }

    // Insert Data into Database
    public long insertContact(String name, String email, String number) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(Contact.COLUMN_NAME, name);
            values.put(Contact.COLUMN_EMAIL, email);
            values.put(Contact.COLUMN_NUMBER, number);

            return db.insert(Contact.TABLE_NAME, null, values);
        }
    }

    // Get Data from Database
    public Contact getContact(long id) {
        try (SQLiteDatabase db = this.getReadableDatabase()) {
            try (Cursor cursor = db.query(
                        Contact.TABLE_NAME,
                        new String[]{
                                Contact.COLUMN_ID,
                                Contact.COLUMN_NAME,
                                Contact.COLUMN_EMAIL,
                                Contact.COLUMN_NUMBER
                        },
                        Contact.COLUMN_ID + "=?",
                        new String[]{
                                String.valueOf(id)
                        },
                        null,
                        null,
                        null,
                        null
                )) {
                assert cursor != null;
                cursor.moveToFirst();

                return new Contact(cursor);
            }
        }
    }

    // Get all Contacts from Database
    public ArrayList<Contact> getAllContacts() {
        try (SQLiteDatabase db = this.getReadableDatabase()) {

            ArrayList<Contact> contactList = new ArrayList<>();

            String selectQuery = "SELECT * FROM " + Contact.TABLE_NAME
                    + " ORDER BY " + Contact.COLUMN_ID + " DESC";

            try (Cursor cursor = db.rawQuery(selectQuery, null)) {
                if (!cursor.moveToFirst()) {
                    return contactList;
                }

                do {
                    contactList.add(new Contact(cursor));
                } while (cursor.moveToNext());
            }

            return contactList;
        }
    }

    // Update Contact columns in Database
    public long updateContact(Contact contact) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(Contact.COLUMN_NAME, contact.getName());
            values.put(Contact.COLUMN_EMAIL, contact.getEmail());
            values.put(Contact.COLUMN_NUMBER, contact.getNumber());

            return db.update(
                    Contact.TABLE_NAME,
                    values,
                    Contact.COLUMN_ID + " =? ",
                    new String[]{String.valueOf(contact.getId())}
            );
        }

    }

    // Delete Contact from Database
    public void deleteContact(Contact contact) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.delete(
                    Contact.TABLE_NAME,
                    Contact.COLUMN_ID + " =? ",
                    new String[]{String.valueOf(contact.getId())}
            );
        }
    }
}
