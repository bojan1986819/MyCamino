package com.bgp.mycamino.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

public class RouteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "route.db";
    private static final int DATABASE_VERSION = 1;

    private final Context myContext;

    public static final String TABLE_ROUTE = "route";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LAT = "LATITUDE";
    public static final String COLUMN_LONG = "LONGITUDE";
    public static final String COLUMN_ELEV = "ELEVATION";
    public static final String COLUMN_STAGID = "STAGEID";

    public SQLiteDatabase database;

    private static RouteOpenHelper mInstance = null;

    public static RouteOpenHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new RouteOpenHelper(context.getApplicationContext());
        }
        return mInstance;
    }


    public RouteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i("MYCAMINO", "Database has been upgraded from " + oldVersion + " to " + newVersion);
    }

    public void openDatabase() {
        String dbPatch = myContext.getDatabasePath(DATABASE_NAME).getPath();
        if (database != null && database.isOpen()) {
            return;
        }
        database = SQLiteDatabase.openDatabase(dbPatch, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (database != null) {
            database.close();
        }
    }

    public void createDatabase() {
        createDB();
    }

    private void createDB() {
        boolean dbExist = DBExists();

        if (!dbExist) {
            this.getReadableDatabase();
            copyDBFromResource();
        }
    }

    private boolean DBExists() {
        SQLiteDatabase db = null;
        String Path = myContext.getDatabasePath(DATABASE_NAME).getPath();

        try {
            String databasePath = Path;
            db = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
            db.setLocale(Locale.getDefault());
            db.setLockingEnabled(true);
            db.setVersion(1);
        } catch (SQLiteException e) {
            Log.e("SqlHelper", "database not found");
        }

        if (db != null) {
            db.close();
        }
        return db != null ? true : false;
    }

    private void copyDBFromResource() {
        InputStream inputStream = null;
        OutputStream outStream = null;
        String Path = myContext.getDatabasePath(DATABASE_NAME).getPath();
        String dbFilePath = Path;


        try {
            inputStream = myContext.getAssets().open(DATABASE_NAME);
            outStream = new FileOutputStream(dbFilePath);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }

            outStream.flush();
            outStream.close();
            inputStream.close();

        } catch (IOException e) {
            throw new Error("Problem copying database from resource file.");
        }

    }
}
