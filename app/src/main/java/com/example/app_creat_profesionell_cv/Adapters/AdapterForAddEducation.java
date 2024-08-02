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

import com.example.app_creat_profesionell_cv.Classes.InfoEducation;
import com.example.app_creat_profesionell_cv.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterForAddEducation extends RecyclerView.Adapter<AdapterForAddEducation.ViewHolder> {

    private ArrayList<InfoEducation> educations;

    public AdapterForAddEducation(ArrayList<InfoEducation> educations) {
        this.educations = educations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_for_education, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InfoEducation infoEducation = educations.get(position);
        holder.nameOfSchool.setText(infoEducation.getShool());
        holder.yourMetier.setText(infoEducation.getMetier());
        holder.start.setText(infoEducation.getStartYier());
        holder.Finich.setText(infoEducation.getEndYier());

        holder.start.setOnClickListener(v -> showDatePickerDialog(holder.start));
        holder.Finich.setOnClickListener(v -> showDatePickerDialog(holder.Finich));
    }

    @Override
    public int getItemCount() {
        return educations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        EditText nameOfSchool, yourMetier;
        TextView start, Finich;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameOfSchool = itemView.findViewById(R.id.nameOfSchool);
            yourMetier = itemView.findViewById(R.id.yourMetier);
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
