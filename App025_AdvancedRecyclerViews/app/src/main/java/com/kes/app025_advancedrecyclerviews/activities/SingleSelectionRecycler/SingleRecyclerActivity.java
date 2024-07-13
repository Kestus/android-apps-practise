package com.kes.app025_advancedrecyclerviews.activities.SingleSelectionRecycler;

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

import java.util.List;

public class SingleRecyclerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button button;

    private JsonRepository repository;
    private List<Planet> planets;

    private SingleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_single_selection_recycler);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.single_selection_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getApplicationContext(),
                LinearLayoutManager.VERTICAL
        ));

        button = findViewById(R.id.single_selection_recycler_button);


        repository = new JsonRepository(getApplicationContext());
        planets = repository.getPlanets();

        adapter = new SingleAdapter(getApplicationContext(), planets);
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Planet selectedPlanet = adapter.getSelected();
                if (selectedPlanet != null) {
                    Toast.makeText(
                            SingleRecyclerActivity.this,
                            selectedPlanet.getName(),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

    }



}