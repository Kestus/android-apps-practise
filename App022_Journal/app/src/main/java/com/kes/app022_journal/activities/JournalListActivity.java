package com.kes.app022_journal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.kes.app022_journal.MainActivity;
import com.kes.app022_journal.R;
import com.kes.app022_journal.adapters.JournalRecyclerAdapter;
import com.kes.app022_journal.models.CurrentUser;
import com.kes.app022_journal.models.Journal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JournalListActivity extends AppCompatActivity {

    // Widgets
    private RecyclerView recyclerView;
    private TextView noPostsView;
    MaterialToolbar toolbar;

    // Auth
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser firebaseUser;

    // Database
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference journalsDB = db.collection("Journals");
    private StorageReference storageReference;
    private List<Journal> posts;

    private JournalRecyclerAdapter journalRecyclerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_journal_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        // Widgets
        noPostsView = findViewById(R.id.list_no_posts);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // Posts
        posts = new ArrayList<>();

        // Toolbar menu
        toolbar = findViewById(R.id.appbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return HandleItemClick(item);

            }
        });
    }

    private boolean HandleItemClick(MenuItem item) {
        if (item.getItemId() == R.id.menu_action_add) {
            // Redirect to add journal activity
            if (firebaseUser != null && firebaseAuth != null) {
                startActivity(new Intent(getApplicationContext(), AddJournalActivity.class));
                return true;
            }
        } else if (item.getItemId() == R.id.menu_action_sign_out) {
            // Logout and redirect to main activity
            if (firebaseUser != null && firebaseAuth != null) {
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            }
        }

        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        journalsDB.whereEqualTo("userID", firebaseUser.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            noPostsView.setVisibility(TextView.VISIBLE);
                            return;
                        }

                        // Add retrieved documents to array of posts
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                posts.add(document.toObject(Journal.class));
                        }

                        // RecyclerView
                        journalRecyclerAdapter = new JournalRecyclerAdapter(
                                getApplicationContext(),
                                posts
                        );
                        recyclerView.setAdapter(journalRecyclerAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(JournalListActivity.this, "Failed to load Posts", Toast.LENGTH_SHORT).show();
                        Log.e("ERROR", Objects.requireNonNull(e.getMessage()));
                    }
                });

    }
}