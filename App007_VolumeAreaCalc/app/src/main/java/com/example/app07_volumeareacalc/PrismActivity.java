package com.example.app07_volumeareacalc;

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

public class PrismActivity extends AppCompatActivity {

    EditText height;
    EditText width;
    EditText length;
    Button submitBtn;
    TextView resultView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prism);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        height = findViewById(R.id.prism_height_input);
        width = findViewById(R.id.prism_width_input);
        length = findViewById(R.id.prism_length_input);
        resultView = findViewById(R.id.result_view);
        submitBtn = findViewById(R.id.submit_btn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double h = Double.parseDouble(height.getText().toString());
                double w = Double.parseDouble(width.getText().toString());
                double l = Double.parseDouble(length.getText().toString());

                double volume = h * w * l;
                String result = "V = " + new DecimalFormat("#0.0000").format(volume);
                resultView.setText(result);
            }
        });
    }
}