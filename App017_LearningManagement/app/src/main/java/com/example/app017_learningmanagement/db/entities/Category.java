package com.example.app017_learningmanagement.db.entities;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "categories")
public class Category extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    long id;
    @ColumnInfo(name = "category_name")
    String name;
    @ColumnInfo(name = "category_description")
    String description;

    @Ignore
    public Category() {}

    public Category(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Bindable
    public long getId() {
        return id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
