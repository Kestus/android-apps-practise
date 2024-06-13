package com.example.app04_luckynumber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class SecondActivity extends AppCompatActivity {
    TextView luckyNumberText;
    Button shareBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        luckyNumberText = findViewById(R.id.luckyNumber);
        shareBTN = findViewById(R.id.shareBtn);

        // Receive data from the main activity
        Intent intent = getIntent();
        String userName = intent.getStringExtra("name");
        int luckyNumber = generateRandomNumber();
        luckyNumberText.setText(String.valueOf(luckyNumber));

        shareBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareData(userName, luckyNumber);
            }
        });


    }

    public int generateRandomNumber() {
        Random generator = new Random();
        int upperLimit = 1000;
        return generator.nextInt(upperLimit);
    }

    public void shareData(String username, int number) {
        // Implicit Intent
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        // Additional info
        intent.putExtra(Intent.EXTRA_SUBJECT, username + "'s lucky number");
        intent.putExtra(Intent.EXTRA_TEXT, "My lucky number is: " + number);

        startActivity(Intent.createChooser(intent, "Choose a Platform"));
    }
}