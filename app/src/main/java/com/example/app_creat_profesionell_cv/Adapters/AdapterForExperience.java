package com.example.app_creat_profesionell_cv.Adapters;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
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
import java.util.ArrayList;
import java.util.Locale;

public class AdapterForExperience extends RecyclerView.Adapter<AdapterForExperience.ViewHolder> {
    ArrayList<InfoExperience> infoExperienceArrayList;

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

        holder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(holder.start);
            }
        });

        holder.Finich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(holder.Finich);
            }
        });
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
            nameEntreprise = itemView.findViewById(R.id.nameOfSchool);
            titreDeJob = itemView.findViewById(R.id.yourMetier);
            Résumé = itemView.findViewById(R.id.rs);
            start = itemView.findViewById(R.id.start);
            Finich = itemView.findViewById(R.id.Finich);
        }
    }

    private void showDatePickerDialog(final TextView textView) {
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
            }
        };

        new DatePickerDialog(textView.getContext(), dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
