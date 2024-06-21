package com.example.app017_learningmanagement.db;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Entity;

import com.example.app017_learningmanagement.db.dao.CategoryDAO;
import com.example.app017_learningmanagement.db.dao.CourseDAO;
import com.example.app017_learningmanagement.db.entities.Category;
import com.example.app017_learningmanagement.db.entities.Course;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private CategoryDAO categoryDAO;
    private CourseDAO courseDAO;

    private LiveData<List<Category>> categories;
    private LiveData<List<Course>> courses;

    public Repository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application.getApplicationContext());
        categoryDAO = db.categoryDao();
        courseDAO = db.courseDao();
    }

    //Getters
    public LiveData<List<Category>> getCategories() {
        return categoryDAO.getAllCategories();
    }

    public LiveData<List<Course>> getCoursesByCategory(long categoryID) {
        return courseDAO.getCoursesByCategory(categoryID);
    }


    // Methods
    public enum Actions {
        INSERT,
        DELETE,
        UPDATE
    }

    public void query(Actions action, Category category) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                switch (action) {
                    case INSERT:
                        categoryDAO.insert(category);
                    case DELETE:
                        categoryDAO.delete(category);
                    case UPDATE:
                        categoryDAO.update(category);
                }
            }
        });
    }

    public void query(Actions action, Course course) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                switch (action) {
                    case INSERT:
                        courseDAO.insert(course);
                    case DELETE:
                        courseDAO.delete(course);
                    case UPDATE:
                        courseDAO.update(course);
                }
            }
        });
    }
}
