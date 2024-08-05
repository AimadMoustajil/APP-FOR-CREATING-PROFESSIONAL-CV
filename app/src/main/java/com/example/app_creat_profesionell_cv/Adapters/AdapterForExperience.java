package com.example.app_creat_profesionell_cv.Adapters;

import android.app.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;  // Changed from android.icu.util.Calendar
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

import com.example.app_creat_profesionell_cv.Classes.InfoExperience;
import com.example.app_creat_profesionell_cv.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AdapterForExperience extends RecyclerView.Adapter<AdapterForExperience.ViewHolder> {
    private final ArrayList<InfoExperience> infoExperienceArrayList;

    public AdapterForExperience(ArrayList<InfoExperience> infoExperienceArrayList) {
        this.infoExperienceArrayList = infoExperienceArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_experience, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InfoExperience experience = infoExperienceArrayList.get(position);
        holder.nameEntreprise.setText(experience.getNomEntreprise());
        holder.titreDeJob.setText(experience.getTitreDePoste());
        holder.Résumé.setText(experience.getRésumé());
        holder.start.setText(experience.getDateDébut());
        holder.Finich.setText(experience.getDateDeFin());

        // Set up text change listeners for EditText fields
        holder.nameEntreprise.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                experience.setNomEntreprise(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        holder.titreDeJob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                experience.setTitreDePoste(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        holder.Résumé.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                experience.setRésumé(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        holder.start.setOnClickListener(v -> showDatePickerDialog(holder.start, experience, true));
        holder.Finich.setOnClickListener(v -> showDatePickerDialog(holder.Finich, experience, false));
    }

    @Override
    public int getItemCount() {
        return infoExperienceArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        EditText nameEntreprise, titreDeJob, Résumé;
        TextView start, Finich;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameEntreprise = itemView.findViewById(R.id.nameOfSchool);  // Ensure these IDs match the XML
            titreDeJob = itemView.findViewById(R.id.yourMetier);
            Résumé = itemView.findViewById(R.id.rs);
            start = itemView.findViewById(R.id.start);
            Finich = itemView.findViewById(R.id.Finich);
        }
    }

    private void showDatePickerDialog(final TextView textView, final InfoExperience experience, final boolean isStartDate) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String dateString = dateFormat.format(calendar.getTime());

                textView.setText(dateString);

                if (isStartDate) {
                    experience.setDateDébut(dateString);
                } else {
                    experience.setDateDeFin(dateString);
                }
            }
        };

        new DatePickerDialog(textView.getContext(), dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
