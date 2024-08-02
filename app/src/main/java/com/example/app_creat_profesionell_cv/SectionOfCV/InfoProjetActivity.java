package com.example.app_creat_profesionell_cv.SectionOfCV;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.app_creat_profesionell_cv.Adapters.AdapterForExperience;
import com.example.app_creat_profesionell_cv.Adapters.AdapterForProjet;
import com.example.app_creat_profesionell_cv.Classes.InfoExperience;
import com.example.app_creat_profesionell_cv.Classes.InfoProjet;
import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;

public class InfoProjetActivity extends AppCompatActivity {

    ArrayList<InfoProjet> infoProjets;
    AdapterForProjet adapterForProjet;
    RecyclerView recyclerView;
    ImageView addExperience,removeExperience,check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_projet);

        infoProjets = new ArrayList<>();
        InfoProjet newEx = new InfoProjet();
        infoProjets.add(newEx);
        adapterForProjet = new AdapterForProjet(infoProjets);
        recyclerView  = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterForProjet);
        addExperience = findViewById(R.id.addEducation);
        removeExperience = findViewById(R.id.removeEducation);
        check = findViewById(R.id.checkInfo);


        addExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoProjet newProjet = new InfoProjet();
                infoProjets.add(newProjet);
                adapterForProjet.notifyItemInserted(infoProjets.size()-1);
            }
        });

        removeExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!infoProjets.isEmpty()){
                    int lastIndex = infoProjets.size() - 1;
                    infoProjets.remove(lastIndex);
                    adapterForProjet.notifyItemRemoved(lastIndex);
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