package com.example.app08_recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    // 1 -- Data:
    private final VaccineModel[] listOfVaccines;

    public RecyclerAdapter(VaccineModel[] listOfVaccines) {
        this.listOfVaccines = listOfVaccines;
    }

    // 4 -- Click events handling
    private ItemClickListener clickListener;
    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    // 2 -- View Holder Class:
    // Creates a link between custom item layout and data model.
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.image_view);
            this.textView = itemView.findViewById(R.id.text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onClick(view, getAdapterPosition());
            }
        }
    }

    // 3 -- Implement the Methods
    // Called when new view is required.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem = inflater.inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(listItem);
    }

    // Called when already existing recycled view can be provided
    // and be bound with the new data.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final VaccineModel vaccine = listOfVaccines[position];
        holder.textView.setText(vaccine.getTitle());
        holder.imageView.setImageResource(vaccine.getImage());
    }

    @Override
    public int getItemCount() {
        return listOfVaccines.length;
    }
}
