package com.bgp.mycamino.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DiaryDataSource {
    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;

    String[] allColumns={
            DiaryOpenHelper.COLUMN_DIARY_ID,
            DiaryOpenHelper.COLUMN_DIARY_ENTRYDATE,
            DiaryOpenHelper.COLUMN_DIARY_TEXT,
            DiaryOpenHelper.COLUMN_DIARY_TOPIC,
            DiaryOpenHelper.COLUMN_DIARY_CITYNAME,
            DiaryOpenHelper.COLUMN_DIARY_IMAGE
    };

    public DiaryDataSource(Context context){
        dbhelper=new DiaryOpenHelper(context);
    }

    public void open(){
        database=dbhelper.getWritableDatabase();
    }

    public void close(){
        dbhelper.close();
    }

    public List<DiaryEntry> listAllEntries(){
        List<DiaryEntry> listEntries = new ArrayList<DiaryEntry>();

        Cursor cursor=database.query(DiaryOpenHelper.TABLE_MYDIARY,allColumns, null,null,null,null,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DiaryEntry entries = cursorToEtries(cursor);
            listEntries.add(entries);
            cursor.moveToNext();
        }
        return listEntries;
    }

    public List<DiaryEntry> showEntry(String position){
        List<DiaryEntry> listEntries = new ArrayList<DiaryEntry>();

        Cursor cursor=database.query(DiaryOpenHelper.TABLE_MYDIARY,allColumns, position,null,null,null,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DiaryEntry entries = cursorToEtries(cursor);
            listEntries.add(entries);
            cursor.moveToNext();
        }
        return listEntries;
    }

    private DiaryEntry cursorToEtries(Cursor cursor){
        DiaryEntry entry=new DiaryEntry();
        entry.setID(cursor.getInt(cursor.getColumnIndex(DiaryOpenHelper.COLUMN_DIARY_ID)));
        entry.setEntryDate(cursor.getString(cursor.getColumnIndex(DiaryOpenHelper.COLUMN_DIARY_ENTRYDATE)));
        entry.setText(cursor.getString(cursor.getColumnIndex(DiaryOpenHelper.COLUMN_DIARY_TEXT)));
        entry.setTopic(cursor.getString(cursor.getColumnIndex(DiaryOpenHelper.COLUMN_DIARY_TOPIC)));
        entry.setCityname(cursor.getString(cursor.getColumnIndex(DiaryOpenHelper.COLUMN_DIARY_CITYNAME)));
        entry.setImage(cursor.getString(cursor.getColumnIndex(DiaryOpenHelper.COLUMN_DIARY_IMAGE)));
        return entry;
    }
}
