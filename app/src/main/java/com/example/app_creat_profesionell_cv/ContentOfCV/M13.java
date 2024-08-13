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
import com.example.app_creat_profesionell_cv.Classes.InfoPersonnelle;
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

public class M13 extends AppCompatActivity {

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
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m13);
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
                startActivity(new Intent(M13.this, InfoPersonnelleActivity.class));
            }
        });

        éducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M13.this, InfoEducationActivity.class));
            }
        });
        exDeTravaille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M13.this, ExperinceActivity.class));
            }
        });
        projet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M13.this, InfoProjetActivity.class));
            }
        });

        infoAdditionnelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M13.this, InformationAdditionnelleActivity.class));
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
                if (ContextCompat.checkSelfPermission(M13.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(M13.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {
                    // Permission is granted, proceed with PDF creation
                    PdfDocument document = createPDF();
                    if (document != null) {
                        ByteArrayOutputStream outputStream = savePDFToByteArray(document);

                        // Start ShowDocumentActivity to display the PDF
                        Intent intent = new Intent(M13.this, ShowDocumentActivity.class);
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

            //drawBlueVerticalLine(canvas);
            drawBlueBackground(canvas);
            //drawGreenBackground(canvas);
            // Draw header image
            drawContactInfo(canvas,220);
            // Draw Competence and get the Y position where the next section should start
            int competenceEndY = drawCompetence(canvas, 220);

            int skillEnfY = drawSkills(canvas,competenceEndY+30);
            // Draw Experience de Travail section starting just below Competence
            //drawExperienceDeTravail(canvas, skillEnfY + 30);

            int experienceEndY = drawExperienceDeTravail(canvas, skillEnfY + 30); // Adding extra margin

            // Draw Projet section starting just below Experience de Travail
            drawProjet(canvas, experienceEndY + 30);
            int projetEndY = drawProjet(canvas, experienceEndY + 30);
            drawEducation(canvas, projetEndY + 30);


            drawLanguages(canvas, 410);


            drawGreenBackground(canvas);
            // Draw user name and job title
            int logoBottomY = IMAGE_MARGIN + (int) (canvas.getWidth() * IMAGE_SIZE_PERCENT / 100);
            drawUserName(canvas);

            Paint paintForJobTitle = new Paint();
            paintForJobTitle.setColor(Color.BLACK);
            paintForJobTitle.setTextSize(40);
            paintForJobTitle.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

            // Draw job title
            drawUserJobTitle(canvas);

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
        paint.setColor(Color.rgb(57, 57, 57));
        paint.setTextSize(20); // Heading text size
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface

        Paint titleT = new Paint();
        titleT.setColor(Color.rgb(57, 57, 57));
        titleT.setTextSize(15); // Heading text size
        titleT.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Bold typeface

        // Define margins and spacing
        final int marginLeft = 260;
        final int bulletMarginLeft = 270; // Additional margin for bullet points
        final int headingMarginBottom = 10; // Space below the heading
        final int elementMarginTop = 25; // Reduced margin between line and first element
        final int lineHeight = 25; // Space between lines
        final int bulletSize = 15; // Size of bullet point
        final int bulletTextMargin = 5; // Margin between bullet and text
        final int smallMarginTop = 10; // Small margin before resume text
        final int resumeMaxWidth = 300; // Maximum width for resume text

        // Draw the "Projet" heading
        String heading = "Projet".toUpperCase();
        float headingY = startY + paint.getTextSize() - 30;  // Y position for heading
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Measure the width of the heading text
        float headingWidth = paint.measureText(heading);

        // Retrieve project information from database
        ArrayList<InfoProjet> projectInfo;
        try {
            projectInfo = dbProjet.getAllInfoProjets();
        } catch (Exception e) {
            // Handle the exception (e.g., log the error)
            return startY; // Return the initial Y position if there's an error
        }

        // Configure Paint object for project information text
        Paint grayPaint = new Paint();
        grayPaint.setColor(Color.rgb(57, 57, 57)); // Gray text color
        grayPaint.setTextSize(15); // Text size for project information
        grayPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Normal typeface

        // Configure Paint object for project title
        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.rgb(57,57,57)); // Gray text color
        titlePaint.setTextSize(15); // Text size for title
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Bold typeface

        // Configure Paint object for bullet point
        Paint bulletPaint = new Paint();
        bulletPaint.setColor(Color.rgb(57, 57, 57)); // Gray text color
        bulletPaint.setTextSize(bulletSize); // Bullet point size

        // Calculate Y offset for project information
        int yOffset = (int) (headingY + headingMarginBottom + elementMarginTop);

        // Draw each piece of project information
        for (InfoProjet info : projectInfo) {
            boolean hasContent = false;

            // Check if any content is present
            if (info.getNomEntreprise() != null && !info.getNomEntreprise().trim().isEmpty()) {
                hasContent = true;
                String formattedCompanyName = capitalizeFirstLetterOfEachWord(info.getNomEntreprise());
                canvas.drawText(formattedCompanyName, marginLeft, yOffset, titleT);
                yOffset += lineHeight;
            }

            if (info.getTitreProjet() != null && !info.getTitreProjet().trim().isEmpty() ||
                    info.getDateDebut() != null && !info.getDateDebut().trim().isEmpty() ||
                    info.getDateFin() != null && !info.getDateFin().trim().isEmpty()) {
                hasContent = true;
                String formattedTitle = info.getTitreProjet() != null ? capitalizeFirstLetterOfEachWord(info.getTitreProjet()) : "";
                String dates = (info.getDateDebut() != null ? info.getDateDebut() : "") +
                        (info.getDateFin() != null ? " - " + info.getDateFin() : "");
                canvas.drawText(formattedTitle + " " + dates, marginLeft, yOffset, titlePaint);
                yOffset += lineHeight;
            }

            if (info.getResume() != null && !info.getResume().trim().isEmpty()) {
                hasContent = true;
                String resumeText = info.getResume();
                // Draw the bullet point with additional margin
                canvas.drawText("•", bulletMarginLeft, yOffset, bulletPaint);

                // Handle text wrapping
                Paint.FontMetrics fontMetrics = grayPaint.getFontMetrics();
                float x = bulletMarginLeft + bulletSize + bulletTextMargin;
                float lineHeightF = fontMetrics.descent - fontMetrics.ascent;
                float y = yOffset;

                String[] words = resumeText.split(" ");
                StringBuilder line = new StringBuilder();
                for (String word : words) {
                    String testLine = line + word + " ";
                    float testWidth = grayPaint.measureText(testLine);
                    if (testWidth > resumeMaxWidth) {
                        // Draw the line and start a new one
                        canvas.drawText(line.toString(), x, y, grayPaint);
                        y += lineHeightF;
                        line = new StringBuilder(word + " ");
                    } else {
                        line.append(word).append(" ");
                    }
                }
                // Draw the last line
                if (line.length() > 0) {
                    canvas.drawText(line.toString(), x, y, grayPaint);
                    y += lineHeightF;
                }

                yOffset = (int) (y + smallMarginTop); // Update yOffset for the next project
            } else {
                // If resume is empty, adjust yOffset to account for missing resume
                yOffset += 20;
            }

            // Add space between different projects only if there was content
            if (hasContent) {
                yOffset += elementMarginTop;
            }
        }

        // Return the bottom Y position of this section
        return yOffset;
    }





    private void drawGreenBackground(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(	57	,57	,58)); // Set the background color to green

        // Define the rectangle area to fill
        int startX = 0; // Start x position
        int startY = 0; // Start y position (top of the canvas)
        int endX = canvas.getWidth(); // End x position (full width of the canvas)
        int endY = 200; // End y position (200 pixels from the top)

        // Draw the rectangle
        canvas.drawRect(startX, startY, endX, endY, paint);
    }









    private void drawContactInfo(Canvas canvas, int startY) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(57,57,57));
        paint.setTextSize(20); // Increased text size for the heading
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface for the heading

        int marginLeft = 60; // Left margin for text (considering icon width)
        int headingMarginBottom = 10; // Space below the heading
        int elementMarginTop = 15; // Space between line and first element
        int lineHeight = 50; // Space between lines
        int iconMarginRight = 10; // Space between icon and text

        // Load icons from resources
        Bitmap phoneIcon = BitmapFactory.decodeResource(getResources(), R.drawable.telephone);
        Bitmap emailIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_email);
        Bitmap addressIcon = BitmapFactory.decodeResource(getResources(), R.drawable.social);
        Bitmap placeholderIcon = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);

        // Calculate the new left margin to align heading with the phone icon
        int iconWidth = phoneIcon.getWidth();
        int newMarginLeft = marginLeft - iconWidth - iconMarginRight;

        // Draw the "Contact" heading
        String heading = "Contact".toUpperCase();
        float headingY = startY + paint.getTextSize(); // Y position for heading
        canvas.drawText(heading, newMarginLeft, headingY, paint);

        // Contact information text and corresponding icons
        String[] contactInfo = {
                dbInfoPersonnelle.getInfo().get(0).getN_phone(),
                dbInfoPersonnelle.getInfo().get(0).getEmail(),
                dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLinkdin(),
                dbInfoPersonnelle.getInfo().get(0).getPays() + "," + dbInfoPersonnelle.getInfo().get(0).getVille()
        };
        Bitmap[] icons = {phoneIcon, emailIcon, addressIcon, placeholderIcon};

        // Filter out empty contact information
        ArrayList<String> filteredContactInfo = new ArrayList<>();
        ArrayList<Bitmap> filteredIcons = new ArrayList<>();

        for (int i = 0; i < contactInfo.length; i++) {
            if (contactInfo[i] != null && !contactInfo[i].trim().isEmpty()) {
                filteredContactInfo.add(contactInfo[i]);
                filteredIcons.add(icons[i]);
            }
        }

        // Draw each line of contact information with its icon
        paint.setTextSize(10); // Reset text size for contact information
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Reset typeface for contact information

        float textY = headingY + headingMarginBottom + elementMarginTop;
        for (int i = 0; i < filteredContactInfo.size(); i++) {
            // Draw icon
            Bitmap icon = filteredIcons.get(i);
            if (icon != null) {
                float iconY = textY - icon.getHeight() / 2;
                canvas.drawBitmap(icon, newMarginLeft, iconY, null);
            }

            // Draw text
            canvas.drawText(filteredContactInfo.get(i), marginLeft, textY, paint);
            textY += lineHeight;
        }
    }



    private int drawCompetence(Canvas canvas, int startY) {
        // Create and configure Paint object for heading
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.rgb(57,57,57));
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure Paint object for competence text
        Paint textPaint = new Paint();
        textPaint.setColor(Color.rgb(57,57,57));
        textPaint.setTextSize(15);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = 260;
        int headingMarginBottom = 10;
        int elementMarginTop = 20;
        int lineHeight = 25;
        int competenceMaxWidth = 300; // Maximum width for competence text

        // Draw the "Compétence" heading
        String heading = "Compétence".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);


        // Competence information text (dummy data here; replace with actual data)
        ArrayList<InfoPersonnelle> aboutUser = dbInfoPersonnelle.getInfo();

        // Draw each line of competence information with text wrapping
        int yOffset = (int) (headingY + headingMarginBottom + elementMarginTop);

        for (InfoPersonnelle info : aboutUser) {
            String aboutText = info.getAbout();
            // Handle text wrapping
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            float x = marginLeft;
            float y = yOffset;

            String[] words = aboutText.split(" ");
            StringBuilder line = new StringBuilder();
            for (String word : words) {
                String testLine = line + word + " ";
                float testWidth = textPaint.measureText(testLine);
                if (testWidth > competenceMaxWidth) {
                    // Draw the line and start a new one
                    canvas.drawText(line.toString(), x, y, textPaint);
                    y += lineHeight;
                    line = new StringBuilder(word + " ");
                } else {
                    line.append(word).append(" ");
                }
            }
            // Draw the last line
            if (line.length() > 0) {
                canvas.drawText(line.toString(), x, y, textPaint);
                y += lineHeight;
            }

            // Update yOffset for the next competence entry
            yOffset = (int) (y + elementMarginTop);
        }

        // Return the bottom Y position of this section
        return yOffset;
    }


    private int drawSkills(Canvas canvas, int startY) {
        // Create and configure the Paint object for the skills heading (bold and big)
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.rgb(57,57,57));
        headingPaint.setTextSize(20); // Adjust size as needed
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for skill items (normal)
        Paint skillPaint = new Paint();
        skillPaint.setColor(Color.rgb(57,57,  57));
        skillPaint.setTextSize(15); // Adjust size as needed
        skillPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = 260;
        int headingMarginBottom = 10;

        // Get the skills information
        ArrayList<String> skillsInfo = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getCompetanceArrayList(); // Assuming a method to fetch skills info

        // If there are no skills, do not draw this section
        if (skillsInfo == null || skillsInfo.isEmpty()) {
            return startY; // Return the starting Y position without drawing anything
        }

        // Draw the "Skills" heading
        String heading = "Skills".toUpperCase();
        float headingY = startY + headingPaint.getTextSize()-40;
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Combine all skills into a single line, separated by commas
        StringBuilder combinedSkills = new StringBuilder();
        for (int i = 0; i < skillsInfo.size(); i++) {
            combinedSkills.append(skillsInfo.get(i));
            if (i < skillsInfo.size() - 1) {
                combinedSkills.append(", ");
            }
        }

        // Draw the combined skills on the same line
        float skillsY = headingY + headingMarginBottom + headingPaint.getTextSize();
        canvas.drawText(combinedSkills.toString(), marginLeft, skillsY, skillPaint);

        // Return the bottom Y position of this section
        return (int) (skillsY + headingMarginBottom);
    }


    private int drawExperienceDeTravail(Canvas canvas, int startY) {
        // Create and configure Paint objects for heading, normal text, and bullet points
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.rgb(57, 57, 57));
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint normalPaint = new Paint();
        normalPaint.setColor(Color.rgb(57, 57, 57)); // Set text color to gray
        normalPaint.setTextSize(15);
        normalPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        Paint bulletPaint = new Paint();
        bulletPaint.setColor(Color.rgb(57, 57, 57)); // Set text color to gray
        bulletPaint.setTextSize(15);
        bulletPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Define margins and spacing
        int marginLeft = 260;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;
        int bulletMargin = 20;
        int resumeMaxWidth = 300; // Maximum width for resume text

        // Draw the "Expérience de Travail" heading
        String heading = "Expérience de Travail".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Retrieve experience information from database
        ArrayList<InfoExperience> experienceInfo = dbExperienceDeTravaille.getAllInfoExperience();

        // Calculate the Y offset for experience information
        float yOffset = headingY + headingMarginBottom + elementMarginTop;

        // Draw each piece of experience information
        for (InfoExperience info : experienceInfo) {
            // Draw the company name on the first line
            String company = info.getNomEntreprise();
            canvas.drawText(company, marginLeft, yOffset, bulletPaint);
            yOffset += lineHeight;

            // Draw the job title, start date, and end date on the next line
            String jobTitleAndDates = info.getTitreDePoste() + " - " + info.getDateDébut() + " to " + info.getDateDeFin();
            canvas.drawText(jobTitleAndDates, marginLeft, yOffset, normalPaint);
            yOffset += lineHeight;

            // Draw the resume with text wrapping, if it's not empty
            String resume = info.getRésumé();
            if (resume != null && !resume.trim().isEmpty()) {
                resume = "• " + resume;
                Paint.FontMetrics fontMetrics = normalPaint.getFontMetrics();
                float textX = marginLeft + bulletMargin;
                float textY = yOffset;

                String[] words = resume.split(" ");
                StringBuilder line = new StringBuilder();
                for (String word : words) {
                    String testLine = line + word + " ";
                    float testWidth = normalPaint.measureText(testLine);
                    if (testWidth > resumeMaxWidth) {
                        // Draw the line and start a new one
                        canvas.drawText(line.toString(), textX, textY, normalPaint);
                        textY += lineHeight;
                        line = new StringBuilder(word + " ");
                    } else {
                        line.append(word).append(" ");
                    }
                }
                // Draw the last line
                if (line.length() > 0) {
                    canvas.drawText(line.toString(), textX, textY, normalPaint);
                    textY += lineHeight;
                }

                yOffset = textY + 20; // Add extra space after resume
            } else {
                // If resume is empty, adjust yOffset to account for missing resume
                yOffset += 20;
            }

            // Add space between different experiences
            yOffset += elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) yOffset;
    }








    private int drawEducation(Canvas canvas, int startY) {
        // Create and configure the Paint object for headings
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.rgb(57,57,57));
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for school names (big and black)
        Paint schoolNamePaint = new Paint();
        schoolNamePaint.setColor(Color.rgb(57, 57, 57));
        schoolNamePaint.setTextSize(18); // Adjust size as needed
        schoolNamePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for professions (normal)
        Paint professionPaint = new Paint();
        professionPaint.setColor(Color.rgb(57, 57, 57));
        professionPaint.setTextSize(15); // Adjust size as needed
        professionPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = 260;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;
        int resumeMaxWidth = 300; // Maximum width for resume text

        // Draw the "Éducation" heading
        String heading = "Éducation".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Get the education information
        ArrayList<InfoEducation> educationInfo = dbInfoEducation.getAllInfoEducation();

        // Draw each piece of education information
        float currentY = headingY + headingMarginBottom + elementMarginTop;

        for (InfoEducation education : educationInfo) {
            // Draw the dates on the first line
            String dateRange = (education.getStartYier() != null ? education.getStartYier() : "") +
                    (education.getEndYier() != null ? " to " + education.getEndYier() : "");
            canvas.drawText(dateRange, marginLeft, currentY, professionPaint);

            // Move to the next line for the school name
            currentY += lineHeight;
            if (education.getShool() != null) {
                canvas.drawText(education.getShool(), marginLeft, currentY, schoolNamePaint);
            }

            // Move to the next line for the job title
            currentY += lineHeight;
            String metier = education.getMetier();
            if (metier != null && !metier.isEmpty()) {
                // Handle text wrapping for metier
                Paint.FontMetrics fontMetrics = professionPaint.getFontMetrics();
                float x = marginLeft;
                float y = currentY;

                String[] words = metier.split(" ");
                StringBuilder line = new StringBuilder();
                for (String word : words) {
                    String testLine = line + word + " ";
                    float testWidth = professionPaint.measureText(testLine);
                    if (testWidth > resumeMaxWidth) {
                        // Draw the line and start a new one
                        canvas.drawText(line.toString(), x, y, professionPaint);
                        y += lineHeight;
                        line = new StringBuilder(word + " ");
                    } else {
                        line.append(word).append(" ");
                    }
                }
                // Draw the last line
                if (line.length() > 0) {
                    canvas.drawText(line.toString(), x, y, professionPaint);
                    y += lineHeight;
                }

                // Update currentY position after printing metier
                currentY = y + elementMarginTop;
            }

            // Move to the next section with padding
            currentY += elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }






    private void drawLanguages(Canvas canvas, int startY) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(57,57,57));
        paint.setTextSize(20); // Text size for the heading
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface for the heading

        int marginLeft = 24; // Left margin for text
        int headingMarginBottom = 10; // Space below the heading
        int elementMarginTop = 25; // Space between line and first element
        int lineHeight = 25; // Space between lines

        float currentY = startY;

        // Draw the "Languages" heading
        String heading = "Languages".toUpperCase();
        float headingY = currentY + paint.getTextSize() + 50; // Y position for heading
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Filter and draw language information text
        ArrayList<String> languages = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLangueArrayList();
        languages.removeIf(String::isEmpty); // Remove empty entries

        if (!languages.isEmpty()) {
            paint.setTextSize(15); // Reset text size for languages
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Reset typeface for languages
            float textY = headingY + headingMarginBottom + elementMarginTop;
            for (String language : languages) {
                canvas.drawText("• " + language, marginLeft, textY, paint);
                textY += lineHeight;
            }
            currentY = textY;
        }

        // Draw the "Soft Skills" heading if there are any soft skills
        ArrayList<String> softSkills = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getSoftSkillsArrayList();
        softSkills.removeIf(String::isEmpty); // Remove empty entries

        if (!softSkills.isEmpty()) {
            float softSkillsHeadingY = currentY + 30; // Adjust Y position for "Soft Skills" heading
            paint.setTextSize(20); // Reset text size for heading
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("Soft Skills".toUpperCase(), marginLeft, softSkillsHeadingY, paint);
            paint.setTextSize(15); // Reset text size for content
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

            float textY = softSkillsHeadingY + headingMarginBottom + elementMarginTop;
            for (String softSkill : softSkills) {
                canvas.drawText("• " + softSkill, marginLeft, textY, paint);
                textY += lineHeight;
            }
            currentY = textY;
        }

        // Draw the "Loisirs" heading if there are any loisirs
        ArrayList<String> loisirs = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLoisirArrayList();
        loisirs.removeIf(String::isEmpty); // Remove empty entries

        if (!loisirs.isEmpty()) {
            float loisirsHeadingY = currentY + 30; // Adjust Y position for "Loisirs" heading
            paint.setTextSize(20); // Reset text size for heading
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("Loisirs".toUpperCase(), marginLeft, loisirsHeadingY, paint);
            paint.setTextSize(15); // Reset text size for content
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

            float textY = loisirsHeadingY + headingMarginBottom + elementMarginTop;
            for (String loisir : loisirs) {
                canvas.drawText("• " + loisir, marginLeft, textY, paint);
                textY += lineHeight;
            }
            currentY = textY;
        }

        // Draw the "Certificate" heading if there are any certificates
        ArrayList<String> certificates = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getCertificateArrayList();
        certificates.removeIf(String::isEmpty); // Remove empty entries

        if (!certificates.isEmpty()) {
            float certificateHeadingY = currentY + 30; // Adjust Y position for "Certificate" heading
            paint.setTextSize(20); // Reset text size for heading
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("Certificate".toUpperCase(), marginLeft, certificateHeadingY, paint);
            paint.setTextSize(15); // Reset text size for content
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

            float textY = certificateHeadingY + headingMarginBottom + elementMarginTop;
            for (String certificate : certificates) {
                canvas.drawText("• " + certificate, marginLeft, textY, paint);
                textY += lineHeight;
            }
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

    private Bitmap getBitmapFromURL(String urlImage) throws IOException {
        URL url = new URL(urlImage);

        if ("http".equalsIgnoreCase(url.getProtocol()) || "https".equalsIgnoreCase(url.getProtocol())) {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            try (InputStream input = connection.getInputStream()) {
                return BitmapFactory.decodeStream(input);
            } finally {
                connection.disconnect();
            }
        } else if ("file".equalsIgnoreCase(url.getProtocol())) {
            try (InputStream input = url.openStream()) {
                return BitmapFactory.decodeStream(input);
            }
        } else {
            throw new IllegalArgumentException("Unsupported URL scheme: " + url.getProtocol());
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


    private Bitmap resizeBitmap(Bitmap originalBitmap) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();

        float scaleWidth = ((float) 150) / width;
        float scaleHeight = ((float) 150) / height;

        // Create a matrix for the manipulation
        Matrix matrix = new Matrix();

        // Resize the bitmap
        matrix.postScale(scaleWidth, scaleHeight);

        // Recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, width, height, matrix, false);

        // Release the original bitmap
        originalBitmap.recycle();

        return resizedBitmap;
    }







    private void drawUserName(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(223,211,200)); // Set color for the user's name
        paint.setTextSize(40); // Adjust text size for the user's name
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Set text style

        // Fetch the user's name
        String userName = (dbInfoPersonnelle.getInfo().get(0).getF_name().toUpperCase()) + " " + dbInfoPersonnelle.getInfo().get(0).getL_name().toUpperCase(); // Adjust method to get user name
        float nameTextWidth = paint.measureText(userName);

        // Calculate the horizontal position for the user's name (center horizontally)
        float nameTextX = (canvas.getWidth() - nameTextWidth) / 2;

        // Set the vertical position for the user's name to y = 30
        float nameTextY = 80;

        // Draw the user's name at the calculated position
        canvas.drawText(userName, nameTextX, nameTextY, paint);
    }


    private void drawUserJobTitle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(223,211,200)); // Set a different color for the job title
        paint.setTextSize(30); // Adjust text size for the job title
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Set text style

        // Fetch the user's job title
        String userJobTitle = capitalizeFirstLetterOfEachWord(dbInfoPersonnelle.getInfo().get(0).getJob()); // Adjust method to get job title
        float jobTitleWidth = paint.measureText(userJobTitle);

        // Calculate the horizontal position for the job title (center horizontally)
        float jobTitleTextX = (canvas.getWidth() - jobTitleWidth) / 2;

        // Set the vertical position for the job title to y = 50
        float jobTitleTextY = 120;

        // Draw the job title at the calculated position
        canvas.drawText(userJobTitle, jobTitleTextX, jobTitleTextY, paint);
    }





    private void drawBlueBackground(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(	223	,211	,200)); // Set the background color to blue

        // Define the rectangle area to fill
        int startX = 10; // Start x position
        int endX = 225; // End x position
        int startY = 200; // Start y position (top of the canvas)
        int endY = canvas.getHeight()-30; // End y position (bottom of the canvas)

        // Draw the rectangle
        canvas.drawRect(startX, startY, endX, endY, paint);
    }





    // Helper method to capitalize the first letter of each word
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
}