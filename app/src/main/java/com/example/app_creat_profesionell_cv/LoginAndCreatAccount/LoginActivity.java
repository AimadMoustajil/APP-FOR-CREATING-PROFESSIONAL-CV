package com.example.app_creat_profesionell_cv.LoginAndCreatAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_creat_profesionell_cv.DashBoardActivity;
import com.example.app_creat_profesionell_cv.DataBase.LoginAndRegister;
import com.example.app_creat_profesionell_cv.R;

public class LoginActivity extends AppCompatActivity {

    EditText name,password;
    TextView creatAccount;
    Button login;
    LoginAndRegister db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = findViewById(R.id.name);
        password = findViewById(R.id.passwos);
        creatAccount = findViewById(R.id.creatAccount);
        login = findViewById(R.id.btnLogin);
        db = new LoginAndRegister(this);


        creatAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.checkUserCredentials(name.getText().toString(),password.getText().toString())){
                    startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
                }else {
                    Toast.makeText(LoginActivity.this, "that user not exists", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}