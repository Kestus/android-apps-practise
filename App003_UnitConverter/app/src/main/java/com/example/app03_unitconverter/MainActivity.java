package com.example.app03_unitconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText inputField;
    Button submitButton;
    TextView textView;
    double factor = 2.20462;

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

        submitButton = findViewById(R.id.submit_button);
        inputField = findViewById(R.id.text_input);
        textView = findViewById(R.id.textView);
        textView.setText("0");


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = inputField.getText().toString();
                double kilos = Double.parseDouble(input);
                double pounds = kilos * factor;
                DecimalFormat decimalFormat = new DecimalFormat("#.00000");
                String stringPounds = decimalFormat.format(pounds);
                textView.setText(String.valueOf(pounds));
            }
        });
    }
}