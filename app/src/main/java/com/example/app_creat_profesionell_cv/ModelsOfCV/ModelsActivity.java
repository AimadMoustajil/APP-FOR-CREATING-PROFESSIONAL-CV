package com.example.app_creat_profesionell_cv.ModelsOfCV;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.app_creat_profesionell_cv.Adapters.AdapterOfImageCV;
import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;

public class ModelsActivity extends AppCompatActivity {

    RecyclerView rec;
    AdapterOfImageCV adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_models);

        rec = findViewById(R.id.recForModels);

        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.cv);
        images.add(R.drawable.m1);

        rec.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new AdapterOfImageCV(images, this);
        rec.setAdapter(adapter);
    }
}
