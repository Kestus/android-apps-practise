package com.kes.app022_journal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kes.app022_journal.R;
import com.kes.app022_journal.models.CurrentUser;
import com.kes.app022_journal.models.User;

public class SignUpActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private Button submitBtn;

    // Firebase Authentication
    // Firebase Auth requires Google Account on the device/emulator
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    // Firebase DB
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference users = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etUsername = findViewById(R.id.input_login_username);
        etEmail = findViewById(R.id.input_login_email);
        etPassword = findViewById(R.id.input_login_pw);
        submitBtn = findViewById(R.id.button_login);

        // Authentication
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();

                if (currentUser != null) {
                     // User already logged-in
                }
            }
        };

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etEmail.getText().toString())
                        && !TextUtils.isEmpty(etPassword.getText().toString())) {
                    String email = etEmail.getText().toString().trim();
                    String password = etPassword.getText().toString().trim();
                    String username = etUsername.getText().toString().trim();
                    CreateUser(email, password, username);
                } else {
                    Toast.makeText(
                            SignUpActivity.this,
                            "Email and Password are Required!",
                            Toast.LENGTH_SHORT
                            ).show();
                }
            }
        });
    }

    private void CreateUser(String email, String password, String username) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Create new user
                            currentUser = firebaseAuth.getCurrentUser();
                            assert currentUser != null;

                            User newUser = new User();
                            newUser.setUsername(
                                    !Strings.isEmptyOrWhitespace(username) ?
                                            username :
                                            currentUser.getUid()
                            );
                            newUser.setEmail(email);
                            newUser.setUserID(currentUser.getUid());

                            // Save to DB
                            SaveUser(newUser);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void SaveUser(User newUser) {
        users.add(newUser)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        documentReference.get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.getResult().exists()) {
                                            // If User is registered successfully
                                            // redirect to AddJournal Activity
                                            Intent i = new Intent(
                                                    SignUpActivity.this,
                                                    AddJournalActivity.class
                                            );

                                            // Serialize User to Gson
                                            User user = task.getResult().toObject(User.class);

                                            // Set CurrentUser instance
                                            assert user != null;
                                            CurrentUser.setInstance(user);

                                            startActivity(i);
                                        }
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}