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
        images.add(R.drawable.m1);
        images.add(R.drawable.m2);
        images.add(R.drawable.m3);
        images.add(R.drawable.m4);
        images.add(R.drawable.m5);
        images.add(R.drawable.m6);
        images.add(R.drawable.m7);
        images.add(R.drawable.m8);
        images.add(R.drawable.m9);
        images.add(R.drawable.m10);
        images.add(R.drawable.m11);
        images.add(R.drawable.m12);
        images.add(R.drawable.m13);
        images.add(R.drawable.m14);
        images.add(R.drawable.m15);
        images.add(R.drawable.m16);
        images.add(R.drawable.m17);
        images.add(R.drawable.m18);
        images.add(R.drawable.m19);
        images.add(R.drawable.m20);
        images.add(R.drawable.m21);
        images.add(R.drawable.m22);
        images.add(R.drawable.m23);
        images.add(R.drawable.m24);
        images.add(R.drawable.m25);
        images.add(R.drawable.m26);
        images.add(R.drawable.m27);
        images.add(R.drawable.m28);
        images.add(R.drawable.m29);
        images.add(R.drawable.m30);



        rec.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new AdapterOfImageCV(images, this);
        rec.setAdapter(adapter);
    }
}
