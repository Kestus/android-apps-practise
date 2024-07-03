package com.kes.app022_journal.adapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kes.app022_journal.R;
import com.kes.app022_journal.models.Journal;

import java.util.List;

public class JournalRecyclerAdapter extends RecyclerView.Adapter<JournalRecyclerAdapter.ViewHolder> {

    private Context context;
    List<Journal> posts;

    public JournalRecyclerAdapter(Context context, List<Journal> posts) {
        this.context = context;
        this.posts = posts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView content;
        public TextView name;
        public TextView timestamp;
        public ImageView image;
        public ImageView shareButton;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);

            context = ctx;
            title = itemView.findViewById(R.id.journal_item_title);
            content = itemView.findViewById(R.id.journal_item_content);
            timestamp = itemView.findViewById(R.id.journal_item_timestamp);

            image = itemView.findViewById(R.id.journal_item_image);
            name = itemView.findViewById(R.id.journal_item_username);

            shareButton = itemView.findViewById(R.id.journal_item_share_button);
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.journal_item, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalRecyclerAdapter.ViewHolder holder, int position) {
        Journal journal = posts.get(position);

        holder.title.setText(journal.getTitle());
        holder.content.setText(journal.getContent());
        holder.name.setText(journal.getUsername());

        String imageURL = journal.getImageURL();
        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(
                journal.getTimestamp().getSeconds() * 1000
        );

        holder.timestamp.setText(timeAgo);

        // Glide
        Glide.with(context)
                .load(imageURL)
                .placeholder(R.drawable.image_broken)
                .error(R.drawable.image_broken)
                .fitCenter()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return posts == null ? 0 : posts.size();
    }
}
