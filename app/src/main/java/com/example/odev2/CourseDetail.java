package com.example.odev2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CourseDetail extends AppCompatActivity {

    private TextView name,studentNum,average;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);

        Intent intent = getIntent();

        Course course = (Course) intent.getSerializableExtra("COURSE");

        name = (TextView) findViewById(R.id.cnameview);
        studentNum = (TextView) findViewById(R.id.studentNum);
        average = (TextView) findViewById(R.id.average);

        name.setText(course.getName());
        studentNum.setText(String.valueOf(course.getNumOfStudents()));
        average.setText(String.valueOf(course.getAverageGrade()));
    }

}
