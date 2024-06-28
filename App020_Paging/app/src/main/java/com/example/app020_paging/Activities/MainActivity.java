package com.example.app020_paging.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.RequestManager;
import com.example.app020_paging.Adapters.MoviesAdapter;
import com.example.app020_paging.Adapters.MoviesLoadStateAdapter;
import com.example.app020_paging.R;
import com.example.app020_paging.Util.GridSpace;
import com.example.app020_paging.Util.MovieComparator;
import com.example.app020_paging.ViewModel.MovieViewModel;
import com.example.app020_paging.databinding.ActivityMainBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    MovieViewModel mainActivityViewModel;
    ActivityMainBinding binding;
    MoviesAdapter moviesAdapter;

    // Javax
    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        if (BuildConfig.API_KEY == null || BuildConfig.API_KEY.isEmpty()) {
//            Toast.makeText(this, "API Key is missing!", Toast.LENGTH_SHORT).show();
//        }

        moviesAdapter = new MoviesAdapter(
                new MovieComparator(),
                requestManager
        );

        mainActivityViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        initRecyclerViewAndAdapter();

        mainActivityViewModel.moviePagingFlowable.subscribe(moviePagingData -> {
            moviesAdapter.submitData(getLifecycle(), moviePagingData);
        });


    }

    private void initRecyclerViewAndAdapter() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        binding.recyclerViewMovies.setLayoutManager(gridLayoutManager);
        binding.recyclerViewMovies.addItemDecoration(new GridSpace(
                2,
                20,
                true
        ));
        binding.recyclerViewMovies.setAdapter(
                moviesAdapter.withLoadStateFooter(
                        new MoviesLoadStateAdapter(view -> {
                            moviesAdapter.retry();
                        })
                )
        );

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return moviesAdapter.getItemViewType(position) == MoviesAdapter.LOADING_ITEM ? 1 : 2;
            }
        });

    }
}