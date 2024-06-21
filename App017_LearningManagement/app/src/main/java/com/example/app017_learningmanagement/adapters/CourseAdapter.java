package com.example.app017_learningmanagement.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app017_learningmanagement.R;
import com.example.app017_learningmanagement.databinding.ListItemBinding;
import com.example.app017_learningmanagement.db.entities.Course;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private OnItemCLickListener listener;
    private ArrayList<Course> courses;

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private ListItemBinding itemBinding;

        public CourseViewHolder(ListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;

            itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemCLick(courses.get(position));
                    }
                }
            });
        }
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> newCourses) {
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(
                new CourseDiffCallback(courses, newCourses),
                false
        );

        courses = newCourses;
        result.dispatchUpdatesTo(CourseAdapter.this);
    }

    public interface OnItemCLickListener {
        void onItemCLick(Course course);
    }

    public void setListener(OnItemCLickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item,
                parent,
                false
        );
        return new CourseViewHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.itemBinding.setCourse(course);
    }

    @Override
    public int getItemCount() {
        return courses == null ? 0 : courses.size();
    }
}
