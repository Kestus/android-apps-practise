package com.example.app05_frenchteacher;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    Button blackBtn, greenBtn, purpleBtn, redBtn, yellowBtn;
    HashMap<Button, Integer> btnMap = new HashMap<>(5);

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

        blackBtn = findViewById(R.id.btn_black);
        greenBtn = findViewById(R.id.btn_green);
        purpleBtn = findViewById(R.id.btn_purple);
        redBtn = findViewById(R.id.btn_red);
        yellowBtn = findViewById(R.id.btn_yellow);

        btnMap.put(blackBtn, R.raw.black);
        btnMap.put(greenBtn, R.raw.green);
        btnMap.put(purpleBtn, R.raw.purple);
        btnMap.put(redBtn, R.raw.red);
        btnMap.put(yellowBtn, R.raw.yellow);

        btnMap.forEach((btn, val) -> btn.setOnClickListener(v -> playSound(val)));
    }

    public void playSound(int id) {
        MediaPlayer player = MediaPlayer.create(
                getApplicationContext(),
                id
        );

        player.start();
    }
}