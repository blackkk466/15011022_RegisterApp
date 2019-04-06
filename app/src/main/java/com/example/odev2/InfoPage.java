package com.example.odev2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bumptech.glide.Glide;



public class InfoPage extends AppCompatActivity {

    private final int CALL_PERMISSION = 100;

    Person person;
    ImageView pp;
    Uri imagePath;
    TextView name,surname,tcNo,birthday, age, email,phone;
    Button showCourses;

    protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.info_page);


        Bundle bundle = getIntent().getExtras();

        try{
            person = bundle.getParcelable("user");
        }catch (Exception e){
            e.printStackTrace();
            Log.e("BUNDLE_SEND_ERROR",e.getMessage());
        }

        pp = (ImageView) findViewById(R.id.profilePic);
        pp.setVisibility(View.VISIBLE);

        imagePath = person.getProfilePic();


        Glide.with(this).load(imagePath).placeholder(new ColorDrawable(Color.RED)).into(pp);

        name = (TextView) findViewById(R.id.nameView);
        surname = (TextView) findViewById(R.id.surnameView);

        name.setText(person.getName());
        surname.setText(person.getSurname());

        tcNo = (TextView) findViewById(R.id.tcNo);
        tcNo.setText(person.getTcNo());

        birthday = (TextView) findViewById(R.id.birthday);
        age = (TextView) findViewById(R.id.age);


        long diff = (new Date()).getTime() - person.getBirthday().getTime();
        int ageInDays = (int) ( diff / (1000 * 60 * 60 * 24 ));
        int ageInYears = (ageInDays / 360)+1;

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");


        birthday.setText(df.format(person.getBirthday()));
        age.setText(String.valueOf(ageInYears));

        //Log.e("----AFTERSET----DAYS",String.valueOf(ageInDays));
        //Log.e("----AFTERSET----YEARS",String.valueOf(ageInYears));

        email = (TextView) findViewById(R.id.email);
        email.setText(person.getEmail());


        email.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
                mailIntent.setData( Uri.parse("mailto:"+person.getPhone()));

                startActivity(mailIntent);
            }
        });


        phone = (TextView) findViewById(R.id.phonenumber);
        phone.setText(person.getPhone());

        phone.setOnClickListener(new View.OnClickListener() {



            public void onClick(View v) {
                onCall();
            }
        });


        showCourses = (Button) findViewById(R.id.showCourses);
        showCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoPage.this,CoursePage.class);
                intent.putExtra("MAP", (Serializable) person.getTakenCourses());
                startActivity(intent);
            }
        });

    }

    public void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                     CALL_PERMISSION);
        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData( Uri.parse("tel:"+person.getEmail()));

            startActivity(callIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case CALL_PERMISSION:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onCall();
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }
}
