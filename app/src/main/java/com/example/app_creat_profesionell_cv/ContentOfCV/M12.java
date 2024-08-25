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

public class M12 extends AppCompatActivity {

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
        setContentView(R.layout.activity_m12);

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
                startActivity(new Intent(M12.this, InfoPersonnelleActivity.class));
            }
        });

        éducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M12.this, InfoEducationActivity.class));
            }
        });
        exDeTravaille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M12.this, ExperinceActivity.class));
            }
        });
        projet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M12.this, InfoProjetActivity.class));
            }
        });

        infoAdditionnelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M12.this, InformationAdditionnelleActivity.class));
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
                if (ContextCompat.checkSelfPermission(M12.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(M12.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {
                    // Permission is granted, proceed with PDF creation
                    PdfDocument document = createPDF();
                    if (document != null) {
                        ByteArrayOutputStream outputStream = savePDFToByteArray(document);

                        // Start ShowDocumentActivity to display the PDF
                        Intent intent = new Intent(M12.this, ShowDocumentActivity.class);
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

            drawContactInfo(canvas,180);
            // Draw Competence and get the Y position where the next section should start
            int competenceEndY = drawCompetence(canvas, 200);
            int skillsEndY = drawSkills(canvas,competenceEndY+30);
            // Draw Experience de Travail section starting just below Competence
            //drawExperienceDeTravail(canvas, skillsEndY + 30);

            int experienceEndY = drawExperienceDeTravail(canvas, skillsEndY + 30); // Adding extra margin
            // Draw Projet section starting just below Experience de Travail
            //drawProjet(canvas, experienceEndY + 30);
            int projetEndY = drawProjet(canvas, experienceEndY + 30);
            int drwarEducationY = drawEducation(canvas, projetEndY + 30);
            drawVerticalLine(canvas,180,drwarEducationY);


            drawLanguages(canvas, 430);


            // Draw user name and job title
            int logoBottomY = IMAGE_MARGIN + (int) (canvas.getWidth() * IMAGE_SIZE_PERCENT / 100);
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

        Paint titleT = new Paint();
        titleT.setColor(Color.rgb(40, 40, 40));
        titleT.setTextSize(20); // Title text size
        titleT.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface

        // Define margins and spacing
        final int marginLeft = 260;
        final int bulletMarginLeft = 270; // Additional margin for bullet points
        final int headingMarginBottom = 10; // Space below the heading
        final int elementMarginTop = 25; // Reduced margin between line and first element
        final int lineHeight = 25; // Space between lines
        final int bulletSize = 10; // Size of bullet point
        final int bulletTextMargin = 5; // Margin between bullet and text
        final int smallMarginTop = 10; // Small margin before resume text
        final int maxWidth = 250; // Maximum width for text before wrapping

        // Draw the "Projet" heading
        String heading = "Projet".toUpperCase();
        float headingY = startY + paint.getTextSize()-50;  // Y position for heading
        canvas.drawText(heading, marginLeft, headingY, paint);

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
        grayPaint.setColor(Color.rgb(96, 96, 96)); // Gray text color
        grayPaint.setTextSize(15); // Text size for project information
        grayPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Normal typeface

        // Configure Paint object for project title
        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.GRAY); // Gray text color
        titlePaint.setTextSize(15); // Text size for title
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface

        // Configure Paint object for bullet point
        Paint bulletPaint = new Paint();
        bulletPaint.setColor(Color.rgb(48, 48, 48)); // Gray text color
        bulletPaint.setTextSize(bulletSize); // Bullet point size

        // Calculate Y offset for project information
        float yOffset = headingY + headingMarginBottom + elementMarginTop;

        // Draw each piece of project information
        for (InfoProjet info : projectInfo) {
            // Draw the company name on the first line
            if (info.getNomEntreprise() != null) {
                String formattedTitle = capitalizeFirstLetterOfEachWord(info.getNomEntreprise());
                canvas.drawText(formattedTitle, marginLeft, yOffset, titleT);
                yOffset += lineHeight;
            }

            // Draw the dates on the next line
            if (info.getDateDebut() != null || info.getDateFin() != null) {
                String dates = (info.getDateDebut() != null ? info.getDateDebut() : "") +
                        (info.getDateFin() != null ? " - " + info.getDateFin() : "");
                canvas.drawText(dates, marginLeft, yOffset, grayPaint);
                yOffset += lineHeight;
            }

            // Draw the project title on the next line
            if (info.getTitreProjet() != null) {
                String formattedJobTitle = capitalizeFirstLetterOfEachWord(info.getTitreProjet());
                canvas.drawText(formattedJobTitle, marginLeft, yOffset, titlePaint);
                yOffset += lineHeight;
            }

            // Draw the resume on the next line with a bullet point
            if (info.getResume() != null) {
                String resumeText = info.getResume();
                // Draw the bullet point with additional margin
                canvas.drawText("•", bulletMarginLeft, yOffset, bulletPaint);

                // Wrap and draw resume text
                float textWidth;
                while (resumeText.length() > 0) {
                    // Measure the width of the text to fit
                    textWidth = grayPaint.measureText(resumeText);
                    if (textWidth > maxWidth) {
                        // Break the text into lines that fit within the maxWidth
                        int breakIndex = grayPaint.breakText(resumeText, true, maxWidth, null);
                        String line = resumeText.substring(0, breakIndex);
                        canvas.drawText(line, bulletMarginLeft + bulletSize + bulletTextMargin, yOffset, grayPaint);
                        yOffset += lineHeight + smallMarginTop;
                        resumeText = resumeText.substring(breakIndex);
                    } else {
                        // Draw the remaining text
                        canvas.drawText(resumeText, bulletMarginLeft + bulletSize + bulletTextMargin, yOffset, grayPaint);
                        yOffset += lineHeight + smallMarginTop;
                        resumeText = "";
                    }
                }
            }

            // Add space between different projects
            yOffset += elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) yOffset;
    }



    private void drawContactInfo(Canvas canvas, int startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20); // Text size for the heading
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface for the heading

        int marginLeft = 60; // Left margin for text
        int headingMarginBottom = 10; // Space below the heading
        int elementMarginTop = 15; // Space between line and first element
        int lineHeight = 50; // Space between lines
        int iconMarginRight = 10; // Space between icon and text
        int lastElementMarginBottom = 20; // Margin below the last element

        // Load icons from resources
        Bitmap phoneIcon = BitmapFactory.decodeResource(getResources(), R.drawable.telephone);
        Bitmap emailIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_email);
        Bitmap addressIcon = BitmapFactory.decodeResource(getResources(), R.drawable.social);
        Bitmap placeholder = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);

        // Define contact information and corresponding icons
        String[] contactInfo = {
                dbInfoPersonnelle.getInfo().get(0).getN_phone(),
                dbInfoPersonnelle.getInfo().get(0).getEmail(),
                dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLinkdin(),
                dbInfoPersonnelle.getInfo().get(0).getPays() + ", " + dbInfoPersonnelle.getInfo().get(0).getVille()
        };
        Bitmap[] icons = {phoneIcon, emailIcon, addressIcon, placeholder};

        // Filter out empty contact info and corresponding icons
        List<String> filteredContactInfo = new ArrayList<>();
        List<Bitmap> filteredIcons = new ArrayList<>();

        for (int i = 0; i < contactInfo.length; i++) {
            if (contactInfo[i] != null && !contactInfo[i].trim().isEmpty()) {
                filteredContactInfo.add(contactInfo[i]);
                filteredIcons.add(icons[i]);
            }
        }

        // Draw the heading
        String heading = "CONTACT";
        float headingY = startY + paint.getTextSize() + 25; // Y position for heading
        canvas.drawText(heading, marginLeft-40, headingY, paint);

        // Set up paint for contact information
        paint.setTextSize(10); // Smaller text size for contact information
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Normal typeface for contact information

        // Draw each line of contact information with its icon
        for (int i = 0; i < filteredContactInfo.size(); i++) {
            // Calculate Y position for icon
            if (filteredIcons.get(i) != null) {
                float iconY = headingY + headingMarginBottom + elementMarginTop + (lineHeight * i) - (filteredIcons.get(i).getHeight() / 2);
                canvas.drawBitmap(filteredIcons.get(i), marginLeft - filteredIcons.get(i).getWidth() - iconMarginRight, iconY, null);
            }

            // Draw text
            float textY = headingY + headingMarginBottom + elementMarginTop + (lineHeight * i);
            canvas.drawText(filteredContactInfo.get(i), marginLeft, textY, paint);
        }

        // Add margin below the last element
        if (!filteredContactInfo.isEmpty()) {
            float lastElementY = headingY + headingMarginBottom + elementMarginTop + (lineHeight * (filteredContactInfo.size() - 1)) + lastElementMarginBottom;
            // Optionally, you could use this position for additional elements or just to ensure spacing
            // canvas.drawText("Some footer text", marginLeft, lastElementY, paint); // Example footer text
        }
    }




    private void drawVerticalLine(Canvas canvas, int startY, int endY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK); // Set the color of the line
        paint.setStrokeWidth(2); // Set the thickness of the line

        int x = 230; // X-coordinate where the vertical line starts

        // Draw the vertical line
        canvas.drawLine(x, startY, x, endY, paint);
    }


    private int drawCompetence(Canvas canvas, int startY) {
        // Create and configure Paint object for headings
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure Paint object for competence text
        Paint textPaint = new Paint();
        textPaint.setColor(Color.rgb(96, 96, 96)); // Set text color to gray
        textPaint.setTextSize(15);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = 260;
        int headingMarginBottom = 10;
        int elementMarginTop = 20;
        int lineHeight = 25;
        int maxWidth = 250; // Maximum width before wrapping text

        // Draw the "Profil" heading
        String heading = "Profil".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Get the competence information
        ArrayList<InfoPersonnelle> aboutUser = dbInfoPersonnelle.getInfo();

        // Calculate the Y offset for competence information
        float currentY = headingY + headingMarginBottom + elementMarginTop;

        // Draw each line of competence information
        for (InfoPersonnelle info : aboutUser) {
            String text = info.getAbout();

            // Skip empty text
            if (text == null || text.trim().isEmpty()) {
                continue;
            }

            // Wrap and draw text
            while (!text.isEmpty()) {
                int charsToDraw = textPaint.breakText(text, true, maxWidth, null);
                String line = text.substring(0, charsToDraw);
                canvas.drawText(line, marginLeft, currentY, textPaint);
                currentY += lineHeight; // Move to the next line
                text = text.substring(charsToDraw); // Remaining text
            }
            currentY += elementMarginTop; // Add margin between items
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }



    private int drawSkills(Canvas canvas, int startY) {
        // Create and configure the Paint object for the skills heading (bold and big)
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Adjust size as needed
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for skill items (normal)
        Paint skillPaint = new Paint();
        skillPaint.setColor(Color.rgb(40, 40, 40));
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
        float headingY = startY + headingPaint.getTextSize();
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
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint normalPaint = new Paint();
        normalPaint.setColor(Color.rgb(96, 96, 96)); // Set text color to gray
        normalPaint.setTextSize(15);
        normalPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        Paint bulletPaint = new Paint();
        bulletPaint.setColor(Color.rgb(48, 48, 48)); // Set text color to gray
        bulletPaint.setTextSize(15);
        bulletPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        int marginLeft = 260;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;
        int bulletMargin = 20;
        int maxWidth = 250; // Maximum width before wrapping text

        // Draw the "Expérience de Travail" heading
        String heading = "Expérience de Travail".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Retrieve experience information from the database
        ArrayList<InfoExperience> experienceInfo = dbExperienceDeTravaille.getAllInfoExperience();

        // Calculate the Y offset for experience information
        float yOffset = headingY + headingMarginBottom + elementMarginTop;

        // Draw each piece of experience information
        for (InfoExperience info : experienceInfo) {
            boolean hasContent = false;

            // Check and draw the company name if not empty
            String companyName = info.getNomEntreprise();
            if (companyName != null && !companyName.trim().isEmpty()) {
                canvas.drawText(companyName, marginLeft, yOffset, bulletPaint);
                yOffset += lineHeight;
                hasContent = true;
            }

            // Check and draw the dates if not empty
            String dates = (info.getDateDébut() != null ? info.getDateDébut() : "") +
                    (info.getDateDeFin() != null ? " - " + info.getDateDeFin() : "");
            if (!dates.trim().isEmpty()) {
                canvas.drawText(dates, marginLeft, yOffset, normalPaint);
                yOffset += lineHeight;
                hasContent = true;
            }

            // Check and draw the job title if not empty
            String jobTitle = info.getTitreDePoste();
            if (jobTitle != null && !jobTitle.trim().isEmpty()) {
                canvas.drawText(jobTitle, marginLeft, yOffset, normalPaint);
                yOffset += lineHeight;
                hasContent = true;
            }

            // Check and draw the resume if not empty
            String resume = info.getRésumé();
            if (resume != null && !resume.trim().isEmpty()) {
                resume = "• " + resume;
                while (!resume.isEmpty()) {
                    int charsToDraw = normalPaint.breakText(resume, true, maxWidth, null);
                    String line = resume.substring(0, charsToDraw);
                    canvas.drawText(line, marginLeft + bulletMargin, yOffset, normalPaint);
                    yOffset += lineHeight; // Move to the next line
                    resume = resume.substring(charsToDraw); // Remaining text
                }
                yOffset += lineHeight + 20; // Add extra space after the resume
                hasContent = true;
            }

            // Only add space between experiences if there was content drawn
            if (hasContent) {
                yOffset += elementMarginTop;
            }
        }

        // Return the bottom Y position of this section
        return (int) yOffset;
    }










    private int drawEducation(Canvas canvas, int startY) {
        // Create and configure the Paint objects for headings, school names, professions, and dates
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint schoolNamePaint = new Paint();
        schoolNamePaint.setColor(Color.rgb(48, 48, 48));
        schoolNamePaint.setTextSize(18); // Adjust size as needed
        schoolNamePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint professionPaint = new Paint();
        professionPaint.setColor(Color.rgb(40, 40, 40));
        professionPaint.setTextSize(15); // Adjust size as needed
        professionPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        Paint datePaint = new Paint();
        datePaint.setColor(Color.rgb(96, 96, 96)); // Gray color for dates
        datePaint.setTextSize(15); // Adjust size as needed
        datePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = 260;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;

        // Get the education information
        ArrayList<InfoEducation> educationInfo = dbInfoEducation.getAllInfoEducation();

        // If there are no education entries, skip drawing this section
        if (educationInfo.isEmpty()) {
            return startY; // Return the starting Y position without drawing anything
        }

        // Draw the "Éducation" heading
        String heading = "Éducation".toUpperCase();
        float headingY = startY + headingPaint.getTextSize() - 50;
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Calculate the Y offset for education information
        float currentY = headingY + headingMarginBottom + elementMarginTop;

        for (InfoEducation education : educationInfo) {
            boolean hasContent = false;

            // Check and draw the school name if not empty
            String schoolName = education.getShool();
            if (schoolName != null && !schoolName.trim().isEmpty()) {
                canvas.drawText(schoolName, marginLeft, currentY, schoolNamePaint);
                currentY += lineHeight;
                hasContent = true;
            }

            // Check and draw the profession (metier) if not empty
            String profession = education.getMetier();
            if (profession != null && !profession.trim().isEmpty()) {
                canvas.drawText(profession, marginLeft, currentY, professionPaint);
                currentY += lineHeight;
                hasContent = true;
            }

            // Check and draw the start and end dates if not empty
            String dates = (education.getStartYier() != null ? education.getStartYier() : "") +
                    (education.getEndYier() != null ? " - " + education.getEndYier() : "");
            if (!dates.trim().isEmpty()) {
                canvas.drawText(dates, marginLeft, currentY, datePaint);
                currentY += lineHeight + elementMarginTop;
                hasContent = true;
            }

            // Only add space between entries if there was content drawn
            if (hasContent) {
                currentY += elementMarginTop;
            }
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }


    private String[] wrapText(String text, int maxWidth, Paint paint, int marginLeft, float startY) {
        // Split text into lines that fit within maxWidth
        ArrayList<String> lines = new ArrayList<>();
        float currentY = startY;
        while (!text.isEmpty()) {
            int charsToDraw = paint.breakText(text, true, maxWidth, null);
            String line = text.substring(0, charsToDraw);
            lines.add(line);
            text = text.substring(charsToDraw);
        }
        return lines.toArray(new String[0]);
    }






    private void drawLanguages(Canvas canvas, int startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20); // Text size for the heading
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface for the heading

        int marginLeft = 24; // Left margin for text
        int headingMarginBottom = 10; // Space below the heading
        int elementMarginTop = 25; // Space between line and first element
        int lineHeight = 25; // Space between lines
        int sectionMarginBottom = 30; // Margin below each section

        float textY = startY + paint.getTextSize() + 50; // Initial Y position

        // Draw "Languages" section if not empty
        ArrayList<String> languages = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLangueArrayList();
        if (languages != null && !languages.isEmpty()) {
            String heading = "Languages".toUpperCase();
            canvas.drawText(heading, marginLeft, textY, paint);
            paint.setTextSize(15); // Text size for languages
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Normal typeface for languages
            textY += headingMarginBottom + elementMarginTop;

            for (String language : languages) {
                if (language != null && !language.trim().isEmpty()) {
                    canvas.drawText("• " + language, marginLeft, textY, paint);
                    textY += lineHeight;
                }
            }
            textY += sectionMarginBottom; // Space below the languages section
        }

        // Draw "Soft Skills" section if not empty
        ArrayList<String> softSkills = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getSoftSkillsArrayList();
        if (softSkills != null && !softSkills.isEmpty()) {
            paint.setTextSize(20); // Text size for the heading
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface for the heading
            canvas.drawText("Soft Skills".toUpperCase(), marginLeft, textY, paint);
            paint.setTextSize(15); // Text size for soft skills
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Normal typeface for soft skills
            textY += headingMarginBottom + elementMarginTop;

            for (String softSkill : softSkills) {
                if (softSkill != null && !softSkill.trim().isEmpty()) {
                    canvas.drawText("• " + softSkill, marginLeft, textY, paint);
                    textY += lineHeight;
                }
            }
            textY += sectionMarginBottom; // Space below the soft skills section
        }

        // Draw "Loisirs" section if not empty
        ArrayList<String> loisirs = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLoisirArrayList();
        if (loisirs != null && !loisirs.isEmpty()) {
            paint.setTextSize(20); // Text size for the heading
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface for the heading
            canvas.drawText("Loisirs".toUpperCase(), marginLeft, textY, paint);
            paint.setTextSize(15); // Text size for loisirs
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Normal typeface for loisirs
            textY += headingMarginBottom + elementMarginTop;

            for (String loisir : loisirs) {
                if (loisir != null && !loisir.trim().isEmpty()) {
                    canvas.drawText("• " + loisir, marginLeft, textY, paint);
                    textY += lineHeight;
                }
            }
            textY += sectionMarginBottom; // Space below the loisirs section
        }

        // Draw "Certificates" section if not empty
        ArrayList<String> certificates = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getCertificateArrayList();
        if (certificates != null && !certificates.isEmpty()) {
            paint.setTextSize(20); // Text size for the heading
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface for the heading
            canvas.drawText("Certificate".toUpperCase(), marginLeft, textY, paint);
            paint.setTextSize(15); // Text size for certificates
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Normal typeface for certificates
            textY += headingMarginBottom + elementMarginTop;

            for (String certificate : certificates) {
                if (certificate != null && !certificate.trim().isEmpty()) {
                    canvas.drawText("• " + certificate, marginLeft, textY, paint);
                    textY += lineHeight;
                }
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





    private void drawUserName(Canvas canvas, int logoBottomY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(35); // Adjust text size as needed
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Set text style

        // Fetch the user's name
        String userName = dbInfoPersonnelle.getInfo().get(0).getL_name().toUpperCase() + " " + dbInfoPersonnelle.getInfo().get(0).getF_name().toUpperCase()  ;
        float textWidth = paint.measureText(capitalizeFirstLetterOfEachWord(userName));


        // Calculate the position for the text (center horizontally)
        float textX = 50;

        // Draw the text at the calculated position
        canvas.drawText(userName, textX, 70, paint);
    }

    private void drawUserJobTitle(Canvas canvas, float nameTextX, float nameTextY) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(36, 36, 36)); // Set a different color for job title
        paint.setTextSize(30); // Adjust text size for job title
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Set text style

        // Fetch the user's job title
        String userJobTitle = capitalizeFirstLetterOfEachWord(dbInfoPersonnelle.getInfo().get(0).getJob()); // Adjust method to get job title
        float jobTitleWidth = paint.measureText(userJobTitle);

        // Define a fixed vertical space below the user's name
        float spaceBelowName = 10; // Adjust this value as needed

        // Calculate the vertical position for the job title
        float jobTitleTextY = nameTextY + spaceBelowName + paint.getTextSize();

        // Calculate the horizontal position for the job title (center horizontally)
        float jobTitleTextX = 50;

        // Draw the job title at the calculated position
        canvas.drawText(userJobTitle, jobTitleTextX, 115, paint);
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