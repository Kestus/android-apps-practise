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

public class CylinderActivity extends AppCompatActivity {

    EditText height;
    EditText radius;
    Button submitBtn;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cylinder);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        height = findViewById(R.id.cylinder_height_input);
        radius = findViewById(R.id.cylinder_radius_input);
        result = findViewById(R.id.result_view);
        submitBtn = findViewById(R.id.submit_btn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double h = Double.parseDouble(height.getText().toString());
                double r = Double.parseDouble(radius.getText().toString());
                double volume = Math.PI * r*r * h;

                String resultStr = "V = " + new DecimalFormat("#0.0000").format(volume);
                result.setText(resultStr);
            }
        });


    }
}