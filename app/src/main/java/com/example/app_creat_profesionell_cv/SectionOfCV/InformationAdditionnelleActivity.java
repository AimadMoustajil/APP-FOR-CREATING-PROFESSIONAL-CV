package com.example.app_creat_profesionell_cv.SectionOfCV;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.app_creat_profesionell_cv.Adapters.AdapterForCompetance;
import com.example.app_creat_profesionell_cv.Adapters.AdapterForLangue;
import com.example.app_creat_profesionell_cv.Adapters.AdapterForLoisir;
import com.example.app_creat_profesionell_cv.Adapters.AdapterForSoftSkills;
import com.example.app_creat_profesionell_cv.Classes.Competance;
import com.example.app_creat_profesionell_cv.Classes.Langue;
import com.example.app_creat_profesionell_cv.Classes.Loisir;
import com.example.app_creat_profesionell_cv.Classes.SoftSkills;
import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;

public class InformationAdditionnelleActivity extends AppCompatActivity {

    EditText competance, softSkills, language, loisir, linkedin, github, leetcode;
    ImageView addCompetance, addSoftSkills, addLanguage, addLoisir;
    RecyclerView recForCompetance, recForSoftSkills, recForLanguage, recForLoisir;

    // Array Of Info
    ArrayList<Loisir> loisirArrayList;
    ArrayList<SoftSkills> softSkillsArrayList;
    ArrayList<Langue> langueArrayList;
    ArrayList<Competance> competanceArrayList;

    // Adapters
    AdapterForSoftSkills adapterForSoftSkills;
    AdapterForLoisir adapterForLoisir;
    AdapterForLangue adapterForLangue;
    AdapterForCompetance adapterForCompetance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_additionnelle);
        initialElements();

        loisirArrayList = new ArrayList<>();
        softSkillsArrayList = new ArrayList<>();
        langueArrayList = new ArrayList<>();
        competanceArrayList = new ArrayList<>();

        adapterForSoftSkills = new AdapterForSoftSkills(softSkillsArrayList);
        adapterForLoisir = new AdapterForLoisir(loisirArrayList);
        adapterForLangue = new AdapterForLangue(langueArrayList);
        adapterForCompetance = new AdapterForCompetance(competanceArrayList);

        recForLoisir.setAdapter(adapterForLoisir);
        recForSoftSkills.setAdapter(adapterForSoftSkills);
        recForCompetance.setAdapter(adapterForCompetance);
        recForLanguage.setAdapter(adapterForLangue);

        recForLoisir.setLayoutManager(new LinearLayoutManager(this));
        recForLanguage.setLayoutManager(new LinearLayoutManager(this));
        recForCompetance.setLayoutManager(new LinearLayoutManager(this));
        recForSoftSkills.setLayoutManager(new LinearLayoutManager(this));

        addCompetance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Competance competance1 = new Competance();
                competanceArrayList.add(competance1);
                adapterForCompetance.notifyDataSetChanged();
            }
        });

        addLoisir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loisir loisir1 = new Loisir();
                loisirArrayList.add(loisir1);
                adapterForLoisir.notifyDataSetChanged();
            }
        });

        addLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Langue langue1 = new Langue();
                langueArrayList.add(langue1);
                adapterForLangue.notifyDataSetChanged();
            }
        });

        addSoftSkills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoftSkills softSkill1 = new SoftSkills();
                softSkillsArrayList.add(softSkill1);
                adapterForSoftSkills.notifyDataSetChanged();
            }
        });
    }

    private void initialElements() {
        competance = findViewById(R.id.competance);
        softSkills = findViewById(R.id.softskills);
        language = findViewById(R.id.langue);
        loisir = findViewById(R.id.loisir);
        linkedin = findViewById(R.id.linkdin);
        github = findViewById(R.id.github);
        leetcode = findViewById(R.id.leetCode);
        addCompetance = findViewById(R.id.addCompétence);
        addSoftSkills = findViewById(R.id.softSkills);
        addLanguage = findViewById(R.id.lesLangue);
        addLoisir = findViewById(R.id.loisirAdded);

        recForCompetance = findViewById(R.id.recForCompetance);
        recForSoftSkills = findViewById(R.id.recForSoftSkills);
        recForLanguage = findViewById(R.id.recForLanguage);
        recForLoisir = findViewById(R.id.recForLoisir);
    }
}
