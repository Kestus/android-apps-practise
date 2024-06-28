package com.example.app019_movies.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit instance = null;
    private static String BASE_URL = "https://api.themoviedb.org/3/";


    public static  MovieDataService getService() {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return instance.create(MovieDataService.class);
    }


}
