package com.example.app018_countrynames.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app018_countrynames.Models.Country;
import com.example.app018_countrynames.R;

import java.util.ArrayList;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {

    private ArrayList<Country> countries;

    public CountriesAdapter(ArrayList<Country> countries) {
        this.countries = countries;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.country_name);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameView.setText(countries.get(position).name.getCommon());
    }

    @Override
    public int getItemCount() {
        return countries == null ? 0 : countries.size();
    }
}
