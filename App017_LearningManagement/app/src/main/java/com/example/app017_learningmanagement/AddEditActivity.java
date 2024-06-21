package com.example.app017_learningmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.example.app017_learningmanagement.databinding.ActivityAddEditBinding;
import com.example.app017_learningmanagement.db.entities.Course;

public class AddEditActivity extends AppCompatActivity {

    private Course course;

    public static final String COURSE_ID = "course_id";
    public static final String COURSE_NAME = "course_name";
    public static final String COURSE_DESCRIPTION = "course_description";
    public static final String COURSE_PRICE = "course_price";

    private ActivityAddEditBinding activityAddEditBinding;
    private AddEditActivityClickHandlers clickHandlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        course = new Course();
        activityAddEditBinding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_add_edit
        );

        activityAddEditBinding.setCourse(course);

        clickHandlers = new AddEditActivityClickHandlers(getApplicationContext());
        activityAddEditBinding.setClickHandler(clickHandlers);

        Intent i = getIntent();

        if (i.hasExtra(COURSE_ID)) {
            setTitle("Edit Course");
            course.setName(i.getStringExtra(COURSE_NAME));
            course.setDescription(i.getStringExtra(COURSE_DESCRIPTION));
            course.setPrice(i.getStringExtra(COURSE_PRICE));
        } else {
            setTitle("Add New Course");
        }
    }


    public class AddEditActivityClickHandlers {
        Context context;

        public AddEditActivityClickHandlers(Context context) {
            this.context = context;
        }
        

        public void onSubmit(View view) {
            if (course.getName() == null) {
                Toast.makeText(context, "Course Name should not be empty!", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent();
                i.putExtra(COURSE_NAME, course.getName());
                i.putExtra(COURSE_DESCRIPTION, course.getDescription());
                i.putExtra(COURSE_PRICE, course.getPrice());
                setResult(RESULT_OK, i);
                finish();
            }
        }
    }
}