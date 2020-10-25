package com.relit.health_care_app.sugar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.relit.health_care_app.models.Date;
import com.relit.health_care_app.pressure.PressureModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class SugarDataBaseHelper extends SQLiteOpenHelper {

    public static final String SUGAR_TABLE ="SUGAR_TABLE";
    public static final String SUGAR_DATE = "SUGAR_DATE";
    public static final String SUGAR_VALUE = "SUGAR_VALUE";

    public SugarDataBaseHelper(@Nullable Context context) {
        super(context, "sugar_level.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + SUGAR_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + SUGAR_DATE + " TEXT, " + SUGAR_VALUE + " INTEGER)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addElement(SugarModel model){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SUGAR_DATE, model.getDate().toString());
        cv.put(SUGAR_VALUE, model.getValue());
        long insert = db.insert(SUGAR_TABLE, null, cv);
        return insert != -1;
    }
    public List<SugarModel> getEveryone(){
        List<SugarModel> list = new ArrayList<>();
        String query = "SELECT * FROM " + SUGAR_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String date = cursor.getString(1);
                int value = cursor.getInt(2);
                SugarModel model = new SugarModel(id, new Date(date), value);
                list.add(model);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean hasElements(){
        String query = "SELECT * FROM " + SUGAR_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    public SugarModel getLastData(){
        String query = "SELECT * FROM " + SUGAR_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        SugarModel model = null;
        if(cursor.moveToLast()){
            int id = cursor.getInt(0);
            String date = cursor.getString(1);
            int value = cursor.getInt(2);
            model = new SugarModel(id, new Date(date), value);
        }
        return model;
    }
}
