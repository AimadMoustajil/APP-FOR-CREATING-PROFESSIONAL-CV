package com.example.app_creat_profesionell_cv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.app_creat_profesionell_cv.LoginAndCreatAccount.LoginActivity;
import com.google.android.material.navigation.NavigationView;

public class DashBoardActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageButton buttonDrawelToggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        drawerLayout = findViewById(R.id.dr);
        buttonDrawelToggle = findViewById(R.id.buttonDrawelToggle);
        navigationView = findViewById(R.id.navigationView);

        buttonDrawelToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        //header View
        View view = navigationView.getHeaderView(0);
        ImageButton imageOfuse = view.findViewById(R.id.imageOfuser);
        TextView nameOfUser = view.findViewById(R.id.nameOfUser);
        TextView emailOfUser = view.findViewById(R.id.emailOfUser);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.home){

                } else if (itemId == R.id.about) {
                    startActivity(new Intent(DashBoardActivity.this,AboutActivity.class));
                }else if (itemId == R.id.logout){
                    startActivity(new Intent(DashBoardActivity.this, LoginActivity.class));
                }else if (itemId == R.id.user){
                    startActivity(new Intent(DashBoardActivity.this, SettingActivity.class));
                }
                return false;
            }
        });

    }

}