package com.example.app08_recyclerview;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    RecyclerView recyclerView;
    VaccineModel[] listOfVaccines;

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

        recyclerView = findViewById(R.id.recycler_view);
        listOfVaccines = new VaccineModel[] {
                new VaccineModel("Measles", R.drawable.vaccine1),
                new VaccineModel("Tetanus", R.drawable.vaccine2),
                new VaccineModel("Rotavirus", R.drawable.vaccine3),
                new VaccineModel("Cholera", R.drawable.vaccine4),
                new VaccineModel("Polio", R.drawable.vaccine5),
                new VaccineModel("Pneumococcal", R.drawable.vaccine6),
                new VaccineModel("Influenza", R.drawable.vaccine7)
        };

        // Adapter
        RecyclerAdapter adapter = new RecyclerAdapter(listOfVaccines);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.setAdapter(adapter);

        // Click Events handling
        adapter.setClickListener(this);
    }

    @Override
    public void onClick(View v, int position) {
        Toast.makeText(
                getApplicationContext(),
                "Vaccine name: " + listOfVaccines[position].getTitle(),
                Toast.LENGTH_SHORT
        ).show();
    }
}