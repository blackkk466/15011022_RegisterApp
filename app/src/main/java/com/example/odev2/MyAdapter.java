package com.example.odev2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private ArrayList<Course> courses;
    private ArrayList<String> grades;
    private OnItemClickListener listener;

    public MyAdapter(ArrayList<Course> courses,ArrayList<String> grades,OnItemClickListener listener) {
        this.courses = courses;
        this.grades = grades;
        this.listener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView courseName,courseGrade;
        public MyViewHolder(View v){
            super(v);
            courseName = (TextView) v.findViewById(R.id.course) ;
            courseGrade = (TextView) v.findViewById(R.id.grade);
        }

        public void bind(final Course course, final String grade, final OnItemClickListener listener){
           courseName.setText(course.getName());
           courseGrade.setText(grade);

            itemView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    listener.onItemClick(course);
                }
            });
        }
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.course_layout, parent, false);


        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.bind(courses.get(i),grades.get(i),listener);
    }

    public int getItemCount() {

        return courses.size();
    }

    public interface OnItemClickListener{
        void onItemClick(Course item);
    }

}

