package com.kes.app025_advancedrecyclerviews.activities.CardRecycler;

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

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardsViewHolder> {

    Context context;
    List<Planet> planets;

    public CardsAdapter(Context context, List<Planet> planets) {
        this.context = context;
        this.planets = planets;
    }

    @NonNull
    @Override
    public CardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.card_item_planet,
                parent,
                false
        );
        return new CardsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardsViewHolder holder, int position) {
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

    public class CardsViewHolder extends RecyclerView.ViewHolder {

        TextView position;
        TextView name;
        TextView distance;
        TextView velocity;

        public CardsViewHolder(@NonNull View itemView) {
            super(itemView);

            position = itemView.findViewById(R.id.item_position);
            name = itemView.findViewById(R.id.item_name);
            distance = itemView.findViewById(R.id.item_distance);
            velocity = itemView.findViewById(R.id.item_velocity);

        }
    }
}
