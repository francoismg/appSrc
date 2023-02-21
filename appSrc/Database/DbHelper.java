package com.archtanlabs.root.essentialoils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.archtanlabs.root.essentialoils.Oil;
import com.archtanlabs.root.essentialoils.Symptom;
import com.archtanlabs.root.essentialoils.database.model.OilTable;
import com.archtanlabs.root.essentialoils.database.model.SymptomTable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "coffret_db";

    private static DbHelper dbHelper;

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DbHelper getInstance(Context context) {

       if(DbHelper.dbHelper == null) DbHelper.dbHelper = new DbHelper(context);

       return DbHelper.dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(OilTable.CREATE_TABLE);
        db.execSQL(SymptomTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOil(Oil oil) {
        SQLiteDatabase db = this.getWritableDatabase();

        boolean isAdded = true;

        ContentValues values = new ContentValues();
        values.put(OilTable.COLUMN_ID, oil.getId());
        values.put(OilTable.COLUMN_NAME, oil.getName());

        db.beginTransaction();

        try {
            db.insert(OilTable.TABLE_NAME, null, values);

            if(!oil.getListSymptom().isEmpty()) {

                for(Symptom s : oil.getListSymptom()) {
                    values.clear();

                    values.put(SymptomTable.COLUMN_ID, s.getId());
                    values.put(SymptomTable.COLUMN_NAME, s.getName());
                    values.put(SymptomTable.COLUMN_ID_OIL, oil.getId());

                    db.insert(SymptomTable.TABLE_NAME,null, values);
                }
            }

            db.setTransactionSuccessful();
        } catch(SQLiteException e) {
            isAdded = false;
        } finally {
            db.endTransaction();
        }

        db.close();

        return isAdded;
    }

    public boolean removeOil(int idOil) {
        SQLiteDatabase db = this.getWritableDatabase();

        boolean isRemoved = true;

        String arrayArgs[] = new String[1];

        arrayArgs[0] = String.valueOf(idOil);

        db.beginTransaction();
        try {
            db.delete(OilTable.TABLE_NAME, OilTable.COLUMN_ID+"=?", arrayArgs);
            db.delete(SymptomTable.TABLE_NAME, SymptomTable.COLUMN_ID_OIL+"=?", arrayArgs);
            db.setTransactionSuccessful();
        } catch(SQLException e) {
            isRemoved = false;
        } finally {
            db.endTransaction();
        }

        db.close();

        return isRemoved;
    }

    public List<Oil> getOilList() {
        List<Oil> oils = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + OilTable.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Oil oil;
        if (cursor.moveToFirst()) {
            do {
                oil = new Oil();
                oil.setId(cursor.getInt(cursor.getColumnIndex(OilTable.COLUMN_ID)));
                oil.setName(cursor.getString(cursor.getColumnIndex(OilTable.COLUMN_NAME)));

                oils.add(oil);
            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return oils;
    }

    public boolean isOilStored(int idOil) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT id FROM " + OilTable.TABLE_NAME + " WHERE " + OilTable.COLUMN_ID + " = " + idOil;

        boolean isStored = false;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) isStored = true;

        cursor.close();

        db.close();

        return isStored;
    }

    public List<Symptom> getSymptomList() {
        List<Symptom> symptoms = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + SymptomTable.TABLE_NAME + " GROUP BY " + SymptomTable.COLUMN_ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Symptom symptom;
        if (cursor.moveToFirst()) {
            do {
                symptom = new Symptom();
                symptom.setId(cursor.getInt(cursor.getColumnIndex(SymptomTable.COLUMN_ID)));
                symptom.setName(cursor.getString(cursor.getColumnIndex(SymptomTable.COLUMN_NAME)));

                symptoms.add(symptom);
            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return symptoms;
    }

}
