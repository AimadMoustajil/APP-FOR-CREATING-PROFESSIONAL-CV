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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_creat_profesionell_cv.Adapters.AdapterForShapeOnDashBoard;
import com.example.app_creat_profesionell_cv.Classes.Shapes;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class DashBoardActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    RecyclerView rec;
    AdapterForShapeOnDashBoard adapter;
    ImageView transletLanguage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        drawerLayout = findViewById(R.id.dr);
        rec = findViewById(R.id.rec);
        transletLanguage =findViewById(R.id.imageView4);

        transletLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, Languages.class));
            }
        });

        //RecycleView with Adapter
        ArrayList<Shapes> myShapesInDashBoard = new ArrayList<>();
        myShapesInDashBoard.add(new Shapes("cv",R.string.creat_cv));
        myShapesInDashBoard.add(new Shapes("note",R.string.creat_letter_de_motivation));
        myShapesInDashBoard.add(new Shapes("resign",R.string.creat_letter_de_dimission));
        myShapesInDashBoard.add(new Shapes("promotion",R.string.creat_letter_de_promotion));
        myShapesInDashBoard.add(new Shapes("ask",R.string.Question_d_entrevue));
        myShapesInDashBoard.add(new Shapes("book",R.string.Books));

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