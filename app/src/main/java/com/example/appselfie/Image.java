package com.example.appselfie;
import android.graphics.Bitmap;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Image implements Serializable {
    private String imageName;
    private Bitmap imageItem;

    public Image(Bitmap imageItem){
        this.imageName = convertDateToString();
        this.imageItem = imageItem;
    }

    public String convertDateToString(){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public void setImageName(Bitmap imageItem){
        this.imageItem = imageItem;
    }

    public Bitmap getImageItem(){
        return imageItem;
    }

    public String getImageName(){
        return imageName;
    }
}

