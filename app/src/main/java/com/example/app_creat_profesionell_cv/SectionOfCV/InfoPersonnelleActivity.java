package com.example.app_creat_profesionell_cv.SectionOfCV;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_creat_profesionell_cv.R;

public class InfoPersonnelleActivity extends AppCompatActivity {

    ImageView imageOfUser,check;
    EditText firstName,lastName,numberPhone,emailOfUSer,paysOfUser,villeOfUSer,jobOfUSer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_personnelle);

        imageOfUser = findViewById(R.id.imageView3);
        check = findViewById(R.id.check);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        numberPhone = findViewById(R.id.numberPhone);
        emailOfUSer = findViewById(R.id.emailOfUSer);
        paysOfUser = findViewById(R.id.paysOfUser);
        villeOfUSer = findViewById(R.id.villeOfUSer);
        jobOfUSer = findViewById(R.id.jobOfUSer);
    }
}