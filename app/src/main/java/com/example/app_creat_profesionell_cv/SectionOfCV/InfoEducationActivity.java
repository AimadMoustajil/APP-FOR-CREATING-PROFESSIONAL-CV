package com.example.app_creat_profesionell_cv.SectionOfCV;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_creat_profesionell_cv.Adapters.AdapterForAddEducation;
import com.example.app_creat_profesionell_cv.Classes.InfoEducation;
import com.example.app_creat_profesionell_cv.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class InfoEducationActivity extends AppCompatActivity {

    ImageView check, addEducation, removeEducation;
    RecyclerView recyclerView;
    ArrayList<InfoEducation> educations;
    AdapterForAddEducation adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_education);

        check = findViewById(R.id.checkInfo);
        addEducation = findViewById(R.id.addEducation);
        removeEducation = findViewById(R.id.removeEducation);
        recyclerView = findViewById(R.id.recyclerView);

        educations = new ArrayList<>();
        educations.add(new InfoEducation()); // Adding initial InfoEducation

        adapter = new AdapterForAddEducation(educations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoEducation newEd = new InfoEducation();
                educations.add(newEd);
                adapter.notifyItemInserted(educations.size() - 1);
            }
        });

        removeEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!educations.isEmpty()) {
                    int lastIndex = educations.size() - 1;
                    educations.remove(lastIndex);
                    adapter.notifyItemRemoved(lastIndex);
                }
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
