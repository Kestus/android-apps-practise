package com.example.app014_gridview;

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

public class CustomGridAdapter extends ArrayAdapter<GridModel> {

    public CustomGridAdapter(@NonNull Context context, ArrayList<GridModel> gridItems) {
        super(context, 0, gridItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View gridItemView = convertView;
        if (gridItemView == null) {
            gridItemView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.grid_card_item, parent, false);
        }

        GridModel gridModel = getItem(position);
        TextView textView = gridItemView.findViewById(R.id.grid_text_view);
        ImageView imageView = gridItemView.findViewById(R.id.grid_image_view);

        assert gridModel != null;
        textView.setText(gridModel.getText());
        imageView.setImageResource(gridModel.getImage());

        return gridItemView;
    }
}
