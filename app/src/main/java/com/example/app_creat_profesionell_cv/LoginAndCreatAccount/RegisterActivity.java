package com.example.app_creat_profesionell_cv.LoginAndCreatAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app_creat_profesionell_cv.R;

public class RegisterActivity extends AppCompatActivity {

    EditText nameOfUse,email,password,confirmPassword;
    Button creat;
    TextView login;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameOfUse = findViewById(R.id.nameOfUser);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        creat = findViewById(R.id.creat);
        login = findViewById(R.id.loginFromHere);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });



    }
}