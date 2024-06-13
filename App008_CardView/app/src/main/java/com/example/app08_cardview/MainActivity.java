package com.example.app08_cardview;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // 1 -- Data
    ArrayList<GameModel> listOfGames;

    // 2 -- Adapter View
    RecyclerView recyclerView;

    // 3 -- Adapter
    GameAdapter adapter;

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
        listOfGames = new ArrayList<>();
        listOfGames.add(new GameModel("Banana", R.drawable.banana));
        listOfGames.add(new GameModel("Black Desert", R.drawable.bdo));
        listOfGames.add(new GameModel("Cuphead", R.drawable.cuphead));
        listOfGames.add(new GameModel("Destiny 2", R.drawable.destiny2));
        listOfGames.add(new GameModel("Slay The Spire", R.drawable.slaythespire));
        listOfGames.add(new GameModel("Stellaris", R.drawable.stellaris));
        listOfGames.add(new GameModel("War Thunder", R.drawable.warthunder));
        listOfGames.add(new GameModel("Terraria", R.drawable.terraria));

        adapter = new GameAdapter(getApplicationContext(), listOfGames);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false
        );

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}