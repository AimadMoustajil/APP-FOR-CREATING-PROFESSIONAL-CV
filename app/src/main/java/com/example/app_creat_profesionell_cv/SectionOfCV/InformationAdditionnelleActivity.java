package com.example.app_creat_profesionell_cv.SectionOfCV;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.app_creat_profesionell_cv.R;

public class InformationAdditionnelleActivity extends AppCompatActivity {

    EditText competance,softSkills,lanuage,loisir,linkdin,github,leetcode;
    ImageView addCometance,addSoftSkills,addLanguage,addLoisir;
    RecyclerView recForCometance,recForSoftSkills,recForLanguage,recForLoisir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_additionnelle);
        initialElemnt();


    }

    private void initialElemnt(){
        competance = findViewById(R.id.competance);
        softSkills = findViewById(R.id.softskills);
        lanuage = findViewById(R.id.langue);
        loisir = findViewById(R.id.loisir);
        linkdin = findViewById(R.id.linkdin);
        github = findViewById(R.id.github);
        leetcode = findViewById(R.id.leetCode);
        addCometance = findViewById(R.id.addCompétence);
        addSoftSkills = findViewById(R.id.softSkills);
        addLanguage = findViewById(R.id.lesLangue);
        addLoisir = findViewById(R.id.loisirAdded);

        recForCometance = findViewById(R.id.recForCompetance);
        recForSoftSkills = findViewById(R.id.recForSoftSkills);
        recForLanguage = findViewById(R.id.recForLanguage);
        recForLoisir= findViewById(R.id.recForLoisir);
    }
}