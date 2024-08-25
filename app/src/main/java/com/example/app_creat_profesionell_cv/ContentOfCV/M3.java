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
import android.text.Layout;
import android.text.StaticLayout;
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
import java.util.Arrays;
import java.util.List;

public class M3 extends AppCompatActivity {

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
        setContentView(R.layout.activity_m3);


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
                startActivity(new Intent(M3.this, InfoPersonnelleActivity.class));
            }
        });

        éducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M3.this, InfoEducationActivity.class));
            }
        });
        exDeTravaille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M3.this, ExperinceActivity.class));
            }
        });
        projet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M3.this, InfoProjetActivity.class));
            }
        });

        infoAdditionnelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M3.this, InformationAdditionnelleActivity.class));
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
                if (ContextCompat.checkSelfPermission(M3.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(M3.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {
                    // Permission is granted, proceed with PDF creation
                    PdfDocument document = createPDF();
                    if (document != null) {
                        ByteArrayOutputStream outputStream = savePDFToByteArray(document);

                        // Start ShowDocumentActivity to display the PDF
                        Intent intent = new Intent(M3.this, ShowDocumentActivity.class);
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

            //drawBlueVerticalLine(canvas);
            drawBlueBackground(canvas);
            // Draw header image
            drawHeaderImage(canvas);
            drawContactInfo(canvas,220);
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
        titleT.setTextSize(15); // Heading text size
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
        final int maxLineWidth = 300; // Maximum width for resume text before wrapping

        // Draw the "Projet" heading
        String heading = "Projet".toUpperCase();
        float headingY = startY + paint.getTextSize();  // Y position for heading
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Measure the width of the heading text
        float headingWidth = paint.measureText(heading);

        // Draw a line directly below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.rgb(96, 96, 96));
        linePaint.setStrokeWidth(2); // Line width
        canvas.drawLine(marginLeft, headingY + headingMarginBottom, 550, headingY + headingMarginBottom, linePaint);

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
        int yOffset = (int) (headingY + headingMarginBottom + elementMarginTop);

        // Draw each piece of project information
        for (InfoProjet info : projectInfo) {
            // Check if all fields are empty
            if (isInfoProjetEmpty(info)) {
                continue; // Skip this project if all fields are empty
            }

            // Draw the project title and dates on the same line
            if (info.getNomEntreprise() != null && !info.getNomEntreprise().isEmpty()) {
                String formattedTitle = capitalizeFirstLetterOfEachWord(info.getNomEntreprise());
                String dates = (info.getDateDebut() != null ? info.getDateDebut() : "") +
                        (info.getDateFin() != null ? " - " + info.getDateFin() : "");
                canvas.drawText(formattedTitle + " " + dates, marginLeft, yOffset, titleT);
                yOffset += lineHeight;
            }

            // Draw the job title (metier)
            if (info.getTitreProjet() != null && !info.getTitreProjet().isEmpty()) {
                String formattedJobTitle = capitalizeFirstLetterOfEachWord(info.getTitreProjet());
                canvas.drawText(formattedJobTitle, marginLeft, yOffset, titlePaint);
                yOffset += lineHeight;
            }

            // Draw the resume with a bullet point and small margin
            if (info.getResume() != null && !info.getResume().isEmpty()) {
                String resumeText = info.getResume();

                // Draw the bullet point with additional margin
                canvas.drawText("•", bulletMarginLeft, yOffset, bulletPaint);

                // Measure the resume text and wrap if necessary
                String[] wrappedLines = wrapText(resumeText, maxLineWidth, grayPaint);
                for (String line : wrappedLines) {
                    canvas.drawText(line, bulletMarginLeft + bulletSize + bulletTextMargin, yOffset, grayPaint);
                    yOffset += lineHeight; // Move to next line
                }
                yOffset += smallMarginTop; // Additional space after the resume
            }

            // Add space between different projects
            yOffset += elementMarginTop;
        }

        // Return the bottom Y position of this section
        return yOffset;
    }

    // Helper method to check if all fields of InfoProjet are empty
    private boolean isInfoProjetEmpty(InfoProjet info) {
        return (info.getNomEntreprise() == null || info.getNomEntreprise().isEmpty()) &&
                (info.getDateDebut() == null || info.getDateDebut().isEmpty()) &&
                (info.getDateFin() == null || info.getDateFin().isEmpty()) &&
                (info.getTitreProjet() == null || info.getTitreProjet().isEmpty()) &&
                (info.getResume() == null || info.getResume().isEmpty());
    }

    // Helper method to wrap text to a maximum width
    private String[] wrapText(String text, int maxWidth, Paint paint) {
        ArrayList<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            String testLine = line.toString() + (line.length() > 0 ? " " : "") + word;
            if (paint.measureText(testLine) <= maxWidth) {
                line.append((line.length() > 0 ? " " : "")).append(word);
            } else {
                lines.add(line.toString());
                line = new StringBuilder(word);
            }
        }

        if (line.length() > 0) {
            lines.add(line.toString());
        }

        return lines.toArray(new String[0]);
    }



    private void drawContactInfo(Canvas canvas, int startY) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(20); // Increased text size for the heading
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface for the heading

        int marginLeft = 60; // Left margin for text (considering icon width)
        int headingMarginBottom = 10; // Space below the heading
        int elementMarginTop = 15; // Space between line and first element
        int lineHeight = 50; // Space between lines
        int iconMarginRight = 10; // Space between icon and text

        // Load icons from resources or use a placeholder
        Bitmap phoneIcon = BitmapFactory.decodeResource(getResources(), R.drawable.telephone);
        Bitmap emailIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_email);
        Bitmap addressIcon = BitmapFactory.decodeResource(getResources(), R.drawable.social);
        Bitmap placeholderIcon = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder); // Placeholder icon

        // Check if dbInfoPersonnelle is empty or null
        if (dbInfoPersonnelle == null || dbInfoPersonnelle.getInfo() == null || dbInfoPersonnelle.getInfo().isEmpty()) {
            return; // Exit if no contact information is available
        }

        // Fetch the user's contact information
        String phone = dbInfoPersonnelle.getInfo().get(0).getN_phone();
        String email = dbInfoPersonnelle.getInfo().get(0).getEmail();
        String linkedin = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLinkdin();
        String address = dbInfoPersonnelle.getInfo().get(0).getPays() + "," + dbInfoPersonnelle.getInfo().get(0).getVille();

        // Create lists for non-empty contact information and icons
        ArrayList<String> contactInfo = new ArrayList<>();
        ArrayList<Bitmap> icons = new ArrayList<>();

        if (phone != null && !phone.isEmpty()) {
            contactInfo.add(phone);
            icons.add(phoneIcon != null ? phoneIcon : placeholderIcon);
        }
        if (email != null && !email.isEmpty()) {
            contactInfo.add(email);
            icons.add(emailIcon != null ? emailIcon : placeholderIcon);
        }
        if (linkedin != null && !linkedin.isEmpty()) {
            contactInfo.add(linkedin);
            icons.add(addressIcon != null ? addressIcon : placeholderIcon);
        }
        if (address != null && !address.isEmpty()) {
            contactInfo.add(address);
            icons.add(placeholderIcon); // Use placeholder for address
        }

        // Calculate the new left margin to align heading with the phone icon
        int iconWidth = (phoneIcon != null) ? phoneIcon.getWidth() : placeholderIcon.getWidth();
        int newMarginLeft = marginLeft - iconWidth - iconMarginRight;

        // Draw the "Contact" heading
        String heading = "Contact".toUpperCase();
        float headingY = startY + paint.getTextSize(); // Y position for heading
        canvas.drawText(heading, newMarginLeft, headingY, paint);

        // Measure the width of the heading text
        float headingWidth = paint.measureText(heading);

        // Draw a line directly below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.WHITE);
        linePaint.setStrokeWidth(2); // Line width
        canvas.drawLine(newMarginLeft, headingY + headingMarginBottom, newMarginLeft + headingWidth, headingY + headingMarginBottom, linePaint);

        // Draw each line of contact information with its icon
        for (int i = 0; i < contactInfo.size(); i++) {
            // Draw icon
            Bitmap currentIcon = icons.get(i);
            if (currentIcon != null) {
                float iconY = headingY + headingMarginBottom + elementMarginTop - 10 + paint.getTextSize() + lineHeight * i - currentIcon.getHeight() / 2;
                canvas.drawBitmap(currentIcon, newMarginLeft, iconY, null);
            }

            // Draw text
            paint.setTextSize(13); // Reset text size for contact information
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Reset typeface for contact information
            String infoText = contactInfo.get(i) != null ? contactInfo.get(i) : ""; // Ensure text is not null
            float textY = headingY + headingMarginBottom + elementMarginTop + paint.getTextSize() + lineHeight * i;
            canvas.drawText(infoText, marginLeft, textY, paint);
        }
    }





    private int drawCompetence(Canvas canvas, int startY) {
        ArrayList<InfoPersonnelle> aboutUser = dbInfoPersonnelle.getInfo();

        // If aboutUser is empty, return the initial startY without drawing anything
        if (aboutUser == null || aboutUser.isEmpty()) {
            return startY;
        }

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2);

        Paint normalPaint = new Paint();
        normalPaint.setColor(Color.rgb(96, 96, 96));
        normalPaint.setTextSize(15);
        normalPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        int marginLeft = 260;
        int headingMarginBottom = 10;
        int elementMarginTop = 20;
        int lineHeight = 25;
        int maxLineWidth = 300; // Maximum width for a single line of text

        // Draw the "Compétence" heading
        String heading = "Compétence".toUpperCase();
        float headingY = startY + paint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Draw a line directly below the heading
        canvas.drawLine(marginLeft, headingY + headingMarginBottom, 550, headingY + headingMarginBottom, linePaint);

        // Draw each line of competence information
        float currentY = headingY + headingMarginBottom + elementMarginTop;

        for (int i = 0; i < aboutUser.size(); i++) {
            String text = aboutUser.get(i).getAbout() != null ? aboutUser.get(i).getAbout() : "";
            // Break text into lines that fit within the maximum width
            String[] lines = wrapText(text, maxLineWidth, normalPaint);

            for (String line : lines) {
                canvas.drawText(line, marginLeft, currentY, normalPaint);
                currentY += lineHeight;
            }

            currentY += elementMarginTop; // Add space between different competence items
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }

    // Helper method to wrap text




    private int drawExperienceDeTravail(Canvas canvas, int startY) {
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.rgb(56, 56, 56));
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint normalPaint = new Paint();
        normalPaint.setColor(Color.rgb(96, 96, 96)); // Set text color to gray
        normalPaint.setTextSize(15);
        normalPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        Paint bulletPaint = new Paint();
        bulletPaint.setColor(Color.rgb(48, 48, 48)); // Set bullet color to gray
        bulletPaint.setTextSize(15);
        bulletPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        int marginLeft = 260;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;
        int bulletMargin = 20;
        int maxLineWidth = 300; // Maximum width for text before wrapping

        // Retrieve experience information from database
        ArrayList<InfoExperience> experienceInfo = dbExperienceDeTravaille.getAllInfoExperience();

        // Check if experienceInfo is empty and return the original startY if so
        if (experienceInfo == null || experienceInfo.isEmpty()) {
            return startY;
        }

        // Draw the "Expérience de Travail" heading
        String heading = "Expérience de Travail".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Draw a line directly below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2);
        canvas.drawLine(marginLeft, headingY + headingMarginBottom,
                marginLeft + 550, headingY + headingMarginBottom, linePaint);

        // Calculate the Y offset for experience information
        float yOffset = headingY + headingMarginBottom + elementMarginTop;

        // Draw each piece of experience information
        for (InfoExperience info : experienceInfo) {
            // Draw the company name and dates
            String companyAndDates = info.getNomEntreprise() + "   " + info.getDateDébut() + " - " + info.getDateDeFin();
            canvas.drawText(companyAndDates, marginLeft, yOffset, bulletPaint);
            yOffset += lineHeight;

            // Draw the job title
            canvas.drawText(info.getTitreDePoste(), marginLeft, yOffset, normalPaint);
            yOffset += lineHeight;

            // Draw the resume with wrapping if necessary
            String resumeText = info.getRésumé();
            Paint.FontMetrics fontMetrics = normalPaint.getFontMetrics();
            float textWidth = 0;

            String[] words = resumeText.split(" ");
            StringBuilder line = new StringBuilder();
            boolean isFirstLine = true;

            for (String word : words) {
                String testLine = line.toString() + word + " ";
                textWidth = normalPaint.measureText(testLine);

                if (textWidth > maxLineWidth) {
                    // Draw the previous line with bullet point if it is the first line
                    if (isFirstLine) {
                        canvas.drawText("• " + line.toString().trim(), marginLeft, yOffset, normalPaint);
                        isFirstLine = false; // Subsequent lines will not have a bullet point
                    } else {
                        canvas.drawText(line.toString().trim(), marginLeft, yOffset, normalPaint);
                    }
                    yOffset += lineHeight;
                    line.setLength(0); // Clear the line
                }
                line.append(word).append(" ");
            }

            if (line.length() > 0) {
                // Draw the last line of the resume
                if (isFirstLine) {
                    canvas.drawText("• " + line.toString().trim(), marginLeft+20, yOffset, normalPaint);
                } else {
                    canvas.drawText(line.toString().trim(), marginLeft, yOffset, normalPaint);
                }
                yOffset += lineHeight;
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
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for lines
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2);

        // Create and configure the Paint object for school names (big and black)
        Paint schoolNamePaint = new Paint();
        schoolNamePaint.setColor(Color.rgb(48,48,48));
        schoolNamePaint.setTextSize(18); // Adjust size as needed
        schoolNamePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for date ranges (small and gray)
        Paint dateRangePaint = new Paint();
        dateRangePaint.setColor(Color.GRAY); // Change color to gray for date ranges
        dateRangePaint.setTextSize(14); // Adjust size as needed
        dateRangePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Create and configure the Paint object for professions (normal)
        Paint professionPaint = new Paint();
        professionPaint.setColor(Color.rgb(40,40,40));
        professionPaint.setTextSize(15); // Adjust size as needed
        professionPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = 260;
        int bulletMarginLeft = 270; // Additional margin for bullet points
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;
        int textWidthLimit = 300; // Limit for wrapping text

        // Get the education information
        ArrayList<InfoEducation> educationInfo = dbInfoEducation.getAllInfoEducation();

        // Check if all elements in educationInfo are empty
        boolean allEmpty = true;
        for (InfoEducation education : educationInfo) {
            if (education.getShool() != null && !education.getShool().isEmpty() ||
                    education.getStartYier() != null && !education.getStartYier().isEmpty() ||
                    education.getEndYier() != null && !education.getEndYier().isEmpty() ||
                    education.getMetier() != null && !education.getMetier().isEmpty()) {
                allEmpty = false;
                break;
            }
        }

        // If all elements are empty, do not draw the section
        if (allEmpty) {
            return startY; // Return the starting Y position if nothing is drawn
        }

        // Draw the "Éducation" heading
        String heading = "Éducation".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Draw a line below the heading
        float lineEndX = 550;
        canvas.drawLine(marginLeft, headingY + headingMarginBottom, lineEndX, headingY + headingMarginBottom, linePaint);

        // Draw each piece of education information
        float currentY = headingY + headingMarginBottom + elementMarginTop;

        for (InfoEducation education : educationInfo) {
            // Draw the school name and dates on the first line
            String schoolNameAndDates = education.getShool() + "  " + education.getStartYier() + " - " + education.getEndYier();
            canvas.drawText(schoolNameAndDates, marginLeft, currentY, schoolNamePaint);

            // Move to the next line for the job title
            currentY += lineHeight;

            // Wrap and draw the profession text
            String professionText = "• " + education.getMetier();
            float textWidth = professionPaint.measureText(professionText);
            if (textWidth > textWidthLimit) {
                // Split text into lines if it exceeds the width limit
                float availableWidth = textWidthLimit - bulletMarginLeft;
                float startX = bulletMarginLeft;
                float lineWidth = 0;
                String[] words = professionText.split(" ");
                StringBuilder currentLine = new StringBuilder();

                for (String word : words) {
                    float wordWidth = professionPaint.measureText(word + " ");
                    if (lineWidth + wordWidth > availableWidth) {
                        canvas.drawText(currentLine.toString(), startX, currentY, professionPaint);
                        currentLine.setLength(0);
                        currentY += lineHeight;
                        lineWidth = 0;
                        startX = bulletMarginLeft;
                    }
                    currentLine.append(word).append(" ");
                    lineWidth += wordWidth;
                }
                if (currentLine.length() > 0) {
                    canvas.drawText(currentLine.toString(), startX, currentY, professionPaint);
                }
            } else {
                // Draw profession text without wrapping
                canvas.drawText(professionText, bulletMarginLeft, currentY, professionPaint);
            }

            // Move to the next section with padding
            currentY += lineHeight + elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }






    private void drawLanguages(Canvas canvas, int startY) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(20); // Text size for the heading
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface for the heading

        int marginLeft = 24; // Left margin for text
        int headingMarginBottom = 10; // Space below the heading
        int elementMarginTop = 25; // Space between line and first element
        int lineHeight = 25; // Space between lines

        // Draw the "Languages" heading
        String heading = "Languages".toUpperCase();
        float headingY = startY + paint.getTextSize() + 50; // Y position for heading
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Measure the width of the heading text
        float headingWidth = paint.measureText(heading);

        // Draw a line directly below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.WHITE);
        linePaint.setStrokeWidth(2); // Line width
        canvas.drawLine(marginLeft, headingY + headingMarginBottom, 200, headingY + headingMarginBottom, linePaint);

        // Language information text
        ArrayList<String> languages = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLangueArrayList();

        // Draw each language
        paint.setTextSize(20); // Reset text size for languages
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Reset typeface for languages
        float textY = headingY + headingMarginBottom + elementMarginTop;
        for (String language : languages) {
            canvas.drawText("• " + language, marginLeft, textY, paint);
            textY += lineHeight;
        }

        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        // Draw the "Soft Skills" heading below the languages
        float softSkillsHeadingY = textY + 30; // Adjust Y position
        canvas.drawText("Soft Skills".toUpperCase(), marginLeft, softSkillsHeadingY, paint);

        // Measure the width of the Soft Skills heading text
        float softSkillsHeadingWidth = paint.measureText("Soft Skills");

        // Draw a line directly below the Soft Skills heading
        canvas.drawLine(marginLeft, softSkillsHeadingY + headingMarginBottom, 200, softSkillsHeadingY + headingMarginBottom, linePaint);

        // Soft skills information text
        ArrayList<String> softSkills = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getSoftSkillsArrayList();

        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        // Draw each soft skill
        textY = softSkillsHeadingY + headingMarginBottom + elementMarginTop;
        for (String softSkill : softSkills) {
            canvas.drawText("• " + softSkill, marginLeft, textY, paint);
            textY += lineHeight;
        }
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Draw the "Loisirs" heading below the soft skills
        float loisirsHeadingY = textY + 30; // Adjust Y position
        canvas.drawText("Loisirs".toUpperCase(), marginLeft, loisirsHeadingY, paint);

        // Measure the width of the Loisirs heading text
        float loisirsHeadingWidth = paint.measureText("Loisirs");

        // Draw a line directly below the Loisirs heading
        canvas.drawLine(marginLeft, loisirsHeadingY + headingMarginBottom, 200, loisirsHeadingY + headingMarginBottom, linePaint);

        // Loisirs information text
        ArrayList<String> loisirs = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLoisirArrayList();

        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        // Draw each loisir
        textY = loisirsHeadingY + headingMarginBottom + elementMarginTop;
        for (String loisir : loisirs) {
            canvas.drawText("• " + loisir, marginLeft, textY, paint);
            textY += lineHeight;
        }

        // Draw the "Certificate" heading below the loisirs
        float certificateHeadingY = textY + 30; // Adjust Y position
        canvas.drawText("Certificate".toUpperCase(), marginLeft, certificateHeadingY, paint);

        // Measure the width of the Certificate heading text
        float certificateHeadingWidth = paint.measureText("Certificate");

        // Draw a line directly below the Certificate heading
        canvas.drawLine(marginLeft, certificateHeadingY + headingMarginBottom, 200, certificateHeadingY + headingMarginBottom, linePaint);

        // Certificates information text
        ArrayList<String> certificates = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getCertificateArrayList();

       // paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        // Draw each certificate
        textY = certificateHeadingY + headingMarginBottom + elementMarginTop;
        for (String certificate : certificates) {
            canvas.drawText("• " + certificate, marginLeft, textY, paint);
            textY += lineHeight;
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
        paint.setTextSize(35); // Adjust text size as needed
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Set text style

        // Fetch the user's name
        String userName = dbInfoPersonnelle.getInfo().get(0).getL_name().toUpperCase() + " " + dbInfoPersonnelle.getInfo().get(0).getF_name().toUpperCase()  ;
        float textWidth = paint.measureText(capitalizeFirstLetterOfEachWord(userName));


        // Calculate the position for the text (center horizontally)
        float textX = ((canvas.getWidth() - textWidth)+128) / 2;

        // Draw the text at the calculated position
        canvas.drawText(userName, textX, 128, paint);
    }

    private void drawUserJobTitle(Canvas canvas, float nameTextX, float nameTextY) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(36,36,36)); // Set a different color for job title
        paint.setTextSize(30); // Adjust text size for job title
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Set text style

        // Fetch the user's job title
        String userJobTitle = capitalizeFirstLetterOfEachWord(dbInfoPersonnelle.getInfo().get(0).getJob()); // Adjust method to get job title
        float jobTitleWidth = paint.measureText(userJobTitle);

        // Define a fixed vertical space below the user's name
        float spaceBelowName = 10; // Adjust this value as needed

        // Calculate the vertical position for the job title
        float jobTitleTextY = nameTextY + spaceBelowName + paint.getTextSize();

        // Use the same horizontal position as the user's name but adjust by -30
        float jobTitleTextX = nameTextX - 30;

        // Draw the job title at the calculated position
        canvas.drawText(userJobTitle, jobTitleTextX, jobTitleTextY, paint);

        // Draw a line below the job title
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK); // Set the line color to match the job title
        linePaint.setStrokeWidth(2); // Set line width

        // Calculate the start and end X positions for the line
        float lineStartX = jobTitleTextX;
        float lineEndX = jobTitleTextX + jobTitleWidth;
        float lineY = jobTitleTextY + 10; // Adjust vertical position as needed

        // Draw the line
        canvas.drawLine(lineStartX, lineY, lineEndX, lineY, linePaint);
    }










    /*private void drawCircularImage(Canvas canvas, Bitmap bitmap, int x, int y) {
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
    }*/
    private void drawCircularImage(Canvas canvas, Bitmap bitmap, int x, int y) {
        // Define fixed width for the circular image
        int imageSize = 150;
        int borderWidth = 3; // Width of the border in pixels

        // Calculate the aspect ratio of the original image
        float aspectRatio = (float) bitmap.getWidth() / bitmap.getHeight();

        // Determine new height based on the aspect ratio and fixed width
        int newHeight = (int) (imageSize / aspectRatio);

        // Create a resized bitmap with fixed width and calculated height
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, imageSize, newHeight, false);

        // Create a circular bitmap with fixed size
        Bitmap circularBitmap = Bitmap.createBitmap(imageSize, imageSize, Bitmap.Config.ARGB_8888);
        Canvas circularCanvas = new Canvas(circularBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        // Define rectangle and circle for drawing
        Rect rect = new Rect(0, 0, resizedBitmap.getWidth(), resizedBitmap.getHeight());
        RectF rectF = new RectF(0, 0, imageSize, imageSize);

        // Draw the circular bitmap
        circularCanvas.drawARGB(0, 0, 0, 0);
        circularCanvas.drawCircle(imageSize / 2, imageSize / 2, imageSize / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        circularCanvas.drawBitmap(resizedBitmap, rect, rectF, paint);

        // Draw the border around the circular image
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.WHITE); // Border color
        borderPaint.setStyle(Paint.Style.STROKE); // Set to stroke to draw border
        borderPaint.setStrokeWidth(borderWidth); // Set border width
        borderPaint.setAntiAlias(true);

        circularCanvas.drawCircle(imageSize / 2, imageSize / 2, (imageSize / 2) - borderWidth / 2, borderPaint);

        // Draw the circular bitmap on the main canvas
        canvas.drawBitmap(circularBitmap, x -10, y , null);

        // Recycle bitmaps to free up memory
        resizedBitmap.recycle();
        circularBitmap.recycle();
    }


    private void drawBlueBackground(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(22,56,83)); // Set the background color to blue

        // Define the rectangle area to fill
        int startX = 0; // Start x position
        int endX = 230; // End x position
        int startY = 0; // Start y position (top of the canvas)
        int endY = canvas.getHeight(); // End y position (bottom of the canvas)

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