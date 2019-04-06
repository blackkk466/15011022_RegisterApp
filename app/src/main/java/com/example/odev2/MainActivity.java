package com.example.odev2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editId, pass;
    Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editId = (EditText) findViewById(R.id.editId);
        pass = (EditText) findViewById(R.id.editPass);
        signInButton = (Button) findViewById(R.id.signIn);


        signInButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

                if( editId.getText().toString().compareTo("admin") == 0 && pass.getText().toString().compareTo("password") == 0 ){

                    Intent intent = new Intent( MainActivity.this, NewRegister.class);

                    startActivity(intent);

                }else{
                    Toast.makeText(MainActivity.this,"Kullanıcı adı ya da şifre hatalı!",Toast.LENGTH_SHORT).show();
                }

            }

        });



    }
}
