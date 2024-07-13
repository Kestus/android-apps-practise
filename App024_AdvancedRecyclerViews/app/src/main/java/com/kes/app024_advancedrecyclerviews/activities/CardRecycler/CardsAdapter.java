package com.kes.app024_advancedrecyclerviews.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kes.app024_advancedrecyclerviews.R;
import com.kes.app024_advancedrecyclerviews.models.Planet;

import java.util.List;

public class PlanetsAdapter extends RecyclerView.Adapter<PlanetsAdapter.PlanetsViewHolder> {

    Context context;
    List<Planet> planets;

    public PlanetsAdapter(Context context, List<Planet> planets) {
        this.context = context;
        this.planets = planets;
    }

    @NonNull
    @Override
    public PlanetsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.recycler_view_item,
                parent,
                false
        );
        return new PlanetsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanetsViewHolder holder, int position) {
        Planet planet = planets.get(position);
        holder.position.setText(planet.getPosition());
        holder.name.setText(planet.getName());
        holder.distance.setText("Distance: " + planet.getDistance() + " Mil.Kilometers");
        holder.velocity.setText("Velocity: " + planet.getVelocity() + " ml/s.");
    }

    @Override
    public int getItemCount() {
        return planets.size();
    }

    public class PlanetsViewHolder extends RecyclerView.ViewHolder {

        TextView position;
        TextView name;
        TextView distance;
        TextView velocity;

        public PlanetsViewHolder(@NonNull View itemView) {
            super(itemView);

            position = itemView.findViewById(R.id.item_position);
            name = itemView.findViewById(R.id.item_title);
            distance = itemView.findViewById(R.id.item_distance);
            velocity = itemView.findViewById(R.id.item_velocity);

        }


    }
}
