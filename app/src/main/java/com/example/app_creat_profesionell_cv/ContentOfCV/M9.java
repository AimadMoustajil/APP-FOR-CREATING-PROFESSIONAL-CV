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

public class M9 extends AppCompatActivity {

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
        setContentView(R.layout.activity_m9);

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
                startActivity(new Intent(M9.this, InfoPersonnelleActivity.class));
            }
        });

        éducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M9.this, InfoEducationActivity.class));
            }
        });
        exDeTravaille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M9.this, ExperinceActivity.class));
            }
        });
        projet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M9.this, InfoProjetActivity.class));
            }
        });

        infoAdditionnelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M9.this, InformationAdditionnelleActivity.class));
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
                if (ContextCompat.checkSelfPermission(M9.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(M9.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {
                    // Permission is granted, proceed with PDF creation
                    PdfDocument document = createPDF();
                    if (document != null) {
                        ByteArrayOutputStream outputStream = savePDFToByteArray(document);

                        // Start ShowDocumentActivity to display the PDF
                        Intent intent = new Intent(M9.this, ShowDocumentActivity.class);
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

            drawGrayBackground(canvas);
            // Draw header image and content
            drawHeaderImage(canvas);

            // Draw user name and job title
            int logoBottomY = IMAGE_MARGIN + (int) (canvas.getWidth() * IMAGE_SIZE_PERCENT / 100);
            drawUserName(canvas, logoBottomY);

            Paint paintForJobTitle = new Paint();
            paintForJobTitle.setColor(Color.GRAY);
            paintForJobTitle.setTextSize(40);
            paintForJobTitle.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

            // Draw job title
            float jobTitleTextY = logoBottomY - 50;
            drawUserJobTitle(canvas, (canvas.getWidth() - paintForJobTitle.measureText(dbInfoPersonnelle.getInfo().get(0).getJob())) / 2, jobTitleTextY);

            // Draw contact info
            float contactInfoY = jobTitleTextY + 70; // Adjust Y position as needed
            drawContactInfo(canvas, contactInfoY);

            // Draw vertical line

            // Draw education section
            int educationBottomY = drawEducation(canvas, 225);

            // Draw skills section below education
            int skillsBottomY = drawSkills(canvas, educationBottomY + 10); // Adding some padding between sections

            // Draw languages section below skills
            int languagesBottomY = drawLanguages(canvas, skillsBottomY + 10); // Adding some padding between sections

            // Draw loisirs section below languages
            int loisirsBottomY = drawLoisirs(canvas, languagesBottomY + 10); // Adding some padding between sections

            int softskillsBottomY = softskillsBottomY(canvas,loisirsBottomY+10);

            // Draw competence section
            int competenceBottomY = drawCompetence(canvas, 330);

            int progetBottomY = drawProjet(canvas,competenceBottomY+30);
            // Draw experience section below competence
            int experienceBottomY = drawExperienceDeTravail(canvas, progetBottomY + 30); // Adding some padding between sections

            // Draw certification section below experience
            drawCertification(canvas, experienceBottomY + 30); // Adding some padding between sections

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


    private void drawGrayBackground(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(244,244,244)); // Set the color to gray

        // Get the width and height of the canvas
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        // Define the rectangle bounds
        float left = 0;
        float top = 0;
        float right = 220; // Width of the background rectangle
        float bottom = canvasHeight; // Full height of the canvas

        // Draw the rectangle on the canvas
        canvas.drawRect(left, top, right, bottom, paint);
    }




    private int drawCertification(Canvas canvas, float startY) {
        // Create and configure the Paint object for the heading (bold and big)
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Adjust size as needed
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for certification items (normal)
        Paint skillPaint = new Paint();
        skillPaint.setColor(Color.rgb(40, 40, 40));
        skillPaint.setTextSize(15); // Adjust size as needed
        skillPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Create and configure the Paint object for the line below heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.rgb(96, 96, 96)); // Line color
        linePaint.setStrokeWidth(2); // Line width

        // Define margins and spacing
        int marginLeft = 250;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;

        // Draw the "Certification" heading
        String heading = "Certification".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Draw a line below the heading
        float lineY = headingY + headingMarginBottom;
        canvas.drawLine(marginLeft, lineY, 550, lineY, linePaint);

        // Get the certification information
        ArrayList<String> certificationInfo = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getCertificateArrayList(); // Assuming a method to fetch certification info

        // If there are no certifications, return the Y position after the heading and line
        if (certificationInfo == null || certificationInfo.isEmpty()) {
            return (int) lineY;
        }

        // Draw each certification
        float currentY = lineY + elementMarginTop;
        for (String certification : certificationInfo) {
            canvas.drawText("• " + certification, marginLeft, currentY, skillPaint);
            currentY += lineHeight - 5; // Adjust spacing between items
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }




    private void drawContactInfo(Canvas canvas, float startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(15); // Adjust text size as needed
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Set text style

        // Fetch contact information
        String phone = dbInfoPersonnelle.getInfo().get(0).getN_phone(); // Replace with actual method to get phone number
        String email = dbInfoPersonnelle.getInfo().get(0).getEmail(); // Replace with actual method to get email
        String address = dbInfoPersonnelle.getInfo().get(0).getPays() + ", " + dbInfoPersonnelle.getInfo().get(0).getVille(); // Replace with actual method to get address
        String linkedin = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLinkdin(); // Replace with actual method to get LinkedIn

        // Define initial X position and maximum line width
        float startX = 250;
        float maxLineWidth = 300; // Maximum width for a line before wrapping
        float lineHeight = 30; // Line height for spacing between lines

        // Define spacing between text blocks
        float spacing = 20; // Spacing between different contact info blocks

        // Text blocks
        ArrayList<String> texts = new ArrayList<>();
        if (phone != null && !phone.trim().isEmpty()) {
            texts.add("Phone: ".toUpperCase() + phone);
        }
        if (email != null && !email.trim().isEmpty()) {
            texts.add("Email: ".toUpperCase() + email);
        }
        if (address != null && !address.trim().isEmpty()) {
            texts.add("Address: ".toUpperCase() + address);
        }
        if (linkedin != null && !linkedin.trim().isEmpty()) {
            texts.add("LinkedIn: ".toUpperCase() + linkedin);
        }

        // Start drawing text
        float currentX = startX;
        float currentY = startY+15;

        for (String text : texts) {
            float textWidth = paint.measureText(text);

            // Check if text fits on the current line
            if (currentX + textWidth > startX + maxLineWidth) {
                // Move to next line if text exceeds maxLineWidth
                currentX = startX;
                currentY += lineHeight;
            }

            // Draw the text
            canvas.drawText(text, currentX, currentY, paint);

            // Update the current X position for the next text block
            currentX += textWidth + spacing;
        }
    }








    private int drawEducation(Canvas canvas, float startY) {
        // Create and configure the Paint object for headings
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for school names (big and black)
        Paint schoolNamePaint = new Paint();
        schoolNamePaint.setColor(Color.rgb(48, 48, 48));
        schoolNamePaint.setTextSize(18); // Adjust size as needed
        schoolNamePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for professions (normal)
        Paint professionPaint = new Paint();
        professionPaint.setColor(Color.rgb(40, 40, 40));
        professionPaint.setTextSize(15); // Adjust size as needed
        professionPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Create and configure the Paint object for the line
        Paint linePaint = new Paint();
        linePaint.setColor(Color.rgb(96, 96, 96));
        linePaint.setStrokeWidth(2); // Line width

        // Define margins and spacing
        int marginLeft = 25;
        int bulletMarginLeft = 25; // Additional margin for bullet points
        int headingMarginBottom = 15;
        int elementMarginTop = 10;
        int lineHeight = 25;
        int maxLineWidth = 300; // Maximum width before wrapping

        // Get the education information
        ArrayList<InfoEducation> educationInfo = dbInfoEducation.getAllInfoEducation();

        // If there is no education information, return the current Y position
        if (educationInfo.isEmpty()) {
            return (int) startY;
        }

        // Draw the "Éducation" heading
        String heading = "Éducation".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Draw a line below the heading
        float lineY = headingY + 5; // Small margin between heading and line
        canvas.drawLine(marginLeft, lineY, 200, lineY, linePaint);

        // Draw each piece of education information
        float currentY = lineY + headingMarginBottom + elementMarginTop;

        for (InfoEducation education : educationInfo) {
            // Draw the school name
            String schoolName = education.getShool();
            currentY = drawWrappedText(canvas, schoolName, marginLeft, currentY, schoolNamePaint, maxLineWidth);

            // Draw the dates on the next line
            String dates = education.getStartYier() + " - " + education.getEndYier();
            currentY = drawWrappedText(canvas, dates, marginLeft, currentY, schoolNamePaint, maxLineWidth);

            // Draw the job title on the next line with additional margin for bullet point
            currentY = drawWrappedText(canvas, education.getMetier(), bulletMarginLeft, currentY, professionPaint, maxLineWidth);

            // Move to the next section with padding
            currentY += elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }



    private float drawWrappedText(Canvas canvas, String text, float x, float y, Paint paint, int maxLineWidth) {
        // Split the text into lines if it exceeds the maximum width
        float currentY = y;
        String[] lines = splitTextIntoLines(text, paint, maxLineWidth);

        for (String line : lines) {
            canvas.drawText(line, x, currentY, paint);
            currentY += paint.getTextSize() + 5; // Adjust spacing as needed
        }

        return currentY;
    }

    private String[] splitTextIntoLines(String text, Paint paint, int maxLineWidth) {
        ArrayList<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder lineBuilder = new StringBuilder();

        for (String word : words) {
            String testLine = lineBuilder.toString() + word + " ";
            if (paint.measureText(testLine) > maxLineWidth) {
                lines.add(lineBuilder.toString().trim());
                lineBuilder = new StringBuilder(word + " ");
            } else {
                lineBuilder.append(word).append(" ");
            }
        }

        if (lineBuilder.length() > 0) {
            lines.add(lineBuilder.toString().trim());
        }

        return lines.toArray(new String[0]);
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

        // Create and configure the Paint object for the line below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.rgb(96, 96, 96));
        linePaint.setStrokeWidth(2); // Line width

        // Define margins and spacing
        int marginLeft = 25;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;

        // Get the skills information
        ArrayList<String> skillsInfo = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getSoftSkillsArrayList(); // Assuming a method to fetch skills info

        // If there are no skills, do not draw this section
        if (skillsInfo.isEmpty()) {
            return startY; // Return the starting Y position without drawing anything
        }

        // Draw the "Skills" heading
        String heading = "Skills".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Draw a line below the heading
        float lineY = headingY + headingMarginBottom; // Y position for the line
        canvas.drawLine(marginLeft, lineY, 200, lineY, linePaint);

        // Draw each skill
        float currentY = lineY + headingMarginBottom + elementMarginTop;
        for (String skill : skillsInfo) {
            canvas.drawText("• " + skill, marginLeft, currentY, skillPaint);
            currentY += lineHeight - 5; // Adjust spacing between lines
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }


    private int drawLoisirs(Canvas canvas, int startY) {
        // Create and configure the Paint object for the heading (bold and big)
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Adjust size as needed
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for items (normal)
        Paint skillPaint = new Paint();
        skillPaint.setColor(Color.rgb(40, 40, 40));
        skillPaint.setTextSize(15); // Adjust size as needed
        skillPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Create and configure the Paint object for the line
        Paint linePaint = new Paint();
        linePaint.setColor(Color.rgb(96, 96, 96));
        linePaint.setStrokeWidth(2); // Line width

        // Define margins and spacing
        int marginLeft = 25;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;
        int canvasWidth = canvas.getWidth(); // Canvas width for line drawing

        // Get the hobbies or interests information
        ArrayList<String> loisirsInfo = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLoisirArrayList(); // Assuming a method to fetch hobbies info

        // If there are no loisirs, return the current Y position without drawing anything
        if (loisirsInfo == null || loisirsInfo.isEmpty()) {
            return (int) startY;
        }

        // Draw the "Loisirs" heading
        String heading = "Loisirs".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Draw a line below the heading
        float lineY = headingY + headingMarginBottom;
        canvas.drawLine(marginLeft, lineY, 200, lineY, linePaint);

        // Draw each hobby or interest
        float currentY = lineY + elementMarginTop;
        for (String skill : loisirsInfo) {
            canvas.drawText("• " + skill, marginLeft, currentY, skillPaint);
            currentY += lineHeight - 5; // Adjust spacing between items
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }




    private int softskillsBottomY(Canvas canvas, int startY) {
        // Create and configure the Paint object for the heading (bold and big)
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Adjust size as needed
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for skill items (normal)
        Paint skillPaint = new Paint();
        skillPaint.setColor(Color.rgb(40, 40, 40));
        skillPaint.setTextSize(15); // Adjust size as needed
        skillPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Create and configure the Paint object for the line below heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.rgb(96, 96, 96)); // Line color
        linePaint.setStrokeWidth(2); // Line width

        // Define margins and spacing
        int marginLeft = 25;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;

        // Draw the "Soft Skills" heading
        String heading = "soft skills".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Draw a line below the heading
        float lineY = headingY + headingMarginBottom;
        canvas.drawLine(marginLeft, lineY, 200, lineY, linePaint);

        // Get the soft skills information
        ArrayList<String> softSkillsInfo = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getSoftSkillsArrayList(); // Assuming a method to fetch soft skills info

        // If there are no skills, return the current Y position without drawing anything
        if (softSkillsInfo == null || softSkillsInfo.isEmpty()) {
            return (int) headingY;
        }

        // Draw each skill
        float currentY = lineY + elementMarginTop;
        for (String skill : softSkillsInfo) {
            canvas.drawText("• " + skill, marginLeft, currentY, skillPaint);
            currentY += lineHeight - 5; // Adjust spacing between items
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }



    private int drawProjet(Canvas canvas, int startY) {
        // Create and configure Paint objects
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20); // Heading text size
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface

        Paint titleT = new Paint();
        titleT.setColor(Color.rgb(48, 48, 48)); // Gray color for title and dates
        titleT.setTextSize(15);
        titleT.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.GRAY); // Gray color for job titles
        titlePaint.setTextSize(15);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint bulletPaint = new Paint();
        bulletPaint.setColor(Color.rgb(48, 48, 48)); // Gray color for bullets
        bulletPaint.setTextSize(10); // Bullet point size

        Paint grayPaint = new Paint();
        grayPaint.setColor(Color.rgb(96, 96, 96)); // Gray color for resume text
        grayPaint.setTextSize(15);
        grayPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        final int marginLeft = 260;
        final int bulletMarginLeft = 260; // Additional margin for bullet points
        final int headingMarginBottom = 10;
        final int elementMarginTop = 25;
        final int lineHeight = 25;
        final int bulletSize = 10;
        final int bulletTextMargin = 0;
        final int smallMarginTop = 10;
        final int resumeMaxWidth = 300;

        // Draw the "Projet" heading
        String heading = "Projet".toUpperCase();
        float headingY = startY + paint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Draw a line below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.rgb(96, 96, 96)); // Line color
        linePaint.setStrokeWidth(2); // Line width
        canvas.drawLine(marginLeft, headingY + headingMarginBottom, 550, headingY + headingMarginBottom, linePaint);

        // Retrieve project information from database
        ArrayList<InfoProjet> projectInfo;
        try {
            projectInfo = dbProjet.getAllInfoProjets();
        } catch (Exception e) {
            // Handle the exception (e.g., log the error)
            return (int) headingY; // Return the Y position if there's an error
        }

        // If no project information, remove the section by returning the position right after the heading and line
        if (projectInfo == null || projectInfo.isEmpty()) {
            return (int) (headingY + headingMarginBottom);
        }

        // Calculate Y offset for project information
        float yOffset = headingY + headingMarginBottom + elementMarginTop;

        // Draw each piece of project information
        for (InfoProjet info : projectInfo) {
            // Draw the project title and dates on the same line
            if (info.getNomEntreprise() != null) {
                String formattedTitle = capitalizeFirstLetterOfEachWord(info.getNomEntreprise());
                String dates = (info.getDateDebut() != null ? info.getDateDebut() : "") +
                        (info.getDateFin() != null ? " - " + info.getDateFin() : "");
                canvas.drawText(formattedTitle + "               " + dates, marginLeft, yOffset, titleT);
                yOffset += lineHeight;
            }

            // Draw the job title (metier)
            if (info.getTitreProjet() != null) {
                String formattedJobTitle = capitalizeFirstLetterOfEachWord(info.getTitreProjet());
                canvas.drawText(formattedJobTitle, marginLeft, yOffset, titlePaint);
                yOffset += lineHeight;
            }

            // Draw the resume with a bullet point and handle text wrapping if resume is not empty
            String resumeText = info.getResume();
            if (resumeText != null && !resumeText.trim().isEmpty()) {
                // Draw the bullet point
                canvas.drawText("•", bulletMarginLeft, yOffset, bulletPaint);

                // Handle text wrapping
                Paint.FontMetrics fontMetrics = grayPaint.getFontMetrics();
                float x = bulletMarginLeft + bulletSize + bulletTextMargin;
                float y = yOffset;

                String[] words = resumeText.split(" ");
                StringBuilder line = new StringBuilder();
                for (String word : words) {
                    String testLine = line + word + " ";
                    float testWidth = grayPaint.measureText(testLine);
                    if (testWidth > resumeMaxWidth) {
                        // Draw the line and start a new one
                        canvas.drawText(line.toString(), x, y, grayPaint);
                        y += fontMetrics.descent - fontMetrics.ascent;
                        line = new StringBuilder(word + " ");
                    } else {
                        line.append(word).append(" ");
                    }
                }
                // Draw the last line
                if (line.length() > 0) {
                    canvas.drawText(line.toString(), x, y, grayPaint);
                    y += fontMetrics.descent - fontMetrics.ascent;
                }

                yOffset = y + smallMarginTop; // Update yOffset for the next project
            } else {
                // Skip to the next project if resume is empty
                yOffset += smallMarginTop;
            }

            // Add space between different projects
            yOffset += elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) yOffset;
    }


    // Helper method to wrap text within a given width
    private List<String> wrapText(String text, Paint paint, int maxWidth) {
        List<String> lines = new ArrayList<>();
        int start = 0;

        while (start < text.length()) {
            int end = paint.breakText(text, start, text.length(), true, maxWidth, null);
            lines.add(text.substring(start, start + end));
            start += end;
        }

        return lines;
    }



    private int drawExperienceDeTravail(Canvas canvas, int startY) {
        // Paint for the heading
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.rgb(56, 56, 56));
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Paint for normal text
        Paint normalPaint = new Paint();
        normalPaint.setColor(Color.rgb(96, 96, 96)); // Set text color to gray
        normalPaint.setTextSize(15);
        normalPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Paint for bullet points
        Paint bulletPaint = new Paint();
        bulletPaint.setColor(Color.rgb(48, 48, 48)); // Set text color to gray
        bulletPaint.setTextSize(15);
        bulletPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        int marginLeft = 250;
        int headingMarginBottom = 10;
        int elementMarginTop = 5;
        int lineHeight = 5;
        int bulletMargin = 0;
        int maxLineWidth = 300; // Maximum width before wrapping

        // Small margin between the line and elements
        int lineElementMargin = 20;

        // Retrieve experience information from the database
        ArrayList<InfoExperience> experienceInfo;
        try {
            experienceInfo = dbExperienceDeTravaille.getAllInfoExperience();
        } catch (Exception e) {
            // Handle the exception (e.g., log the error)
            return startY; // Return the initial Y position if there's an error
        }

        // If no experience information is available, skip drawing
        if (experienceInfo.isEmpty()) {
            return startY;
        }

        // Draw the "Expérience de Travail" heading
        String heading = "Expérience de Travail".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Draw a line below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.rgb(96, 96, 96));
        linePaint.setStrokeWidth(2); // Line width
        float lineY = headingY + headingMarginBottom; // Y position for the line
        canvas.drawLine(marginLeft, lineY, 550, lineY, linePaint);

        // Calculate the Y offset for experience information, including the margin after the line
        float yOffset = lineY + lineElementMargin + elementMarginTop;

        // Draw each piece of experience information
        for (InfoExperience info : experienceInfo) {
            // Draw the company name and dates on the first line
            String companyAndDates = info.getNomEntreprise() + "               " + info.getDateDébut() + " to " + info.getDateDeFin();
            yOffset = drawWrappedText(canvas, companyAndDates, marginLeft, yOffset, bulletPaint, maxLineWidth);

            // Draw the job title on the next line
            yOffset += lineHeight;
            yOffset = drawWrappedText(canvas, info.getTitreDePoste(), marginLeft, yOffset, normalPaint, maxLineWidth);

            // Draw the résumé on the next line with a bullet point
            yOffset += lineHeight;
            yOffset = drawWrappedText(canvas, "• " + info.getRésumé(), marginLeft + bulletMargin, yOffset, normalPaint, maxLineWidth);

            // Add space between different experiences
            yOffset += elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) yOffset;
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


    private int drawLanguages(Canvas canvas, int startY) {
        // Create and configure the Paint object for the heading (bold and big)
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Adjust size as needed
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for skill items (normal)
        Paint skillPaint = new Paint();
        skillPaint.setColor(Color.rgb(40, 40, 40));
        skillPaint.setTextSize(15); // Adjust size as needed
        skillPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Create and configure the Paint object for the line below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.rgb(96, 96, 96)); // Line color
        linePaint.setStrokeWidth(2); // Line width

        // Define margins and spacing
        int marginLeft = 25;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;

        // Draw the "Languages" heading
        String heading = "Languages".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Draw a line below the heading
        float lineY = headingY + headingMarginBottom;
        canvas.drawLine(marginLeft, lineY, 200, lineY, linePaint);

        // Get the languages information
        ArrayList<String> languagesInfo = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLangueArrayList(); // Assuming a method to fetch languages info

        // If there are no languages, return the Y position after the heading and line
        if (languagesInfo == null || languagesInfo.isEmpty()) {
            return (int) lineY;
        }

        // Draw each language
        float currentY = lineY + elementMarginTop;
        for (String language : languagesInfo) {
            canvas.drawText("• " + language, marginLeft, currentY, skillPaint);
            currentY += lineHeight - 5; // Adjust spacing between items
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }





    private int drawCompetence(Canvas canvas, int startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        int marginLeft = 250;
        int headingMarginBottom = 10;
        int elementMarginTop = 20;
        int lineHeight = 25;

        // Draw the "Compétence" heading
        String heading = "Proféle".toUpperCase();
        float headingY = startY + paint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Competence information text (dummy data here; replace with actual data)
        ArrayList<InfoPersonnelle> aboutUser = dbInfoPersonnelle.getInfo();

        // Prepare for drawing competence information
        float currentY = headingY + headingMarginBottom + elementMarginTop;
        paint.setColor(Color.rgb(96, 96, 96)); // Set text color to gray
        paint.setTextSize(15);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        for (int i = 0; i < aboutUser.size(); i++) {
            String text = aboutUser.get(i).getAbout();
            List<String> lines = new ArrayList<>();

            // Break text into multiple lines if it exceeds 300 pixels
            int start = 0;
            while (start < text.length()) {
                int end = paint.breakText(text, start, text.length(), true, 250, null);
                lines.add(text.substring(start, start + end));
                start += end;
            }

            // Draw each line
            for (String line : lines) {
                canvas.drawText(line, marginLeft, currentY, paint);
                currentY += lineHeight;
            }

            // Add margin for the next block of text
            currentY += elementMarginTop;
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


    private void drawHeaderImage(Canvas canvas) {
        try {
            // Retrieve and resize the bitmap from the URL
            Bitmap originalBitmap = getBitmapFromURL(dbInfoPersonnelle.getInfo().get(0).getLogoOfUser());
            Bitmap resizedBitmap = resizeBitmap(originalBitmap);

            // Calculate the position for the image in the top-left corner
            int imageX = IMAGE_MARGIN; // Padding from the left edge
            int imageY = IMAGE_MARGIN; // Padding from the top edge

            // Draw the circular image at the calculated position
            drawCircularImage(canvas, resizedBitmap, imageX, imageY);

            // Draw the user's name centered horizontally between the logo and the end of the page
            drawUserName(canvas, resizedBitmap.getWidth() + 2 * IMAGE_MARGIN); // Position the name after the logo with padding

            // Release the resized bitmap to free memory
            resizedBitmap.recycle();
        } catch (Exception e) {
            // Handle exceptions, e.g., logging or showing a toast message
            e.printStackTrace();
        }
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
        float textX = ((canvas.getWidth() - textWidth)+140) / 2;

        // Draw the text at the calculated position
        canvas.drawText(userName, textX, 128, paint);
    }

    private void drawUserJobTitle(Canvas canvas, float nameTextX, float nameTextY) {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY); // Set a different color for job title
        paint.setTextSize(30); // Adjust text size for job title
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Set text style

        // Fetch the user's job title
        String userJobTitle =capitalizeFirstLetterOfEachWord( dbInfoPersonnelle.getInfo().get(0).getJob()); // Adjust method to get job title
        float jobTitleWidth = paint.measureText(userJobTitle);

        // Define a fixed vertical space below the user's name
        float spaceBelowName = 10; // Adjust this value as needed

        // Calculate the vertical position for the job title
        float jobTitleTextY = nameTextY + spaceBelowName + paint.getTextSize();

        // Calculate the horizontal position for the job title (center horizontally)
        float jobTitleTextX = ((canvas.getWidth() - jobTitleWidth)+7) / 2;

        // Draw the job title at the calculated position
        canvas.drawText(userJobTitle, jobTitleTextX, jobTitleTextY, paint);
    }








    private void drawCircularImage(Canvas canvas, Bitmap bitmap, int x, int y) {
        int imageSize = (int) (canvas.getWidth() * IMAGE_SIZE_PERCENT / 100);
        Bitmap circularBitmap = Bitmap.createBitmap(imageSize, imageSize, Bitmap.Config.ARGB_8888);
        Canvas circularCanvas = new Canvas(circularBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Rect rect = new Rect(0, 0, imageSize, imageSize);
        RectF rectF = new RectF(rect);
        circularCanvas.drawARGB(0, 0, 0, 0);
        circularCanvas.drawCircle(imageSize / 2, imageSize / 2, imageSize / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        circularCanvas.drawBitmap(bitmap, rect, rect, paint);
        canvas.drawBitmap(circularBitmap, x, y, null);
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