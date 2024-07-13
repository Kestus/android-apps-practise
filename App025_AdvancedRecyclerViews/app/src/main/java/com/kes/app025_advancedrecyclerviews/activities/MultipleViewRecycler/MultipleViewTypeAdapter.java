package com.kes.app025_advancedrecyclerviews.activities.MultipleViewRecycler;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kes.app025_advancedrecyclerviews.R;
import com.kes.app025_advancedrecyclerviews.models.Planet;

import java.util.List;

public class MultipleViewTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Planet> planets;

    private int planetViewType;
    private int starViewType;

    public MultipleViewTypeAdapter(Context context, @NonNull List<Planet> planets) {
        this.context = context;
        this.planets = planets;

        planetViewType = R.layout.card_item_planet;
        starViewType = R.layout.card_item_star;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Return ViewHolder depending on viewType.
        if (viewType == planetViewType) {
            View view = LayoutInflater.from(context).inflate(
                    R.layout.card_item_planet,
                    parent,
                    false
            );
            return new PlanetViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(
                    R.layout.card_item_star,
                    parent,
                    false
            );
            return new StarViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        // Decide what viewType to use for item in position.
//        int type = getItemViewType(position);
//        Planet item = planets.get(position);
//        // If item is a planet.
//        if (type == planetViewType) {
//            // Use bind from PlanetViewHolder.
//            ((PlanetViewHolder) holder).bind(item);
//        } else {
//            // Else use bind from StarViewHolder.
//            ((StarViewHolder) holder).bind(item);
//        }

        Planet item = planets.get(position);
        // Decide what viewType to use for item in position.
        if (holder instanceof PlanetViewHolder) {
            // If holder is a PlanetViewHolder
            // use bind from PlanetViewHolder.
            ((PlanetViewHolder) holder).bind(item);
        } else if (holder instanceof StarViewHolder) {
            // Else if holder is a StarViewHolder
            // use bind from StarViewHolder.
            ((StarViewHolder) holder).bind(item);
        } else {
            throw new RuntimeException("Unexpected subtype of ViewHolder");
        }
    }

    @Override
    public int getItemCount() {
        return planets.size();
    }

    @Override
    public int getItemViewType(int position) {
        // If item's distance is 0, it's a star, otherwise it's a planet.
        Planet item = planets.get(position);
        int distance = Integer.parseInt(item.getDistance());
        return distance == 0 ? starViewType : planetViewType;
    }

    class PlanetViewHolder extends RecyclerView.ViewHolder {
        TextView position;
        TextView name;
        TextView distance;
        TextView velocity;

        public PlanetViewHolder(@NonNull View itemView) {
            super(itemView);

            position = itemView.findViewById(R.id.item_position);
            name = itemView.findViewById(R.id.item_name);
            distance = itemView.findViewById(R.id.item_distance);
            velocity = itemView.findViewById(R.id.item_velocity);
        }

        public void bind(Planet planet) {
            position.setText(planet.getPosition());
            name.setText(planet.getName());
            distance.setText("Distance: " + planet.getDistance() + " Mil.Kilometers");
            velocity.setText("Velocity: " + planet.getVelocity() + " ml/s.");
        }
    }

    class StarViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView description;

        public StarViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_name);
            description = itemView.findViewById(R.id.item_description);
        }

        public void bind(Planet star) {
            name.setText(star.getName());
            description.setText(star.getDescription());
        }
    }


}
