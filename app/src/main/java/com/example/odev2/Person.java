package com.example.odev2;


import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class Person implements Parcelable {
    private String name,surname,tcNo;
    private Date birthday;
    private String phone;
    private String email;
    private Uri profilePic;
    private HashMap<Course,String> takenCourses;

    public Person(String name, String surname, String tcNo, String phone, String email ,Date birthday) {
        this.name = name;
        this.surname = surname;
        this.tcNo = tcNo;
        this.phone = phone;
        this.email = email;
        this.birthday = birthday;

        takenCourses = new HashMap<Course,String>();

        createCourses();
    }


    public String getName() {return name;}

    public String getSurname() {return surname;}

    public String getTcNo() {return tcNo;}

    public Date getBirthday(){return birthday;}

    public String getPhone() {return phone;}

    public String getEmail() {return email;}


    public Uri getProfilePic() {return profilePic;}
    public void setProfilePic(Uri profilePic) {this.profilePic = profilePic;}

    public HashMap<Course,String> getTakenCourses(){return takenCourses;}

    public void createCourses(){
        int courseId,gradeId;
        int i;
        ArrayList<Integer> gradeIDs = new ArrayList<Integer>();
        String[] grades = {"AA","BA","BB","CB","CC","DC"};

        Random rand = new Random();

        for( i= 0; i<30 ; i++){
            gradeIDs.add(i+1);
        }

        Collections.shuffle(gradeIDs);

        String name;

        for (i=0 ; i<20 ; i++){
            name = "Course" + (gradeIDs.get(i)+1);
            gradeId = rand.nextInt(6);

            takenCourses.put(new Course(name),grades[gradeId]);
        }

    }


    @Override
    public int describeContents() {
        return 0;
    }


    public Person(Parcel in){
        this.name = in.readString();
        this.surname = in.readString();
        this.tcNo = in.readString();
        this.phone = in.readString();
        this.email = in.readString();

        this.profilePic = Uri.parse(in.readString());
        //iyi performans icin date objesi long olarak yazilip okundu
        //out.writeLong(birthday.getTime());
        this.birthday = new Date(in.readLong());

        takenCourses =  (HashMap<Course,String>) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(tcNo);
        dest.writeString(phone);
        dest.writeString(email);

        dest.writeString(profilePic.toString());

        dest.writeLong(birthday.getTime());

        dest.writeSerializable(takenCourses);
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
