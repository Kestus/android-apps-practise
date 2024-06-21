package com.example.app017_learningmanagement.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.app017_learningmanagement.db.Repository;
import com.example.app017_learningmanagement.db.entities.Category;
import com.example.app017_learningmanagement.db.entities.Course;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    // Repository
    private Repository repository;

    // Live Data
    private LiveData<List<Category>> categories;
    private LiveData<List<Course>> currentCategoryCourses;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
    }

    public LiveData<List<Category>> getAllCategories() {
        categories = repository.getCategories();
        return categories;
    }

    public LiveData<List<Course>> getCoursesInCategory(long categoryID) {
        currentCategoryCourses = repository.getCoursesByCategory(categoryID);
        return currentCategoryCourses;
    }

    public void insertCourse(Course course) {
        repository.query(Repository.Actions.INSERT, course);
    }

    public void updateCourse(Course course) {
        repository.query(Repository.Actions.UPDATE, course);
    }

    public void deleteCourse(Course course) {
        repository.query(Repository.Actions.DELETE, course);
    }
}
