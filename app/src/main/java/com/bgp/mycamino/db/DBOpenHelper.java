package com.bgp.mycamino.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

public class DBOpenHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME="mycamino.db";
    //ezt a számot kell egyel növelnem, hogy updatekor felülírja az adatbázist ha volt változás
    private static final int DATABASE_VERSION=5;
    private static final String SP_KEY_DB_VER = "db_ver";

    private final Context myContext;

    public static final String TABLE_ALBERGUES="ALBERGUES";
    public static final String COLUMN_ALBNAME = "ALBERGUENAME";
    public static final String COLUMN_ALBTYPE = "ALBERGUETYPE";
    public static final String COLUMN_ADDRESS = "ADDRESS";
    public static final String COLUMN_TEL = "TEL";
    public static final String COLUMN_LINK = "LINK";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_CREDENTIAL = "CREDENTIAL";
    public static final String COLUMN_NUMBEDS = "NUMBEDS";
    public static final String COLUMN_BEDCOST = "BEDCOST";
    public static final String COLUMN_KITCHEN = "KITCHEN";
    public static final String COLUMN_BFASTINCL = "COSTWITHBFAST";
    public static final String COLUMN_MEALINCL = "COSTWITHMEAL";
    public static final String COLUMN_BFASTCOST = "BFASTCOST";
    public static final String COLUMN_MEALCOST = "MEALCOST";
    public static final String COLUMN_PICNICPACK = "PICNICPACK";
    public static final String COLUMN_WASHM = "WASHM";
    public static final String COLUMN_DRYM = "DRYM";
    public static final String COLUMN_VENDMACHINE = "VENDMACHINE";
    public static final String COLUMN_OPENTIME = "OPENTIME";
    public static final String COLUMN_OPENSEASON = "OPENSEASON";
    public static final String COLUMN_RESERVATION = "RESERVATION";
    public static final String COLUMN_CITYID = "CITYID";
    public static final String COLUMN_LATITUDE = "LATITUDE";
    public static final String COLUMN_LONGITUDE = "LONGITUDE";
    public static final String COLUMN_INTERNET = "INTERNET";
    public static final String COLUMN_INTERNETCOST = "INTERNETCOST";
    public static final String COLUMN_WIFI = "WIFI";
    public static final String COLUMN_BIKE = "BIKE";



    public static final String TABLE_CITIES="CITIES";
    public static final String COLUMN_CITYNAME = "CITYNAME";
    public static final String COLUMN_DESC = "DESC";
    public static final String COLUMN_DISTFROMLAST = "DISTFROMLAST";
    public static final String COLUMN_DISTFROMALTLAST = "DISTFROMALTLAST";
    public static final String COLUMN_DISTFROMSJPD = "DISTFROMSJPD";
    public static final String COLUMN_DISTTOSO = "DISTTOSO";
    public static final String COLUMN_ALTITUDE = "ALTITUDE";
    public static final String COLUMN_IMAGE = "IMAGE";
    public static final String COLUMN_WATER = "WATER";
    public static final String COLUMN_ATMBANK = "ATMBANK";
    public static final String COLUMN_RESTAURANT = "RESTAURANT";
    public static final String COLUMN_CAFE = "CAFE";
    public static final String COLUMN_GROCERY = "GROCERY";
    public static final String COLUMN_HOSPITAL = "HOSPITAL";
    public static final String COLUMN_PHARMACY = "PHARMACY";
    public static final String COLUMN_POST = "POST";
    public static final String COLUMN_TAXI = "TAXI";
    public static final String COLUMN_BUS = "BUS";
    public static final String COLUMN_TRAIN = "TRAIN";
    public static final String COLUMN_AIRPORT = "AIRPORT";
    public static final String COLUMN_CHURCH = "CHURCH";
    public static final String COLUMN_PILGRIMOF = "PILGRIMOFC";
    public static final String COLUMN_MUNALB = "MUNALBERGUE";
    public static final String COLUMN_PARALB = "ALBPARROQUIAL";
    public static final String COLUMN_PRIVALB = "PRIVHOSTEL";
    public static final String COLUMN_HOTEL = "HOTEL";
    public static final String COLUMN_CAMP = "CAMPSITE";
//    public static final String COLUMN_LATITUDE = "LATITUDE";
//    public static final String COLUMN_LONGITUDE = "LONGITUDE";
    public static final String COLUMN_STAGESID = "STAGESID";
    public static final String COLUMN_CITYCODE = "CITYCODE";
    public static final String COLUMN_WIKILINK = "WIKILINK";
    public static final String COLUMN_FBPLACEID = "FBPLACEID";

    public static final String TABLE_STAGES = "STAGES";
    public static final String COLUMN_STAGENAME = "STAGENAME";
    public static final String COLUMN_DAYNUM = "DAYNUM";
    public static final String COLUMN_KMTOTAL = "KMSTOTAL";
