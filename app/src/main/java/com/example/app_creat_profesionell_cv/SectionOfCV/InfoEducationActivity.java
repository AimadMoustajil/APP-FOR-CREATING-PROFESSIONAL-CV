package com.example.app_creat_profesionell_cv.SectionOfCV;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.app_creat_profesionell_cv.Adapters.AdapterForAddEducation;
import com.example.app_creat_profesionell_cv.Classes.InfoEducation;
import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;

public class InfoEducationActivity extends AppCompatActivity {

    EditText nameOfSchool,yourMetier,start, Finich;
    ImageView check,addEducation,removeEducation;
    RecyclerView recyclerView;
    ArrayList<InfoEducation> educations;
    AdapterForAddEducation adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_education);

        educations = new ArrayList<>();
        adapter = new AdapterForAddEducation(educations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        nameOfSchool = findViewById(R.id.nameOfSchool);
        yourMetier = findViewById(R.id.yourMetier);
        start = findViewById(R.id.start);
        Finich = findViewById(R.id.Finich);
        check = findViewById(R.id.check);
        recyclerView = findViewById(R.id.recyclerView);
        addEducation = findViewById(R.id.addEducation);
        removeEducation = findViewById(R.id.removeEducation);


        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        addEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}