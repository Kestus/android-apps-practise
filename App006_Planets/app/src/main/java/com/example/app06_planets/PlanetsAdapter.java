package com.example.app06_planets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PlanetsAdapter extends ArrayAdapter<Planet> {

    private final Context context;
    private ArrayList<Planet> planets;

    public PlanetsAdapter(Context context, ArrayList<Planet> planets) {
        super(context, R.layout.item_list, planets);
        this.context = context;
        this.planets = planets;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 1 - Get planet object
        Planet planet = getItem(position);

        // 2 - Inflate Layout:
        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(
                    R.layout.item_list,
                    parent,
                    false
            );
            viewHolder.planetName = convertView.findViewById(R.id.planetName);
            viewHolder.moonCount = convertView.findViewById(R.id.moonCount);
            viewHolder.planetImage = convertView.findViewById(R.id.imageView);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            // recycle view
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        assert planet != null;
        viewHolder.planetName.setText(planet.getName());
        viewHolder.moonCount.setText(planet.getStringNumberOfMoons());
        viewHolder.planetImage.setImageResource(planet.getImageRes());

        return result;
    }

    //    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//        // convertView: is a recycled View, that you can reuse,
//        //              to improve performance of listView.
//        if (convertView == null) {
//            // Inflate new View.
//            convertView = LayoutInflater
//                    .from(context)
//                    .inflate(R.layout.item_list, parent, false);
//            // Create new View for this item.
//            holder = new ViewHolder();
//            holder.planetImage = convertView.findViewById(R.id.imageView);
//            holder.planetName = convertView.findViewById(R.id.planetName);
//            holder.moonCount = convertView.findViewById(R.id.moonCount);
//            convertView.setTag(holder);
//        } else {
//            // Recycle previously created View.
//            // getTag returns previously associated ViewHolder object.
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//
//        // Set the data to the view
//        Planet planet = (Planet) getItem(position);
//        holder.planetImage.setImageResource(planet.getImageRes());
//        holder.planetName.setText(planet.getName());
//        holder.moonCount.setText(planet.getStringNumberOfMoons());
//
//        return convertView;
//    }

    private static class ViewHolder {
        // Holds references to the views within an item layout
        TextView planetName;
        TextView moonCount;
        ImageView planetImage;
    }
}
