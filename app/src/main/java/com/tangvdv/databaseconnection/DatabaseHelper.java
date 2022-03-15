package com.tangvdv.databaseconnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "kanji.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists Kanji(" +
                "idKanji INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(255), " +
                "translation VARCHAR(255), " +
                "banner VARCHAR(255), " +
                "note TEXT, jlpt INTEGER, " +
                "difficulty INTEGER)");

        db.execSQL("create table if not exists Package(" +
                "idPackage INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(255), " +
                "numberKanji INTEGER DEFAULT 0)");

        db.execSQL("create table if not exists Package_has_Kanji(" +
                "Package_id INTEGER NOT NULL, " +
                "Kanji_id INTEGER NOT NULL, " +
                "PRIMARY KEY(Package_id, Kanji_id), " +
                "FOREIGN KEY(Package_id) REFERENCES Package(idPackage), " +
                "FOREIGN KEY(Kanji_id) REFERENCES Kanji(idKanji))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Kanji");
        db.execSQL("DROP TABLE IF EXISTS Package");
        db.execSQL("DROP TABLE IF EXISTS Group_has_Kanji");
        onCreate(db);
    }

    public Cursor getSpecificPackage(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select name, numberKanji from Package WHERE idPackage = "+id,null);
        return res;
    }

    public void deleteSpecificPackage(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Package WHERE idPackage = "+id);
    }

    public void deleteKanjiFromPackage(Integer idPackage, Integer idKanji){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Package_has_Kanji WHERE Package_id = "+idPackage+" AND Kanji_id = "+idKanji);
    }

    public Cursor getKanjiFromPackage(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select idKanji, name, translation from Kanji INNER JOIN Package_has_Kanji WHERE Package_has_Kanji.Package_id = "+id+" AND Kanji_id = idKanji" ,null);
        return res;
    }


    public Cursor getAllData(String TABLE_NAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public Cursor getSpecificData(Integer id, String TABLE_NAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+ " WHERE idKanji = "+id,null);
        return res;
    }

    public Cursor getSpecificDataByName(String name, String TABLE_NAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+ " WHERE name = '"+name+"'",null);
        return res;
    }

    public Cursor getLastInsertRowId(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select last_insert_rowid()", null);
        return res;
    }

    public boolean insertData(String[] column, String[] data, String TABLE_NAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i<column.length; ++i) {
            contentValues.put(column[i], data[i]);
        }
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
}