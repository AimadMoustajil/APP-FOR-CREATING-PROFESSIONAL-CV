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

public class M11 extends AppCompatActivity {

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
        setContentView(R.layout.activity_m11);

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
                startActivity(new Intent(M11.this, InfoPersonnelleActivity.class));
            }
        });

        éducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M11.this, InfoEducationActivity.class));
            }
        });
        exDeTravaille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M11.this, ExperinceActivity.class));
            }
        });
        projet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M11.this, InfoProjetActivity.class));
            }
        });

        infoAdditionnelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M11.this, InformationAdditionnelleActivity.class));
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
                if (ContextCompat.checkSelfPermission(M11.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(M11.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {
                    // Permission is granted, proceed with PDF creation
                    PdfDocument document = createPDF();
                    if (document != null) {
                        ByteArrayOutputStream outputStream = savePDFToByteArray(document);

                        // Start ShowDocumentActivity to display the PDF
                        Intent intent = new Intent(M11.this, ShowDocumentActivity.class);
                        intent.putExtra("pdfByteArray", outputStream.toByteArray());
                        startActivity(intent);

                        // Close the document after starting the activity
                        document.close();
                    }
                }
            }
        });

    }







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

            // Draw user name and job title
            int logoBottomY = IMAGE_MARGIN + (int) (canvas.getWidth() * IMAGE_SIZE_PERCENT / 100);
            drawUserName(canvas, logoBottomY);

            // Draw job title below user name
            drawUserJobTitle(canvas, logoBottomY);

            // Draw contact info
            float contactInfoY = logoBottomY - 65; // Adjust Y position as needed
            drawContactInfo(canvas, contactInfoY);

            // Draw competence section
            int competenceBottomY = drawCompetence(canvas, (int) (contactInfoY + 100));

            // Draw experience section below competence
            int experienceBottomY = drawExperienceDeTravail(canvas, competenceBottomY + 30); // Adding some padding between sections

            // Draw project section below experience
            int projetBottomY = drawProjet(canvas, experienceBottomY + 30); // Adding some padding between sections

            // Draw skills section below project
            int skillsBottomY = drawSkills(canvas, projetBottomY + 30); // Adding some padding between sections

            drawLanguages(canvas,projetBottomY+30);
            // Draw soft skills section below skills
            softskillsBottomY(canvas, skillsBottomY + 30);// Adding some padding between sections
            drawLoisirs(canvas,skillsBottomY+30);


            document.finishPage(page); // Finish the page before returning

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
        paint.setColor(Color.rgb(96, 96, 96));
        paint.setTextSize(20); // Heading text size
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface

        Paint titleT = new Paint();
        titleT.setColor(Color.rgb(40, 40, 40));
        titleT.setTextSize(20); // Heading text size
        titleT.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface

        // Define margins and spacing
        final int marginLeft = 40;
        final int bulletMarginLeft = 50; // Additional margin for bullet points
        final int headingMarginBottom = 10; // Space below the heading
        final int elementMarginTop = 25; // Reduced margin between line and first element
        final int lineHeight = 25; // Space between lines
        final int bulletSize = 10; // Size of bullet point
        final int bulletTextMargin = 5; // Margin between bullet and text
        final int smallMarginTop = 10; // Small margin before resume text

        // Draw the "Projet" heading
        String heading = "Projet".toUpperCase();
        float headingY = startY + paint.getTextSize();  // Y position for heading
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
        grayPaint.setColor(Color.rgb(48, 48, 48)); // Gray text color
        grayPaint.setTextSize(10); // Text size for project information
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
        int yOffset = (int) (headingY + headingMarginBottom + elementMarginTop);

        // Draw each piece of project information
        for (InfoProjet info : projectInfo) {
            // Draw the project title and dates on the same line
            if (info.getNomEntreprise() != null) {
                String formattedTitle = capitalizeFirstLetterOfEachWord(info.getNomEntreprise());
                String dates = (info.getDateDebut() != null ? info.getDateDebut() : "") +
                        (info.getDateFin() != null ? " - " + info.getDateFin() : "");
                canvas.drawText(dates + " " + formattedTitle, marginLeft, yOffset, titleT);
                yOffset += lineHeight;
            }

            // Draw the job title (metier)
            if (info.getTitreProjet() != null) {
                String formattedJobTitle = capitalizeFirstLetterOfEachWord(info.getTitreProjet());
                canvas.drawText(formattedJobTitle, marginLeft, yOffset, titlePaint);
                yOffset += lineHeight;
            }

            // Draw the resume with a bullet point and small margin
            if (info.getResume() != null) {
                String resumeText = info.getResume();
                // Draw the bullet point with additional margin
                canvas.drawText("•", bulletMarginLeft, yOffset, bulletPaint);
                // Draw the resume text with margin between bullet and text
                canvas.drawText(resumeText, bulletMarginLeft + bulletSize + bulletTextMargin, yOffset, grayPaint);
                yOffset += lineHeight + smallMarginTop; // Additional space after the resume
            }

            // Add space between different projects
            yOffset += elementMarginTop;
        }

        // Draw a horizontal line below the project section
        // Draw a line below the project section
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK); // Set line color
        linePaint.setStrokeWidth(3); // Set line width

        float lineStartX = marginLeft; // X position to start the line
        float lineEndX = canvas.getWidth() - marginLeft; // X position to end the line
        float lineY = yOffset + 10; // Y position for the line, with a margin

        canvas.drawLine(lineStartX, lineY, lineEndX, lineY, linePaint);

        canvas.drawLine(lineStartX, lineY, lineEndX, lineY, linePaint);

        // Draw a vertical line in the middle of the page
        Paint verticalLinePaint = new Paint();
        verticalLinePaint.setColor(Color.BLACK); // Set line color
        verticalLinePaint.setStrokeWidth(3); // Set line width

        float middleX = canvas.getWidth() / 2; // X position for the vertical line
        float verticalLineStartY = lineY; // Start Y position of the vertical line
        float verticalLineEndY = canvas.getHeight(); // End Y position of the vertical line

        canvas.drawLine(middleX, verticalLineStartY+10, middleX, verticalLineEndY-10, verticalLinePaint);

        // Return the bottom Y position of this section
        return (int) (lineY + 10); // Include margin for the line
    }





    private void drawContactInfo(Canvas canvas, float startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(15); // Adjust text size as needed
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Set text style

        // Fetch contact information
        String phone = dbInfoPersonnelle.getInfo().get(0).getN_phone(); // Replace with actual method to get phone number
        String email = dbInfoPersonnelle.getInfo().get(0).getEmail(); // Replace with actual method to get email
        String address = dbInfoPersonnelle.getInfo().get(0).getPays(); // Replace with actual method to get address

        // Calculate horizontal position
        String phoneEmailText = phone + "   " + email;
        float textWidth = paint.measureText(phoneEmailText);
        float textX = 40;

        // Draw contact information
        float iconMargin = 10; // Margin between icon and text
        float textMargin = 10; // Margin between phone/email and address
        float lineHeight = 30; // Line height for spacing between lines

        // Draw phone and email on the same line
        canvas.drawText(phone, textX, startY, paint);

        float emailX = textX + paint.measureText(phone) + 60; // Position for email (20px margin from phone number)
        canvas.drawText(email, emailX, startY, paint);

        // Draw address below phone number and email
        float addressY = startY + lineHeight; // Adjust for address spacing
        canvas.drawText(address, textX, addressY, paint);

        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2); // Adjust line thickness as needed

        float lineY = 150; // Y-coordinate for the line
        canvas.drawLine(0, lineY, canvas.getWidth(), lineY, linePaint);
    }


    private int drawExperienceDeTravail(Canvas canvas, int startY) {
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.rgb(56,56,56));
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint normalPaint = new Paint();
        normalPaint.setColor(Color.rgb(96,96,96)); // Set text color to gray
        normalPaint.setTextSize(15);
        normalPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        Paint bulletPaint = new Paint();
        bulletPaint.setColor(Color.rgb(48,48,48)); // Set text color to gray
        bulletPaint.setTextSize(15);
        bulletPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        int marginLeft = 40;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;
        int bulletMargin = 20;

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
            // Draw the company name and dates on the first line
            String companyAndDates = info.getNomEntreprise() + " - " + info.getDateDébut() + " to " + info.getDateDeFin();
            canvas.drawText(companyAndDates, marginLeft, yOffset, bulletPaint);
            yOffset += lineHeight;

            // Draw the job title on the next line
            canvas.drawText(info.getTitreDePoste(), marginLeft, yOffset, normalPaint);
            yOffset += lineHeight;

            // Draw the resume on the next line with a bullet point
            String resume = "• " + info.getRésumé();
            canvas.drawText(resume, marginLeft + bulletMargin, yOffset, normalPaint);
            yOffset += lineHeight + 20; // Add extra space after resume

            // Add space between different experiences
            yOffset += elementMarginTop;
        }

        // Draw a line below the experience section
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK); // Set line color
        linePaint.setStrokeWidth(3); // Set line width

        float lineStartX = marginLeft; // X position to start the line
        float lineEndX = canvas.getWidth() - marginLeft; // X position to end the line
        float lineY = yOffset - 25; // Y position for the line, with a margin

        canvas.drawLine(lineStartX, lineY, lineEndX, lineY, linePaint);

        // Return the bottom Y position of this section
        return (int) (lineY + 10); // Include margin for the line
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


    /*private int drawLanguages(Canvas canvas, int startY) {
        // Create and configure the Paint object for the languages heading (big and black)
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Adjust size as needed
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for languages (normal)
        Paint languagePaint = new Paint();
        languagePaint.setColor(Color.rgb(40, 40, 40));
        languagePaint.setTextSize(15); // Adjust size as needed
        languagePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = 40;
        int bulletMarginLeft = 50; // Additional margin for bullet points
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;

        // Draw the "Languages" heading
        String heading = "Languages".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Get the languages information
        ArrayList<String> languages = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLangueArrayList(); // Replace with your method to fetch languages

        // Draw each piece of language information
        float currentY = headingY + headingMarginBottom + elementMarginTop;

        for (String language : languages) {
            // Draw the language on the line with additional margin for bullet point
            canvas.drawText("• " + language, bulletMarginLeft, currentY, languagePaint);

            // Move to the next line with padding
            currentY += lineHeight + elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }*/



    private int drawCompetence(Canvas canvas, int startY) {
        // Create and configure the Paint object for the heading (big and black)
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Adjust size as needed
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for the competence text (normal)
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK); // Or another color if needed
        textPaint.setTextSize(15); // Adjust size as needed
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = 40;
        int headingMarginBottom = 10;
        int elementMarginTop = 20;
        int lineHeight = 25;
        int lineWidth = 500; // Width of the line
        int lineMarginTop = 10; // Margin between the text and the line

        // Draw the "Compétence" heading
        String heading = "".toUpperCase(); // Set heading text
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Competence information text (dummy data here; replace with actual data)
        ArrayList<InfoPersonnelle> aboutUser = dbInfoPersonnelle.getInfo();

        // Prepare for drawing competence information
        float currentY = headingY + headingMarginBottom + elementMarginTop;
        textPaint.setTextSize(15);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        for (int i = 0; i < aboutUser.size(); i++) {
            String text = aboutUser.get(i).getAbout() + aboutUser.get(i).getAbout() + aboutUser.get(i).getAbout(); // Concatenate text (replace with actual data)
            List<String> lines = new ArrayList<>();

            // Break text into multiple lines if it exceeds 250 pixels
            int start = 0;
            while (start < text.length()) {
                int end = textPaint.breakText(text, start, text.length(), true, 250, null);
                lines.add(text.substring(start, start + end));
                start += end;
            }

            // Draw each line
            for (String line : lines) {
                canvas.drawText(line, marginLeft, currentY, textPaint);
                currentY += lineHeight;
            }

            // Add margin for the next block of text
            currentY += elementMarginTop;
        }

        // Draw a line below the competence section
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK); // Set line color
        linePaint.setStrokeWidth(3); // Set line width

        float lineStartX = marginLeft; // X position to start the line
        float lineEndX = marginLeft + lineWidth; // X position to end the line
        float lineY = currentY + lineMarginTop-25; // Y position for the line

        canvas.drawLine(lineStartX, lineY, lineEndX, lineY, linePaint);

        // Return the bottom Y position of this section
        return (int) (lineY + lineMarginTop); // Include margin for the line
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
        int marginLeft = 40;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;

        // Draw the "Skills" heading
        String heading = "Skills".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Get the skills information
        ArrayList<String> skillsInfo = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getSoftSkillsArrayList(); // Assuming a method to fetch skills info

        // Draw each skill
        float currentY = headingY + headingMarginBottom + elementMarginTop;
        for (String skill : skillsInfo) {
            canvas.drawText("• " + skill, marginLeft, currentY, skillPaint);
            currentY += lineHeight - 5;
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }

    private int drawLanguages(Canvas canvas, int startY) {
        // Create and configure the Paint object for the languages heading (bold and big)
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Adjust size as needed
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for language items (normal)
        Paint languagePaint = new Paint();
        languagePaint.setColor(Color.rgb(40, 40, 40));
        languagePaint.setTextSize(15); // Adjust size as needed
        languagePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = canvas.getWidth() / 2 + 15; // Center of the page + 15
        int headingMarginBottom = 10;
        int elementMarginTop = 10;
        int lineHeight = 25;

        // Draw the "Languages" heading
        String heading = "Languages".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Get the languages information
        ArrayList<String> languages = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLangueArrayList(); // Replace with your method to fetch languages

        // Draw each language
        float currentY = headingY + headingMarginBottom + elementMarginTop;
        for (String language : languages) {
            canvas.drawText("• " + language, marginLeft, currentY, languagePaint);
            currentY += lineHeight + elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }

    private int drawLoisirs(Canvas canvas, int startY) {
        // Create and configure the Paint object for the languages heading (bold and big)
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Adjust size as needed
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for language items (normal)
        Paint languagePaint = new Paint();
        languagePaint.setColor(Color.rgb(40, 40, 40));
        languagePaint.setTextSize(15); // Adjust size as needed
        languagePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = canvas.getWidth() / 2 + 15; // Center of the page + 15
        int headingMarginBottom = 10;
        int elementMarginTop = 10;
        int lineHeight = 25;

        // Draw the "Languages" heading
        String heading = "Loisirs".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Get the languages information
        ArrayList<String> languages = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLoisirArrayList(); // Replace with your method to fetch languages

        // Draw each language
        float currentY = headingY + headingMarginBottom + elementMarginTop;
        for (String language : languages) {
            canvas.drawText("• " + language, marginLeft, currentY, languagePaint);
            currentY += lineHeight + elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }

    private int softskillsBottomY(Canvas canvas, int startY) {
        // Create and configure the Paint object for the soft skills heading (big and black)
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Adjust size as needed
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for soft skills (normal)
        Paint loisirsPaint = new Paint();
        loisirsPaint.setColor(Color.rgb(40, 40, 40));
        loisirsPaint.setTextSize(15); // Adjust size as needed
        loisirsPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = 40;
        int headingMarginBottom = 10;
        int elementMarginTop = 5;
        int lineHeight = 25;

        // Draw the "Soft Skills" heading
        String heading = "Soft Skills".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Get the soft skills information
        ArrayList<String> softSkills = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getSoftSkillsArrayList(); // Replace with your method to fetch soft skills

        // Draw each piece of soft skills information
        float currentY = headingY + headingMarginBottom + elementMarginTop;

        for (String skill : softSkills) {
            // Draw the soft skill on the line with additional margin for bullet point
            canvas.drawText("• " + skill, 50, currentY, loisirsPaint);

            // Move to the next line with padding
            currentY += lineHeight + elementMarginTop - 5;
        }

        // Return the bottom Y position of this section
        return (int) currentY;
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
        String userName = dbInfoPersonnelle.getInfo().get(0).getL_name().toUpperCase() + " " + capitalizeFirstLetterOfEachWord(dbInfoPersonnelle.getInfo().get(0).getF_name())  ;
        float textWidth = paint.measureText(capitalizeFirstLetterOfEachWord(userName));

        // Define a fixed vertical space from the bottom of the logo

        // Calculate the vertical position for the text (fixed space from the bottom of the logo)

        // Calculate the position for the text (center horizontally)
        float textX = 40;

        // Draw the text at the calculated position
        canvas.drawText(userName, textX, 70, paint);
    }

    private void drawUserJobTitle(Canvas canvas, float contactInfoBottomY) {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY); // Set a different color for job title
        paint.setTextSize(30); // Adjust text size for job title
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Set text style

        // Fetch the user's job title
        String userJobTitle = capitalizeFirstLetterOfEachWord(dbInfoPersonnelle.getInfo().get(0).getJob()); // Adjust method to get job title
        float jobTitleWidth = paint.measureText(userJobTitle);

        // Define a fixed vertical space below the contact information
        float spaceBelowContactInfo = 20; // Adjust this value as needed

        // Calculate the vertical position for the job title
        float jobTitleTextY = contactInfoBottomY + spaceBelowContactInfo + paint.getTextSize()-25;

        // Define horizontal position for the job title (same as the contact info for alignment)
        float jobTitleTextX = 40; // Adjust this value as needed for alignment

        // Draw the job title at the calculated position
        canvas.drawText(userJobTitle, jobTitleTextX, jobTitleTextY, paint);
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