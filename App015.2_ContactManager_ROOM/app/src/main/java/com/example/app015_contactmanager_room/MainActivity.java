package com.example.app015_contactmanager_room;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.app015_contactmanager_room.adapter.ContactsAdapter;
import com.example.app015_contactmanager_room.db.ContactsAppDatabase;
import com.example.app015_contactmanager_room.db.entity.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ContactsAdapter contactsAdapter;
    private ArrayList<Contact> contactList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ContactsAppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact Manager");

        // RecyclerView
        recyclerView = findViewById(R.id.recycler_contacts_view);

        // DB CallBacks
        RoomDatabase.Callback dbCallback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                // Executes when db first created
                Log.i("TAG", "Database has been created!");
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
                // Executes every time app is launched.
                Log.i("TAG", "Database is loaded!");

            }
        };

        // Database
        db = Room.databaseBuilder(
                        getApplicationContext(),
                        ContactsAppDatabase.class,
                        "ContactDB"
                )
                .addCallback(dbCallback)
                .build();

        // All Contacts List Data
        DisplayAllContacts();

        // Contacts List Adapter
        contactsAdapter = new ContactsAdapter(getApplicationContext(), contactList, MainActivity.this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactsAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAndEditContacts(false, null, -1);
            }
        });


    }

    public void addAndEditContacts(final boolean isUpdated, Contact contact, final int position) {
        // Inflate add/update dialog box
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.add_contact, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(view);

        // Declare Views in the dialog
        TextView contactTitle = view.findViewById(R.id.new_contact_title);
        EditText contactName = view.findViewById(R.id.new_contact_name);
        EditText contactEmail = view.findViewById(R.id.new_contact_email);
        EditText contactNumber = view.findViewById(R.id.new_contact_number);

        contactTitle.setText(!isUpdated ? "Add New Contact" : "Edit Contact");

        // Fill-in existing contact info, if contact is being updated.
        if (isUpdated && contact != null) {
            contactName.setText(contact.getName());
            contactEmail.setText(contact.getEmail());
            contactNumber.setText(contact.getNumber());
        }

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(isUpdated ? "Update" : "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isUpdated && contact != null) {
                            DeleteContact(contact, position);
                        } else {
                            dialog.cancel();
                        }
                    }
                });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(contactName.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Please Enter a Name", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                if (isUpdated && contact != null) {
                    UpdateContact(
                            contactName.getText().toString(),
                            contactEmail.getText().toString(),
                            contactNumber.getText().toString(),
                            position
                    );
                } else {
                    CreateContact(
                            contactName.getText().toString(),
                            contactEmail.getText().toString(),
                            contactNumber.getText().toString()
                    );
                }
            }
        });

    }

    private void DeleteContact(Contact contact, int position) {
        contactList.remove(position);
        db.getContactDAO().deleteContact(contact);
        contactsAdapter.notifyItemRemoved(position);
    }

    private void UpdateContact(String name, String email, String number, int position) {
        Contact contact = contactList.get(position);

        contact.setName(name);
        contact.setEmail(email);
        contact.setNumber(number);

        db.getContactDAO().updateContact(contact);
        contactList.set(position, contact);
        contactsAdapter.notifyItemChanged(position);
    }

    private void CreateContact(String name, String email, String number) {
        long id = db.getContactDAO()
                .addContact(
                        new Contact(0, name, email, number)
                );
        Contact contact = db.getContactDAO().getContact(id);

        if (contact != null) {
            contactList.add(0, contact);
            contactsAdapter.notifyItemInserted(0);
        }
    }

    private void DisplayAllContacts() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Background Work
                contactList.addAll(db.getContactDAO().getContacts());

                // Executed after bg work had finished
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        contactsAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }

    // Menu bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}