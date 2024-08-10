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

        holder.nameOfSchool.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                infoEducation.setShool(s.toString());
            }
        });

        holder.yourMetier.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                infoEducation.setMetier(s.toString());
            }
        });

        holder.start.setOnClickListener(v -> showDatePickerDialog(holder.start, infoEducation, true));
        holder.Finich.setOnClickListener(v -> showDatePickerDialog(holder.Finich, infoEducation, false));
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

    private void showDatePickerDialog(final TextView textView, final InfoEducation infoEducation, final boolean isStartDate) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
                String dateString = dateFormat.format(calendar.getTime());

                textView.setText(dateString);
                if (isStartDate) {
                    infoEducation.setStartYier(dateString);
                } else {
                    infoEducation.setEndYier(dateString);
                }
            }
        };

        new DatePickerDialog(textView.getContext(), dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

}
