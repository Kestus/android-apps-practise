package com.example.app016_contactmanager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.app016_contactmanager2.adapters.ContactDataAdapter;
import com.example.app016_contactmanager2.databinding.ActivityMainBinding;
import com.example.app016_contactmanager2.db.AppDatabase;
import com.example.app016_contactmanager2.db.entities.Contact;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private ArrayList<Contact> contacts;
    private ContactDataAdapter adapter;

    // Binding
    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandlers clickHandlers;

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

        // Data Binding
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // Click Handlers
        clickHandlers = new MainActivityClickHandlers(getApplicationContext());
        activityMainBinding.setClickHandler(clickHandlers);

        // RecyclerView
//        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView recyclerView = activityMainBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.hasFixedSize();

        // Adapter
        adapter = new ContactDataAdapter();
        recyclerView.setAdapter(adapter);

        // Database
        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "AppDB"
        ).build();

        // Load db contacts data on the background
        LoadContacts();

        // Swipe Handling
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                DeleteContact(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void LoadContacts() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Background Work
                contacts = (ArrayList<Contact>) db.getContactDao().getAllContacts();

                // Execute after BG Work
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setContacts(contacts);
                        adapter.notifyItemRangeChanged(0, contacts.size());
                    }
                });
            }
        });
    }

    private void DeleteContact(int position) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Contact contact = contacts.get(position);
                db.getContactDao().deleteContact(contact);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        contacts.remove(position);
                        adapter.notifyItemRemoved(position);
                    }
                });
            }
        });
    }

    private void AddContact(Contact contact) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                long id = db.getContactDao().insertContact(contact);
                Contact savedContact = db.getContactDao().getContact(id);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        contacts.add(0, contact);
                        adapter.notifyItemInserted(0);
                    }
                });
            }
        });
    }


    public class MainActivityClickHandlers {
        Context context;

        public MainActivityClickHandlers(Context context) {
            this.context = context;
        }

        public void onFABClicked(View view) {
            Intent i = new Intent(MainActivity.this, AddContactActivity.class);
            startActivityForResult(i, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            assert data != null;
            String name = data.getStringExtra("NAME");
            String email = data.getStringExtra("EMAIL");

            Contact contact = new Contact(0, name, email);

            AddContact(contact);
        }
    }
}