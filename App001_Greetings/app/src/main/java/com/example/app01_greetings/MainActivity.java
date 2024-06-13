package com.example.app01_greetings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView editTitle;
    EditText editName;
    Button submitBTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTitle = findViewById(R.id.title);
        editName = findViewById(R.id.inputName);
        submitBTN = findViewById(R.id.greetBtn);

        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = editName.getText().toString();
                Toast.makeText(
                        MainActivity.this,
                        "Welcome " + inputName + " to the app!",
                        Toast.LENGTH_LONG
                ).show();
            }
        });


    }
}