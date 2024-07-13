package com.kes.app025_advancedrecyclerviews.activities.MultipleSelectionRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kes.app025_advancedrecyclerviews.R;
import com.kes.app025_advancedrecyclerviews.models.Planet;

import java.util.ArrayList;
import java.util.List;

public class MultipleAdapter extends RecyclerView.Adapter<MultipleAdapter.MultipleViewHolder> {

    private Context context;
    private List<Planet> planets;

//    private ArrayList<Integer> selectedPositions;

    public MultipleAdapter(Context context, @NonNull List<Planet> planets) {
        this.context = context;
        this.planets = planets;

//        selectedPositions = new ArrayList<>();
    }

    @NonNull
    @Override
    public MultipleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.selection_recycler_item,
                parent,
                false
        );
        return new MultipleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultipleViewHolder holder, int position) {
        holder.bind(planets.get(position));
    }

    @Override
    public int getItemCount() {
        return planets.size();
    }

    class MultipleViewHolder extends RecyclerView.ViewHolder {

        ImageView checkSign;
        TextView name;
        TextView distance;
        TextView velocity;

        public MultipleViewHolder(@NonNull View itemView) {
            super(itemView);

            checkSign = itemView.findViewById(R.id.item_check_sign);
            name = itemView.findViewById(R.id.item_name);
            distance = itemView.findViewById(R.id.item_distance);
            velocity = itemView.findViewById(R.id.item_velocity);
        }

        void bind(Planet planet) {
            name.setText(planet.getName());
            distance.setText("Distance: " + planet.getDistance() + " Mil.Kilometers");
            velocity.setText("Velocity: " + planet.getVelocity() + " ml/s.");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (!selectedPositions.contains(getAdapterPosition())) {
//                        selectedPositions.add(getAdapterPosition());
//                        checkSign.setVisibility(View.VISIBLE);
//                    } else {
                        // Without casting to Integer, adapter position treated like index of
                        // item in the array, instead of object in array.
                        // Causing null pointer exception when removing position larger than
                        // current array size.
//                        selectedPositions.remove((Integer) getAdapterPosition());
//                        checkSign.setVisibility(View.INVISIBLE);
//                    }

                    if (!planet.isChecked()) {
                        planet.setChecked(true);
                        checkSign.setVisibility(View.VISIBLE);
                    } else {
                        planet.setChecked(false);
                        checkSign.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }

    public List<Planet> getSelected() {
        ArrayList<Planet> selectedPlanets = new ArrayList<>();
//        for (Integer position : selectedPositions) {
//            selectedPlanets.add(planets.get(position));
//        }

        for (Planet planet : planets) {
            if (planet.isChecked()) {
                selectedPlanets.add(planet);
            }
        }

        return selectedPlanets;
    }

}
