package com.example.app019_movies.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.app019_movies.BuildConfig;
import com.example.app019_movies.models.Movie;
import com.example.app019_movies.models.RequestResult;
import com.example.app019_movies.service.MovieDataService;
import com.example.app019_movies.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private ArrayList<Movie> movies = new ArrayList<>();
    private MutableLiveData<List<Movie>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public MovieRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Movie>> getMutableLiveData() {
        MovieDataService movieDataService = RetrofitInstance.getService();

        Call<RequestResult> call = movieDataService.getPopularMovies(BuildConfig.API_KEY);

        call.enqueue(new Callback<RequestResult>() {
            @Override
            public void onResponse(Call<RequestResult> call, Response<RequestResult> response) {
                RequestResult result = response.body();

                if (result != null && result.getMovies() != null) {
                    movies = (ArrayList<Movie>) result.getMovies();
                    mutableLiveData.setValue(movies);
                }
            }

            @Override
            public void onFailure(Call<RequestResult> call, Throwable throwable) {
                Log.e("TAG", throwable.toString());
                Toast.makeText(application.getApplicationContext(), "Failed to fetch movies!", Toast.LENGTH_LONG).show();
            }
        });

        return mutableLiveData;
    }
}
