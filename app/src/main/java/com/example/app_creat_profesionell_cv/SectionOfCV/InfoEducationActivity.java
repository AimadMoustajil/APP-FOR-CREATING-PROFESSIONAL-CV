package com.example.app_creat_profesionell_cv.SectionOfCV;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app_creat_profesionell_cv.Adapters.AdapterForAddEducation;
import com.example.app_creat_profesionell_cv.Classes.InfoEducation;
import com.example.app_creat_profesionell_cv.ContentOfCV.ContentOfInformationCv;
import com.example.app_creat_profesionell_cv.DB.Education;
import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;

public class InfoEducationActivity extends AppCompatActivity {

    private ImageView check, addEducation, removeEducation;
    private RecyclerView recyclerView;
    private ArrayList<InfoEducation> educations;
    private AdapterForAddEducation adapter;

    // Database instance
    private Education dbEducation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_education);

        // Initialize UI components
        check = findViewById(R.id.checkInfo);
        addEducation = findViewById(R.id.addEducation);
        removeEducation = findViewById(R.id.removeEducation);
        recyclerView = findViewById(R.id.recyclerView);

        // Initialize database instance
        dbEducation = new Education(this);

        // Initialize education list and adapter
        educations = new ArrayList<>();
        educations.add(new InfoEducation()); // Adding initial InfoEducation

        adapter = new AdapterForAddEducation(educations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Add Education button click listener
        addEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoEducation newEd = new InfoEducation();
                educations.add(newEd);
                adapter.notifyItemInserted(educations.size() - 1);
            }
        });

        // Remove Education button click listener
        removeEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!educations.isEmpty()) {
                    int lastIndex = educations.size() - 1;
                    educations.remove(lastIndex);
                    adapter.notifyItemRemoved(lastIndex);
                } else {
                    Toast.makeText(InfoEducationActivity.this, "No items to remove", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Check button click listener
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!educations.isEmpty()) {
                    for (InfoEducation infoEducation : educations) {
                        dbEducation.addInfoEducation(infoEducation);
                    }
                    Toast.makeText(InfoEducationActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InfoEducationActivity.this, "No data to save", Toast.LENGTH_SHORT).show();
                }
                // Navigate to ContentOfInformationCv activity
                startActivity(new Intent(InfoEducationActivity.this, ContentOfInformationCv.class));
            }
        });
    }
}
