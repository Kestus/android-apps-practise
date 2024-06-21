package com.example.app017_learningmanagement.db.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(
        tableName = "courses",
        foreignKeys = @ForeignKey(
                entity = Category.class,
                parentColumns = "id",
                childColumns = "category_id",
                onDelete = CASCADE
        )
)
public class Course extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    long id;

    @ColumnInfo(name = "course_name")
    String name;

    @ColumnInfo(name = "course_description")
    String description;

    @ColumnInfo(name = "course_price")
    String price;

    @ColumnInfo(name = "category_id")
    long categoryID;


    @Ignore
    public Course() {
    }

    public Course(long id, String name, String description, String price, long categoryID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryID = categoryID;
    }

    @Bindable
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Bindable
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    @Bindable
    public long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(long categoryID) {
        this.categoryID = categoryID;
        notifyPropertyChanged(BR.categoryID);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Course course = (Course) object;
        return id == course.id
                && categoryID == course.categoryID
                && Objects.equals(name, course.name)
                && Objects.equals(description, course.description)
                && Objects.equals(price, course.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, categoryID);
    }
}
