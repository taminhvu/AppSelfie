package com.example.appselfie;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private CustomAdapter customAdapter;
    private ArrayList<Image> images;
    ImageView cameraImage;
    private static final int REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewImageItem();
        cameraImage = (ImageView) findViewById(R.id.cameraImage);
        cameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {

        if (requestCode == REQUEST_CODE && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Image image = new Image(bitmap);
            images.add(image);
            customAdapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void viewImageItem(){
        images = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        customAdapter = new CustomAdapter(this,R.layout.activity_listview, images);
        listView.setAdapter(customAdapter);
    }
}