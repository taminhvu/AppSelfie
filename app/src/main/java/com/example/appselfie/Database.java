package com.example.appselfie;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void Insert_HinhAnh(String ten,byte[] image){
        SQLiteDatabase database = getWritableDatabase();
        String sqlInsert = "INSERT INTO HinhAnh VALUES(null, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sqlInsert);
        statement.clearBindings();

        statement.bindString(1,ten);
        statement.bindBlob(2,image);
        statement.executeInsert();
    }

    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
