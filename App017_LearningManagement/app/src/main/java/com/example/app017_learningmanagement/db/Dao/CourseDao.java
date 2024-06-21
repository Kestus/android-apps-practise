package com.example.app017_learningmanagement.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.app017_learningmanagement.db.entities.Course;

import java.util.List;

@Dao
public interface CourseDAO {

    @Insert
    void insert(Course course);

    @Delete
    void delete(Course course);

    @Update
    void update(Course course);

    @Query("SELECT * FROM courses ORDER BY id DESC")
    LiveData<List<Course>> getAllCourses();

    @Query("SELECT * FROM courses WHERE category_id == :categoryID")
    LiveData<List<Course>> getCoursesByCategory(long categoryID);

    @Query("SELECT * FROM courses WHERE id == :courseID")
    LiveData<Course> getCourseById(long courseID);
}
