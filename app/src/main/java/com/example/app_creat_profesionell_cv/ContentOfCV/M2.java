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
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_creat_profesionell_cv.Classes.InfoAdditionnelle;
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

public class M2 extends AppCompatActivity {

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
        setContentView(R.layout.activity_m2);

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
                startActivity(new Intent(M2.this, InfoPersonnelleActivity.class));
            }
        });

        éducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M2.this, InfoEducationActivity.class));
            }
        });
        exDeTravaille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M2.this, ExperinceActivity.class));
            }
        });
        projet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M2.this, InfoProjetActivity.class));
            }
        });

        infoAdditionnelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M2.this, InformationAdditionnelleActivity.class));
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
                if (ContextCompat.checkSelfPermission(M2.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(M2.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {
                    // Permission is granted, proceed with PDF creation
                    PdfDocument document = createPDF();
                    if (document != null) {
                        ByteArrayOutputStream outputStream = savePDFToByteArray(document);

                        // Start ShowDocumentActivity to display the PDF
                        Intent intent = new Intent(M2.this, ShowDocumentActivity.class);
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

            // Draw the background color
            Paint backgroundPaint = new Paint();
            backgroundPaint.setColor(Color.parseColor("#F4F4F4"));
            canvas.drawRect(0, 0, pageInfo.getPageWidth(), 200, backgroundPaint);

            // Draw the summary text starting from y = 270
            drawSummary(canvas);
            drawVerticalLine(canvas);
            int educationY = drawEducation(canvas, 370);

            // Draw skills section
            //int skillsY = drawSkills(canvas, educationY + 20); // Adjust spacing as needed

            // Draw user name and job title
            int logoBottomY = IMAGE_MARGIN + (int) (canvas.getWidth() * IMAGE_SIZE_PERCENT / 100);
            drawUserName(canvas, logoBottomY);
            drawHorizontalLine(canvas, 200);

            drawContactInfo(canvas, 40);
            Paint paintForJobTitle = new Paint();
            paintForJobTitle.setColor(Color.GRAY);
            paintForJobTitle.setTextSize(40);
            paintForJobTitle.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

            // Draw job title
            drawUserJobTitle(canvas, (canvas.getWidth() - paint.measureText(dbInfoPersonnelle.getInfo().get(0).getJob())) / 2, logoBottomY - 75);

            // Draw experience section
            int experienceY = drawExperience(canvas, 370);

            // Draw projects section
            int projectsY = drawProjects(canvas, experienceY + 20); // Adjust spacing as needed
            int skillsY = drawSkills(canvas, projectsY - 10);
            // Draw languages section
            int drawerLanguage = drawLanguages(canvas, educationY-40); // Adjust spacing as needed

            // Draw certification section
            int certificationsY = drawCertifications(canvas, drawerLanguage + 20); // Adjust spacing as needed

            // Draw loisirs section
            int loisirsY = drawLoisirs(canvas, skillsY); // Adjust spacing as needed
            drawerLs(canvas,certificationsY);
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




    private void drawSummary(Canvas canvas) {
        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextSize(30); // Adjust text size for the title
        titlePaint.setTypeface(Typeface.MONOSPACE); // Set text style

        String title = "Summary".toUpperCase();
        float titleWidth = titlePaint.measureText(title);

        // Calculate the x position to center the title
        float titleX = (canvas.getWidth() - titleWidth) / 2;
        float titleY = 250; // Adjust the Y position as needed

        // Draw the title
        canvas.drawText(title, titleX, titleY, titlePaint);

        // Draw the line below the title
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2);
        float lineY = titleY + 10; // Adjust the space below the title
        canvas.drawLine(245, lineY, canvas.getWidth() - 245, lineY, linePaint);

        // Retrieve the summary text
        String summaryText = dbInfoPersonnelle.getInfo().get(0).getAbout(); // Replace with actual summary text

        // Check if summary text is not empty
        if (summaryText != null && !summaryText.trim().isEmpty()) {
            // Draw the summary text below the line
            TextPaint textPaint = new TextPaint();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(15); // Adjust text size for the summary text
            textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Reset text style

            float summaryY = lineY + 15; // Adjust the space below the line

            // Set the maximum width for the summary text
            int maxWidth = canvas.getWidth() - 100; // Adjust based on the page margins

            // Create a StaticLayout to handle text wrapping
            StaticLayout staticLayout = new StaticLayout(
                    summaryText,
                    textPaint,
                    maxWidth,
                    Layout.Alignment.ALIGN_NORMAL,
                    1.0f,
                    0.0f,
                    false
            );

            // Save the current canvas state
            canvas.save();

            // Translate the canvas to the starting position for the summary text
            canvas.translate(50, summaryY); // Adjust the x position as needed

            // Draw the StaticLayout on the canvas
            staticLayout.draw(canvas);

            // Restore the canvas state
            canvas.restore();
        }
    }



    private void drawVerticalLine(Canvas canvas) {
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK); // Set line color
        linePaint.setStrokeWidth(3); // Set line width

        // Calculate the X position for the vertical line (center of the page)
        float centerX = (canvas.getWidth() / 2)-50 ;

        // Draw the vertical line
        canvas.drawLine(centerX, 375, centerX, canvas.getHeight(), linePaint);
    }




    private int drawEducation(Canvas canvas, int startY) {
        Paint paint = new Paint();

        // Common parameters
        int marginLeft = 50; // Margin from the left edge
        int headingMarginBottom = 10; // Space below the heading
        int elementMarginTop = 20; // Space between elements
        int lineHeight = 30; // Height of each line of text

        // Education information text (dummy data; replace with actual data)
        ArrayList<InfoEducation> educationInfo = dbInfoEducation.getAllInfoEducation();

        // Check if all education elements are empty
        boolean isAllEmpty = true;
        for (InfoEducation info : educationInfo) {
            if (!info.getShool().isEmpty() || !info.getMetier().isEmpty() || (!info.getStartYier().isEmpty() && !info.getEndYier().isEmpty())) {
                isAllEmpty = false;
                break;
            }
        }

        // If all elements are empty, return the original startY without drawing anything
        if (isAllEmpty) {
            return startY;
        }

        // Draw the "Education" heading
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        paint.setTypeface(Typeface.MONOSPACE); // Font style for heading
        String heading = "Education".toUpperCase();
        float headingY = startY + paint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Draw a line below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2);
        canvas.drawLine(marginLeft, headingY + headingMarginBottom, marginLeft + paint.measureText(heading), headingY + headingMarginBottom, linePaint);

        // Draw each line of education information
        Paint schoolPaint = new Paint();
        schoolPaint.setColor(Color.rgb(48,48,48)); // Dark gray color for school names
        schoolPaint.setTextSize(18); // Larger size for school names
        schoolPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold text for school names

        Paint detailsPaint = new Paint();
        detailsPaint.setColor(Color.rgb(48,48,48)); // Dark gray color for details
        detailsPaint.setTextSize(14); // Smaller size for dates and occupations
        detailsPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Normal text for details

        // Start drawing from 30 pixels below the heading
        float textY = headingY + headingMarginBottom + 30;

        for (int i = 0; i < educationInfo.size(); i++) {
            InfoEducation info = educationInfo.get(i);

            // Draw school name if it's not empty
            if (!info.getShool().isEmpty()) {
                canvas.drawText(info.getShool().toUpperCase(), marginLeft, textY, schoolPaint);
                textY += lineHeight; // Move to next line
            }

            // Draw field of study (metier) if it's not empty
            if (!info.getMetier().isEmpty()) {
                canvas.drawText(info.getMetier(), marginLeft, textY, detailsPaint);
                textY += lineHeight; // Move to next line
            }

            // Draw dates if they're not empty
            if (!info.getStartYier().isEmpty() && !info.getEndYier().isEmpty()) {
                canvas.drawText(info.getStartYier() + " - " + info.getEndYier(), marginLeft, textY, detailsPaint);
                textY += lineHeight; // Move to next line
            }

            textY += elementMarginTop; // Space between different education entries
        }

        // Return the bottom Y position of this section
        return (int) (textY + 40); // Add extra space for margin or padding
    }




    private int drawSkills(Canvas canvas, int startY) {
        Paint paint = new Paint();

        // Retrieve skills information
        ArrayList<String> skillsInfo = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getSoftSkillsArrayList();

        // Check if skillsInfo is empty; if so, return startY to skip drawing
        if (skillsInfo == null || skillsInfo.isEmpty()) {
            return startY;
        }

        // Common parameters
        int marginLeft = canvas.getWidth() / 2 - 25; // Adjust if necessary
        int headingMarginBottom = 10;
        int elementMarginTop = 20; // Increased space between lines
        int lineHeight = 30; // Increased line height for more space

        // Draw the "Skills" heading
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        paint.setTypeface(Typeface.MONOSPACE);
        String heading = "Skills".toUpperCase();
        float headingY = startY + paint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Draw a line directly below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2);
        canvas.drawLine(marginLeft, headingY + headingMarginBottom, marginLeft + paint.measureText(heading), headingY + headingMarginBottom, linePaint);

        // Draw each skill with a bullet point
        Paint skillPaint = new Paint();
        skillPaint.setColor(Color.rgb(48,48,48));
        skillPaint.setTextSize(18); // Size for skills
        skillPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Start drawing from 30 pixels below the heading
        float textY = headingY + headingMarginBottom + 30;

        for (int i = 0; i < skillsInfo.size(); i++) {
            // Add bullet point before each skill
            String skillText = "• " + skillsInfo.get(i);
            // Draw skill
            canvas.drawText(skillText, marginLeft, textY, skillPaint);

            textY += lineHeight; // Move to next line
        }

        // Return the bottom Y position of this section
        return (int) (textY + 40); // The last textY value will be the bottom position
    }



    private int drawLanguages(Canvas canvas, int startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        paint.setTypeface(Typeface.MONOSPACE);

        // Common parameters
        int marginLeft = 50;
        int headingMarginBottom = 10;
        int elementMarginTop = 20;
        int lineHeight = 30;

        // Languages information text
        ArrayList<String> languageInfo = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLangueArrayList();

        // Check if languageInfo is empty
        if (languageInfo.isEmpty()) {
            return startY; // Return the original startY if there is no content to draw
        }

        // Draw the "Languages" heading
        String heading = "Languages".toUpperCase();
        float headingY = startY + paint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Draw a line directly below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2);
        canvas.drawLine(marginLeft, headingY + headingMarginBottom, marginLeft + paint.measureText(heading), headingY + headingMarginBottom, linePaint);

        // Draw each line of language information
        Paint languagePaint = new Paint();
        languagePaint.setColor(Color.rgb(48, 48, 48));
        languagePaint.setTextSize(18);
        languagePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Start drawing from 30 pixels below the heading
        float textY = headingY + headingMarginBottom + 30;

        for (String language : languageInfo) {
            // Draw language name and proficiency level with bullet point
            String bulletPoint = "• " + language;
            canvas.drawText(bulletPoint, marginLeft, textY, languagePaint);

            textY += lineHeight - 10; // Move to next line
        }

        // Return the bottom Y position of this section
        return (int) (textY + elementMarginTop); // The last textY value will be the bottom position
    }



    private int drawCertifications(Canvas canvas, int startY) {
        // Fetch certifications information (dummy data here; replace with actual data)
        ArrayList<String> softSkills = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getSoftSkillsArrayList();

        // Check if the certificationInfo list is empty
        if (softSkills.isEmpty()) {
            // Return the startY unchanged if there's nothing to draw
            return startY;
        }

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        paint.setTypeface(Typeface.MONOSPACE);

        // Common parameters
        int marginLeft = 50;
        int headingMarginBottom = 10;
        int elementMarginTop = 20;
        int lineHeight = 30;
        int bulletMarginLeft = 4; // Margin for the bullet point

        // Draw the "Certifications" heading
        String heading = "Soft Skills".toUpperCase();
        float headingY = startY + paint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Draw a line directly below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2);
        canvas.drawLine(marginLeft, headingY + headingMarginBottom, marginLeft + paint.measureText(heading), headingY + headingMarginBottom, linePaint);

        // Draw each line of certification information
        Paint certificationPaint = new Paint();
        certificationPaint.setColor(Color.rgb(48, 48, 48));
        certificationPaint.setTextSize(18);
        certificationPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Start drawing from 30 pixels below the heading
        float textY = headingY + headingMarginBottom + 30;

        for (int i = 0; i < softSkills.size(); i++) {
            // Draw bullet point
            String bullet = "•";
            canvas.drawText(bullet, marginLeft, textY, certificationPaint);

            // Draw certification name
            float bulletWidth = certificationPaint.measureText(bullet);
            canvas.drawText(softSkills.get(i), marginLeft + bulletWidth + bulletMarginLeft, textY, certificationPaint);

            textY += lineHeight - 10; // Move to next line
        }

        // Return the bottom Y position of this section
        return (int) (textY + elementMarginTop); // The last textY value will be the bottom position
    }


    private int drawerLs(Canvas canvas, int startY) {
        // Fetch certifications information (dummy data here; replace with actual data)
        ArrayList<String> softSkills = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLoisirArrayList();

        // Check if the certificationInfo list is empty
        if (softSkills.isEmpty()) {
            // Return the startY unchanged if there's nothing to draw
            return startY;
        }

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        paint.setTypeface(Typeface.MONOSPACE);

        // Common parameters
        int marginLeft = 50;
        int headingMarginBottom = 10;
        int elementMarginTop = 20;
        int lineHeight = 30;
        int bulletMarginLeft = 4; // Margin for the bullet point

        // Draw the "Certifications" heading
        String heading = "Loisir".toUpperCase();
        float headingY = startY + paint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Draw a line directly below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2);
        canvas.drawLine(marginLeft, headingY + headingMarginBottom, marginLeft + paint.measureText(heading), headingY + headingMarginBottom, linePaint);

        // Draw each line of certification information
        Paint certificationPaint = new Paint();
        certificationPaint.setColor(Color.rgb(48, 48, 48));
        certificationPaint.setTextSize(18);
        certificationPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Start drawing from 30 pixels below the heading
        float textY = headingY + headingMarginBottom + 30;

        for (int i = 0; i < softSkills.size(); i++) {
            // Draw bullet point
            String bullet = "•";
            canvas.drawText(bullet, marginLeft, textY, certificationPaint);

            // Draw certification name
            float bulletWidth = certificationPaint.measureText(bullet);
            canvas.drawText(softSkills.get(i), marginLeft + bulletWidth + bulletMarginLeft, textY, certificationPaint);

            textY += lineHeight - 10; // Move to next line
        }

        // Return the bottom Y position of this section
        return (int) (textY + elementMarginTop); // The last textY value will be the bottom position
    }


    private int drawExperience(Canvas canvas, int startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        paint.setTypeface(Typeface.MONOSPACE);

        // Common parameters
        int marginLeft = canvas.getWidth() / 2 - 25; // Centering the text
        int headingMarginBottom = 10;
        int elementMarginTop = 20;
        int lineHeight = 30;
        int bulletMarginLeft = 20; // Margin for bullet points
        int maxLineWidth = 250; // Maximum width for a line before wrapping

        // Draw the "Experience" heading
        String heading = "Experience".toUpperCase();
        float headingY = startY + paint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Draw a line directly below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2);
        canvas.drawLine(marginLeft, headingY + headingMarginBottom, marginLeft + paint.measureText(heading), headingY + headingMarginBottom, linePaint);

        // Experience information text (dummy data here; replace with actual data)
        ArrayList<InfoExperience> experienceInfo = dbExperienceDeTravaille.getAllInfoExperience();

        // Check if there is any non-empty experience information
        boolean hasNonEmptyExperience = false;
        for (InfoExperience exp : experienceInfo) {
            if (!exp.getNomEntreprise().isEmpty() || !exp.getDateDébut().isEmpty() || !exp.getDateDeFin().isEmpty() ||
                    !exp.getTitreDePoste().isEmpty() || !exp.getRésumé().isEmpty()) {
                hasNonEmptyExperience = true;
                break;
            }
        }

        if (!hasNonEmptyExperience) {
            // If all experience info is empty, skip drawing this section
            return startY; // Return the original startY if no content is drawn
        }

        // Draw each line of experience information
        Paint experiencePaint = new Paint();
        experiencePaint.setColor(Color.rgb(48, 48, 48));
        experiencePaint.setTextSize(18); // For company names
        experiencePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint detailsPaint = new Paint();
        detailsPaint.setColor(Color.rgb(48, 48, 48));
        detailsPaint.setTextSize(14); // For job titles and dates
        detailsPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        Paint bulletPaint = new Paint();
        bulletPaint.setColor(Color.rgb(48, 48, 48)); // Color for bullet points
        bulletPaint.setTextSize(14); // Same size as for details

        // Start drawing from 30 pixels below the heading
        float textY = headingY + headingMarginBottom + 30;

        for (int i = 0; i < experienceInfo.size(); i++) {
            InfoExperience exp = experienceInfo.get(i);
            boolean hasContent = !exp.getNomEntreprise().isEmpty() || !exp.getDateDébut().isEmpty() ||
                    !exp.getDateDeFin().isEmpty() || !exp.getTitreDePoste().isEmpty() ||
                    !exp.getRésumé().isEmpty();
            if (hasContent) {
                // Draw company name
                canvas.drawText(exp.getNomEntreprise().toUpperCase(), marginLeft, textY, experiencePaint);
                // Draw dates
                textY += lineHeight - 10; // Move to next line
                canvas.drawText(exp.getDateDébut() + " - " + exp.getDateDeFin(), marginLeft, textY, detailsPaint);

                // Draw job title
                textY += lineHeight - 10; // Move to next line
                canvas.drawText(exp.getTitreDePoste(), marginLeft, textY, detailsPaint);

                // Draw each bullet point for the summary
                String[] lines = wrapText(exp.getRésumé(), maxLineWidth, bulletPaint);
                boolean firstLine = true;
                for (String line : lines) {
                    textY += lineHeight - 10; // Move to next line
                    if (firstLine) {
                        canvas.drawText("• " + line, marginLeft + bulletMarginLeft, textY, bulletPaint);
                        firstLine = false;
                    } else {
                        canvas.drawText(line, marginLeft+25, textY, detailsPaint);
                    }
                }

                textY += elementMarginTop; // Space between entries
            }
        }

        // Return the bottom Y position of this section
        return (int) (textY + elementMarginTop); // The last textY value plus extra space
    }





    private int drawProjects(Canvas canvas, int startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        paint.setTypeface(Typeface.MONOSPACE);

        // Common parameters
        int marginLeft = canvas.getWidth() / 2 - 25; // Margin from the center
        int headingMarginBottom = 10;
        int elementMarginTop = 20; // Increased space between lines
        int lineHeight = 30; // Increased line height for more space
        int bulletMarginLeft = 20; // Margin for bullet points
        int maxLineWidth = 250; // Maximum width for a line before wrapping

        // Draw the "Projects" heading
        String heading = "Projects".toUpperCase();
        float headingY = startY + paint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Draw a line directly below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2);
        canvas.drawLine(marginLeft, headingY + headingMarginBottom, marginLeft + paint.measureText(heading), headingY + headingMarginBottom, linePaint);

        // Projects information text (dummy data here; replace with actual data)
        ArrayList<InfoProjet> projectsInfo = dbProjet.getAllInfoProjets();

        // Check if there is any non-empty project information
        boolean hasNonEmptyProjects = false;
        for (InfoProjet project : projectsInfo) {
            if (!project.getNomEntreprise().isEmpty() || !project.getDateDebut().isEmpty() || !project.getDateFin().isEmpty() ||
                    !project.getTitreProjet().isEmpty() || !project.getResume().isEmpty()) {
                hasNonEmptyProjects = true;
                break;
            }
        }

        if (!hasNonEmptyProjects) {
            // If all project info is empty, skip drawing this section
            return startY; // Return the original startY if no content is drawn
        }

        // Draw each line of project information
        Paint projectPaint = new Paint();
        projectPaint.setColor(Color.rgb(48, 48, 48));
        projectPaint.setTextSize(18); // For project names
        projectPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint detailsPaint = new Paint();
        detailsPaint.setColor(Color.rgb(48, 48, 48));
        detailsPaint.setTextSize(14); // For descriptions
        detailsPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        Paint bulletPaint = new Paint();
        bulletPaint.setColor(Color.rgb(48, 48, 48)); // Color for bullet points
        bulletPaint.setTextSize(14); // Same size as for details

        // Start drawing from 30 pixels below the heading
        float textY = headingY + headingMarginBottom + 30;

        for (InfoProjet project : projectsInfo) {
            boolean hasContent = !project.getNomEntreprise().isEmpty() || !project.getDateDebut().isEmpty() ||
                    !project.getDateFin().isEmpty() || !project.getTitreProjet().isEmpty() ||
                    !project.getResume().isEmpty();
            if (hasContent) {
                // Draw project name
                canvas.drawText(project.getNomEntreprise().toUpperCase(), marginLeft, textY, projectPaint);

                // Draw project dates
                textY += lineHeight - 10; // Move to next line
                canvas.drawText(project.getDateDebut() + " - " + project.getDateFin(), marginLeft, textY, detailsPaint);

                // Draw project title
                textY += lineHeight - 10; // Move to next line
                canvas.drawText(project.getTitreProjet(), marginLeft, textY, detailsPaint);

                // Draw project summary with bullet points only on the first line
                String[] lines = wrapText(project.getResume(), maxLineWidth, bulletPaint);
                boolean firstLine = true;
                for (String line : lines) {
                    textY += lineHeight - 10; // Move to next line
                    if (firstLine) {
                        canvas.drawText("• " + line, marginLeft + bulletMarginLeft, textY, bulletPaint);
                        firstLine = false;
                    } else {
                        canvas.drawText(line, marginLeft+30, textY, detailsPaint);
                    }
                }

                textY += elementMarginTop; // Space between different project entries
            }
        }

        // Return the bottom Y position of this section
        return (int) (textY + elementMarginTop); // The last textY value plus extra space
    }




    private String[] wrapText(String text, int maxLineWidth, Paint paint) {
        ArrayList<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            String testLine = line.toString() + (line.length() > 0 ? " " : "") + word;
            if (paint.measureText(testLine) > maxLineWidth) {
                if (line.length() > 0) {
                    lines.add(line.toString());
                    line = new StringBuilder(word);
                }
            } else {
                line.append((line.length() > 0 ? " " : "") + word);
            }
        }
        if (line.length() > 0) {
            lines.add(line.toString());
        }

        return lines.toArray(new String[0]);
    }




    private int drawLoisirs(Canvas canvas, int startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        paint.setTypeface(Typeface.MONOSPACE);

        // Common parameters
        int marginLeft = canvas.getWidth() / 2 - 25; // Margin from the center
        int headingMarginBottom = 10;
        int elementMarginTop = 20; // Increased space between lines
        int lineHeight = 30; // Increased line height for more space
        int bulletOffset = 30; // Offset for bullet point

        // Loisirs information text (dummy data here; replace with actual data)
        ArrayList<String> loisirsInfo = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getCertificateArrayList();

        // Check if loisirsInfo is empty
        if (loisirsInfo == null || loisirsInfo.isEmpty()) {
            // If empty, skip drawing the section and return the current Y position
            return startY; // No extra space is added
        }

        // Draw the "Loisirs" heading
        String heading = "Certificate".toUpperCase(); // This should be "Loisirs"
        float headingY = startY + paint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Draw a line directly below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2);
        canvas.drawLine(marginLeft, headingY + headingMarginBottom, marginLeft + paint.measureText(heading), headingY + headingMarginBottom, linePaint);

        // Draw each line of loisirs information
        Paint loisirsPaint = new Paint();
        loisirsPaint.setColor(Color.rgb(48, 48, 48));
        loisirsPaint.setTextSize(18); // Larger size for loisirs names
        loisirsPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint detailsPaint = new Paint();
        detailsPaint.setColor(Color.rgb(48, 48, 48));
        detailsPaint.setTextSize(14); // Smaller size for loisirs details
        detailsPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Start drawing from 30 pixels below the heading
        float textY = headingY + headingMarginBottom + 30;

        for (int i = 0; i < loisirsInfo.size(); i++) {
            // Draw bullet point
            String bulletPoint = "• "; // Unicode bullet point character
            canvas.drawText(bulletPoint, marginLeft, textY, loisirsPaint);

            // Draw loisirs name
            canvas.drawText(loisirsInfo.get(i), marginLeft + bulletOffset, textY, detailsPaint);

            textY += lineHeight - 10; // Move to next line
        }

        // Return the bottom Y position of this section
        return (int) (textY + elementMarginTop); // The last textY value will be the bottom position
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
        paint.setTextSize(30); // Adjust text size as needed
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Set text style

        // Fetch the user's name
        String userName = dbInfoPersonnelle.getInfo().get(0).getL_name().toUpperCase() + " " + dbInfoPersonnelle.getInfo().get(0).getF_name().toUpperCase();
        float textWidth = paint.measureText(userName);

        // Define a fixed margin from the left
        float marginLeft = 20; // Adjust this value as needed

        // Define the Y position for the text
        float textY = 100; // Adjust this value as needed

        // Draw the text at the calculated position
        canvas.drawText(userName, marginLeft, textY, paint);

        // Define paint for the line
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK); // Set color for the line
        linePaint.setStrokeWidth(2); // Adjust line thickness as needed

        // Draw the line below the text
        float lineStartX = marginLeft;
        float lineEndX = marginLeft + textWidth;
        float lineY = textY + 10; // Position the line below the text; adjust as needed
        canvas.drawLine(lineStartX, lineY, lineEndX, lineY, linePaint);
    }


    private void drawUserJobTitle(Canvas canvas, float nameTextX, float nameTextY) {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY); // Set a different color for job title
        paint.setTextSize(20); // Adjust text size for job title
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Set text style

        // Fetch the user's job title
        String userJobTitle = capitalizeFirstLetterOfEachWord(dbInfoPersonnelle.getInfo().get(0).getJob()); // Adjust method to get job title
        float jobTitleWidth = paint.measureText(userJobTitle);

        // Define a fixed margin from the left
        float marginLeft = 20; // Adjust this value as needed

        // Define a fixed vertical space below the user's name
        float spaceBelowName = 20; // Adjust this value as needed

        // Calculate the vertical position for the job title
        float jobTitleTextY = nameTextY + spaceBelowName + paint.getTextSize();

        // Draw the job title at the calculated position
        canvas.drawText(userJobTitle, marginLeft, jobTitleTextY, paint);
    }


    private void drawContactInfo(Canvas canvas, float startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(15); // Adjust text size as needed
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Set text style

        // Define a fixed margin from the right for contact information
        float marginRight = 50; // Adjust this value as needed

        // Calculate the starting position for the contact information
        float contactInfoX = canvas.getWidth() - marginRight;

        // Fetch contact information
        String phoneNumber = dbInfoPersonnelle.getInfo().get(0).getN_phone();
        String email = dbInfoPersonnelle.getInfo().get(0).getEmail();
        String linkedinUrl = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLinkdin(); // Adjust method to get LinkedIn URL
        String placeholder = dbInfoPersonnelle.getInfo().get(0).getPays() + "," + dbInfoPersonnelle.getInfo().get(0).getVille();

        // Load icons
        Bitmap phoneIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_phone);
        Bitmap emailIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_email);
        Bitmap linkedinIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_linkdin);
        Bitmap placeholderIcon = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder); // New placeholder image

        // Define icon size and margin
        int iconSize = 20; // Adjust as needed
        float iconTextMargin = 10;

        // Initialize vertical position
        float currentY = startY + 20;

        // Draw phone number with icon if it's not empty
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            canvas.drawText(phoneNumber, contactInfoX - paint.measureText(phoneNumber) - iconSize - iconTextMargin, currentY + iconSize / 2, paint);
            canvas.drawBitmap(phoneIcon, contactInfoX - iconSize, currentY - iconSize / 2, paint);
            currentY += paint.getTextSize() + 20; // Adjust vertical spacing as needed
        }

        // Draw email with icon if it's not empty
        if (email != null && !email.isEmpty()) {
            canvas.drawText(email, contactInfoX - paint.measureText(email) - iconSize - iconTextMargin, currentY + iconSize / 2, paint);
            canvas.drawBitmap(emailIcon, contactInfoX - iconSize, currentY - iconSize / 2, paint);
            currentY += paint.getTextSize() + 20; // Adjust vertical spacing as needed
        }

        // Draw LinkedIn URL with icon if it's not empty
        if (linkedinUrl != null && !linkedinUrl.isEmpty()) {
            canvas.drawText(linkedinUrl, contactInfoX - paint.measureText(linkedinUrl) - iconSize - iconTextMargin, currentY + iconSize / 2, paint);
            canvas.drawBitmap(linkedinIcon, contactInfoX - iconSize, currentY - iconSize / 2, paint);
            currentY += paint.getTextSize() + 20; // Adjust vertical spacing as needed
        }

        // Draw placeholder icon and text if placeholder is not empty
        if (placeholder != null && !placeholder.isEmpty()) {
            canvas.drawBitmap(placeholderIcon, contactInfoX - iconSize, currentY - iconSize / 2, paint);
            canvas.drawText(placeholder, contactInfoX - paint.measureText(placeholder) - iconSize - iconTextMargin, currentY + iconSize / 2, paint);
        }
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

    private void drawHorizontalLine(Canvas canvas, float y) {
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK); // Set line color
        linePaint.setStrokeWidth(5); // Set line width

        float startX = 0;
        float stopX = canvas.getWidth();

        canvas.drawLine(startX, y, stopX, y, linePaint);
    }

}