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

public class SphereActivity extends AppCompatActivity {

    EditText radius;
    TextView result;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sphere);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        radius = findViewById(R.id.cube_sides_length);
        result = findViewById(R.id.result_view);
        submitBtn = findViewById(R.id.submit_btn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int r = Integer.parseInt(SphereActivity.this.radius.getText().toString());

                double volume = ((double) 4/3) * Math.PI * Math.pow(r, 3);
                DecimalFormat decimalFormat = new DecimalFormat("#0.0000");
                String resultStr = "V = " + decimalFormat.format(volume) + " m^3";

                result.setText(resultStr);
            }
        });



    }
}