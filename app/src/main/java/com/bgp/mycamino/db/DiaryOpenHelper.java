package com.bgp.mycamino.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DiaryOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="mydiary.db";
    private static final int DATABASE_VERSION=1;
    private final Context myContext;

    public static final String TABLE_MYDIARY="MYDIARY";
    public static final String COLUMN_DIARY_ID="ID";
    public static final String COLUMN_DIARY_ENTRYDATE="ENTRYDATE";
    public static final String COLUMN_DIARY_TEXT="TEXT";
    public static final String COLUMN_DIARY_TOPIC="TOPIC";
    public static final String COLUMN_DIARY_CITYNAME="CITYNAME";
    public static final String COLUMN_DIARY_IMAGE="IMAGE";


    public DiaryOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.myContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MYDIARY_TABLE = "CREATE TABLE " +
                TABLE_MYDIARY + "("
                + COLUMN_DIARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_DIARY_ENTRYDATE
                + " TEXT," + COLUMN_DIARY_TEXT + " TEXT," + COLUMN_DIARY_TOPIC + " TEXT,"
                +COLUMN_DIARY_CITYNAME + " TEXT," +COLUMN_DIARY_IMAGE + " TEXT" +")";
        db.execSQL(CREATE_MYDIARY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYDIARY);
        onCreate(db);
    }


    public boolean insertData(String entrydate,String text,String topic){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DIARY_ENTRYDATE, entrydate);
        values.put(COLUMN_DIARY_TEXT, text);
        values.put(COLUMN_DIARY_TOPIC, topic);
//        values.put(COLUMN_DIARY_CITYNAME, cityname);
//        values.put(COLUMN_DIARY_IMAGE, image);
        long result=db.insert(TABLE_MYDIARY,null,values);
        if (result==-1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * FROM " + TABLE_MYDIARY,null);
        return res;
    }

    public boolean updateData(String id,String entrydate,String text,String topic){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DIARY_ID,id);
        values.put(COLUMN_DIARY_ENTRYDATE, entrydate);
        values.put(COLUMN_DIARY_TEXT, text);
        values.put(COLUMN_DIARY_TOPIC, topic);
//        values.put(COLUMN_DIARY_CITYNAME, cityname);
//        values.put(COLUMN_DIARY_IMAGE, image);
        db.update(TABLE_MYDIARY,values,"ID=?",new String[]{ id });
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_MYDIARY,"ID = ?",new String[]{id});
    }



}
