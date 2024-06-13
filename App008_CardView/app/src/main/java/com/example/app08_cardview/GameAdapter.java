package com.example.app08_cardview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    // 1 -- Data:
    private Context context;
    private ArrayList<GameModel> dataList;

    // 2 -- Constructor
    public GameAdapter(Context context, ArrayList<GameModel> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    // 3 -- View Holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView gameImage;
        private TextView gameTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            gameImage = itemView.findViewById(R.id.card_image);
            gameTitle = itemView.findViewById(R.id.card_title);
        }
    }

    // 4 -- Implement Methods
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameModel model = dataList.get(position);
        holder.gameTitle.setText(model.getName());
        holder.gameImage.setImageResource(model.getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        context,
                        "Title: " + model.getName(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
