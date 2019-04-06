package com.example.odev2;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewRegister extends AppCompatActivity {

    private static int REQUEST_IMAGE_OPEN = 1;

    Button sendButton,clearButton,buttonLoadImage,buttonPickDate;
    EditText name,surname, tcNo, editBirthdate,email,phone;
    Context context = this;
    Date bdate;
    Uri imagePath;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_register);

        imagePath = null;

        //profil fotografinin secildigi kisim
        buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                selectImage();
            }
        });

        //kisisel bilgilerin alindigi kisim
        name = (EditText) findViewById(R.id.editName);
        surname = (EditText) findViewById(R.id.editSName);
        tcNo = (EditText) findViewById(R.id.editTC);
        editBirthdate = (EditText) findViewById(R.id.editBirthdate);
        email = (EditText) findViewById(R.id.editEmail);
        phone = (EditText) findViewById(R.id.editNumber);

        //dogum tarihinin alindigi kisim
        buttonPickDate = (Button) findViewById(R.id.pickButton);
        buttonPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar takvim = Calendar.getInstance();
                int yil = takvim.get(Calendar.YEAR);
                int ay = takvim.get(Calendar.MONTH);
                int gun = takvim.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dpd = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                month += 1;
                                editBirthdate.setText(dayOfMonth + "/" + month + "/" + year);
                            }
                        }, yil, ay, gun);

                dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Seç", dpd);
                dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", dpd);
                dpd.show();

            }
        });

        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( editBirthdate.getText().toString().length() == 0 ){
                    Toast.makeText(NewRegister.this,"Doğum tarihi boş bırakılamaz",Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    bdate = df.parse(editBirthdate.getText().toString());

                    if( !bdate.before(new Date()) ){
                        Toast.makeText(NewRegister.this,"Tarih günümüzden önce olmalıdır!",Toast.LENGTH_SHORT).show();
                        editBirthdate.setText("");
                        bdate = null;
                        return;
                    }
                }catch( ParseException e) {
                    e.printStackTrace();
                    Log.e("BIRTHDAY ERROR",e.getMessage());
                }

                if ( name.getText().toString().length() == 0 || surname.getText().toString().length() == 0
                        || tcNo.getText().toString().length() == 0 || phone.getText().toString().length() == 0
                        || email.getText().toString().length() == 0 ){

                    Toast.makeText(NewRegister.this,"Lütfen eksik alanları doldurun.",Toast.LENGTH_SHORT).show();
                    return;
                }

                Person user = new Person( name.getText().toString(),
                        surname.getText().toString(),
                        tcNo.getText().toString(),
                        phone.getText().toString(),
                        email.getText().toString(),
                        bdate);

                user.setProfilePic(imagePath);
                user.createCourses();

                Intent intent = new Intent(NewRegister.this , InfoPage.class);
                intent.putExtra("user",user);

                startActivity(intent);

                //***
                //Log.d("DATE VALUE",bdate.toString());
                //***
            }
        });


        clearButton = (Button) findViewById(R.id.clearForm);

        clearButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){
                name.setText("");
                surname.setText("");
                tcNo.setText("");
                phone.setText("");
                email.setText("");
                editBirthdate.setText("");
                ImageView imageView = (ImageView) findViewById(R.id.imgView);
                imageView.setImageResource(R.drawable.profile);
            }
        });
    }



    public void onSaveInstanceState( Bundle savedInstanceState ){

        savedInstanceState.putString("NAME",name.getText().toString());
        savedInstanceState.putString("SURNAME", surname.getText().toString());
        savedInstanceState.putString("PHONE", phone.getText().toString());
        savedInstanceState.putString("EMAIL",email.getText().toString());
        savedInstanceState.putString("BDAY",editBirthdate.getText().toString());
        savedInstanceState.putString("TC",tcNo.getText().toString());
        if ( imagePath != null )
            savedInstanceState.putString("URI",imagePath.toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle saved) {
        super.onRestoreInstanceState(saved);
        name.setText(saved.getString("NAME"));
        surname.setText(saved.getString("SURNAME"));
        phone.setText(saved.getString("PHONE"));
        email.setText(saved.getString("EMAIL"));
        editBirthdate.setText(saved.getString("BDAY"));
        tcNo.setText(saved.getString("TC"));

        ImageView imageView = (ImageView) findViewById(R.id.imgView);
        if( imagePath == null )
            imageView.setImageResource(R.drawable.profile);
        else
            Glide.with(this).load(imagePath).placeholder(new ColorDrawable(Color.RED)).into(imageView);
    }


    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_IMAGE_OPEN);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_OPEN && resultCode == RESULT_OK) {
            imagePath = data.getData();
/*            try {
                Log.e("uri", imagePath.toString());
            }catch (NullPointerException e){
                e.printStackTrace();
                Log.e("----NULLPOINTER",e.getMessage());
            }*/
            ImageView imageView = (ImageView) findViewById(R.id.imgView);

            imageView.setVisibility(View.VISIBLE);
            Glide.with(this).load(imagePath).placeholder(new ColorDrawable(Color.RED)).into(imageView);

        }else{
            Log.d("ELSE","RESULCODE"+resultCode);
        }
    }
}
