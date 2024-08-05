package com.example.app_creat_profesionell_cv.ContentOfCV;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_creat_profesionell_cv.Classes.InfoEducation;
import com.example.app_creat_profesionell_cv.Classes.InfoExperience;
import com.example.app_creat_profesionell_cv.Classes.InfoProjet;
import com.example.app_creat_profesionell_cv.DB.Education;
import com.example.app_creat_profesionell_cv.DB.ExperinceDeTravaile;
import com.example.app_creat_profesionell_cv.DB.InfoPersonnel;
import com.example.app_creat_profesionell_cv.DB.InformationAdditionnelle;
import com.example.app_creat_profesionell_cv.DB.Projet;
import com.example.app_creat_profesionell_cv.R;
import com.example.app_creat_profesionell_cv.SectionOfCV.ExperinceActivity;
import com.example.app_creat_profesionell_cv.SectionOfCV.InfoEducationActivity;
import com.example.app_creat_profesionell_cv.SectionOfCV.InfoPersonnelleActivity;
import com.example.app_creat_profesionell_cv.SectionOfCV.InfoProjetActivity;
import com.example.app_creat_profesionell_cv.SectionOfCV.InformationAdditionnelleActivity;
import com.example.app_creat_profesionell_cv.ShowDocument.ShowDocumentActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class M1 extends AppCompatActivity {

    TextView infoPersonnel,éducation,exDeTravaille,projet,infoAdditionnelle;
    Button generateCV;
    //Image Of Checks
    ImageView checkInfoPersonnel,checkEucation,checkExDeTravaille,checkProjet,checkInfoAdditionnelle;
    //

    //DB
    InfoPersonnel dbInfoPersonnelle;
    Education dbInfoEducation;
    ExperinceDeTravaile dbExperienceDeTravaille;
    Projet dbProjet;
    InformationAdditionnelle dbInformationAitionnelle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m1);

        //DB
        dbInfoPersonnelle = new InfoPersonnel(this);
        dbInfoEducation = new Education(this);
        dbExperienceDeTravaille = new ExperinceDeTravaile(this);
        dbProjet = new Projet(this);
        dbInformationAitionnelle = new InformationAdditionnelle(this);
        //

        infoPersonnel = findViewById(R.id.infoPersonnel);
        éducation = findViewById(R.id.éducation);
        exDeTravaille = findViewById(R.id.exDeTravaille);
        projet = findViewById(R.id.projet);
        infoAdditionnelle = findViewById(R.id.infoAdditionnelle);
        generateCV = findViewById(R.id.generateCV);


        checkInfoPersonnel = findViewById(R.id.checkInfoPersonnelles);
        checkEucation = findViewById(R.id.checkEducation);
        checkExDeTravaille = findViewById(R.id.checkExDeTravaile);
        checkProjet = findViewById(R.id.checkProjet);
        checkInfoAdditionnelle = findViewById(R.id.checkInfoAdditionnelle);

        //check DB
        if (!dbInfoPersonnelle.isDatabaseEmpty()){
            checkInfoPersonnel.setVisibility(View.VISIBLE);
        }

        if (!dbInfoEducation.isDatabaseEmpty()){
            checkEucation.setVisibility(View.VISIBLE);
        }

        if (!dbExperienceDeTravaille.isDatabaseEmpty()){
            checkExDeTravaille.setVisibility(View.VISIBLE);
        }

        if (!dbProjet.isDatabaseEmpty()){
            checkProjet.setVisibility(View.VISIBLE);
        }

        if (!dbInformationAitionnelle.isDatabaseEmpty()){
            checkInfoAdditionnelle.setVisibility(View.VISIBLE);
        }
        //

        infoPersonnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M1.this, InfoPersonnelleActivity.class));
            }
        });

        éducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M1.this, InfoEducationActivity.class));
            }
        });
        exDeTravaille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M1.this, ExperinceActivity.class));
            }
        });
        projet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M1.this, InfoProjetActivity.class));
            }
        });

        infoAdditionnelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M1.this, InformationAdditionnelleActivity.class));
            }
        });


        try {
            List<String> languages = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLangueArrayList();
            if (languages != null && languages.size() > 1) {
                Toast.makeText(this, languages.get(1), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No languages available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error fetching language data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        generateCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ShowDocumentActivity
                // Check for permission to write to external storage
                if (ContextCompat.checkSelfPermission(M1.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(M1.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {
                    // Permission is granted, proceed with PDF creation
                    PdfDocument document = createPDF();
                    if (document != null) {
                        ByteArrayOutputStream outputStream = savePDFToByteArray(document);

                        // Start ShowDocumentActivity to display the PDF
                        Intent intent = new Intent(M1.this, ShowDocumentActivity.class);
                        intent.putExtra("pdfByteArray", outputStream.toByteArray());
                        startActivity(intent);

                        // Close the document after starting the activity
                        document.close();
                    }
                }
            }
        });
    }
    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        dbInfoPersonnelle.clearAllData();
        dbInfoEducation.clearAllData();
        dbProjet.clearAllData();
        dbInformationAitionnelle.clearAllData();
        dbExperienceDeTravaille.clearAllData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbInfoPersonnelle.clearAllData();
        dbInfoEducation.clearAllData();
        dbProjet.clearAllData();
        dbInformationAitionnelle.clearAllData();
        dbExperienceDeTravaille.clearAllData();
    }*/












    private static final int START_Y_POSITION = 270;
    private static final int LINE_SPACING = 25;
    private static final int COLUMN_PRICE_X = 50;
    private static final int COLUMN_TVA_X = 150;
    private static final int COLUMN_PRODUCT_X = 250;
    private static final int COLUMN_QUANTITY_X = 400;
    private static final int SIGNATURE_BOX_WIDTH = 200;
    private static final int SIGNATURE_BOX_HEIGHT = 60;
    private static final int IMAGE_SIZE_PERCENT = 20;
    private static final int IMAGE_MARGIN = 50;
    private static final int TABLE_HEADER_COLOR = Color.RED;
    private static final int TOTALS_COLOR = Color.BLUE;
    private static final int BACKGROUND_COLOR = Color.GREEN;


    private PdfDocument createPDF() {
        if (!isExternalStorageWritable()) {
            showToastOnUiThread("External storage not writable");
            return null;
        }

        PdfDocument document = new PdfDocument();
        PdfDocument.Page page = null;

        try {
            PdfDocument.PageInfo pageInfo = createPageInfo();
            page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            Paint paint = createPaint();

            // Draw the background color
            Paint backgroundPaint = new Paint();
            backgroundPaint.setColor(Color.parseColor("#DBEFFF"));
            canvas.drawRect(0, 0, pageInfo.getPageWidth(), 200, backgroundPaint); // Draw rectangle with full width and height of 200

            // Draw header image
            drawContactInfo(canvas,220);
            drawVerticalLine(canvas, 200, canvas.getHeight());
            // Draw Competence and get the Y position where the next section should start
            int competenceEndY = drawCompetence(canvas, 220);

            // Draw Experience de Travail section starting just below Competence
            drawExperienceDeTravail(canvas, competenceEndY + 30);

            int experienceEndY = drawExperienceDeTravail(canvas, competenceEndY + 30); // Adding extra margin

            // Draw Projet section starting just below Experience de Travail
            drawProjet(canvas, experienceEndY + 30);
            int projetEndY = drawProjet(canvas, experienceEndY + 30);
            drawEducation(canvas, projetEndY + 30);


            drawLanguages(canvas, 400);

            // Draw user name and job title
            int logoBottomY =100;
            drawUserName(canvas, logoBottomY);

            Paint paintForJobTitle = new Paint();
            paintForJobTitle.setColor(Color.GRAY);
            paintForJobTitle.setTextSize(40);
            paintForJobTitle.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

            // Draw job title
            drawUserJobTitle(canvas, (canvas.getWidth() - paint.measureText(dbInfoPersonnelle.getInfo().get(0).getJob())) / 2, logoBottomY - 50);

            document.finishPage(page); // Finish the page before returning

            // Don't close the document here, close it after saving or sending
            return document;

        } catch (Exception e) {
            e.printStackTrace();
            showToastOnUiThread("Error creating PDF: " + e.getMessage());
            if (page != null) {
                document.finishPage(page); // Finish the page in case of an error
            }
            document.close(); // Close the document in case of an error
            return null;
        }
    }


    private int drawProjet(Canvas canvas, int startY) {
        // Create and configure Paint object for heading
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20); // Heading text size
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface

        // Define margins and spacing
        final int marginLeft = 260;
        final int headingMarginBottom = 10; // Space below the heading
        final int elementMarginTop = 25; // Reduced margin between line and first element
        final int lineHeight = 25; // Space between lines

        // Draw the "Projet" heading
        String heading = "Projet".toUpperCase();
        float headingY = startY + paint.getTextSize();  // Y position for heading
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Measure the width of the heading text
        float headingWidth = paint.measureText(heading);

        // Draw a line directly below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2); // Line width
        canvas.drawLine(marginLeft, headingY + headingMarginBottom,
                marginLeft + headingWidth, headingY + headingMarginBottom, linePaint);

        // Retrieve project information from database
        ArrayList<InfoProjet> projectInfo;
        try {
            projectInfo = dbProjet.getAllInfoProjets();
        } catch (Exception e) {
            // Handle the exception (e.g., log the error)
            return startY; // Return the initial Y position if there's an error
        }

        // Configure Paint object for project information text
        Paint normalPaint = new Paint();
        normalPaint.setColor(Color.BLACK);
        normalPaint.setTextSize(10); // Text size for project information
        normalPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Normal typeface

        // Configure Paint object for project title and subject
        Paint bigPaint = new Paint();
        bigPaint.setColor(Color.BLACK);
        bigPaint.setTextSize(15); // Text size for title and subject
        bigPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Bold typeface

        // Calculate Y offset for project information
        int yOffset = (int) (headingY + headingMarginBottom + elementMarginTop);

        // Draw each piece of project information
        for (InfoProjet info : projectInfo) {
            // Draw the project title
            if (info.getTitreProjet() != null) {
                String formattedTitle = capitalizeFirstLetterOfEachWord(info.getNomEntreprise());
                canvas.drawText(formattedTitle, marginLeft, yOffset, bigPaint);
                yOffset += lineHeight;
            }

            // Draw the duration (start date and end date)
            if (info.getDateDebut() != null && info.getDateFin() != null) {
                String duration = info.getDateDebut() + " - " + info.getDateFin();
                canvas.drawText(duration, marginLeft, yOffset, normalPaint);
                yOffset += lineHeight;
            } else {
                // Draw the start date separately if end date is not available
                if (info.getDateDebut() != null) {
                    canvas.drawText("Start Date: " + info.getDateDebut(), marginLeft, yOffset, normalPaint);
                    yOffset += lineHeight;
                }

                // Draw the end date separately if start date is not available
                if (info.getDateFin() != null) {
                    canvas.drawText("End Date: " + info.getDateFin(), marginLeft, yOffset, normalPaint);
                    yOffset += lineHeight;
                }
            }

            // Draw the project subject (sujet)
            String formattedTitle = capitalizeFirstLetterOfEachWord(info.getTitreProjet());
            canvas.drawText(formattedTitle, marginLeft, yOffset, bigPaint);
            yOffset += lineHeight;

            // Draw the project resume with wrapping
            if (info.getResume() != null) {
                String resumeText = info.getResume();
                // Add text wrapping logic if needed
                canvas.drawText(resumeText, marginLeft, yOffset, normalPaint);
                yOffset += lineHeight + 20; // Additional space after the resume
            }

            // Add space between different projects
            yOffset += elementMarginTop;
        }

        // Return the bottom Y position of this section
        return yOffset;
    }








    private void drawContactInfo(Canvas canvas, int startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20); // Increased text size for the heading
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface for the heading

        int marginLeft = 60; // Left margin for text (considering icon width)
        int headingMarginBottom = 10; // Space below the heading
        int elementMarginTop = 15; // Space between line and first element
        int lineHeight = 50; // Space between lines
        int iconMarginRight = 10; // Space between icon and text

        // Load icons from resources
        Bitmap phoneIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_phone);
        Bitmap emailIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_email);
        Bitmap addressIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_linkdin);

        // Calculate the new left margin to align heading with the phone icon
        int iconWidth = phoneIcon.getWidth();
        int newMarginLeft = marginLeft - iconWidth - iconMarginRight;

        // Draw the "Contact" heading
        String heading = "Contact".toUpperCase();
        float headingY = startY + paint.getTextSize(); // Y position for heading
        canvas.drawText(heading, newMarginLeft, headingY, paint);

        // Measure the width of the heading text
        float headingWidth = paint.measureText(heading);

        // Draw a line directly below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2); // Line width
        canvas.drawLine(newMarginLeft, headingY + headingMarginBottom, newMarginLeft + headingWidth, headingY + headingMarginBottom, linePaint);

        // Contact information text and corresponding icons
        String[] contactInfo = {
                dbInfoPersonnelle.getInfo().get(0).getN_phone(),
                dbInfoPersonnelle.getInfo().get(0).getEmail(),
                dbInfoPersonnelle.getInfo().get(0).getPays()
        };
        Bitmap[] icons = {phoneIcon, emailIcon, addressIcon};

        // Draw each line of contact information with its icon
        for (int i = 0; i < contactInfo.length; i++) {
            // Draw icon
            if (icons[i] != null) {
                float iconY = headingY + headingMarginBottom + elementMarginTop-10 + paint.getTextSize() + lineHeight * i - icons[i].getHeight() / 2;
                canvas.drawBitmap(icons[i], newMarginLeft, iconY, null);
            }

            // Draw text
            paint.setTextSize(10); // Reset text size for contact information
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Reset typeface for contact information
            float textY = headingY + headingMarginBottom + elementMarginTop + paint.getTextSize() + lineHeight * i;
            canvas.drawText(contactInfo[i], marginLeft, textY, paint);
        }
    }



    private int drawCompetence(Canvas canvas, int startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        int marginLeft = 260;
        int headingMarginBottom = 10;
        int elementMarginTop = 8;
        int lineHeight = 25;

        // Draw the "Compétence" heading
        String heading = "Compétence".toUpperCase();
        float headingY = startY + paint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Draw a line directly below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2);
        canvas.drawLine(marginLeft, headingY + headingMarginBottom, marginLeft + paint.measureText(heading), headingY + headingMarginBottom, linePaint);

        // Competence information text (dummy data here; replace with actual data)
        ArrayList<String> competenceInfo = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getCompetanceArrayList();

        // Draw each line of competence information
        for (int i = 0; i < competenceInfo.size(); i++) {
            paint.setTextSize(15);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            float textY = headingY + headingMarginBottom + elementMarginTop + paint.getTextSize() + lineHeight * i;
            canvas.drawText(competenceInfo.get(i), marginLeft, textY, paint);
        }

        // Return the bottom Y position of this section
        return (int) (headingY + headingMarginBottom + elementMarginTop + paint.getTextSize() + lineHeight * competenceInfo.size());
    }



    private int drawExperienceDeTravail(Canvas canvas, int startY) {
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint normalPaint = new Paint();
        normalPaint.setColor(Color.BLACK);
        normalPaint.setTextSize(15);
        normalPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        int marginLeft = 260;
        int headingMarginBottom = 10;
        int elementMarginTop = 10;
        int lineHeight = 25;

        // Draw the "Expérience de Travail" heading
        String heading = "Expérience de Travail".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Draw a line directly below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2);
        canvas.drawLine(marginLeft, headingY + headingMarginBottom,
                marginLeft + headingPaint.measureText(heading), headingY + headingMarginBottom, linePaint);

        // Retrieve experience information from database
        ArrayList<InfoExperience> experienceInfo = dbExperienceDeTravaille.getAllInfoExperience();

        // Calculate the Y offset for experience information
        float yOffset = headingY + headingMarginBottom + elementMarginTop;

        // Draw each piece of experience information
        for (InfoExperience info : experienceInfo) {
            // Draw the company name
            canvas.drawText(info.getNomEntreprise(), marginLeft, yOffset, normalPaint);
            yOffset += lineHeight;

            // Draw the job title
            canvas.drawText(info.getTitreDePoste(), marginLeft, yOffset, normalPaint);
            yOffset += lineHeight;

            // Draw the start date
            canvas.drawText("" + info.getDateDébut(), marginLeft, yOffset, normalPaint);
            yOffset += lineHeight;

            // Draw the end date
            canvas.drawText(info.getDateDeFin(), marginLeft, yOffset, normalPaint);
            yOffset += lineHeight;

            // Draw the resume
            canvas.drawText(info.getRésumé(), marginLeft, yOffset, normalPaint);
            yOffset += lineHeight + 20; // Add extra space after resume

            // Add space between different experiences
            yOffset += elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) yOffset;
    }



    private int drawEducation(Canvas canvas, int startY) {
        // Create and configure the Paint object for heading
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for lines
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2);

        // Create and configure the Paint object for school names (big and black)
        Paint schoolNamePaint = new Paint();
        schoolNamePaint.setColor(Color.BLACK);
        schoolNamePaint.setTextSize(18); // Adjust size as needed
        schoolNamePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for date ranges (small and gray)
        Paint dateRangePaint = new Paint();
        dateRangePaint.setColor(Color.BLACK);

        dateRangePaint.setTextSize(14); // Adjust size as needed
        dateRangePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Create and configure the Paint object for professions (normal)
        Paint professionPaint = new Paint();
        professionPaint.setColor(Color.BLACK);
        professionPaint.setTextSize(15); // Adjust size as needed
        professionPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = 260;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;

        // Draw the "Éducation" heading
        String heading = "Éducation".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Draw a line below the heading
        float lineEndX = marginLeft + headingPaint.measureText(heading);
        canvas.drawLine(marginLeft, headingY + headingMarginBottom, lineEndX, headingY + headingMarginBottom, linePaint);

        // Get the education information
        ArrayList<InfoEducation> educationInfo = dbInfoEducation.getAllInfoEducation();

        // Draw each piece of education information
        float currentY = headingY + headingMarginBottom + elementMarginTop;
        for (InfoEducation info : educationInfo) {
            // Draw school name (big and black)
            canvas.drawText(info.getShool().toUpperCase(), marginLeft, currentY, schoolNamePaint);

            // Draw date range (small and gray)
            currentY += lineHeight; // Move to next line
            String dateRange = info.getStartYier() + " - " + info.getEndYier();
            canvas.drawText(dateRange, marginLeft, currentY, dateRangePaint);

            // Draw profession or title (normal)
            currentY += lineHeight; // Move to next line
            canvas.drawText(info.getMetier(), marginLeft, currentY, professionPaint);

            // Adjust Y position for next education item
            currentY += lineHeight;
        }

        // Return the bottom Y position of this section
        return (int) (currentY + elementMarginTop);
    }

    private void drawLanguages(Canvas canvas, int startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20); // Text size for the heading
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface for the heading

        int marginLeft = 24; // Left margin for text
        int headingMarginBottom = 10; // Space below the heading
        int elementMarginTop = 15; // Space between line and first element
        int lineHeight = 25; // Space between lines

        // Draw the "Languages" heading
        String heading = "Languages".toUpperCase();
        float headingY = startY + paint.getTextSize() + 50; // Y position for heading
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Measure the width of the heading text
        float headingWidth = paint.measureText(heading);

        // Draw a line directly below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2); // Line width
        canvas.drawLine(marginLeft, headingY + headingMarginBottom, marginLeft + headingWidth, headingY + headingMarginBottom, linePaint);

        // Language information text
        ArrayList<String> languages = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLangueArrayList();

        // Draw each language
        for (int i = 0; i < languages.size(); i++) {
            paint.setTextSize(15); // Reset text size for languages
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Reset typeface for languages
            float textY = headingY + headingMarginBottom + elementMarginTop + paint.getTextSize() + lineHeight * i;
            canvas.drawText(languages.get(i), marginLeft, textY, paint);
        }

        // Draw the "Soft Skills" heading below the languages
        float softSkillsHeadingY = headingY + headingMarginBottom + elementMarginTop + paint.getTextSize() + lineHeight * languages.size() + 40; // Adjust Y position
        canvas.drawText("Soft Skills".toUpperCase(), marginLeft, softSkillsHeadingY, paint);

        // Measure the width of the Soft Skills heading text
        float softSkillsHeadingWidth = paint.measureText("Soft Skills");

        // Draw a line directly below the Soft Skills heading
        canvas.drawLine(marginLeft, softSkillsHeadingY + headingMarginBottom, marginLeft + softSkillsHeadingWidth, softSkillsHeadingY + headingMarginBottom, linePaint);

        // Soft skills information text
        ArrayList<String> softSkills = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getSoftSkillsArrayList();

        // Draw each soft skill
        for (int i = 0; i < softSkills.size(); i++) {
            paint.setTextSize(15); // Reset text size for soft skills
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Reset typeface for soft skills
            float textY = softSkillsHeadingY + headingMarginBottom + elementMarginTop + paint.getTextSize() + lineHeight * i;
            canvas.drawText(softSkills.get(i), marginLeft, textY, paint);
        }

        // Draw the "Loisirs" heading below the soft skills
        float loisirsHeadingY = softSkillsHeadingY + headingMarginBottom + elementMarginTop + paint.getTextSize() + lineHeight * softSkills.size() + 40; // Adjust Y position
        canvas.drawText("Loisirs".toUpperCase(), marginLeft, loisirsHeadingY, paint);

        // Measure the width of the Loisirs heading text
        float loisirsHeadingWidth = paint.measureText("Loisirs");

        // Draw a line directly below the Loisirs heading
        canvas.drawLine(marginLeft, loisirsHeadingY + headingMarginBottom, marginLeft + loisirsHeadingWidth, loisirsHeadingY + headingMarginBottom, linePaint);

        // Loisirs information text
        ArrayList<String> loisirs = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLoisirArrayList();

        // Draw each loisir
        for (int i = 0; i < loisirs.size(); i++) {
            paint.setTextSize(15); // Reset text size for loisirs
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Reset typeface for loisirs
            float textY = loisirsHeadingY + headingMarginBottom + elementMarginTop + paint.getTextSize() + lineHeight * i;
            canvas.drawText(loisirs.get(i), marginLeft, textY, paint);
        }
    }


    private ByteArrayOutputStream savePDFToByteArray(PdfDocument document) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            document.writeTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            showToastOnUiThread("Error saving PDF to byte array: " + e.getMessage());
        }
        return outputStream;
    }


    private void showToastOnUiThread(final String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private void savePDF(PdfDocument document) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "NEWPDFInvoice.pdf");
            document.writeTo(new FileOutputStream(file));
            showToastOnUiThread("PDF created successfully: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            showToastOnUiThread("Error saving PDF: " + e.getMessage());
        }
    }



    private Paint createPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(14);
        return paint;
    }

    private PdfDocument.PageInfo createPageInfo() {
        int contentHeight = 1000;
        int contentWidth = 610; // Or calculate based on content if needed

        // Adjust the height to include some padding or margins
        int pageHeight = contentHeight + 200; // Add some padding or margins
        int pageWidth = contentWidth; // Use the content width or adjust as needed

        return new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
    }



    private void drawUserName(Canvas canvas, int logoBottomY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(40); // Adjust text size as needed
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Set text style

        // Fetch the user's name
        String userName = dbInfoPersonnelle.getInfo().get(0).getL_name().toUpperCase() + " " + capitalizeFirstLetterOfEachWord(dbInfoPersonnelle.getInfo().get(0).getF_name());
        float textWidth = paint.measureText(userName);

        // Calculate the position for the text (center horizontally)
        float textX = (canvas.getWidth() - textWidth) / 2;

        // Draw the text at the calculated position
        canvas.drawText(userName, textX, logoBottomY, paint); // Adjust vertical position as needed
    }


    private void drawUserJobTitle(Canvas canvas, float nameTextX, float nameTextY) {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY); // Set a different color for job title
        paint.setTextSize(30); // Adjust text size for job title
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Set text style

        // Fetch the user's job title
        String userJobTitle = capitalizeFirstLetterOfEachWord(dbInfoPersonnelle.getInfo().get(0).getJob());
        float jobTitleWidth = paint.measureText(userJobTitle);

        // Define a fixed vertical space below the user's name
        float spaceBelowName = 10; // Adjust this value as needed

        // Calculate the vertical position for the job title
        float jobTitleTextY = 145;

        // Calculate the horizontal position for the job title (center horizontally)
        float jobTitleTextX = (canvas.getWidth() - jobTitleWidth) / 2;

        // Draw the job title at the calculated position
        canvas.drawText(userJobTitle, jobTitleTextX, jobTitleTextY, paint);
    }





    public static String capitalizeFirstLetterOfEachWord(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        String[] words = str.split("\\s+");
        StringBuilder capitalizedStr = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                capitalizedStr.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    capitalizedStr.append(word.substring(1).toLowerCase());
                }
                capitalizedStr.append(" ");
            }
        }

        // Remove the trailing space
        return capitalizedStr.toString().trim();
    }



    private void drawVerticalLine(Canvas canvas, int startY, int endY) {
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK); // Set the color for the line
        linePaint.setStrokeWidth(2); // Set the width of the line

        // Define the X position for the vertical line (from the left margin or edge)
        int lineX = 200; // X position for the vertical line

        // Draw the vertical line from startY to endY
        canvas.drawLine(lineX, startY, lineX, endY, linePaint);
    }




}