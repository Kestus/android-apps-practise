package com.example.app020_paging.Util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.app020_paging.Models.Movie;

import java.util.Objects;

public class MovieComparator extends DiffUtil.ItemCallback<Movie> {
    @Override
    public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
        return Objects.equals(oldItem.getId(), newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
        return oldItem.equals(newItem);
    }
}
