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

public class LoginActivity extends AppCompatActivity {

    EditText name,password;
    TextView creatAccount;
    Button login;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = findViewById(R.id.name);
        password = findViewById(R.id.passwos);
        creatAccount = findViewById(R.id.creatAccount);
        login = findViewById(R.id.btnLogin);

        creatAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }
}