package com.example.app020_paging.Api;

import com.example.app020_paging.Models.Response;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("movie/popular")
    Single<Response> getMoviesByPage(@Query("page") int page);
}
