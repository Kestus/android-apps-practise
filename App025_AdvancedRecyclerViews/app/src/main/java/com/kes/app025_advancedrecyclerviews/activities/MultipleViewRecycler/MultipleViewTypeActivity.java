package com.kes.app025_advancedrecyclerviews.activities.MultipleViewRecycler;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kes.app025_advancedrecyclerviews.R;
import com.kes.app025_advancedrecyclerviews.models.Planet;
import com.kes.app025_advancedrecyclerviews.repository.JsonRepository;

import java.util.List;

public class MultipleViewTypeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MultipleViewTypeAdapter adapter;

    private JsonRepository repository;
    private List<Planet> planets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_multiple_view_type);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        repository = new JsonRepository(getApplicationContext());
        planets = repository.getPlanets();

        recyclerView = findViewById(R.id.multiple_view_type_recycler);
        adapter = new MultipleViewTypeAdapter(getApplicationContext(), planets);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

    }
}