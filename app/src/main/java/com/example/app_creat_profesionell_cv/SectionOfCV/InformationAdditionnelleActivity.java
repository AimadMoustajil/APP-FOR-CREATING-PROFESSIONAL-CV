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
import com.example.app_creat_profesionell_cv.Classes.InfoAdditionnelle;
import com.example.app_creat_profesionell_cv.ContentOfCV.ContentOfInformationCv;
import com.example.app_creat_profesionell_cv.DB.InformationAdditionnelle;
import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;

public class InformationAdditionnelleActivity extends AppCompatActivity {

    EditText competance, softSkills, language, loisir, linkedin, github, leetcode;
    ImageView addCompetance, addSoftSkills, addLanguage, addLoisir, checkInfo;
    RecyclerView recForCompetance, recForSoftSkills, recForLanguage, recForLoisir;

    // Array of Info
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

    // DB
    InformationAdditionnelle db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_additionnelle);
        initialElements();

        // Initialize lists
        loisirArrayList = new ArrayList<>();
        softSkillsArrayList = new ArrayList<>();
        langueArrayList = new ArrayList<>();
        competanceArrayList = new ArrayList<>();


        // Initialize adapters
        adapterForSoftSkills = new AdapterForSoftSkills(softSkillsArrayList);
        adapterForLoisir = new AdapterForLoisir(loisirArrayList);
        adapterForLangue = new AdapterForLangue(langueArrayList);
        adapterForCompetance = new AdapterForCompetance(competanceArrayList);


        // Set adapters to RecyclerViews
        recForLoisir.setAdapter(adapterForLoisir);
        recForSoftSkills.setAdapter(adapterForSoftSkills);
        recForCompetance.setAdapter(adapterForCompetance);
        recForLanguage.setAdapter(adapterForLangue);


        // Set layout managers to RecyclerViews
        recForLoisir.setLayoutManager(new LinearLayoutManager(this));
        recForLanguage.setLayoutManager(new LinearLayoutManager(this));
        recForCompetance.setLayoutManager(new LinearLayoutManager(this));
        recForSoftSkills.setLayoutManager(new LinearLayoutManager(this));


        // OnClickListeners for adding items
        addCompetance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String competance1 = competance.getText().toString();
                if (!competance1.isEmpty()) {
                    competanceArrayList.add(competance1);
                    adapterForCompetance.notifyDataSetChanged();
                    competance.setText(""); // Clear input field
                } else {
                    Toast.makeText(InformationAdditionnelleActivity.this, "Enter competence", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addLoisir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loisir1 = loisir.getText().toString();
                if (!loisir1.isEmpty()) {
                    loisirArrayList.add(loisir1);
                    adapterForLoisir.notifyDataSetChanged();
                    loisir.setText(""); // Clear input field
                } else {
                    Toast.makeText(InformationAdditionnelleActivity.this, "Enter loisir", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String langue1 = language.getText().toString();
                if (!langue1.isEmpty()) {
                    langueArrayList.add(langue1);
                    adapterForLangue.notifyDataSetChanged();
                    language.setText(""); // Clear input field
                } else {
                    Toast.makeText(InformationAdditionnelleActivity.this, "Enter language", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addSoftSkills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String softSkill1 = softSkills.getText().toString();
                if (!softSkill1.isEmpty()) {
                    softSkillsArrayList.add(softSkill1);
                    adapterForSoftSkills.notifyDataSetChanged();
                    softSkills.setText(""); // Clear input field
                } else {
                    Toast.makeText(InformationAdditionnelleActivity.this, "Enter soft skill", Toast.LENGTH_SHORT).show();
                }
            }
        });



        checkInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch values from input fields
                String competanceValue = competance.getText().toString();
                String softSkillValue = softSkills.getText().toString();
                String langueValue = language.getText().toString();
                String loisirValue = loisir.getText().toString();
                String linkedinValue = linkedin.getText().toString();
                String githubValue = github.getText().toString();
                String leetcodeValue = leetcode.getText().toString();

                // Check if the lists are empty and add values from input fields if needed
                if (!competanceValue.isEmpty() && !competanceArrayList.contains(competanceValue)) {
                    competanceArrayList.add(competanceValue);
                }
                if (!softSkillValue.isEmpty() && !softSkillsArrayList.contains(softSkillValue)) {
                    softSkillsArrayList.add(softSkillValue);
                }
                if (!langueValue.isEmpty() && !langueArrayList.contains(langueValue)) {
                    langueArrayList.add(langueValue);
                }
                if (!loisirValue.isEmpty() && !loisirArrayList.contains(loisirValue)) {
                    loisirArrayList.add(loisirValue);
                }

                // Create InfoAdditionnelle object
                InfoAdditionnelle additionnelle = new InfoAdditionnelle(
                        competanceArrayList,
                        softSkillsArrayList,
                        langueArrayList,
                        loisirArrayList,
                        linkedinValue,
                        githubValue,
                        leetcodeValue
                );

                // Check if db is initialized and add information
                if (db != null) {
                    db.addInformationAdditionnelle(additionnelle);
                    Toast.makeText(InformationAdditionnelleActivity.this, "Information added", Toast.LENGTH_SHORT).show();
                    // Navigate to next activity
                    startActivity(new Intent(InformationAdditionnelleActivity.this, ContentOfInformationCv.class));
                } else {
                    Toast.makeText(InformationAdditionnelleActivity.this, "Database not initialized", Toast.LENGTH_SHORT).show();
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

        recForCompetance = findViewById(R.id.recForCompetance);
        recForSoftSkills = findViewById(R.id.recForSoftSkills);
        recForLanguage = findViewById(R.id.recForLanguage);
        recForLoisir = findViewById(R.id.recForLoisir);

        // Initialize database object
        db = new InformationAdditionnelle(this); // Ensure this is initialized correctly
    }
}
