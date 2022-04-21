package com.example.appselfie;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private CustomAdapter customAdapter;
    private ArrayList<Image> images;
    ImageView cameraImage;
    public static Database database;
    private static final int REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        images          = new ArrayList<>();
        listView        = (ListView) findViewById(R.id.listView);
        customAdapter   = new CustomAdapter(this,R.layout.activity_listview, images);
        listView.setAdapter(customAdapter);
        cameraImage     = (ImageView) findViewById(R.id.cameraImage);

        // click button take a photo
        cameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        //create database and table HinhAnh
        database = new Database(this, "KhoAnh.sqlite", null, 1);
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS HinhAnh(Id INTEGER PRIMARY KEY AUTOINCREMENT, Ten VARCHAR(150), image BLOCK)";
        database.QueryData(sqlCreateTable);

        //getData
        getData();
    }
    public void getData(){
        images.clear();
        Cursor cursor =database.GetData("SELECT * FROM HinhAnh");
        while (cursor.moveToNext()){
            images.add(new Image(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getBlob(2)
            ));
        }
        customAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        if (requestCode == REQUEST_CODE && data != null && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            // luu vao database
            ByteArrayOutputStream byteArray =new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100, byteArray);
            byte[] hinhanh = byteArray.toByteArray();

            //Tên hình ảnh
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
            String ten = dateFormat.format(date);
            //luu hinh anh va ten vao database
            database.Insert_HinhAnh(ten,hinhanh);
            Toast.makeText(this, "Da them vao database", Toast.LENGTH_SHORT).show();
            getData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void zoomImage(byte[] HinhAnh){
        Intent intent = new Intent(getApplicationContext(), ZoomActivity.class);
        intent.putExtra("dulieu", HinhAnh);
        startActivity(intent);
    }

    public void diaLogXoaAnh(int id){
        AlertDialog.Builder dialogxoa = new AlertDialog.Builder(this);
        dialogxoa.setMessage("Bạn muốn xóa hình ảnh không");
        dialogxoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String sqlDelete = "DELETE FROM HinhAnh WHERE Id = '"+ id +"'";
                database.QueryData(sqlDelete);
                Toast.makeText(MainActivity.this, "Đã Xóa", Toast.LENGTH_SHORT).show();
                getData();
            }
        });
        dialogxoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogxoa.show();
    }
}