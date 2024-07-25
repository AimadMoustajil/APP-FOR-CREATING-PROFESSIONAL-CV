package com.example.app_creat_profesionell_cv.ContentOfCV;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.app_creat_profesionell_cv.R;

public class ContentOfInformationCv extends AppCompatActivity {

    Button generateCv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_of_information_cv);
        generateCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContentOfInformationCv.this, PDFCV.class));
            }
        });
    }
}