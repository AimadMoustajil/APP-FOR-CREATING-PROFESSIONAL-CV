package com.example.app_creat_profesionell_cv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.app_creat_profesionell_cv.Adapters.AdapterForShapeOnDashBoard;
import com.example.app_creat_profesionell_cv.Classes.Shapes;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class DashBoardActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    RecyclerView rec;
    AdapterForShapeOnDashBoard adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        drawerLayout = findViewById(R.id.dr);
        rec = findViewById(R.id.rec);

        //RecycleView with Adapter
        ArrayList<Shapes> myShapesInDashBoard = new ArrayList<>();
        myShapesInDashBoard.add(new Shapes("cv","creat cv"));
        myShapesInDashBoard.add(new Shapes("note","creat letter de motivation"));
        myShapesInDashBoard.add(new Shapes("resign","creat letter de dimission"));
        myShapesInDashBoard.add(new Shapes("promotion","creat letter de promotion"));
        myShapesInDashBoard.add(new Shapes("ask","Question d'entrevue"));

        // Initialize and set adapter
        adapter = new AdapterForShapeOnDashBoard(myShapesInDashBoard, this);
        rec.setAdapter(adapter);

        adapter = new AdapterForShapeOnDashBoard(myShapesInDashBoard,this);
        // Set layout manager
        rec.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rec.setAdapter(adapter);

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