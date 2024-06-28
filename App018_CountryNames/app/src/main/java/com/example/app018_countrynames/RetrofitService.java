package com.example.app018_countrynames;

import com.example.app018_countrynames.Service.GetCountryData;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static Retrofit retrofit = null;
    private static String BASE_URL = "https://restcountries.com/";

    public static GetCountryData getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(GetCountryData.class);
    }
}
