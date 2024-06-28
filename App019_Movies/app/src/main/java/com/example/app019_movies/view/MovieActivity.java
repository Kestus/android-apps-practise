package com.example.app019_movies.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.example.app019_movies.R;
import com.example.app019_movies.databinding.ActivityMovieBinding;
import com.example.app019_movies.models.Movie;

public class MovieActivity extends AppCompatActivity {

    private Movie movie;
    private ActivityMovieBinding activityMovieBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        activityMovieBinding = DataBindingUtil.setContentView(
//                this,
//                R.layout.activity_movie
//        );
//
//        Intent i = getIntent();
//        if (i.hasExtra("movie")) {
//            movie = i.getParcelableExtra("movie");
//            activityMovieBinding.setMovie(movie);
////            getSupportActionBar().setTitle(movie.getTitle());
//        }

        activityMovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie);
        Intent i = getIntent();
        if (i != null) {
            movie = i.getParcelableExtra("movie");
            activityMovieBinding.setMovie(movie);
        }

    }
}