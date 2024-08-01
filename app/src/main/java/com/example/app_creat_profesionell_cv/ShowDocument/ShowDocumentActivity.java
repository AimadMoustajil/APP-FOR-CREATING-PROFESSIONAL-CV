package com.example.app_creat_profesionell_cv.ShowDocument;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.app_creat_profesionell_cv.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ShowDocumentActivity extends AppCompatActivity {

    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page currentPage;
    private byte[] pdfByteArray;
    private ImageView downloadFile,pdfImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_document);

        initViews();

        pdfByteArray = getIntent().getByteArrayExtra("pdfByteArray");
        if (pdfByteArray != null) {
            displayPdfFromByteArray(pdfByteArray);
        }

        downloadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    downloadPdf(pdfByteArray);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void initViews() {
        downloadFile = findViewById(R.id.downloadFile);
        pdfImageView = findViewById(R.id.imageView);
    }

    private void displayPdfFromByteArray(byte[] pdfByteArray) {
        try {
            ParcelFileDescriptor fileDescriptor = createTempFileDescriptor(pdfByteArray);
            if (fileDescriptor != null) {
                pdfRenderer = new PdfRenderer(fileDescriptor);
                currentPage = pdfRenderer.openPage(0);

                Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(), Bitmap.Config.ARGB_8888);
                currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                pdfImageView.setImageBitmap(bitmap);

                currentPage.close();
                pdfRenderer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            showToast("Error displaying PDF: " + e.getMessage());
        }
    }

    private ParcelFileDescriptor createTempFileDescriptor(byte[] pdfByteArray) throws IOException {
        File tempFile = File.createTempFile("temp_pdf", ".pdf", getCacheDir());
        try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
            fileOutputStream.write(pdfByteArray);
        }

        return ParcelFileDescriptor.open(tempFile, ParcelFileDescriptor.MODE_READ_ONLY);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private void downloadPdf(byte[] pdfByteArray) throws IOException {
        File downloadDir = new File(getExternalFilesDir(null), "downloads");
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }

        File pdfFile = new File(downloadDir, "file.pdf");
        try (FileOutputStream fileOutputStream = new FileOutputStream(pdfFile)) {
            fileOutputStream.write(pdfByteArray);
        }

        showToast("PDF downloaded successfully: " + pdfFile.getAbsolutePath());
    }

    private void sharePdf() {
        File pdfFile = createPdfFile(pdfByteArray);
        if (pdfFile != null && pdfFile.exists()) {
            Uri pdfUri = FileProvider.getUriForFile(this, "com.example.applicationgestionfacture.fileprovider", pdfFile);

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/pdf");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share PDF");
            shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(Intent.createChooser(shareIntent, "Share PDF..."));
        } else {
            showToast("Error sharing PDF");
        }
    }


    private File createPdfFile(byte[] pdfByteArray) {
        File pdfFile = new File(getCacheDir(), "temp_facture.pdf");
        try (FileOutputStream fileOutputStream = new FileOutputStream(pdfFile)) {
            fileOutputStream.write(pdfByteArray);
            return pdfFile;
        } catch (IOException e) {
            e.printStackTrace();
            showToast("Error creating PDF file");
            return null;
        }
    }

}
