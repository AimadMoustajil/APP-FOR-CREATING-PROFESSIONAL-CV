package com.example.app_creat_profesionell_cv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.app_creat_profesionell_cv.DataBase.LoginAndRegister;
import com.example.app_creat_profesionell_cv.LoginAndCreatAccount.LoginActivity;


public class MainActivity extends AppCompatActivity {

    LoginAndRegister db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new LoginAndRegister(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (db.checkUserCredentials(getName(), getPassword())) {
                    Intent intent = new Intent(MainActivity.this, DashBoardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Added finish() to close the MainActivity
                }
            }
        }, 3000);
    }

    public String getName() {
        SharedPreferences sharedPreferences = getSharedPreferences("myInfo", MODE_PRIVATE);
        return sharedPreferences.getString("name", "");
    }

    public String getPassword() {
        SharedPreferences sharedPreferences = getSharedPreferences("myInfo", MODE_PRIVATE);
        return sharedPreferences.getString("password", "");
    }
}
