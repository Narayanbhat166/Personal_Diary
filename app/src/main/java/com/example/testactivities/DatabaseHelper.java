package com.example.testactivities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "BOOK_KEEPING";

    String col_2 = "YEAR";
    String col_3 = "MONTH";
    String col_4 = "DATE";
    String col_5 = "DAY";
    String col_6 = "TIME";
    String col_7 = "INFO";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+DATABASE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,YEAR TEXT,MONTH TEXT,DATE TEXT,DAY TEXT,TIME TEXT,INFO TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);

    }

    public boolean insertData(String year,String month,String date,String day,String time,String info){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2,year);
        contentValues.put(col_3,month);
        contentValues.put(col_4,date);
        contentValues.put(col_5,day);
        contentValues.put(col_6,time);
        contentValues.put(col_7,info);

        long result = db.insert(DATABASE_NAME,null,contentValues);
        if(result == -1) return false;
        return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+DATABASE_NAME,null);
        return res;
    }

    public boolean deleteLastEntry(){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            System.out.println("Hello");
            return true;
        }catch (SQLException e){
            return false;
        }
    }

}
