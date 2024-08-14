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

public class M15 extends AppCompatActivity {

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
        setContentView(R.layout.activity_m15);

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
                startActivity(new Intent(M15.this, InfoPersonnelleActivity.class));
            }
        });

        éducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M15.this, InfoEducationActivity.class));
            }
        });
        exDeTravaille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M15.this, ExperinceActivity.class));
            }
        });
        projet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M15.this, InfoProjetActivity.class));
            }
        });

        infoAdditionnelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M15.this, InformationAdditionnelleActivity.class));
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
                if (ContextCompat.checkSelfPermission(M15.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(M15.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {
                    // Permission is granted, proceed with PDF creation
                    PdfDocument document = createPDF();
                    if (document != null) {
                        ByteArrayOutputStream outputStream = savePDFToByteArray(document);

                        // Start ShowDocumentActivity to display the PDF
                        Intent intent = new Intent(M15.this, ShowDocumentActivity.class);
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

            drawBackground(canvas,canvas.getWidth(),canvas.getHeight());
            // Draw header image and content
            drawHeaderImage(canvas);

            //drawLineAtY(canvas, 300);

            // Draw user name and job title
            int logoBottomY = IMAGE_MARGIN + (int) (canvas.getWidth() * IMAGE_SIZE_PERCENT / 100);
            drawUserName(canvas, logoBottomY);

            Paint paintForJobTitle = new Paint();
            paintForJobTitle.setColor(Color.GRAY);
            paintForJobTitle.setTextSize(40);
            paintForJobTitle.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

            // Draw job title
            float jobTitleTextY = logoBottomY - 70;
            drawUserJobTitle(canvas, (canvas.getWidth() - paintForJobTitle.measureText(dbInfoPersonnelle.getInfo().get(0).getJob())) / 2, jobTitleTextY);

            // Draw contact info
            float contactInfoY = jobTitleTextY + 70; // Adjust Y position as needed
            drawContactInfo(canvas, contactInfoY);

            // Draw vertical line
            //drawVerticalLine(canvas, contactInfoY + 100, 900); // Adjust lineHeight as needed

            // Draw education section
            int educationBottomY = drawEducation(canvas, 340);

            // Draw skills section below education
            int skillsBottomY = drawSkills(canvas, educationBottomY + 10); // Adding some padding between sections

            // Draw languages section below skills
            int languagesBottomY = drawLanguages(canvas, skillsBottomY + 10); // Adding some padding between sections

            // Draw loisirs section below languages
            int loisirsBottomY = drawLoisirs(canvas, languagesBottomY + 10); // Adding some padding between sections

            int softskillsBottomY = softskillsBottomY(canvas,loisirsBottomY+10);

            // Draw competence section
            int competenceBottomY = drawCompetence(canvas, 310);

            // Draw experience section below competence
            int experienceBottomY = drawExperienceDeTravail(canvas, competenceBottomY + 30); // Adding some padding between sections
            int projects = drawProjects(canvas,experienceBottomY+30);

            // Draw certification section below experience
            drawCertification(canvas, softskillsBottomY + 30); // Adding some padding between sections

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

    private void drawBackground(Canvas canvas, int pageWidth, int pageHeight) {
        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.rgb(245, 245, 245)); // White Smoke color

        // Draw a rectangle covering the entire page
        canvas.drawRect(0, 0, pageWidth, pageHeight, backgroundPaint);
    }
    private int drawProjects(Canvas canvas, int startY) {
        // Create and configure the Paint object for the projects heading (big and black)
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Adjust size as needed
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for project details (normal)
        Paint normalPaint = new Paint();
        normalPaint.setColor(Color.BLACK);
        normalPaint.setTextSize(15); // Adjust size as needed
        normalPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Define margins and spacing
        int marginLeft = 280;
        int bulletMargin = 20; // Additional margin for bullet points
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;
        int maxTextWidth = 250; // Maximum width for wrapping text
        int lineWidth = 2; // Thickness of the line

        // Retrieve project information from database
        ArrayList<InfoProjet> projects = dbProjet.getAllInfoProjets();

        // Filter out empty or invalid projects
        ArrayList<InfoProjet> validProjects = new ArrayList<>();
        for (InfoProjet project : projects) {
            if (project.getNomEntreprise() != null && !project.getNomEntreprise().isEmpty() &&
                    project.getDateDebut() != null && !project.getDateDebut().isEmpty() &&
                    project.getDateFin() != null && !project.getDateFin().isEmpty() &&
                    project.getTitreProjet() != null && !project.getTitreProjet().isEmpty() &&
                    project.getResume() != null && !project.getResume().isEmpty()) {
                validProjects.add(project);
            }
        }

        // If no valid projects, skip this section
        if (validProjects.isEmpty()) {
            return startY; // Return the starting Y position unchanged
        }

        // Draw the "Projects" heading
        String heading = "Projects".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Draw a line below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK); // Set the color of the line
        linePaint.setStrokeWidth(lineWidth); // Set the thickness of the line

        // Calculate the width of the line based on the heading text width
        float headingTextWidth = headingPaint.measureText(heading);
        float lineY = headingY + headingMarginBottom;
        canvas.drawLine(marginLeft, lineY, marginLeft + headingTextWidth, lineY, linePaint);

        // Calculate the Y offset for project information
        float yOffset = lineY + lineWidth + elementMarginTop;

        // Draw each piece of project information
        for (InfoProjet project : validProjects) {
            // Draw the project name and dates on the first line
            String projectHeader = project.getNomEntreprise() + " - " + project.getDateDebut() + " to " + project.getDateFin();
            canvas.drawText(projectHeader, marginLeft, yOffset, normalPaint);
            yOffset += lineHeight;

            normalPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            // Draw the project title on the next line with a gray background
            String projectTitle = project.getTitreProjet();
            Paint titlePaint = new Paint(normalPaint);
            titlePaint.setColor(Color.rgb(245, 245, 245)); // Gray background
            Paint.FontMetrics fontMetrics = titlePaint.getFontMetrics();
            canvas.drawRect(marginLeft - 10, yOffset - fontMetrics.bottom, canvas.getWidth() - 10, yOffset + fontMetrics.top, titlePaint);
            titlePaint.setColor(Color.BLACK); // Text color
            canvas.drawText(projectTitle, marginLeft, yOffset, titlePaint);
            yOffset += lineHeight;

            // Draw the project description on the next line with a bullet point, wrapping if necessary
            String projectDescription = "• " + project.getResume();
            yOffset = drawWrappedText(canvas, projectDescription, marginLeft + bulletMargin, yOffset, normalPaint, maxTextWidth);
            yOffset += elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) yOffset;
    }





    private int drawCertification(Canvas canvas, float startY) {
        // Create and configure the Paint object for the certifications heading (big and black)
        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextSize(20); // Title text size
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for certification items (normal)
        Paint paint = new Paint();
        paint.setColor(Color.rgb(48,48,48));
        paint.setTextSize(16); // Adjust text size as needed
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Set text style

        // Define margins and spacing
        float marginLeft = 40;
        float lineHeight = 25; // Adjust line height if needed
        float elementMarginTop = 20;
        float headingMarginBottom = 10; // Space between heading and line

        // Fetch the certifications data
        List<String> certifications = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getCertificateArrayList();

        // Check if certifications are empty; if so, skip drawing this section
        if (certifications == null || certifications.isEmpty()) {
            return (int) startY; // Return the startY value if there are no certifications
        }

        // Draw the "Certifications" heading
        String sectionTitle = "Certifications";
        float currentY = startY;
        canvas.drawText(sectionTitle, marginLeft, currentY, titlePaint);

        // Draw a line below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2); // Thickness of the line

        // Measure the width of the heading text to draw the line
        float headingWidth = titlePaint.measureText(sectionTitle);
        float lineY = currentY + titlePaint.getTextSize() + headingMarginBottom-15; // Position the line below the heading

        // Draw the line
        canvas.drawLine(marginLeft, lineY, marginLeft + headingWidth, lineY, linePaint);

        // Move to the position for the certifications
        currentY = lineY + elementMarginTop;

        // Draw each certification
        for (String certification : certifications) {
            // Break text into multiple lines if it exceeds a certain width
            List<String> lines = new ArrayList<>();
            int start = 0;
            while (start < certification.length()) {
                int end = paint.breakText(certification, start, certification.length(), true, 250, null);
                lines.add(certification.substring(start, start + end));
                start += end;
            }

            // Draw each line
            for (String line : lines) {
                canvas.drawText(" • " + line, marginLeft, currentY, paint);
                currentY += lineHeight;
            }

            // Add margin for the next block of text
            currentY += elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }





    private void drawContactInfo(Canvas canvas, float startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(15); // Adjust text size as needed
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Set text style

        // Define margins and spacing
        float bulletMargin = 10; // Margin between bullet point and text
        float lineHeight = 30; // Line height for spacing between lines
        float xOffset = 40; // Starting X position for contact information

        // Fetch contact information
        String phone = dbInfoPersonnelle.getInfo().get(0).getN_phone(); // Phone number
        String email = dbInfoPersonnelle.getInfo().get(0).getEmail(); // Email address
        String address = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLinkdin(); // Physical address
        String linkedinUrl = dbInfoPersonnelle.getInfo().get(0).getPays() + "," + dbInfoPersonnelle.getInfo().get(0).getVille(); // LinkedIn URL

        // Define the bullet point
        String bulletPoint = "•";

        // Calculate the starting Y position for each contact element
        float currentY = startY;

        // Draw phone number with bullet point
        canvas.drawText(bulletPoint, xOffset, currentY, paint);
        canvas.drawText(phone, xOffset + bulletMargin, currentY, paint);
        currentY += lineHeight;

        // Draw email address with bullet point
        canvas.drawText(bulletPoint, xOffset, currentY, paint);
        canvas.drawText(email, xOffset + bulletMargin, currentY, paint);
        currentY += lineHeight;

        // Draw physical address with bullet point
        canvas.drawText(bulletPoint, xOffset, currentY, paint);
        canvas.drawText(address, xOffset + bulletMargin, currentY, paint);
        currentY += lineHeight;

        // Draw LinkedIn URL with bullet point
        canvas.drawText(bulletPoint, xOffset, currentY, paint);
        canvas.drawText(linkedinUrl, xOffset + bulletMargin, currentY, paint);
    }





    private int drawEducation(Canvas canvas, float startY) {
        // Create and configure the Paint object for headings
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for school names (big and black)
        Paint schoolNamePaint = new Paint();
        schoolNamePaint.setColor(Color.rgb(48,48,48));
        schoolNamePaint.setTextSize(18); // Adjust size as needed
        schoolNamePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for date ranges (small and gray)
        Paint dateRangePaint = new Paint();
        dateRangePaint.setColor(Color.rgb(48,48,48)); // Color for date ranges
        dateRangePaint.setTextSize(14); // Adjust size as needed
        dateRangePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Create and configure the Paint object for professions (normal)
        Paint professionPaint = new Paint();
        professionPaint.setColor(Color.rgb(40,40,40));
        professionPaint.setTextSize(15); // Adjust size as needed
        professionPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = 40;
        int headingMarginBottom = 10;
        int elementMarginTop = 10;
        int lineHeight = 25;
        int lineWidth = 2; // Thickness of the line

        // Draw the "Éducation" heading
        String heading = "Éducation".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Calculate the width of the heading for drawing the line
        float headingWidth = headingPaint.measureText(heading);

        // Draw a line below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(lineWidth);
        float lineY = (headingY + headingMarginBottom / 2); // Position line below heading
        canvas.drawLine(marginLeft, lineY, marginLeft + headingWidth, lineY, linePaint);

        // Get the education information
        ArrayList<InfoEducation> educationInfo = dbInfoEducation.getAllInfoEducation();

        // Draw each piece of education information
        float currentY = lineY + lineHeight + elementMarginTop; // Start below the line

        for (InfoEducation education : educationInfo) {
            boolean isNotEmpty =
                    !education.getShool().trim().isEmpty() &&
                            !education.getMetier().trim().isEmpty() &&
                            !education.getStartYier().trim().isEmpty() &&
                            !education.getEndYier().trim().isEmpty();

            if (isNotEmpty) {
                // Draw the school name on the first line
                canvas.drawText(education.getShool(), marginLeft, currentY, schoolNamePaint);

                // Move to the next line for the job title
                currentY += lineHeight;
                canvas.drawText(education.getMetier(), marginLeft, currentY, professionPaint);

                // Move to the next line for the date range
                currentY += lineHeight;
                String dateRange = education.getStartYier() + " to " + education.getEndYier();
                canvas.drawText(dateRange, marginLeft, currentY, dateRangePaint);

                // Move to the next section with padding
                currentY += lineHeight + elementMarginTop;
            }
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

        // Create and configure the Paint object for the line below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK); // Color for the line
        linePaint.setStrokeWidth(2); // Thickness of the line

        // Define margins and spacing
        int marginLeft = 40;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;

        // Get the skills information
        ArrayList<String> skillsInfo = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getCompetanceArrayList(); // Fetch skills info

        // Check if there are skills to display
        if (skillsInfo == null || skillsInfo.isEmpty()) {
            // No skills, return the current Y position without drawing anything
            return startY;
        }

        // Draw the "Skills" heading
        String heading = "Skills".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Calculate the width of the heading text for the line length
        float headingWidth = headingPaint.measureText(heading);

        // Draw the line below the heading
        float lineY = headingY + headingPaint.getTextSize() / 2; // Line position just below the heading
        canvas.drawLine(marginLeft, lineY, marginLeft + headingWidth, lineY, linePaint);

        // Draw each skill
        float currentY = headingY + headingMarginBottom + elementMarginTop;
        for (String skill : skillsInfo) {
            canvas.drawText("• " + skill, marginLeft, currentY, skillPaint);
            currentY += lineHeight - 5; // Adjust vertical spacing
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }



    private int drawLoisirs(Canvas canvas, int startY) {
        // Create and configure the Paint object for the loisirs heading (big and black)
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Size for heading
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for loisirs (normal)
        Paint loisirsPaint = new Paint();
        loisirsPaint.setColor(Color.rgb(40, 40, 40));
        loisirsPaint.setTextSize(15); // Size for loisirs text
        loisirsPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = 40;
        int bulletMarginLeft = 40; // Additional margin for bullet points
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25; // Set a reasonable line height for spacing

        // Get the loisirs information
        ArrayList<String> loisirs = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLoisirArrayList(); // Replace with your method to fetch loisirs

        // Check if the loisirs list is empty
        if (loisirs == null || loisirs.isEmpty()) {
            // If empty, return the current Y position without drawing the section
            return startY;
        }

        // Draw the "Loisirs" heading
        String heading = "Loisirs".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Draw a line below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2); // Thickness of the line

        // Measure the width of the heading text to draw the line
        float headingWidth = headingPaint.measureText(heading);
        float lineY = headingY + headingPaint.getTextSize() / 2; // Position the line just below the heading

        // Draw the line
        canvas.drawLine(marginLeft, lineY, marginLeft + headingWidth, lineY, linePaint);

        // Draw each piece of loisirs information
        float currentY = headingY + headingMarginBottom + elementMarginTop;
        for (String loisir : loisirs) {
            // Draw the loisirs on the line with additional margin for bullet point
            canvas.drawText("• " + loisir, bulletMarginLeft, currentY, loisirsPaint);

            // Move to the next line with padding
            currentY += lineHeight + elementMarginTop - 5;
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }




    private int softskillsBottomY(Canvas canvas, int startY) {
        // Create and configure the Paint object for the soft skills heading (big and black)
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Size for heading
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for skills (normal)
        Paint skillsPaint = new Paint();
        skillsPaint.setColor(Color.rgb(40, 40, 40));
        skillsPaint.setTextSize(15); // Size for skills text
        skillsPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = 40;
        int bulletMarginLeft = 40; // Additional margin for bullet points
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 10; // Adjust vertical spacing as needed

        // Get the soft skills information
        ArrayList<String> skills = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getSoftSkillsArrayList(); // Fetch skills info

        // Check if the skills list is empty
        if (skills == null || skills.isEmpty()) {
            // If empty, return the current Y position without drawing the section
            return startY;
        }

        // Draw the "Soft Skills" heading
        String heading = "Soft Skills".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Draw a line below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2); // Thickness of the line

        // Measure the width of the heading text to draw the line
        float headingWidth = headingPaint.measureText(heading);
        float lineY = headingY + headingPaint.getTextSize() / 2; // Position the line just below the heading

        // Draw the line
        canvas.drawLine(marginLeft, lineY, marginLeft + headingWidth, lineY, linePaint);

        // Draw each skill
        float currentY = headingY + headingMarginBottom + elementMarginTop;
        for (String skill : skills) {
            // Draw the skill with a bullet point
            canvas.drawText("• " + skill, bulletMarginLeft, currentY, skillsPaint);
            // Move to the next line with padding
            currentY += lineHeight + elementMarginTop - 5;
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }






    private int drawExperienceDeTravail(Canvas canvas, int startY) {
        // Paint settings for heading
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Paint settings for normal text
        Paint normalPaint = new Paint();
        normalPaint.setColor(Color.BLACK); // Set text color to gray
        normalPaint.setTextSize(15);
        normalPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Paint settings for bullet points
        Paint bulletPaint = new Paint();
        bulletPaint.setColor(Color.BLACK); // Set text color to gray
        bulletPaint.setTextSize(15);
        bulletPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Margins and sizes
        int marginLeft = 280;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;
        int bulletMargin = 20;
        int maxTextWidth = 300; // Maximum width for text before wrapping

        // Retrieve experience information from database
        ArrayList<InfoExperience> experienceInfo = dbExperienceDeTravaille.getAllInfoExperience();

        // Filter out empty or invalid experiences
        ArrayList<InfoExperience> validExperienceInfo = new ArrayList<>();
        for (InfoExperience info : experienceInfo) {
            if (info.getNomEntreprise() != null && !info.getNomEntreprise().isEmpty() &&
                    info.getDateDébut() != null && !info.getDateDébut().isEmpty() &&
                    info.getDateDeFin() != null && !info.getDateDeFin().isEmpty() &&
                    info.getTitreDePoste() != null && !info.getTitreDePoste().isEmpty() &&
                    info.getRésumé() != null && !info.getRésumé().isEmpty()) {
                validExperienceInfo.add(info);
            }
        }

        // If no valid experiences, skip this section
        if (validExperienceInfo.isEmpty()) {
            return startY;
        }

        // Draw the "Expérience de Travail" heading
        String heading = "Expérience de Travail".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Draw a line below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK); // Same color as heading
        linePaint.setStrokeWidth(2); // Line width same as heading text size
        float headingWidth = headingPaint.measureText(heading);
        float lineY = headingY + headingPaint.getTextSize() / 2; // Position the line below the heading
        canvas.drawLine(marginLeft, lineY, marginLeft + headingWidth, lineY, linePaint);

        // Calculate the Y offset for experience information
        float yOffset = headingY + headingMarginBottom + elementMarginTop;

        // Draw each piece of experience information
        for (InfoExperience info : validExperienceInfo) {
            // Draw the company name and dates on the first line
            String companyAndDates = info.getNomEntreprise() + " - " + info.getDateDébut() + " to " + info.getDateDeFin();
            canvas.drawText(companyAndDates, marginLeft, yOffset, bulletPaint);
            yOffset += lineHeight;

            // Draw the job title on the next line
            canvas.drawText(info.getTitreDePoste(), marginLeft, yOffset, normalPaint);
            yOffset += lineHeight;

            // Draw the resume on the next line with a bullet point, wrapping if necessary
            String resume = "• " + info.getRésumé();
            yOffset = drawWrappedText(canvas, resume, marginLeft + bulletMargin, yOffset, normalPaint, maxTextWidth);
            yOffset += elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) yOffset;
    }



    private float drawWrappedText(Canvas canvas, String text, float x, float startY, Paint paint, int maxWidth) {
        float y = startY;
        float textWidth = paint.measureText(text);

        // If text is too wide, wrap it
        if (textWidth > maxWidth) {
            // Split text into words
            String[] words = text.split(" ");
            StringBuilder line = new StringBuilder();

            for (String word : words) {
                String testLine = line.toString() + word + " ";
                float testWidth = paint.measureText(testLine);

                // If line width exceeds maxWidth, draw the line and start a new one
                if (testWidth > maxWidth) {
                    canvas.drawText(line.toString(), x, y, paint);
                    y += paint.getTextSize();
                    line = new StringBuilder(word + " ");
                } else {
                    line.append(word).append(" ");
                }
            }
            // Draw the last line
            canvas.drawText(line.toString(), x, y, paint);
            y += paint.getTextSize();
        } else {
            // If text does not exceed maxWidth, draw it on one line
            canvas.drawText(text, x, y, paint);
            y += paint.getTextSize();
        }

        return y;
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
        // Create and configure the Paint object for the languages heading (big and black)
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Heading text size
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for language items (normal)
        Paint languagePaint = new Paint();
        languagePaint.setColor(Color.rgb(40, 40, 40));
        languagePaint.setTextSize(15); // Language text size
        languagePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = 40;
        int bulletMarginLeft = 40; // Additional margin for bullet points
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25; // Set line height to ensure proper spacing

        // Draw the "Languages" heading
        String heading = "Languages".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Draw a line below the heading
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2); // Line thickness

        // Measure the width of the heading text
        float headingWidth = headingPaint.measureText(heading);
        float lineY = headingY + headingMarginBottom; // Position the line below the heading

        // Draw the line
        canvas.drawLine(marginLeft, lineY, marginLeft + headingWidth, lineY, linePaint);

        // Get the languages information
        ArrayList<String> languages = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLangueArrayList(); // Replace with your method to fetch languages

        // Check if the languages list is empty
        if (languages == null || languages.isEmpty()) {
            return (int) (lineY + elementMarginTop); // Return the position if there are no languages
        }

        // Move to the position for the languages
        float currentY = lineY + elementMarginTop;

        // Draw each piece of language information
        for (String language : languages) {
            // Draw the language with a bullet point
            canvas.drawText("• " + language, bulletMarginLeft, currentY, languagePaint);

            // Move to the next line with padding
            currentY += lineHeight;
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }




    private int drawCompetence(Canvas canvas, int startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        int marginLeft = 280;
        int headingMarginBottom = 10;
        int elementMarginTop = 20;
        int lineHeight = 25;
        int lineWidth = 2; // Thickness of the line

        // Retrieve competence information
        ArrayList<InfoPersonnelle> aboutUser = dbInfoPersonnelle.getInfo();

        // Check if aboutUser is empty
        if (aboutUser.isEmpty()) {
            // If empty, return the current Y position (startY) without drawing anything
            return startY;
        }

        // Draw the "Compétence" heading
        String heading = "".toUpperCase(); // Set a meaningful heading
        float headingY = startY + paint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Prepare for drawing competence information
        float currentY = headingY + headingMarginBottom + elementMarginTop;
        paint.setTextSize(15);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        for (int i = 0; i < aboutUser.size(); i++) {
            String text = aboutUser.get(i).getAbout() + aboutUser.get(i).getAbout() + aboutUser.get(i).getAbout();
            List<String> lines = new ArrayList<>();

            // Break text into multiple lines if it exceeds 300 pixels
            int start = 0;
            while (start < text.length()) {
                int end = paint.breakText(text, start, text.length(), true, 300, null);
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

        // Draw a line below the competence section
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK); // Set the color of the line
        linePaint.setStrokeWidth(lineWidth); // Set the thickness of the line

        // Calculate the Y position for the line
        float lineY = currentY + elementMarginTop - 50;

        // Draw the line
        canvas.drawLine(marginLeft, lineY, canvas.getWidth() - 40, lineY, linePaint);

        // Return the bottom Y position of this section
        return (int) (lineY + lineWidth + elementMarginTop); // Move below the line
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
            int imageX = 430; // Padding from the left edge
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
        String userName = dbInfoPersonnelle.getInfo().get(0).getL_name().toUpperCase() + " " + capitalizeFirstLetterOfEachWord(dbInfoPersonnelle.getInfo().get(0).getF_name())  ;
        float textWidth = paint.measureText(capitalizeFirstLetterOfEachWord(userName));

        // Define a fixed vertical space from the bottom of the logo

        // Calculate the vertical position for the text (fixed space from the bottom of the logo)

        // Calculate the position for the text (center horizontally)
        float textX = 40;

        // Draw the text at the calculated position
        canvas.drawText(userName, textX, 100, paint);
    }

    private void drawUserJobTitle(Canvas canvas, float nameTextX, float nameTextY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK); // Set a different color for job title
        paint.setTextSize(25); // Adjust text size for job title
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Set text style

        // Fetch the user's job title
        String userJobTitle =capitalizeFirstLetterOfEachWord( dbInfoPersonnelle.getInfo().get(0).getJob()); // Adjust method to get job title
        float jobTitleWidth = paint.measureText(userJobTitle);

        // Define a fixed vertical space below the user's name
        float spaceBelowName = 10; // Adjust this value as needed

        // Calculate the vertical position for the job title
        float jobTitleTextY = nameTextY + spaceBelowName + paint.getTextSize();

        // Calculate the horizontal position for the job title (center horizontally)
        float jobTitleTextX = 40;

        // Draw the job title at the calculated position
        canvas.drawText(userJobTitle, jobTitleTextX, jobTitleTextY, paint);
    }








    private Bitmap drawCircularImage(Canvas canvas, Bitmap bitmap, int x, int y) {
        int imageSize = (int) (canvas.getWidth() * IMAGE_SIZE_PERCENT / 100);
        int borderWidth = 0;
        int padding = 0;
        int totalSize = imageSize + 2 * (borderWidth + padding);

        Bitmap circularBitmap = Bitmap.createBitmap(totalSize, totalSize, Bitmap.Config.ARGB_8888);
        Canvas circularCanvas = new Canvas(circularBitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        // Draw the circular mask
        float radius = totalSize / 2f;
        circularCanvas.drawCircle(radius, radius, radius - borderWidth - padding, paint);

        // Set the Xfermode to SRC_IN to draw the bitmap only within the circular mask
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect destRect = new Rect(padding + borderWidth, padding + borderWidth, totalSize - (padding + borderWidth), totalSize - (padding + borderWidth));
        circularCanvas.drawBitmap(bitmap, srcRect, destRect, paint);

        // Draw the blue border around the circular image
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.rgb(	108	,154	,195));
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setAntiAlias(true);
        circularCanvas.drawCircle(radius, radius, radius - borderWidth / 2f, borderPaint);

        // Draw the resulting circular bitmap with border onto the original canvas
        canvas.drawBitmap(circularBitmap, x, y, null);

        return circularBitmap;
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
