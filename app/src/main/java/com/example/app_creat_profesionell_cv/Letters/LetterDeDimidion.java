package com.example.app_creat_profesionell_cv.Letters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app_creat_profesionell_cv.Adapters.AdapterOfLettreDeDémission;
import com.example.app_creat_profesionell_cv.R;
import com.example.app_creat_profesionell_cv.ShowDocument.ShowDocumentActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class LetterDeDimidion extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> stringArrayList;
    AdapterOfLettreDeDémission adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_de_dimidion);
        stringArrayList = new ArrayList<>();

        stringArrayList.add("One Week Notice : Sales Exevutive");
        stringArrayList.add("Sample 1: Two Week Notice");
        stringArrayList.add("Sample 2: Two Week Notice");
        stringArrayList.add("Sample 3: Two Week Notice");
        stringArrayList.add("Sample 1: One Month Notice");
        stringArrayList.add("Sample 1:Short Resignation");
        stringArrayList.add("Sample 2:Short Resignation");
        stringArrayList.add("Sample 1:Simple Resignation");
        stringArrayList.add("Sample 2:Simple Resignation");
        stringArrayList.add("Formal Resignation");
        stringArrayList.add("Sample 1:Professional Resignation");
        stringArrayList.add("Sample 2:Professional Resignation");
        stringArrayList.add("Sample 3:Professional Resignation");
        stringArrayList.add("Teacher Resignation");
        stringArrayList.add("Nurse Resignation");
        stringArrayList.add("Board Resignation");
        stringArrayList.add("Police Resignation");
        stringArrayList.add("Church Resignation");
        stringArrayList.add("Career Change Resignation");
        stringArrayList.add("Promotion Letter of Resignation");
        stringArrayList.add("Accountant Resignation");
        stringArrayList.add("Analyst Resignation");
        stringArrayList.add("Customer Service Manager Resignation");
        stringArrayList.add("Data Analyst Resignation");
        stringArrayList.add("Contant Creator Resignation");
        stringArrayList.add("Bank Employee Resignation");
        stringArrayList.add("Sample 1: Immediate Resignation");
        stringArrayList.add("Sample 2: Immediate Resignation");
        stringArrayList.add("Medical Assistant Resignation");
        stringArrayList.add("New Job Resignation");
        stringArrayList.add("Email Resignation");
        stringArrayList.add("Employee Resignation");
        stringArrayList.add("Personal Reason Resignation");
        stringArrayList.add("Assistant Sales Manager Resignation Resignation");
        stringArrayList.add("Doctor Resignation");
        stringArrayList.add("Writer Resignation Letter");
        stringArrayList.add("Technical Executive Resignation");
        stringArrayList.add("Senior Sales Executive Resignation");
        stringArrayList.add("Retirement Resignation");
        stringArrayList.add("Marketing Manager With Reasons");
        stringArrayList.add("Marketing Manager");
        stringArrayList.add("Director Resignation");
        stringArrayList.add("Assistant Manager Of Operation");
        stringArrayList.add("Administrative Assistant");
        stringArrayList.add("Board Of Industry Resignation");
        stringArrayList.add("Lecture Resignation");
        stringArrayList.add("Sales Manager and Marketing");
        stringArrayList.add("Deputy Manager (HR)");
        stringArrayList.add("Sales Support");
        stringArrayList.add("Senior Assistant Accountant");
        stringArrayList.add("Web Designer");

        recyclerView = findViewById(R.id.recOfLetterDeDémission);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterOfLettreDeDémission(stringArrayList,this);
        recyclerView.setAdapter(adapter);

    }
}