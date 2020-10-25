package com.relit.health_care_app.weight;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.relit.health_care_app.models.Date;
import com.relit.health_care_app.temperature.TemperatureModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class WeightDataBaseHelper extends SQLiteOpenHelper {
    public static final String WEIGHT_TABLE = "WEIGHT_TABLE";
    public static final String WEIGHT_DATE = "WEIGHT_DATE";
    public static final String WEIGHT_VALUE = "WEIGHT_VALUE";



    public WeightDataBaseHelper(@Nullable Context context) {
        super(context, "weight.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + WEIGHT_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + WEIGHT_DATE + " TEXT, " + WEIGHT_VALUE + " FLOAT)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addElement(WeightModel model){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(WEIGHT_DATE, model.getDate().toString());
        cv.put(WEIGHT_VALUE, model.getWeight());
        long insert = db.insert(WEIGHT_TABLE, null, cv);
        return insert != -1;
    }
    public List<WeightModel> getEveryone(){
        List<WeightModel> list = new ArrayList<>();
        String query = "SELECT * FROM " + WEIGHT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String date = cursor.getString(1);
                float value = cursor.getFloat(2);
                WeightModel model = new WeightModel(id, new Date(date), value);
                list.add(model);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    public boolean hasElements(){
        String query = "SELECT * FROM " + WEIGHT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }
    public WeightModel getLastData(){
        String query = "SELECT * FROM " + WEIGHT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        WeightModel model = null;
        if(cursor.moveToLast()){
            int id = cursor.getInt(0);
            String date = cursor.getString(1);
            float value = cursor.getFloat(2);
            model = new WeightModel(id, new Date(date), value);
        }
        return model;
    }
}
