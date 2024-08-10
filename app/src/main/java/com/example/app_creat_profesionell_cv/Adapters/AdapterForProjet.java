package com.example.app_creat_profesionell_cv.Adapters;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_creat_profesionell_cv.Classes.InfoProjet;
import com.example.app_creat_profesionell_cv.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterForProjet extends RecyclerView.Adapter<AdapterForProjet.ViewHolder> {
    ArrayList<InfoProjet> infoProjetArrayList;

    public AdapterForProjet(ArrayList<InfoProjet> infoProjetArrayList) {
        this.infoProjetArrayList = infoProjetArrayList;
    }

    @NonNull
    @Override
    public AdapterForProjet.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_of_projet, parent, false);
        return new AdapterForProjet.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InfoProjet projet = infoProjetArrayList.get(position);
        holder.nameEntreprise.setText(projet.getNomEntreprise());
        holder.titreDeJob.setText(projet.getTitreProjet());
        holder.Résumé.setText(projet.getResume());
        holder.start.setText(projet.getDateDebut());
        holder.Finich.setText(projet.getDateFin());

        holder.nameEntreprise.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                projet.setNomEntreprise(s.toString());
            }
        });

        holder.titreDeJob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                projet.setTitreProjet(s.toString());
            }
        });

        holder.Résumé.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                projet.setResume(s.toString());
            }
        });

        holder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(holder.start, projet, true);
            }
        });

        holder.Finich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(holder.Finich, projet, false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return infoProjetArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        EditText nameEntreprise, titreDeJob, Résumé;
        TextView start, Finich;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameEntreprise = itemView.findViewById(R.id.nameOfSchool);
            titreDeJob = itemView.findViewById(R.id.yourMetier);
            Résumé = itemView.findViewById(R.id.rs);
            start = itemView.findViewById(R.id.start);
            Finich = itemView.findViewById(R.id.Finich);
        }
    }

    private void showDatePickerDialog(final TextView textView, final InfoProjet projet, final boolean isStartDate) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, 1);  // Set the day to the first day of the month

                // Format the date as "yyyy-MM" to include only the year and month
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
                String dateString = dateFormat.format(calendar.getTime());

                textView.setText(dateString);
                if (isStartDate) {
                    projet.setDateDebut(dateString);
                } else {
                    projet.setDateFin(dateString);
                }
            }
        };

        new DatePickerDialog(textView.getContext(), dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

}