//    public static final String COLUMN_DESC = "DESC";
//    public static final String COLUMN_IMAGE = "IMAGE";
    public static final String COLUMN_ALTIMAGE = "ALTITUDEIMAGE";
    public static final String COLUMN_STARTCITY = "STARTCITY";
    public static final String COLUMN_ENDCITY = "ENDCITY";
    public static final String COLUMN_KMPART = "KMPART";

    public static final String TABLE_SIGHTSEE = "SIGHTSEE";
    public static final String COLUMN_SIGHTSEENAME = "SIGHTSEENAME";
//    public static final String COLUMN_CITYID = "CITYID";
//    public static final String COLUMN_ADDRESS = "ADDRESS";
//    public static final String COLUMN_LATITUDE = "LATITUDE";
//    public static final String COLUMN_LONGITUDE = "LONGITUDE";
//    public static final String COLUMN_DESC = "DESC";
//    public static final String COLUMN_IMAGE = "IMAGE";
    public static final String COLUMN_SOURCE = "SOURCE";


    public static final String TABLE_ALBOLD = "ALBERGUESOLD";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PART = "part";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_TYPE = "type";
//    public static final String COLUMN_DESC = "description";
//    public static final String COLUMN_LINK = "link";
//    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_FAC = "facilities";
    public static final String COLUMN_PRICE = "price";
//    public static final String COLUMN_IMAGE = "image";
//    public static final String COLUMN_LATITUDE = "latitude";
//    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_MARKERTEXT = "markerText";
    public static final String COLUMN_NOTES = "notes";




    public SQLiteDatabase database;

    private static DBOpenHelper mInstance = null;

    public static DBOpenHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new DBOpenHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext=context;
        initialize();
    }



    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static factory method "getInstance()" instead.
     */


    /*@Override
    public void onCreate(SQLiteDatabase db) {

    }*/

 /*   @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion  + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);

        Log.i("MYCAMINO","Database has been upgraded from " +oldVersion+" to "+newVersion);
    }

    public void openDatabase(){
        String dbPatch=myContext.getDatabasePath(DATABASE_NAME).getPath();
        if(database !=null && database.isOpen()){
            return;
        }
        database=SQLiteDatabase.openDatabase(dbPatch,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase(){
        if (database!=null){
            database.close();
        }
    }
    public void createDatabase() {
        createDB();
    }

    private void createDB() {
        boolean dbExist = DBExists();

        if(!dbExist) {
            this.getReadableDatabase();
            copyDBFromResource();
        }
    }

    private boolean DBExists() {
        SQLiteDatabase db = null;
        String Path= myContext.getDatabasePath(DATABASE_NAME).getPath();

        try {
            String databasePath = Path;
            db = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
            db.setLocale(Locale.getDefault());
            db.setLockingEnabled(true);
            db.setVersion(DATABASE_VERSION);
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
        String Path= myContext.getDatabasePath(DATABASE_NAME).getPath();
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

    }*/




    /**
     * Initializes database. Creates database if doesn't exist.
     */
    private void initialize() {
        if (databaseExists()) {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(myContext);
            int dbVersion = prefs.getInt(SP_KEY_DB_VER, 0);
            if (DATABASE_VERSION != dbVersion) {
                File dbFile = myContext.getDatabasePath(DATABASE_NAME);
                if (!dbFile.delete()) {
                    Log.w("database", "Unable to update database");
                }
            }
        }
        if (!databaseExists()) {
            createDatabase();
        }
    }

    /**
     * Returns true if database file exists, false otherwise.
     * @return
     */
    private boolean databaseExists() {
        File dbFile = myContext.getDatabasePath(DATABASE_NAME);
        return dbFile.exists();
    }

    /**
     * Creates database by copying it from assets directory.
     */
    private void createDatabase() {
        String parentPath = myContext.getDatabasePath(DATABASE_NAME).getParent();
        String path = myContext.getDatabasePath(DATABASE_NAME).getPath();

        File file = new File(parentPath);
        if (!file.exists()) {
            if (!file.mkdir()) {
                Log.w("database", "Unable to create database directory");
                return;
            }
        }

        InputStream is = null;
        OutputStream os = null;
        try {
            is = myContext.getAssets().open(DATABASE_NAME);
            os = new FileOutputStream(path);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(myContext);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(SP_KEY_DB_VER, DATABASE_VERSION);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
    }
}
