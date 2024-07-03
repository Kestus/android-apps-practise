package com.kes.app021_firestore;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // Saving
    // Reading
    // Listening
    // Updating
    // Deleting
    // Saving Custom Objects
    // Saving multiple documents
    // Retrieving multiple documents

    private EditText inputName;
    private EditText inputEmail;
    private Button btnSubmit;

    private TextView textView;
    private Button btnRead;
    private Button btnUpdate;
    private Button btnDelete;


    // Firebase Firestore
    private FirebaseFirestore firebase = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = firebase.collection("Users");
    private DocumentReference documentReference = collectionReference.document("test");
    

    // KEYs
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";

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

        inputName = findViewById(R.id.input_name);
        inputEmail = findViewById(R.id.input_email);
        btnSubmit = findViewById(R.id.button_submit);
        textView = findViewById(R.id.result);

        // Saving Data -----------------------------------------------------------------------------
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                User user = new User(name, email);

                // Save data to specified document
//                firebase.collection("Users")
//                        .document("test")
//                        .set(user)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//                            }
//                        });

                // Save data to new document
                collectionReference.add(user);
            }
        });

        // Reading specified document --------------------------------------------------------------
//        btnRead = findViewById(R.id.button_read);
//        btnRead.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ReadDataFromFirestore();
//            }
//        });

        // Updating Fields -------------------------------------------------------------------------
        btnUpdate = findViewById(R.id.button_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();

                Map<String, Object> data = new HashMap<>();
                data.put(KEY_NAME, name);
                data.put(KEY_EMAIL, email);

                documentReference.update(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(MainActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


        // Deleting data ---------------------------------------------------------------------------
        btnDelete = findViewById(R.id.button_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              // Delete fields
                documentReference.update(KEY_NAME, FieldValue.delete());
                documentReference.update(KEY_EMAIL, FieldValue.delete());

                // Delete Document
                documentReference.delete();
            }
        });

        // Reading Multiple documents
        btnRead = findViewById(R.id.button_read);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllDocuments();
            }
        });
    }

    private void getAllDocuments() {
        collectionReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String dataString = "";

                        // Log retrieved users data
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                            User user = snapshot.toObject(User.class);
                            dataString += "Name: " + user.name + " Email: " + user.email + "\n";
                            Log.i("TAG", dataString);
                        }

                        textView.setText(dataString);
                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();

        // Listening to data change ----------------------------------------------------------------
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(
                    @Nullable DocumentSnapshot value,
                    @Nullable FirebaseFirestoreException error
            ) {
//                if (error != null ) {
//                    Toast.makeText(MainActivity.this, "Change Listener Error", Toast.LENGTH_SHORT).show();
//                    Log.e("TAG", Objects.requireNonNull(error.getMessage()));
//                }
//                if (value != null && value.exists()) {
////                    String name = value.getString(KEY_NAME);
////                    String email = value.getString(KEY_EMAIL);
//
//                    User user = value.toObject(User.class);
//                    assert user != null;
//                    String result = "Name: " + user.name + "\n" + "Email: " + user.email;
//                    textView.setText(result);
//                }

                getAllDocuments();
            }
        });
    }

}