package com.example.app_creat_profesionell_cv.SectionOfCV;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app_creat_profesionell_cv.Adapters.AdapterForExperience;
import com.example.app_creat_profesionell_cv.Classes.InfoExperience;
import com.example.app_creat_profesionell_cv.ContentOfCV.ContentOfInformationCv;
import com.example.app_creat_profesionell_cv.DB.ExperinceDeTravaile;
import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;

public class ExperinceActivity extends AppCompatActivity {

    private ArrayList<InfoExperience> infoExperienceArrayList;
    private AdapterForExperience adapterForExperience;
    private RecyclerView recyclerView;
    private ImageView addExperience, removeExperience, check;
    private ExperinceDeTravaile db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experince); // Make sure layout file name is correct

        // Initialize variables
        infoExperienceArrayList = new ArrayList<>();
        infoExperienceArrayList.add(new InfoExperience());
        adapterForExperience = new AdapterForExperience(infoExperienceArrayList);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterForExperience);

        addExperience = findViewById(R.id.addEducation); // Corrected ID
        removeExperience = findViewById(R.id.removeEducation); // Corrected ID
        check = findViewById(R.id.checkInfo);

        db = new ExperinceDeTravaile(this);

        // Set up onClickListeners
        addExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoExperience newExperience = new InfoExperience();
                infoExperienceArrayList.add(newExperience);
                adapterForExperience.notifyItemInserted(infoExperienceArrayList.size() - 1);
            }
        });

        removeExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!infoExperienceArrayList.isEmpty()) {
                    int lastIndex = infoExperienceArrayList.size() - 1;
                    infoExperienceArrayList.remove(lastIndex);
                    adapterForExperience.notifyItemRemoved(lastIndex);
                } else {
                    Toast.makeText(ExperinceActivity.this, "No experience to remove", Toast.LENGTH_SHORT).show();
                }
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!infoExperienceArrayList.isEmpty()) {
                    for (InfoExperience experience : infoExperienceArrayList) {
                        db.addInfoExperience(experience);
                    }
                    Toast.makeText(ExperinceActivity.this, "Experience saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ExperinceActivity.this, "No experience to save", Toast.LENGTH_SHORT).show();
                }
               startActivity(new Intent(ExperinceActivity.this, ContentOfInformationCv.class));
            }
        });
    }
}
