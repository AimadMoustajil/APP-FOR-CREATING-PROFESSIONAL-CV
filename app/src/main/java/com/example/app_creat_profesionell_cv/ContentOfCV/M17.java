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
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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

public class M17 extends AppCompatActivity {

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
        setContentView(R.layout.activity_m8);

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
                startActivity(new Intent(M17.this, InfoPersonnelleActivity.class));
            }
        });

        éducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M17.this, InfoEducationActivity.class));
            }
        });
        exDeTravaille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M17.this, ExperinceActivity.class));
            }
        });
        projet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M17.this, InfoProjetActivity.class));
            }
        });

        infoAdditionnelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M17.this, InformationAdditionnelleActivity.class));
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
                if (ContextCompat.checkSelfPermission(M17.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(M17.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {
                    // Permission is granted, proceed with PDF creation
                    PdfDocument document = createPDF();
                    if (document != null) {
                        ByteArrayOutputStream outputStream = savePDFToByteArray(document);

                        // Start ShowDocumentActivity to display the PDF
                        Intent intent = new Intent(M17.this, ShowDocumentActivity.class);
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

            //drawGreenBackground(canvas);
            //drawBlueVerticalLine(canvas);


            drawGreenBackground(canvas);
            // Draw header image


            drawWhiteBackgroundWithBorder(canvas);
            drawWhiteBackgroundWithBorder2(canvas);
            drawHeaderImage(canvas);

            drawContactInfo(canvas,210);
            // Draw Competence and get the Y position where the next section should start
            int competenceEndY = drawCompetence(canvas, 220);

            // Draw Skills and get the Y position where the next section should start
            int skillsEndY = drawSkills(canvas, competenceEndY + 30);

            // Draw Experience de Travail section starting just below Skills
            int experienceEndY = drawExperienceDeTravail(canvas, skillsEndY + 30);

            // Draw Projet section starting just below Experience de Travail
            int projetEndY = drawProjet(canvas, experienceEndY + 30);
            drawEducation(canvas, projetEndY + 30);


            drawLanguages(canvas, 420);


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
        // Create and configure Paint objects
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20); // Heading text size
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface

        Paint titleT = new Paint();
        titleT.setColor(Color.rgb(40, 40, 40));
        titleT.setTextSize(20); // Heading text size
        titleT.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface

        Paint grayPaint = new Paint();
        grayPaint.setColor(Color.rgb(96, 96, 96)); // Set text color to gray
        grayPaint.setTextSize(15);
        grayPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Normal typeface

        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.GRAY); // Gray text color
        titlePaint.setTextSize(15); // Text size for title
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface

        Paint bulletPaint = new Paint();
        bulletPaint.setColor(Color.rgb(41, 45, 45)); // Gray text color
        bulletPaint.setTextSize(10); // Bullet point size

        // Define margins and spacing
        final int marginLeft = 290; // Left margin for heading with icon
        final int bulletMarginLeft = 270; // Additional margin for bullet points
        final int headingMarginBottom = 10; // Space below the heading
        final int elementMarginTop = 25; // Reduced margin between line and first element
        final int lineHeight = 25; // Space between lines
        final int bulletSize = 10; // Size of bullet point
        final int bulletTextMargin = 5; // Margin between bullet and text
        final int smallMarginTop = 10; // Small margin before next project
        final int resumeMaxWidth = 300; // Maximum width for resume text

        // Draw the "Projet" heading with an icon
        Bitmap iconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.project); // Replace with your icon resource
        int iconSize = 20; // Size of the icon
        int iconMarginRight = 10; // Margin between icon and heading text
        float headingY = startY + paint.getTextSize(); // Y position for heading

        // Draw the icon
        canvas.drawBitmap(Bitmap.createScaledBitmap(iconBitmap, iconSize, iconSize, true), marginLeft - iconSize - iconMarginRight, startY, null);

        // Draw the heading text
        canvas.drawText("Projet".toUpperCase(), marginLeft, headingY, paint);

        // Retrieve project information from database
        ArrayList<InfoProjet> projectInfo;
        try {
            projectInfo = dbProjet.getAllInfoProjets();
        } catch (Exception e) {
            // Handle the exception (e.g., log the error)
            return startY; // Return the initial Y position if there's an error
        }

        // Calculate Y offset for project information
        int yOffset = (int) (headingY + headingMarginBottom + elementMarginTop);

        // Draw each piece of project information
        for (InfoProjet info : projectInfo) {
            // Draw the project title and dates on the same line
            if (info.getNomEntreprise() != null) {
                String formattedTitle = capitalizeFirstLetterOfEachWord(info.getNomEntreprise());
                String dates = (info.getDateDebut() != null ? info.getDateDebut() : "") +
                        (info.getDateFin() != null ? " - " + info.getDateFin() : "");
                canvas.drawText(formattedTitle + "," + dates, marginLeft, yOffset, titleT);
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
                // Draw the bullet point with additional margin
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

                yOffset = (int) (y + smallMarginTop); // Update yOffset for the next project
            } else {
                // Skip to next project if resume is empty
                yOffset += smallMarginTop;
            }

            // Add space between different projects
            yOffset += elementMarginTop;
        }

        // Return the bottom Y position of this section
        return yOffset;
    }







    private int drawSkills(Canvas canvas, int startY) {
        // Create and configure Paint objects
        Paint paint = new Paint();
        paint.setColor(Color.rgb(96, 96, 96)); // Set text color to gray
        paint.setTextSize(15);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Normal typeface

        int marginLeft = 290; // Left margin for text
        int iconSize = 20; // Size of the icon
        int iconMarginRight = 10; // Margin between icon and heading text
        int lineHeight = 25; // Space between lines
        int maxLineWidth = 300; // Maximum width for wrapping text
        int headingMarginBottom = 25; // Space below the header

        /* int marginLeft = 270; // Left margin for text
        int lineHeight = 25; // Space between lines
        int maxLineWidth = 300; // Maximum width for wrapping text
        int headingMarginBottom = 10; // Space below the header*/
        // Draw the "Skills" header with an icon
        Paint headerPaint = new Paint();
        headerPaint.setColor(Color.BLACK);
        headerPaint.setTextSize(20); // Text size for header
        headerPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface

        // Load and scale the icon
        Bitmap iconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.settings); // Replace with your icon resource
        Bitmap scaledIcon = Bitmap.createScaledBitmap(iconBitmap, iconSize, iconSize, true);

        // Draw the icon
        canvas.drawBitmap(scaledIcon, marginLeft - iconSize - iconMarginRight, startY, null);

        // Draw the heading text
        String skillsHeader = "Skills".toUpperCase();
        canvas.drawText(skillsHeader, marginLeft, startY + iconSize, headerPaint); // Adjust position to accommodate icon

        // Get the skills list
        ArrayList<String> skills = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getCompetanceArrayList();

        if (skills != null && !skills.isEmpty()) {
            float currentY = startY + iconSize + headingMarginBottom; // Move to the next line for the skills list

            // Concatenate all skills into a single line with bullet points
            StringBuilder allSkills = new StringBuilder();
            for (String skill : skills) {
                if (allSkills.length() > 0) {
                    allSkills.append(" • ");
                }
                allSkills.append(skill);
            }

            // Wrap the concatenated text if needed
            String[] wrappedLines = wrapText(allSkills.toString(), paint, maxLineWidth);

            // Draw each wrapped line
            for (String line : wrappedLines) {
                canvas.drawText(" • " + line, marginLeft, currentY, paint);
                currentY += lineHeight; // Move to the next line
            }

            // Return the Y position where the next section should start
            return (int) currentY;
        }

        // If no skills are available, return the Y position with some extra margin
        return (int) (startY + iconSize + headingMarginBottom + 40);
    }



    private String[] wrapText(String text, Paint paint, int maxWidth) {
        ArrayList<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            String testLine = line.toString() + (line.length() > 0 ? " " : "") + word;
            float lineWidth = paint.measureText(testLine);

            if (lineWidth > maxWidth) {
                lines.add(line.toString());
                line = new StringBuilder(word);
            } else {
                line.append(line.length() > 0 ? " " : "").append(word);
            }
        }

        if (line.length() > 0) {
            lines.add(line.toString());
        }

        return lines.toArray(new String[0]);
    }








    private void drawContactInfo(Canvas canvas, int startY) {
        // Create and configure Paint objects
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20); // Increased text size for the heading
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface for the heading

        int marginLeft = 60; // Left margin for text (considering icon width)
        int headingMarginBottom = 10; // Space below the heading
        int elementMarginTop = 15; // Space between line and first element
        int lineHeight = 50; // Space between lines
        int iconMarginRight = 10; // Space between icon and text
        int backgroundWidth = 140; // Width of the background rectangle

        // Load icons from resources
        Bitmap phoneIcon = BitmapFactory.decodeResource(getResources(), R.drawable.telephone);
        Bitmap emailIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_email);
        Bitmap addressIcon = BitmapFactory.decodeResource(getResources(), R.drawable.social);
        Bitmap placeholderIcon = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);

        if (phoneIcon == null || emailIcon == null || addressIcon == null) {
            // Handle missing icons
            Log.e("DrawContactInfo", "One or more icons are missing.");
            return;
        }

        // Retrieve contact information
        String phone = dbInfoPersonnelle.getInfo().get(0).getN_phone();
        String email = dbInfoPersonnelle.getInfo().get(0).getEmail();
        String linkedin = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLinkdin();
        String address = dbInfoPersonnelle.getInfo().get(0).getPays() + "," + dbInfoPersonnelle.getInfo().get(0).getVille();

        // Filter out empty contact information
        List<String> contactInfo = new ArrayList<>();
        List<Bitmap> icons = new ArrayList<>();

        if (!isNullOrEmpty(phone)) {
            contactInfo.add(phone);
            icons.add(phoneIcon);
        }
        if (!isNullOrEmpty(email)) {
            contactInfo.add(email);
            icons.add(emailIcon);
        }
        if (!isNullOrEmpty(linkedin)) {
            contactInfo.add(linkedin);
            icons.add(addressIcon);
        }
        if (!isNullOrEmpty(address)) {
            contactInfo.add(address);
            icons.add(placeholderIcon); // Use addressIcon for address
        }

        // Calculate the new left margin to align heading with the phone icon
        int iconWidth = phoneIcon.getWidth();
        int newMarginLeft = marginLeft - iconWidth - iconMarginRight;

        // Draw the "Contact" heading
        String heading = "Contact".toUpperCase();
        float headingY = startY + paint.getTextSize() + 10; // Y position for heading

        // Draw background rectangle starting from the third letter
        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.LTGRAY); // Background color
        float headingWidth = paint.measureText(heading);
        float startX = newMarginLeft + paint.measureText(heading.substring(0, 2)); // Start from the third letter
        canvas.drawRect(startX, headingY - paint.getTextSize(), startX + backgroundWidth, headingY + paint.getTextSize()-15, backgroundPaint);

        // Draw the heading text
        canvas.drawText(heading, newMarginLeft, headingY, paint);

        // Draw each line of contact information with its icon
        for (int i = 0; i < contactInfo.size(); i++) {
            // Draw icon
            if (i < icons.size() && icons.get(i) != null) {
                float iconY = headingY + headingMarginBottom + elementMarginTop + lineHeight * i - icons.get(i).getHeight() / 2;
                canvas.drawBitmap(icons.get(i), newMarginLeft, iconY, null);
            }

            // Draw text
            paint.setTextSize(10); // Reset text size for contact information
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Reset typeface for contact information
            float textY = headingY + headingMarginBottom + elementMarginTop + paint.getTextSize() + lineHeight * i;
            canvas.drawText(contactInfo.get(i), marginLeft, textY, paint);
        }
    }


    // Helper method to check if a string is null or empty
    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }


    private int drawCompetence(Canvas canvas, int startY) {
        // Create and configure Paint object for heading
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure Paint object for competence text
        Paint textPaint = new Paint();
        textPaint.setColor(Color.rgb(41, 45, 45)); // Set text color to gray
        textPaint.setTextSize(15);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = 290;
        int headingMarginBottom = 10;
        int elementMarginTop = 20;
        int lineHeight = 25;
        int competenceMaxWidth = 300; // Maximum width for competence text
        int iconMarginRight = 10; // Space between icon and heading text

        /*int marginLeft = 270; // Left margin for text
        int iconSize = 20; // Size of the icon
        int iconMarginRight = 10; // Margin between icon and heading text
        int lineHeight = 25; // Space between lines
        int maxLineWidth = 300; // Maximum width for wrapping text
        int headingMarginBottom = 25; // Space below the header*/

        // Load icon from resources
        Bitmap competenceIcon = BitmapFactory.decodeResource(getResources(), R.drawable.user); // Replace with your icon resource
        if (competenceIcon == null) {
            // Handle missing icon, e.g., use a placeholder or log an error
            Log.e("DrawCompetence", "Icon is missing.");
            return startY;
        }

        // Draw the "Compétence" heading with an icon
        String heading = "Compétence".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();

        // Calculate position to draw icon and heading text
        int iconWidth = competenceIcon.getWidth();
        int newMarginLeft = marginLeft - iconWidth - iconMarginRight;

        // Draw the icon
        canvas.drawBitmap(Bitmap.createScaledBitmap(competenceIcon, iconWidth, competenceIcon.getHeight(), true),
                newMarginLeft, startY, null);

        // Draw the heading text
        canvas.drawText(heading, newMarginLeft + iconWidth + iconMarginRight, headingY, headingPaint);

        // Retrieve competence information from database
        ArrayList<InfoPersonnelle> aboutUser = dbInfoPersonnelle.getInfo();

        // Check if there is any competence information to display
        if (aboutUser != null && !aboutUser.isEmpty()) {
            int yOffset = (int) (headingY + headingMarginBottom + elementMarginTop);

            for (InfoPersonnelle info : aboutUser) {
                String aboutText = info.getAbout();
                if (aboutText != null && !aboutText.isEmpty()) {
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
            }

            // Return the bottom Y position of this section
            return yOffset;
        }

        // If no competence information is available, skip drawing and return the initial Y position with extra margin
        return startY;
    }



    private int drawExperienceDeTravail(Canvas canvas, int startY) {
        // Create and configure Paint object for heading
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.rgb(41, 45, 45));
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure Paint object for normal text
        Paint normalPaint = new Paint();
        normalPaint.setColor(Color.BLACK); // Set text color to gray
        normalPaint.setTextSize(15);
        normalPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure Paint object for bullet points
        Paint bulletPaint = new Paint();
        bulletPaint.setColor(Color.BLACK); // Set text color to gray
        bulletPaint.setTextSize(15);
        bulletPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure Paint object for vertical line
        Paint linePaint = new Paint();
        linePaint.setColor(Color.rgb(179, 127, 86)); // Set line color to gray
        linePaint.setStrokeWidth(2);
        linePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Define margins and spacing
        int marginLeft = 290;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;
        int bulletMargin = 20;
        int resumeMaxWidth = 260; // Maximum width for resume text
        int iconMarginRight = 10; // Space between icon and heading text
        int lineLeft = marginLeft + 30; // Left margin for the vertical line
        int dateMarginRight = 15; // Space between dates and the vertical line

        // Load icon from resources
        Bitmap experienceIcon = BitmapFactory.decodeResource(getResources(), R.drawable.portfolio); // Replace with your icon resource
        if (experienceIcon == null) {
            // Handle missing icon, e.g., use a placeholder or log an error
            Log.e("DrawExperience", "Icon is missing.");
            return startY;
        }

        // Draw the "Expérience de Travail" heading with an icon
        String heading = "Expérience de Travail".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();

        // Calculate position to draw icon and heading text
        int iconWidth = experienceIcon.getWidth();
        int newMarginLeft = marginLeft - iconWidth - iconMarginRight;

        // Draw the icon
        canvas.drawBitmap(Bitmap.createScaledBitmap(experienceIcon, iconWidth, experienceIcon.getHeight(), true),
                newMarginLeft, startY, null);

        // Draw the heading text
        canvas.drawText(heading, newMarginLeft + iconWidth + iconMarginRight, headingY, headingPaint);

        // Retrieve experience information from database
        ArrayList<InfoExperience> experienceInfo = dbExperienceDeTravaille.getAllInfoExperience();

        // Check if experienceInfo is empty
        if (experienceInfo == null || experienceInfo.isEmpty()) {
            // Return the starting Y position if there is no experience information
            return startY;
        }

        // Calculate the Y offset for experience information
        float yOffset = headingY + headingMarginBottom + elementMarginTop;

        // Draw each piece of experience information
        for (InfoExperience info : experienceInfo) {
            // Draw the start date
            String startDate = info.getDateDébut();
            float dateTextWidth = normalPaint.measureText(startDate);
            canvas.drawText(startDate, lineLeft - dateTextWidth - dateMarginRight, yOffset, normalPaint);

            // Draw the end date below the start date
            String endDate = info.getDateDeFin();
            canvas.drawText(endDate, lineLeft - dateTextWidth - dateMarginRight, yOffset + lineHeight, normalPaint);

            // Draw the vertical line starting from the yOffset and ending after the job title
            canvas.drawLine(lineLeft, yOffset-10, lineLeft, yOffset + lineHeight * 3, linePaint);

            // Draw the company name on the first line, next to the vertical line
            canvas.drawText(info.getNomEntreprise(), marginLeft+40, yOffset, bulletPaint);
            yOffset += lineHeight;

            normalPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            // Draw the job title on the next line
            canvas.drawText(info.getTitreDePoste(), marginLeft+40, yOffset, normalPaint);
            yOffset += lineHeight;

            normalPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            normalPaint.setColor(Color.rgb(41,45,45));
            // Draw the resume with text wrapping
            String resume = "• " + info.getRésumé();
            Paint.FontMetrics fontMetrics = normalPaint.getFontMetrics();
            float textX = marginLeft + bulletMargin +40;
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

            // Add space between different experiences
            yOffset += elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) yOffset;
    }





    private int drawEducation(Canvas canvas, int startY) {
        // Create and configure the Paint object for headings
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.rgb(41,45,45));
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for school names (big and black)
        Paint schoolNamePaint = new Paint();
        schoolNamePaint.setColor(Color.rgb(41, 45, 45)); // Set text color to gray
        schoolNamePaint.setTextSize(15);
        schoolNamePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for date ranges (small and gray)
        Paint dateRangePaint = new Paint();
        dateRangePaint.setColor(Color.GRAY); // Change color to gray for date ranges
        dateRangePaint.setTextSize(14); // Adjust size as needed
        dateRangePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Create and configure the Paint object for professions (normal)
        Paint professionPaint = new Paint();
        professionPaint.setColor(Color.rgb(41, 45, 45)); // Set text color to gray
        professionPaint.setTextSize(15);
        professionPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = 290;
        int iconMarginRight = 10; // Space between icon and heading text
        int bulletMarginLeft = 270; // Additional margin for bullet points
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;

        // Load the icon for the heading
        Bitmap educationIcon = BitmapFactory.decodeResource(getResources(), R.drawable.book_open);
        int iconWidth = educationIcon.getWidth();
        int iconHeight = educationIcon.getHeight();

        // Get the education information
        ArrayList<InfoEducation> educationInfo = dbInfoEducation.getAllInfoEducation();

        // Check if educationInfo is empty
        if (educationInfo == null || educationInfo.isEmpty()) {
            // Return the starting Y position if there is no education information
            return startY;
        }

        // Draw the "Éducation" heading with an icon
        String heading = "Éducation".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();

        // Draw the icon
        canvas.drawBitmap(educationIcon, marginLeft - iconWidth - iconMarginRight, headingY - iconHeight + 5, null);

        // Draw the heading text next to the icon
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Draw each piece of education information
        float currentY = headingY + headingMarginBottom + elementMarginTop;

        for (InfoEducation education : educationInfo) {
            // Draw the school name and dates on the first line
            String schoolNameAndDates = education.getShool() + " " + education.getStartYier() + " - " + education.getEndYier();
            canvas.drawText(schoolNameAndDates, marginLeft, currentY, schoolNamePaint);

            // Move to the next line for the job title
            currentY += lineHeight;

            // Draw the job title on the next line with additional margin for bullet point
            canvas.drawText("• " + education.getMetier(), bulletMarginLeft+30, currentY, professionPaint);

            // Move to the next section with padding
            currentY += lineHeight + elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }








    private void drawLanguages(Canvas canvas, int startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20); // Text size for the heading
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface for the heading

        int marginLeft = 24; // Left margin for text
        int headingMarginBottom = 10; // Space below the heading
        int elementMarginTop = 25; // Space between heading and first element
        int lineHeight = 25; // Space between lines

        // Define background paint
        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.LTGRAY); // Set background color

        // Draw the "Languages" heading if the languages list is not empty
        ArrayList<String> languages = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLangueArrayList();
        if (languages != null && !languages.isEmpty()) {
            String heading = "Languages".toUpperCase();

            // Measure the width of the first two letters
            float firstTwoLettersWidth = paint.measureText(heading.substring(0, 2));

            // Draw background for the heading starting from the third letter
            float headingY = startY + paint.getTextSize() + 50; // Y position for heading
            canvas.drawRect(marginLeft + firstTwoLettersWidth, headingY - paint.getTextSize(), marginLeft + firstTwoLettersWidth + 150, headingY + 10, backgroundPaint);
            canvas.drawText(heading, marginLeft, headingY, paint);

            // Draw each language
            paint.setTextSize(15); // Reset text size for languages
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Reset typeface for languages
            float textY = headingY + headingMarginBottom + elementMarginTop;
            for (String language : languages) {
                canvas.drawText("• " + language, marginLeft, textY, paint);
                textY += lineHeight;
            }
            startY = (int) textY; // Update startY to be the end of the languages section
        }

        // Draw the "Soft Skills" heading if the soft skills list is not empty
        ArrayList<String> softSkills = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getSoftSkillsArrayList();
        if (softSkills != null && !softSkills.isEmpty()) {
            String softSkillsHeading = "Soft Skills".toUpperCase();

            // Measure the width of the first two letters
            float firstTwoLettersWidth = paint.measureText(softSkillsHeading.substring(0, 2));

            // Draw background for the soft skills heading starting from the third letter
            float softSkillsHeadingY = startY + 30; // Adjust Y position
            canvas.drawRect(marginLeft + firstTwoLettersWidth, softSkillsHeadingY - paint.getTextSize(), marginLeft + firstTwoLettersWidth + 150, softSkillsHeadingY + 10, backgroundPaint);
            paint.setTextSize(20); // Text size for the heading
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText(softSkillsHeading, marginLeft, softSkillsHeadingY, paint);

            paint.setTextSize(15); // Text size for the heading
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

            // Draw each soft skill
            float textY = softSkillsHeadingY + headingMarginBottom + elementMarginTop;
            for (String softSkill : softSkills) {
                canvas.drawText("• " + softSkill, marginLeft, textY, paint);
                textY += lineHeight;
            }
            startY = (int) textY; // Update startY to be the end of the soft skills section
        }

        // Draw the "Loisirs" heading if the loisirs list is not empty
        ArrayList<String> loisirs = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLoisirArrayList();
        if (loisirs != null && !loisirs.isEmpty()) {
            String loisirsHeading = "Loisirs".toUpperCase();

            // Measure the width of the first two letters
            float firstTwoLettersWidth = paint.measureText(loisirsHeading.substring(0, 2));

            // Draw background for the loisirs heading starting from the third letter
            float loisirsHeadingY = startY + 30; // Adjust Y position
            canvas.drawRect(marginLeft + firstTwoLettersWidth, loisirsHeadingY - paint.getTextSize(), marginLeft + firstTwoLettersWidth + 150, loisirsHeadingY + 10, backgroundPaint);
            paint.setTextSize(20); // Text size for the heading
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText(loisirsHeading, marginLeft, loisirsHeadingY, paint);

            paint.setTextSize(15); // Text size for the heading
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

            // Draw each loisir
            float textY = loisirsHeadingY + headingMarginBottom + elementMarginTop;
            for (String loisir : loisirs) {
                canvas.drawText("• " + loisir, marginLeft, textY, paint);
                textY += lineHeight;
            }
            startY = (int) textY; // Update startY to be the end of the loisirs section
        }

        // Draw the "Certificate" heading if the certificates list is not empty
        ArrayList<String> certificates = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getCertificateArrayList();
        if (certificates != null && !certificates.isEmpty()) {
            String certificateHeading = "Certificate".toUpperCase();

            // Measure the width of the first two letters
            float firstTwoLettersWidth = paint.measureText(certificateHeading.substring(0, 2));

            // Draw background for the certificate heading starting from the third letter
            float certificateHeadingY = startY + 30; // Adjust Y position
            canvas.drawRect(marginLeft + firstTwoLettersWidth, certificateHeadingY - paint.getTextSize(), marginLeft + firstTwoLettersWidth + 150, certificateHeadingY + 10, backgroundPaint);
            paint.setTextSize(20); // Text size for the heading
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText(certificateHeading, marginLeft, certificateHeadingY, paint);

            paint.setTextSize(15); // Text size for the heading
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

            // Draw each certificate
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


    private void drawHeaderImage(Canvas canvas) {
        try {
            // Retrieve and resize the bitmap from the URL
            Bitmap originalBitmap = getBitmapFromURL(dbInfoPersonnelle.getInfo().get(0).getLogoOfUser());
            Bitmap resizedBitmap = resizeBitmap(originalBitmap);

            // Calculate the position for the image in the top-left corner
            int imageX = IMAGE_MARGIN; // Padding from the left edge
            int imageY = IMAGE_MARGIN; // Padding from the top edge

            // Draw the circular image at the calculated position
            drawSquareImage(canvas, resizedBitmap, imageX, imageY);

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
        paint.setTextSize(35); // Adjust text size as needed
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Set text style

        // Fetch the user's name
        String userName = dbInfoPersonnelle.getInfo().get(0).getL_name().toUpperCase() + " " + dbInfoPersonnelle.getInfo().get(0).getF_name().toUpperCase()  ;
        float textWidth = paint.measureText(capitalizeFirstLetterOfEachWord(userName));


        // Calculate the position for the text (center horizontally)
        float textX = ((canvas.getWidth() - textWidth)+128) / 2;

        // Draw the text at the calculated position
        canvas.drawText(userName, textX, 90, paint);
    }

    private void drawUserJobTitle(Canvas canvas, float nameTextX, float nameTextY) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(36,36,36)); // Set a different color for job title
        paint.setTextSize(30); // Adjust text size for job title
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Set text style

        // Fetch the user's job title
        String userJobTitle =capitalizeFirstLetterOfEachWord( dbInfoPersonnelle.getInfo().get(0).getJob()); // Adjust method to get job title
        float jobTitleWidth = paint.measureText(userJobTitle);

        // Define a fixed vertical space below the user's name
        float spaceBelowName = 10; // Adjust this value as needed

        // Calculate the vertical position for the job title
        float jobTitleTextY = 125;

        // Calculate the horizontal position for the job title (center horizontally)
        float jobTitleTextX = ((canvas.getWidth() - jobTitleWidth+15)) / 2;

        // Draw the job title at the calculated position
        canvas.drawText(userJobTitle, jobTitleTextX, jobTitleTextY, paint);

    }








    private void drawSquareImage(Canvas canvas, Bitmap bitmap, int x, int y) {
        // Define the size of the square image as a percentage of the canvas width
        int imageSize = (int) (canvas.getWidth() * IMAGE_SIZE_PERCENT / 100);

        // Create a new bitmap with square dimensions
        Bitmap squareBitmap = Bitmap.createBitmap(imageSize, imageSize, Bitmap.Config.ARGB_8888);
        Canvas squareCanvas = new Canvas(squareBitmap);

        // Prepare the paint object
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        // Draw a background color (optional)
        paint.setColor(Color.WHITE); // Set to white or any color you want
        squareCanvas.drawRect(0, 0, imageSize, imageSize, paint);

        // Draw the bitmap onto the square canvas
        Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect dstRect = new Rect(0, 0, imageSize, imageSize);
        squareCanvas.drawBitmap(bitmap, srcRect, dstRect, paint);

        // Draw the square bitmap on the main canvas at position (x, y)
        canvas.drawBitmap(squareBitmap, x, y, null);
    }


    private void drawWhiteBackgroundWithBorder(Canvas canvas) {
        // Create a Paint object for the background color
        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE); // Set the background color to white

        // Create a Paint object for the border
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.BLACK); // Set the border color to black
        borderPaint.setStrokeWidth(2); // Set the border width to 2 pixels
        borderPaint.setStyle(Paint.Style.STROKE); // Set paint style to stroke for border

        // Define the rectangle area to fill
        int startX = 10; // Start x position
        int endX = 225; // End x position
        int startY = 30; // Start y position (top of the canvas)
        int endY = canvas.getHeight() - 30; // End y position (bottom of the canvas)

        // Draw the rectangle with the background color
        canvas.drawRect(startX, startY, endX, endY, backgroundPaint);

        // Draw the border around the rectangle
        canvas.drawRect(startX, startY, endX, endY, borderPaint);
    }

    private void drawWhiteBackgroundWithBorder2(Canvas canvas) {
        // Create a Paint object for the background color
        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.rgb(223, 215, 211)); // Set the background color to a light beige

        // Create a Paint object for the border (bottom border in yellow)
        Paint bottomBorderPaint = new Paint();
        bottomBorderPaint.setColor(Color.rgb(223, 215, 211)); // Set the bottom border color to yellow
        bottomBorderPaint.setStrokeWidth(2); // Set the border width to 2 pixels
        bottomBorderPaint.setStyle(Paint.Style.STROKE); // Set paint style to stroke for border

        // Define the rectangle area to fill
        int startX = 10;  // Start x position
        int startY = 30;  // Start y position (top of the rectangle)
        int endX = 225;   // End x position (right side of the rectangle)
        int endY = 95;    // End y position (bottom of the rectangle)

        // Draw the rectangle with the background color
        canvas.drawRect(startX, startY, endX, endY, backgroundPaint);

        // Draw only the bottom border of the rectangle
        canvas.drawLine(startX, endY, endX, endY, bottomBorderPaint); // Draw bottom border
    }



    private void drawGreenBackground(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(	223	,215	,211)); // Set the background color to green

        // Define the rectangle area to fill
        int startX = 0; // Start x position
        int startY = 0; // Start y position (top of the canvas)
        int endX = canvas.getWidth(); // End x position (full width of the canvas)
        int endY = 97; // End y position (200 pixels from the top)

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