package com.example.app018_countrynames;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app018_countrynames.Models.Country;
import com.example.app018_countrynames.Service.GetCountryData;
import com.example.app018_countrynames.adapters.CountriesAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ArrayList<Country> countries;

    private RecyclerView recyclerView;
    private CountriesAdapter countriesAdapter;
    

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

        GetCountries();
    }

    public void GetCountries() {
        GetCountryData getCountryData = RetrofitService.getInstance();
        Call<List<Country>> call = getCountryData.getCountries();

        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response.body() == null) {
                    Toast.makeText(MainActivity.this, "No countries found", Toast.LENGTH_LONG).show();
                    return;
                }

                countries = (ArrayList<Country>) response.body();
                ViewData();
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Error fetching countries", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ViewData() {
        recyclerView = findViewById(R.id.recycler_view);
        countriesAdapter = new CountriesAdapter(countries);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(countriesAdapter);
    }
}