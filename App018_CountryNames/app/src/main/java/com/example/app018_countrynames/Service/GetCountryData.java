package com.example.app018_countrynames.Service;

import com.example.app018_countrynames.Models.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

// Retrofit interface
public interface GetCountryData {

    @GET("v3.1/all?fields=name,capital,region")
    Call<List<Country>> getCountries();

}
