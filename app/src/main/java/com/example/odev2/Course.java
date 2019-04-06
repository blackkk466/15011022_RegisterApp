package com.example.odev2;

import java.io.Serializable;
import java.util.Random;

public class Course implements Serializable {

    private String name;
    private int numOfStudents;
    private int averageGrade;


    public Course(String name){
        this.name=name;
        Random rand = new Random();
        numOfStudents = rand.nextInt(50)+1;
        averageGrade = rand.nextInt(30)+30;
    }

    public String getName() {
        return name;
    }

    public int getNumOfStudents() {
        return numOfStudents;
    }

    public int getAverageGrade() {
        return averageGrade;
    }
}
