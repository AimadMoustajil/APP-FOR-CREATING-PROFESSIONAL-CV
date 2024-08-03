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

    ArrayList<InfoExperience> infoExperienceArrayList;
    AdapterForExperience adapterForExperience;
    RecyclerView recyclerView;
    ImageView addExperience,removeExperience,check;
    //DB
    ExperinceDeTravaile db;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experince);
        infoExperienceArrayList = new ArrayList<>();
        InfoExperience newEx = new InfoExperience();
        infoExperienceArrayList.add(newEx);
        adapterForExperience = new AdapterForExperience(infoExperienceArrayList);
        recyclerView  = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterForExperience);
        addExperience = findViewById(R.id.addEducation);
        removeExperience = findViewById(R.id.removeEducation);
        check = findViewById(R.id.checkInfo);
        db = new ExperinceDeTravaile(this);

        addExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoExperience newExperience = new InfoExperience();
                infoExperienceArrayList.add(newExperience);
                adapterForExperience.notifyItemInserted(infoExperienceArrayList.size()-1);
            }
        });

        removeExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!infoExperienceArrayList.isEmpty()){
                    int lastIndex = infoExperienceArrayList.size() - 1;
                    infoExperienceArrayList.remove(lastIndex);
                    adapterForExperience.notifyItemRemoved(lastIndex);
                }
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //infoExperienceArrayList
                if (!infoExperienceArrayList.isEmpty()){
                    for (InfoExperience i:infoExperienceArrayList){
                        db.addInfoExperience(i);
                    }
                    Toast.makeText(ExperinceActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ExperinceActivity.this, ContentOfInformationCv.class));
                }else {
                    startActivity(new Intent(ExperinceActivity.this, ContentOfInformationCv.class));

                }
            }
        });
    }
}