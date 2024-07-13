package com.kes.app025_advancedrecyclerviews.activities.MultipleSelectionRecycler;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kes.app025_advancedrecyclerviews.R;
import com.kes.app025_advancedrecyclerviews.models.Planet;
import com.kes.app025_advancedrecyclerviews.repository.JsonRepository;

import java.util.ArrayList;
import java.util.List;

public class MultipleSelectionActivity extends AppCompatActivity {

    Button button;

    RecyclerView recyclerView;
    MultipleAdapter adapter;

    JsonRepository repository;
    List<Planet> planets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_multiple_selection_recycler);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        button = findViewById(R.id.multiple_selection_recycler_button);
        recyclerView = findViewById(R.id.multiple_selection_recycler_view);

        repository = new JsonRepository(getApplicationContext());
        planets = repository.getPlanets();

        adapter = new MultipleAdapter(getApplicationContext(), planets);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getApplicationContext(),
                DividerItemDecoration.VERTICAL
        ));

        recyclerView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Planet> selectedPlanets = adapter.getSelected();
                if (selectedPlanets == null) {
                    return;
                }

                ArrayList<String> names = new ArrayList<>();

                for (Planet planet : selectedPlanets) {
                    names.add(planet.getName());
                }

                Toast.makeText(
                        MultipleSelectionActivity.this,
                        String.join(", ", names),
                        Toast.LENGTH_SHORT
                ).show();

            }
        });
    }
}