package com.example.app020_paging.Api;

import androidx.annotation.NonNull;

import com.example.app020_paging.BuildConfig;
import com.example.app020_paging.Models.Response;

import io.reactivex.rxjava3.core.Single;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    static ApiInterface apiInterface;
    public static String BASE_URL = "https://api.themoviedb.org/3/";

    // Retrofit instance
    public static ApiInterface getApiInterface() {
        if (apiInterface == null) {
            OkHttpClient.Builder client = getBuilder();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();

            apiInterface = retrofit.create(ApiInterface.class);
        }

        return apiInterface;
    }

    private static OkHttpClient.Builder getBuilder() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(chain -> {
            Request originalRequest = chain.request();
            HttpUrl originalHttpUrl = originalRequest.url();
            HttpUrl newUrl = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.API_KEY)
                    .build();

            Request request = originalRequest.newBuilder()
                    .url(newUrl)
                    .build();
            return chain.proceed(request);
        });
        return client;
    }
}
