package com.bgp.mycamino.db;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

public class RouteDataSource {
    public static final String LOGTAG="MYCAMINO";

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;

    String[] allColumns={
            RouteOpenHelper.COLUMN_ID,
            RouteOpenHelper.COLUMN_LAT,
            RouteOpenHelper.COLUMN_LONG,
            RouteOpenHelper.COLUMN_ELEV,
            RouteOpenHelper.COLUMN_STAGID
    };

    public RouteDataSource(Context context){
        dbhelper=new RouteOpenHelper(context);
    }

    public void open(){
        database=dbhelper.getWritableDatabase();
    }

    public void close(){
        dbhelper.close();
    }


    public List<RouteItem> showRoute(){
        List<RouteItem> listRoute = new ArrayList<RouteItem>();

        Cursor cursor=database.query(RouteOpenHelper.TABLE_ROUTE, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            RouteItem tracks = cursorToTrackPoints(cursor);
            listRoute.add(tracks);
            cursor.moveToNext();
        }
        cursor.close();
        return listRoute;
    }

    public List<RouteItem> showRouteFiltered(String selection){
        List<RouteItem> listRoute = new ArrayList<RouteItem>();

        Cursor cursor=database.query(RouteOpenHelper.TABLE_ROUTE, allColumns, selection, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            RouteItem tracks = cursorToTrackPoints(cursor);
            listRoute.add(tracks);
            cursor.moveToNext();
        }
        cursor.close();
        return listRoute;
    }

    private RouteItem cursorToTrackPoints(Cursor cursor){
        RouteItem entry=new RouteItem();
        entry.setID(cursor.getLong(cursor.getColumnIndex(RouteOpenHelper.COLUMN_ID)));
        entry.setLatitude(cursor.getDouble(cursor.getColumnIndex(RouteOpenHelper.COLUMN_LAT)));
        entry.setLongitude(cursor.getDouble(cursor.getColumnIndex(RouteOpenHelper.COLUMN_LONG)));
        entry.setElevation(cursor.getDouble(cursor.getColumnIndex(RouteOpenHelper.COLUMN_ELEV)));
        entry.setStagid(cursor.getString(cursor.getColumnIndex(RouteOpenHelper.COLUMN_STAGID)));
        return entry;
    }

    public List<RouteItem> getAllDatabaseObject() {
        List<RouteItem> dataList = new ArrayList<RouteItem>();
        //  Select All Query
        String selectQuery = "SELECT  * FROM " + RouteOpenHelper.TABLE_ROUTE;

        Cursor cursor = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                RouteItem data = new RouteItem();
                data.setID(Integer.parseInt(cursor.getString(0)));
                data.setLatitude(Double.parseDouble(cursor.getString(1)));
                data.setLongitude(Double.parseDouble(cursor.getString(2)));
                data.setElevation(Double.parseDouble(cursor.getString(3)));
                data.setStagid(cursor.getString(2));
                //   Adding contact to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        // return database object list
        return dataList;
    }
}
