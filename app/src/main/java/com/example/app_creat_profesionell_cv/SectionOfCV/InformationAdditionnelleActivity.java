package com.example.app_creat_profesionell_cv.SectionOfCV;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app_creat_profesionell_cv.Adapters.AdapterForCompetance;
import com.example.app_creat_profesionell_cv.Adapters.AdapterForLangue;
import com.example.app_creat_profesionell_cv.Adapters.AdapterForLoisir;
import com.example.app_creat_profesionell_cv.Adapters.AdapterForSoftSkills;
import com.example.app_creat_profesionell_cv.Classes.Competance;
import com.example.app_creat_profesionell_cv.Classes.InfoAdditionnelle;
import com.example.app_creat_profesionell_cv.Classes.Langue;
import com.example.app_creat_profesionell_cv.Classes.Loisir;
import com.example.app_creat_profesionell_cv.Classes.SoftSkills;
import com.example.app_creat_profesionell_cv.ContentOfCV.ContentOfInformationCv;
import com.example.app_creat_profesionell_cv.DB.InformationAdditionnelle;
import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;

public class InformationAdditionnelleActivity extends AppCompatActivity {

    EditText competance, softSkills, language, loisir, linkedin, github, leetcode;
    ImageView addCompetance, addSoftSkills, addLanguage, addLoisir,checkInfo;
    RecyclerView recForCompetance, recForSoftSkills, recForLanguage, recForLoisir;

    // Array Of Info
    ArrayList<String> loisirArrayList;
    ArrayList<String> softSkillsArrayList;
    ArrayList<String> langueArrayList;
    ArrayList<String> competanceArrayList;

    // Adapters
    AdapterForSoftSkills adapterForSoftSkills;
    AdapterForLoisir adapterForLoisir;
    AdapterForLangue adapterForLangue;
    AdapterForCompetance adapterForCompetance;
    //

    //DB
    InformationAdditionnelle db;
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
                String competance1 = new String();
                competanceArrayList.add(competance1);
                adapterForCompetance.notifyDataSetChanged();
            }
        });

        addLoisir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loisir1 = new String();
                loisirArrayList.add(loisir1);
                adapterForLoisir.notifyDataSetChanged();
            }
        });

        addLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String langue1 = new String();
                langueArrayList.add(langue1);
                adapterForLangue.notifyDataSetChanged();
            }
        });

        addSoftSkills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String softSkill1 = new String();
                softSkillsArrayList.add(softSkill1);
                adapterForSoftSkills.notifyDataSetChanged();
            }
        });


        checkInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loisirArrayList.isEmpty() && !softSkillsArrayList.isEmpty()
                        && !langueArrayList.isEmpty() && !competanceArrayList.isEmpty()
                        && !competance.getText().toString().isEmpty() && !softSkills.getText().toString().isEmpty() &&
                         !language.getText().toString().isEmpty() && !loisir.getText().toString().isEmpty() &&
                         !linkedin.getText().toString().isEmpty() && !github.getText().toString().isEmpty() &&
                          !leetcode.getText().toString().isEmpty()){
                    InfoAdditionnelle additionnelle = new InfoAdditionnelle(competanceArrayList,softSkillsArrayList,langueArrayList,loisirArrayList,linkedin.getText().toString(),github.getText().toString(),leetcode.getText().toString());
                    db.addInformationAdditionnelle(additionnelle);
                    Toast.makeText(InformationAdditionnelleActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(InformationAdditionnelleActivity.this, ContentOfInformationCv.class));
                }else {
                    startActivity(new Intent(InformationAdditionnelleActivity.this, ContentOfInformationCv.class));

                }
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
        checkInfo = findViewById(R.id.checkInfo);

        db = new InformationAdditionnelle(this);

        recForCompetance = findViewById(R.id.recForCompetance);
        recForSoftSkills = findViewById(R.id.recForSoftSkills);
        recForLanguage = findViewById(R.id.recForLanguage);
        recForLoisir = findViewById(R.id.recForLoisir);
    }
}
