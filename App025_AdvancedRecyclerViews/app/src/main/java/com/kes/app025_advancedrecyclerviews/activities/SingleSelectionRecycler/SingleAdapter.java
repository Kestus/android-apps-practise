package com.kes.app025_advancedrecyclerviews.activities.SingleSelectionRecycler;

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

import java.util.List;

public class SingleAdapter extends RecyclerView.Adapter<SingleAdapter.SingleViewHolder> {

    Context context;
    List<Planet> planets;

    private int checkPosition;
    private SingleViewHolder checkedHolder;

    public SingleAdapter(Context context, List<Planet> planets) {
        this.context = context;
        this.planets = planets;
        this.checkPosition = -1;
        this.checkedHolder = null;
    }

    @NonNull
    @Override
    public SingleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.selection_recycler_item,
                parent,
                false
        );
        return new SingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleViewHolder holder, int position) {
        holder.bind(planets.get(position));
    }

    @Override
    public int getItemCount() {
        return planets.size();
    }


    class SingleViewHolder extends RecyclerView.ViewHolder {

        ImageView checkSign;
        TextView name;
        TextView distance;
        TextView velocity;

        public SingleViewHolder(@NonNull View itemView) {
            super(itemView);

            checkSign = itemView.findViewById(R.id.item_check_sign);
            name = itemView.findViewById(R.id.item_name);
            distance = itemView.findViewById(R.id.item_distance);
            velocity = itemView.findViewById(R.id.item_velocity);
        }

        void bind(Planet planet) {
            if (checkPosition != getAdapterPosition()) {
                checkSign.setVisibility(View.INVISIBLE);
            }

            name.setText(planet.getName());
            distance.setText("Distance: " + planet.getDistance() + " Mil.Kilometers");
            velocity.setText("Velocity: " + planet.getVelocity() + " ml/s.");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkPosition == getAdapterPosition()) {
                        checkSign.setVisibility(View.INVISIBLE);
                        checkPosition = -1;
                        checkedHolder = null;
                    } else {
                        if (checkedHolder != null) {
                            checkedHolder.checkSign.setVisibility(View.INVISIBLE);
                        }
                        checkSign.setVisibility(View.VISIBLE);
                        checkPosition = getAdapterPosition();
                        checkedHolder = getHolder();
                    }
                }
            });
        }

        private SingleViewHolder getHolder() {
            return this;
        }
    }

    public Planet getSelected() {
        return checkPosition >= 0 ? planets.get(checkPosition) : null;
    }
}
