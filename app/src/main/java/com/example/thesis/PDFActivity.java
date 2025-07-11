package com.example.thesis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;

public class PDFActivity extends AppCompatActivity implements OnLoadCompleteListener, OnPageErrorListener {

    ProgressBar pdfViewProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        final PDFView pdfView = findViewById(R.id.pdfView);
        pdfViewProgressBar = findViewById(R.id.pdfViewProgressBar);

        pdfViewProgressBar.setVisibility(View.VISIBLE);

        Intent i = this.getIntent();
        final String path = i.getExtras().getString("PATH");

        FileLoader.with(this)
                .load(path, false)
                .fromDirectory("My_PDFs", FileLoader.DIR_INTERNAL)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        pdfViewProgressBar.setVisibility(View.GONE);
                        File pdfFile = response.getBody();
                        try {
                            pdfView.fromFile(pdfFile)
                                    .defaultPage(1)
                                    .enableAnnotationRendering(true)
                                    .onLoad(PDFActivity.this)
                                    .scrollHandle(new DefaultScrollHandle(PDFActivity.this))
                                    .spacing(10)
                                    .onPageError(PDFActivity.this)
                                    .load();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {
                        pdfViewProgressBar.setVisibility(View.GONE);
                        Toast.makeText(PDFActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void loadComplete(int nbPages) {
        pdfViewProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPageError(int page, Throwable t) {
        pdfViewProgressBar.setVisibility(View.GONE);
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
