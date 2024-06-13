package com.example.app07_volumeareacalc;

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
import java.util.zip.Inflater;

public class ShapesAdapter extends ArrayAdapter<Shape> {

    Context context;
    private ArrayList<Shape> shapeArrayList;

    public ShapesAdapter(Context context, ArrayList<Shape> shapes) {
        super(context, R.layout.grid_item, shapes);
        this.context = context;
        this.shapeArrayList = shapes;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Shape shape = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            // Inflate new View
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(
                    R.layout.grid_item,
                    parent,
                    false
            );

            viewHolder.shapeName = convertView.findViewById(R.id.shapeNameView);
            viewHolder.shapeImage = convertView.findViewById(R.id.shapeImageView);

            convertView.setTag(viewHolder);
        } else {
            // Recycle offscreen View
            viewHolder = (ViewHolder) convertView.getTag();
        }

        assert shape != null;
        viewHolder.shapeName.setText(shape.getShapeName());
        viewHolder.shapeImage.setImageResource(shape.getShapeImageRes());

        return convertView;
    }

    private static class ViewHolder {
        TextView shapeName;
        ImageView shapeImage;
    }

}
