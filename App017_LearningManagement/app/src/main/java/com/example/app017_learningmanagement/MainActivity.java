package com.example.app017_learningmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app017_learningmanagement.ViewModel.MainActivityViewModel;
import com.example.app017_learningmanagement.adapters.CourseAdapter;
import com.example.app017_learningmanagement.db.entities.Category;
import com.example.app017_learningmanagement.db.entities.Course;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mainActivityViewModel;
    private ArrayList<Category> categories;
    private ArrayList<Course> courses;
    private com.example.app017_learningmanagement.databinding.ActivityMainBinding activityMainBinding;
    private MainActivityClickHandlers mainActivityClickHandlers;
    private Category selectedCategory;

    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;
    private static final int ADD_COURSE_REQUEST_CODE = 1;
    private static final int EDIT_COURSE_REQUEST_CODE = 2;
    private long selectedCourseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        // Click handlers
        mainActivityClickHandlers = new MainActivityClickHandlers();
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setClickHandlers(mainActivityClickHandlers);



        mainActivityViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                MainActivity.this.categories = (ArrayList<Category>) categories;

                for (Category c : categories) {
                    Log.i("TAG", c.getName());
                }

                showOnSpinner();
            }
        });

        mainActivityViewModel.getCoursesInCategory(1).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                for (Course c : courses) {
                    Log.i("TAG", c.getName());
                }
            }
        });
    }

    private void showOnSpinner() {
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                R.layout.spinner_item,
                categories
        );
        categoryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityMainBinding.setSpinnerAdapter(categoryArrayAdapter);
    }

    public void LoadCourses(long categoryID) {
        mainActivityViewModel.getCoursesInCategory(categoryID).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> coursesList) {
                courses = (ArrayList<Course>) coursesList;
                LoadRecyclerView();
            }
        });
    }

   private void LoadRecyclerView() {
        recyclerView = activityMainBinding.secondaryLayout.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        courseAdapter = new CourseAdapter();
        recyclerView.setAdapter(courseAdapter);

        courseAdapter.setCourses(courses);

        // Edit existing course
       courseAdapter.setListener(new CourseAdapter.OnItemCLickListener() {
           @Override
           public void onItemCLick(Course course) {
               selectedCourseID = course.getId();
               Intent i = new Intent(MainActivity.this, AddEditActivity.class);
               i.putExtra(AddEditActivity.COURSE_ID, selectedCourseID);
               i.putExtra(AddEditActivity.COURSE_NAME, course.getName());
               i.putExtra(AddEditActivity.COURSE_DESCRIPTION, course.getDescription());
               i.putExtra(AddEditActivity.COURSE_PRICE, course.getPrice());
               startActivityForResult(i , EDIT_COURSE_REQUEST_CODE);
           }
       });

       // Delete Course
       new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
           @Override
           public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
               return false;
           }

           @Override
           public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Course course = courses.get(viewHolder.getAdapterPosition());
                mainActivityViewModel.deleteCourse(course);
           }
       }).attachToRecyclerView(recyclerView);
   }

    public class MainActivityClickHandlers {
        public void onFABClick(View view) {
            // Create new course
            Intent i = new Intent(MainActivity.this, AddEditActivity.class);
            startActivityForResult(i, ADD_COURSE_REQUEST_CODE);
        }

        public void onSelectItem(AdapterView<?> parent, View view, int position, long id) {
            selectedCategory = (Category) parent.getItemAtPosition(position);
            String message = "id is: " + selectedCategory.getId() + "\n name is: " + selectedCategory.getName();
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

            LoadCourses(selectedCategory.getId());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        long selectedCategoryID = selectedCategory.getId();

        if (resultCode == RESULT_OK) {
            Course course = new Course();
            course.setCategoryID(selectedCategoryID);
            course.setName(data.getStringExtra(AddEditActivity.COURSE_NAME));
            course.setDescription(data.getStringExtra(AddEditActivity.COURSE_DESCRIPTION));
            course.setPrice(data.getStringExtra(AddEditActivity.COURSE_PRICE));

            switch (requestCode) {
                case ADD_COURSE_REQUEST_CODE:
                    mainActivityViewModel.insertCourse(course);
                case EDIT_COURSE_REQUEST_CODE:
                    course.setId(selectedCourseID);
                    mainActivityViewModel.updateCourse(course);
            }
        }
    }
}