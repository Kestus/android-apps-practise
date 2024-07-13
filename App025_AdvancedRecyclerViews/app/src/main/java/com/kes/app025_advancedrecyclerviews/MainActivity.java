package com.kes.app025_advancedrecyclerviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kes.app025_advancedrecyclerviews.activities.CardRecycler.CardRecyclerActivity;
import com.kes.app025_advancedrecyclerviews.activities.MultipleSelectionRecycler.MultipleSelectionActivity;
import com.kes.app025_advancedrecyclerviews.activities.MultipleViewRecycler.MultipleViewTypeActivity;
import com.kes.app025_advancedrecyclerviews.activities.SingleSelectionRecycler.SingleRecyclerActivity;

public class MainActivity extends AppCompatActivity {

    Button btnCardRecycler;
    Button btnSingleSelectRecycler;
    Button btnMultipleSelectionRecycler;
    Button btnMultipleViewRecycler;

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

        btnCardRecycler = findViewById(R.id.button_card_recycler);
        btnCardRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CardRecyclerActivity.class);
                startActivity(i);
            }
        });

        btnSingleSelectRecycler = findViewById(R.id.button_single_selection_recycler);
        btnSingleSelectRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SingleRecyclerActivity.class);
                startActivity(i);
            }
        });

        btnMultipleSelectionRecycler = findViewById(R.id.button_multiple_selection_recycler);
        btnMultipleSelectionRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MultipleSelectionActivity.class);
                startActivity(i);
            }
        });

        btnMultipleViewRecycler = findViewById(R.id.button_multiple_view_recycler);
        btnMultipleViewRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MultipleViewTypeActivity.class);
                startActivity(i);
            }
        });



    }
}