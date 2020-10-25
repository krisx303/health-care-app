package com.relit.health_care_app.temperature;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.relit.health_care_app.models.Date;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class TemperatureDataBaseHelper extends SQLiteOpenHelper {

    public static final String TEMPERATURE_TABLE = "TEMPERATURE_TABLE";
    public static final String TEMPERATURE_DATE = "TEMPERATURE_DATE";
    public static final String TEMPERATURE_VALUE = "TEMPERATURE_VALUE";

    public TemperatureDataBaseHelper(@Nullable Context context) {
        super(context, "temperature.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TEMPERATURE_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + TEMPERATURE_DATE + " TEXT, " + TEMPERATURE_VALUE + " FLOAT)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addElement(TemperatureModel model){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TEMPERATURE_DATE, model.getDate().toString());
        cv.put(TEMPERATURE_VALUE, model.getTemperature());
        long insert = db.insert(TEMPERATURE_TABLE, null, cv);
        return insert != -1;
    }
    public List<TemperatureModel> getEveryone(){
        List<TemperatureModel> list = new ArrayList<>();
        String query = "SELECT * FROM " + TEMPERATURE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String date = cursor.getString(1);
                float value = cursor.getFloat(2);
                TemperatureModel model = new TemperatureModel(id, new Date(date), value);
                list.add(model);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    public boolean hasElements(){
        String query = "SELECT * FROM " + TEMPERATURE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }
    public TemperatureModel getLastData(){
        String query = "SELECT * FROM " + TEMPERATURE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        TemperatureModel model = null;
        if(cursor.moveToLast()){
            int id = cursor.getInt(0);
            String date = cursor.getString(1);
            float value = cursor.getFloat(2);
            model = new TemperatureModel(id, new Date(date), value);
        }
        return model;
    }
}
