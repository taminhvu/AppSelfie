package com.example.appselfie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class ZoomActivity extends AppCompatActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        imageView = (ImageView) findViewById(R.id.zoomImage);
        Intent intent = getIntent();
        Bitmap image =(Bitmap) intent.getExtras().get("dulieu");
        imageView.setImageBitmap(image);
    }
}