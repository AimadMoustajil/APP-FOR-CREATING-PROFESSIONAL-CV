package com.example.app_creat_profesionell_cv.M_LettersDesMotivation;

import androidx.annotation.NonNull;
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
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app_creat_profesionell_cv.R;
import com.example.app_creat_profesionell_cv.ShowDocument.ShowDocumentActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Counselor extends AppCompatActivity {
    ImageView showLetter;
    EditText entete, corps, basDePage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counselor);
        showLetter = findViewById(R.id.showLetter);
        entete = findViewById(R.id.entete);
        corps = findViewById(R.id.corps);
        basDePage = findViewById(R.id.basDePage);

        showLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check for permission to write to external storage
                if (ContextCompat.checkSelfPermission(Counselor.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Counselor.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {
                    // Permission is granted, proceed with PDF creation
                    PdfDocument document = createPDF();
                    if (document != null) {
                        ByteArrayOutputStream outputStream = savePDFToByteArray(document);

                        // Start ShowDocumentActivity to display the PDF
                        Intent intent = new Intent(Counselor.this, ShowDocumentActivity.class);
                        intent.putExtra("pdfByteArray", outputStream.toByteArray());
                        startActivity(intent);

                        // Close the document after starting the activity
                        document.close();
                    }
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, create PDF
                createPDF();
            } else {
                // Permission denied, show a toast message
                showToastOnUiThread("Permission denied to write to external storage");
            }
        }
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

    private PdfDocument createPDF() {
        if (!isExternalStorageWritable()) {
            showToastOnUiThread("External storage not writable");
            return null;
        }

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = createPageInfo();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = createPaint();

        int margin = 40;
        int lineHeight = 20;
        int paragraphSpacing = lineHeight * 2; // Extra space between paragraphs
        int pageWidth = pageInfo.getPageWidth();
        int currentY = margin;
        int textWidth = pageWidth - 2 * margin;

        // Draw the header
        String headerText = entete.getText().toString();
        String[] headerLines = headerText.split("\n");
        for (String line : headerLines) {
            canvas.drawText(line, margin, currentY, paint);
            currentY += lineHeight;
        }

        // Draw a gap between header and body
        currentY += lineHeight * 2;

        // Draw the body with text wrapping and paragraph spacing
        String bodyText = corps.getText().toString();
        String[] paragraphs = bodyText.split("\n\n"); // Split paragraphs
        for (String paragraph : paragraphs) {
            String[] lines = paragraph.split("\n"); // Split lines within a paragraph
            for (String line : lines) {
                if (paint.measureText(line) > textWidth) {
                    // Text wrapping
                    String[] wrappedLines = wrapText(line, paint, textWidth);
                    for (String wrappedLine : wrappedLines) {
                        canvas.drawText(wrappedLine, margin, currentY, paint);
                        currentY += lineHeight;
                    }
                } else {
                    canvas.drawText(line, margin, currentY, paint);
                    currentY += lineHeight;
                }
            }
            // Add space between paragraphs
            currentY += paragraphSpacing;
        }
        // Draw the footer

        String footerText = basDePage.getText().toString();
        canvas.drawText(footerText, margin, pageInfo.getPageHeight()-(margin*5), paint);

        document.finishPage(page);

        return document;
    }

    private String[] wrapText(String text, Paint paint, int textWidth) {
        ArrayList<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if (paint.measureText(line.toString() + " " + word) > textWidth) {
                lines.add(line.toString());
                line = new StringBuilder(word);
            } else {
                if (line.length() > 0) {
                    line.append(" ");
                }
                line.append(word);
            }
        }
        lines.add(line.toString());

        return lines.toArray(new String[0]);
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
        int contentHeight = 1200;
        int contentWidth = 610; // Or calculate based on content if needed

        // Adjust the height to include some padding or margins
        int pageHeight = contentHeight + 200; // Add some padding or margins
        int pageWidth = contentWidth; // Use the content width or adjust as needed

        return new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
    }

    private Bitmap resizeBitmap(Bitmap originalBitmap) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();

        float scaleWidth = ((float) 128) / width;
        float scaleHeight = ((float) 128) / height;

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
}