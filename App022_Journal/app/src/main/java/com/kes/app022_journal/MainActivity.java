package com.kes.app022_journal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.common.util.Strings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.kes.app022_journal.activities.JournalListActivity;
import com.kes.app022_journal.activities.SignUpActivity;
import com.kes.app022_journal.models.CurrentUser;
import com.kes.app022_journal.models.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Widgets
    private EditText inputEmail;
    private EditText inputPassword;
    private Button loginBtn;
    private Button registerBtn;

    // Firebase Authentication
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser firebaseUser;
    private CurrentUser currentUser;

    // Firestore DB
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference users = db.collection("Users");

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

        // Authentication
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    // User already logged-in
                    startActivity(new Intent(getApplicationContext(), JournalListActivity.class));
                } else {

                }
            }
        };
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(authStateListener);


        loginBtn = findViewById(R.id.button_login);
        registerBtn = findViewById(R.id.button_register);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        // User Login
        inputEmail = findViewById(R.id.input_login_email);
        inputPassword = findViewById(R.id.input_login_pw);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                LoginUserWithEmailPassword(email, password);
            }
        });
    }

    private void LoginUserWithEmailPassword(String email, String password) {
        if (Strings.isEmptyOrWhitespace(email) || Strings.isEmptyOrWhitespace(password)) {
            Toast.makeText(getApplicationContext(), "Email and Password required!", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        firebaseUser = firebaseAuth.getCurrentUser();

                        assert firebaseUser != null;
                        Log.i("LOGIN", firebaseUser.getUid());

                        String userID = firebaseUser.getUid();
                        users.whereEqualTo("userID", userID)
                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                        if (error != null) {
                                            Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                                        }

                                        if (value == null) {
                                            Toast.makeText(MainActivity.this, "Firebase user is not in the database!", Toast.LENGTH_LONG).show();
                                            return;
                                        }

                                        List<DocumentSnapshot> documents = value.getDocuments();
                                        if (documents.size() != 1) {
                                            Toast.makeText(MainActivity.this, "Unexpected amount of users found!", Toast.LENGTH_LONG).show();
                                            return;
                                        }

                                        User user = documents.get(0).toObject(User.class);
                                        assert user != null: "could not convert document to User class" ;
                                        CurrentUser.setInstance(user);
                                        currentUser = CurrentUser.getInstance();

                                        // Redirect to JournalListActivity after successful login
                                        startActivity(new Intent(getApplicationContext(), JournalListActivity.class));
                                    }
                                });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}