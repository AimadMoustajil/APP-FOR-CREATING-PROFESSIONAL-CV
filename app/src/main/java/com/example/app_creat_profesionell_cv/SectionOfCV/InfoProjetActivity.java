package com.example.app_creat_profesionell_cv.SectionOfCV;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app_creat_profesionell_cv.Adapters.AdapterForProjet;
import com.example.app_creat_profesionell_cv.Classes.InfoProjet;
import com.example.app_creat_profesionell_cv.ContentOfCV.ContentOfInformationCv;
import com.example.app_creat_profesionell_cv.DB.Projet;
import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;

public class InfoProjetActivity extends AppCompatActivity {

    ArrayList<InfoProjet> infoProjets;
    AdapterForProjet adapterForProjet;
    RecyclerView recyclerView;
    ImageView addExperience, removeExperience, check;
    //DB
    Projet db;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_projet);

        db = new Projet(this);
        infoProjets = new ArrayList<>();
        InfoProjet newEx = new InfoProjet();
        infoProjets.add(newEx);
        adapterForProjet = new AdapterForProjet(infoProjets);
        recyclerView = findViewById(R.id.recyclerView);
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
                adapterForProjet.notifyItemInserted(infoProjets.size() - 1);
            }
        });

        removeExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!infoProjets.isEmpty()) {
                    int lastIndex = infoProjets.size() - 1;
                    infoProjets.remove(lastIndex);
                    adapterForProjet.notifyItemRemoved(lastIndex);
                }
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder infoString = new StringBuilder();
                for (InfoProjet projet : infoProjets) {
                    infoString.append("Entreprise: ").append(projet.getNomEntreprise()).append("\n")
                            .append("Titre: ").append(projet.getTitreProjet()).append("\n")
                            .append("Résumé: ").append(projet.getResume()).append("\n")
                            .append("Début: ").append(projet.getDateDebut()).append("\n")
                            .append("Fin: ").append(projet.getDateFin()).append("\n\n");
                    db.addInfoProjet(projet);
                }
                startActivity(new Intent(InfoProjetActivity.this, ContentOfInformationCv.class));
                Toast.makeText(InfoProjetActivity.this, infoString.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
