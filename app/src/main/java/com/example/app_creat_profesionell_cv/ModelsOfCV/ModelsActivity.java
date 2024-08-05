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
        images.add(R.drawable.m31);

        ArrayList<String> imageNames = new ArrayList<>();
        imageNames.add("m1");
        imageNames.add("m2");
        imageNames.add("m3");
        imageNames.add("m4");
        imageNames.add("m5");
        imageNames.add("m6");
        imageNames.add("m7");
        imageNames.add("m8");
        imageNames.add("m9");
        imageNames.add("m10");
        imageNames.add("m11");
        imageNames.add("m12");
        imageNames.add("m13");
        imageNames.add("m14");
        imageNames.add("m15");
        imageNames.add("m16");
        imageNames.add("m17");
        imageNames.add("m18");
        imageNames.add("m19");
        imageNames.add("m20");
        imageNames.add("m21");
        imageNames.add("m22");
        imageNames.add("m23");
        imageNames.add("m24");
        imageNames.add("m25");
        imageNames.add("m26");
        imageNames.add("m27");
        imageNames.add("m28");
        imageNames.add("m29");
        imageNames.add("m30");
        imageNames.add("m31");

        rec.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new AdapterOfImageCV(images, imageNames, this);
        rec.setAdapter(adapter);
    }
}
