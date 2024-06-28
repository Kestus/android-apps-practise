package com.example.app019_movies.service;

import com.example.app019_movies.models.RequestResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDataService {

    // Base URL
    // http://api.themoviedb.org/3/
    //
    // Endpoint
    // movie/popular?api_key={api_key}
    //
    @GET("movie/popular")
    Call<RequestResult> getPopularMovies(@Query("api_key") String apiKey);

}
