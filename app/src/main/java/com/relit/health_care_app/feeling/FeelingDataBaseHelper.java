package com.relit.health_care_app.feeling;

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

public class FeelingDataBaseHelper extends SQLiteOpenHelper {
    public static final String FEELING_TABLE = "FEELING_TABLE";
    public static final String FEELING_DATE = "FEELING_DATE";
    public static final String FEELING_VALUE = "FEELING_VALUE";

    public FeelingDataBaseHelper(@Nullable Context context) {
        super(context, "feeling.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + FEELING_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + FEELING_DATE + " TEXT, " + FEELING_VALUE + " FLOAT)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
/*    public boolean addElement(TemperatureModel model){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FEELING_DATE, model.getDate().toString());
        cv.put(FEELING_VALUE, model.getTemperature());
        long insert = db.insert(FEELING_TABLE, null, cv);
        return insert != -1;
    }
    public List<TemperatureModel> getEveryone(){
        List<TemperatureModel> list = new ArrayList<>();
        String query = "SELECT * FROM " + FEELING_TABLE;
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
        String query = "SELECT * FROM " + FEELING_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }
    public TemperatureModel getLastData(){
        String query = "SELECT * FROM " + FEELING_TABLE;
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
    }*/
}
