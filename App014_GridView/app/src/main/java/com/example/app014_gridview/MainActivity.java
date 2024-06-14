package com.example.app014_gridview;

import android.os.Bundle;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView;

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

        gridView = findViewById(R.id.grid_view);

        ArrayList<GridModel> gridItems = new ArrayList<>();
        gridItems.add(new GridModel("Banana", R.drawable.banana));
        gridItems.add(new GridModel("Black Desert", R.drawable.bdo));
        gridItems.add(new GridModel("Cuphead", R.drawable.cuphead));
        gridItems.add(new GridModel("Destiny 2", R.drawable.destiny2));
        gridItems.add(new GridModel("Slay The Spire", R.drawable.slaythespire));
        gridItems.add(new GridModel("Stellaris", R.drawable.stellaris));
        gridItems.add(new GridModel("War Thunder", R.drawable.warthunder));
        gridItems.add(new GridModel("Terraria", R.drawable.terraria));

        CustomGridAdapter gridAdapter = new CustomGridAdapter(getApplicationContext(), gridItems);
        gridView.setAdapter(gridAdapter);

    }
}