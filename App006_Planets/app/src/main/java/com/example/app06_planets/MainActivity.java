package com.example.app06_planets;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ListView planetsView;

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

        // 1: Adapter View: ListView
        planetsView = findViewById(R.id.listView);

        // 2: Data source: Planet Array
        ArrayList<Planet> planets = PlanetList.getPlanets();

        // 3: Adapter: acts as a bridge between "data source and the "AdapterView".
        PlanetsAdapter adapter = new PlanetsAdapter(getApplicationContext(), planets);

        planetsView.setAdapter(adapter);

        // Add onClickListener to every item in the list
        planetsView.setOnItemClickListener((parent, view, position, id) -> Toast.makeText(
                getApplicationContext(),
                "Planet Name: " + Objects.requireNonNull(adapter.getItem(position)).getName(),
                Toast.LENGTH_LONG
        ).show());
    }
}

