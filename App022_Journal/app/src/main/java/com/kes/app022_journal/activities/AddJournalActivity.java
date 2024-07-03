package com.kes.app022_journal.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.common.util.Strings;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kes.app022_journal.R;
import com.kes.app022_journal.models.CurrentUser;
import com.kes.app022_journal.models.Journal;

import java.util.Objects;

public class AddJournalActivity extends AppCompatActivity {

    private static final int GALLERY_CODE = 1;
    // Widgets
    private ProgressBar progressBar;
    private EditText titleInput;
    private EditText contentInput;
    private TextView usernameView;
    private ImageView imageView;
    // Buttons
    private Button submitBtn;
    private ImageView addImageBtn;

    // Firebase Auth
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser firebaseUser;
    private CurrentUser currentUser;

    // Firebase Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference journals = db.collection("Journals");
    private StorageReference storageReference;
    private Uri imageURI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_journal_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.post_progress_bar);
        titleInput = findViewById(R.id.post_title_input);
        contentInput = findViewById(R.id.post_content_input);
        usernameView = findViewById(R.id.post_username);

        imageView = findViewById(R.id.post_image);
        addImageBtn = findViewById(R.id.post_image_input);
        submitBtn = findViewById(R.id.post_submit_button);

        currentUser = CurrentUser.getInstance();
        usernameView.setText(currentUser.getUsername());

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {

                } else {

                }
            }
        };

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveJournal();
            }
        });

        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_CODE);
            }
        });


    }

    private void SaveJournal() {
        String title = titleInput.getText().toString().trim();
        String content = contentInput.getText().toString().trim();
        if (Strings.isEmptyOrWhitespace(title) || Strings.isEmptyOrWhitespace(content)) {
            Toast.makeText(AddJournalActivity.this, "Post Title and Content\nshould not be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imageURI == null) {
            Toast.makeText(AddJournalActivity.this, "Please add Image to the post!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // Save image to Firebase Storage
        // ../journal_images/image.png
        StorageReference filepath = storageReference
                .child("journal_images")
                .child("image_" + Timestamp.now().getSeconds());

        filepath.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageURL = uri.toString();

                        Journal newJournal = new Journal(
                                title,
                                content,
                                imageURL,
                                currentUser.getUserID(),
                                currentUser.getUsername(),
                                Timestamp.now()
                        );

                        journals.add(newJournal)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        startActivity(new Intent(
                                                AddJournalActivity.this,
                                                JournalListActivity.class
                                        ));
                                        finish();
                                    }
                                });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(
                                AddJournalActivity.this,
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        Log.e("ERROR", Objects.requireNonNull(e.getMessage()));
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(
                        AddJournalActivity.this,
                        "Error: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.e("ERROR", Objects.requireNonNull(e.getMessage()));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                imageURI = data.getData();
                imageView.setImageURI(imageURI);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuth != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}