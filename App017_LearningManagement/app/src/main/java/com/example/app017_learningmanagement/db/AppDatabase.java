package com.example.app017_learningmanagement.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.app017_learningmanagement.db.dao.CategoryDAO;
import com.example.app017_learningmanagement.db.dao.CourseDAO;
import com.example.app017_learningmanagement.db.entities.Category;
import com.example.app017_learningmanagement.db.entities.Course;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        version = 3,
        entities = {
                Course.class,
                Category.class
        }
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CategoryDAO categoryDao();

    public abstract CourseDAO courseDao();

    // Singleton
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "AppDB"
            ).fallbackToDestructiveMigration()
             .addCallback(roomCallback)
             .build();
        }

        return instance;
    }

    // Callback
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // Insert Data When DB is Created
            InitData();

        }

        @Override
        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
            super.onDestructiveMigration(db);
            InitData();
        }
    };

    private static void InitData() {
        CourseDAO courseDAO = instance.courseDao();
        CategoryDAO categoryDAO = instance.categoryDao();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Categories
                Category cat1 = new Category();
                cat1.setName("Front End");
                cat1.setDescription("Web development Interface");

                Category cat2 = new Category();
                cat2.setName("Back End");
                cat2.setDescription("Web development Database");

                categoryDAO.insert(cat1);
                categoryDAO.insert(cat2);

                // Courses
                Course course1 = new Course();
                course1.setName("HTML");
                course1.setPrice("$20");
                course1.setCategoryID(1);

                Course course2 = new Course();
                course2.setName("CSS");
                course2.setPrice("$15");
                course2.setCategoryID(1);

                Course course3 = new Course();
                course3.setName("GO");
                course3.setPrice("$40");
                course3.setCategoryID(2);

                Course course4 = new Course();
                course4.setName("JavaScript");
                course4.setPrice("$30");
                course4.setCategoryID(2);

                courseDAO.insert(course1);
                courseDAO.insert(course2);
                courseDAO.insert(course3);
                courseDAO.insert(course4);
            }
        });
    }

}
