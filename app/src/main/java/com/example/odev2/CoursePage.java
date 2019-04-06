package com.example.odev2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class CoursePage extends AppCompatActivity {

    private ArrayList<Course> courses;
    private ArrayList<String> grades;
    private RecyclerView courseList;
    private MyAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses_page);

        Intent intent = getIntent();


        HashMap<Course,String> cg = (HashMap<Course, String>) intent.getSerializableExtra("MAP");


        courseList = (RecyclerView) findViewById(R.id.coursesRecycler);

        courses = new ArrayList<>(cg.keySet());
        grades = new ArrayList<>(cg.values());

        Log.d("---------COURSE",courses.get(0).getName());
        Log.d("---------GRADE",grades.get(0));

        adapter = new MyAdapter(courses, grades, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course item) {
                Intent intent = new Intent(CoursePage.this,CourseDetail.class);
                intent.putExtra("COURSE",(Serializable) item);
                startActivity(intent);
            }
        });

        courseList.setAdapter(adapter);
        courseList.setLayoutManager(new LinearLayoutManager(CoursePage.this));

    }
}
