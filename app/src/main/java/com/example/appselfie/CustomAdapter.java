package com.example.appselfie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<Image> images;

    public CustomAdapter(MainActivity context,int layout,List<Image> images ){
        this.context = context;
        this.layout = layout;
        this.images = images;
    }
    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTen;
        ImageView imgHinh, delete;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view                = layoutInflater.inflate(layout,null);

            viewHolder.txtTen   = (TextView) view.findViewById(R.id.textView);
            viewHolder.imgHinh = (ImageView) view.findViewById(R.id.imageview);
            viewHolder.delete = (ImageView) view.findViewById(R.id.imageDelete);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        Image image         = images.get(i);
        viewHolder.txtTen.setText(image.getTen());
        //Chuyen image view sang bitmap
        byte[] hinhAnh = image.getHinhAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0,hinhAnh.length);
        viewHolder.imgHinh.setImageBitmap(bitmap);
        //zoom Image
        viewHolder.imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.zoomImage(image.getHinhAnh());
            }
        });
        //delete image
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.diaLogXoaAnh(image.getId());
            }
        });
       return view;
    }

}
