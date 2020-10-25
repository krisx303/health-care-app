package com.relit.health_care_app.pressure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.relit.health_care_app.models.Date;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class PressureDataBaseHelper extends SQLiteOpenHelper {

    public static final String PRESSURE_TABLE = "PRESSURE_TABLE";
    public static final String PRESSURE_DATE = "PRESSURE_DATE";
    public static final String PRESSURE_SYSTOLIC = "PRESSURE_SYSTOLIC";
    public static final String PRESSURE_DIASTOLIC = "PRESSURE_DIASTOLIC";

    public PressureDataBaseHelper(@Nullable Context context) {
        super(context, "pressure.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + PRESSURE_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + PRESSURE_DATE + " TEXT, " + PRESSURE_SYSTOLIC + " INTEGER, " + PRESSURE_DIASTOLIC + " INTEGER)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addElement(PressureModel model){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PRESSURE_DATE, model.getDate().toString());
        cv.put(PRESSURE_SYSTOLIC, model.getSystolic());
        cv.put(PRESSURE_DIASTOLIC, model.getDiastolic());
        long insert = db.insert(PRESSURE_TABLE, null, cv);
        return insert != -1;
    }
    public List<PressureModel> getEveryone(){
        List<PressureModel> list = new ArrayList<>();
        String query = "SELECT * FROM " + PRESSURE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String date = cursor.getString(1);
                int systolic = cursor.getInt(2);
                int diastolic = cursor.getInt(3);
                PressureModel model = new PressureModel(id, new Date(date), systolic, diastolic);
                list.add(model);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    public boolean hasElements(){
        String query = "SELECT * FROM " + PRESSURE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }
    public PressureModel getLastData(){
        String query = "SELECT * FROM " + PRESSURE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        PressureModel model = null;
        if(cursor.moveToLast()){
            int id = cursor.getInt(0);
            String date = cursor.getString(1);
            int systolic = cursor.getInt(2);
            int diastolic = cursor.getInt(3);
            model = new PressureModel(id, new Date(date), systolic, diastolic);
        }
        return model;
    }
}
