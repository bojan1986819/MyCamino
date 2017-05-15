package com.bgp.mycamino.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBDataSource {

    public static final String LOGTAG="MYCAMINO";

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;

    String[] allColumns={
            DBOpenHelper.COLUMN_ID,
            DBOpenHelper.COLUMN_PART,
            DBOpenHelper.COLUMN_NAME,
            DBOpenHelper.COLUMN_CITY,
            DBOpenHelper.COLUMN_TYPE,
            DBOpenHelper.COLUMN_DESC,
            DBOpenHelper.COLUMN_LINK,
            DBOpenHelper.COLUMN_EMAIL,
            DBOpenHelper.COLUMN_FAC,
            DBOpenHelper.COLUMN_PRICE,
            DBOpenHelper.COLUMN_IMAGE,
            DBOpenHelper.COLUMN_LATITUDE,
            DBOpenHelper.COLUMN_LONGITUDE,
            DBOpenHelper.COLUMN_MARKERTEXT,
            DBOpenHelper.COLUMN_NOTES
    };

    String[] stagesAllColumns={
            DBOpenHelper.COLUMN_ID,
            DBOpenHelper.COLUMN_STAGENAME,
            DBOpenHelper.COLUMN_DAYNUM,
            DBOpenHelper.COLUMN_KMTOTAL,
            DBOpenHelper.COLUMN_DESC,
            DBOpenHelper.COLUMN_IMAGE,
            DBOpenHelper.COLUMN_ALTIMAGE,
            DBOpenHelper.COLUMN_STARTCITY,
            DBOpenHelper.COLUMN_ENDCITY,
            DBOpenHelper.COLUMN_KMPART
    };

    String[] citiesAllColumns={
            DBOpenHelper.COLUMN_ID,
            DBOpenHelper.COLUMN_CITYNAME,
            DBOpenHelper.COLUMN_DESC,
            DBOpenHelper.COLUMN_DISTFROMLAST,
            DBOpenHelper.COLUMN_DISTFROMALTLAST,
            DBOpenHelper.COLUMN_DISTFROMSJPD,
            DBOpenHelper.COLUMN_DISTTOSO,
            DBOpenHelper.COLUMN_ALTITUDE,
            DBOpenHelper.COLUMN_IMAGE,
            DBOpenHelper.COLUMN_WATER,
            DBOpenHelper.COLUMN_ATMBANK,
            DBOpenHelper.COLUMN_RESTAURANT,
            DBOpenHelper.COLUMN_CAFE,
            DBOpenHelper.COLUMN_GROCERY,
            DBOpenHelper.COLUMN_HOSPITAL,
            DBOpenHelper.COLUMN_PHARMACY,
            DBOpenHelper.COLUMN_POST,
            DBOpenHelper.COLUMN_TAXI,
            DBOpenHelper.COLUMN_BUS,
            DBOpenHelper.COLUMN_TRAIN,
            DBOpenHelper.COLUMN_AIRPORT,
            DBOpenHelper.COLUMN_CHURCH,
            DBOpenHelper.COLUMN_PILGRIMOF,
            DBOpenHelper.COLUMN_MUNALB,
            DBOpenHelper.COLUMN_PARALB,
            DBOpenHelper.COLUMN_PRIVALB,
            DBOpenHelper.COLUMN_HOTEL,
            DBOpenHelper.COLUMN_CAMP,
            DBOpenHelper.COLUMN_LATITUDE,
            DBOpenHelper.COLUMN_LONGITUDE,
            DBOpenHelper.COLUMN_STAGESID,
            DBOpenHelper.COLUMN_CITYCODE,
            DBOpenHelper.COLUMN_WIKILINK,
            DBOpenHelper.COLUMN_FBPLACEID
    };

    String[] albAllColumns={
            DBOpenHelper.COLUMN_ID,
        DBOpenHelper.COLUMN_ALBNAME,
        DBOpenHelper.COLUMN_ALBTYPE,
        DBOpenHelper.COLUMN_ADDRESS,
        DBOpenHelper.COLUMN_TEL,
        DBOpenHelper.COLUMN_LINK,
        DBOpenHelper.COLUMN_EMAIL,
        DBOpenHelper.COLUMN_CREDENTIAL,
        DBOpenHelper.COLUMN_NUMBEDS,
        DBOpenHelper.COLUMN_BEDCOST,
        DBOpenHelper.COLUMN_KITCHEN,
        DBOpenHelper.COLUMN_BFASTINCL,
        DBOpenHelper.COLUMN_MEALINCL,
        DBOpenHelper.COLUMN_BFASTCOST,
        DBOpenHelper.COLUMN_MEALCOST,
        DBOpenHelper.COLUMN_PICNICPACK,
        DBOpenHelper.COLUMN_WASHM,
        DBOpenHelper.COLUMN_DRYM,
        DBOpenHelper.COLUMN_VENDMACHINE,
        DBOpenHelper.COLUMN_OPENTIME,
        DBOpenHelper.COLUMN_OPENSEASON,
        DBOpenHelper.COLUMN_RESERVATION,
        DBOpenHelper.COLUMN_CITYID,
        DBOpenHelper.COLUMN_LATITUDE,
        DBOpenHelper.COLUMN_LONGITUDE,
        DBOpenHelper.COLUMN_INTERNET,
        DBOpenHelper.COLUMN_INTERNETCOST,
        DBOpenHelper.COLUMN_WIFI,
        DBOpenHelper.COLUMN_BIKE
    };

    String[] sseeAllColumns={
            DBOpenHelper.COLUMN_ID,
        DBOpenHelper.COLUMN_SIGHTSEENAME,
        DBOpenHelper.COLUMN_CITYID,
        DBOpenHelper.COLUMN_ADDRESS,
        DBOpenHelper.COLUMN_LATITUDE,
        DBOpenHelper.COLUMN_LONGITUDE,
        DBOpenHelper.COLUMN_DESC,
        DBOpenHelper.COLUMN_IMAGE,
            DBOpenHelper.COLUMN_SOURCE
    };

    public DBDataSource(Context context){
        dbhelper=new DBOpenHelper(context);
    }

    public void open(){
        database=dbhelper.getWritableDatabase();
    }

    public void close(){
        dbhelper.close();
    }



    public List<DBItem> findStagesFiltered(){
        List<DBItem> listStages = new ArrayList<DBItem>();

        Cursor cursor=database.query(DBOpenHelper.TABLE_STAGES, stagesAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DBItem stages = cursorToStages(cursor);
            listStages.add(stages);
            cursor.moveToNext();
        }
        cursor.close();
        return listStages;

    }

    public List<DBItem> findCitiesFiltered(String selection){
        List<DBItem> listCities = new ArrayList<DBItem>();

        Cursor cursor=database.query(DBOpenHelper.TABLE_CITIES, citiesAllColumns, selection, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DBItem cities = cursorToCities(cursor);
            listCities.add(cities);
            cursor.moveToNext();
        }
        cursor.close();
        return listCities;
    }

    public List<DBItem> findAlberguesFiltered(String selection){
        List<DBItem> listAlbergues = new ArrayList<DBItem>();

        Cursor cursor=database.query(DBOpenHelper.TABLE_ALBERGUES, albAllColumns, selection, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DBItem albergues = cursorToAlbergues(cursor);
            listAlbergues.add(albergues);
            cursor.moveToNext();
        }
        cursor.close();
        return listAlbergues;
    }

    public List<DBItem> findSightseeFiltered(String selection){
        List<DBItem> listSightsee = new ArrayList<DBItem>();

        Cursor cursor=database.query(DBOpenHelper.TABLE_SIGHTSEE, sseeAllColumns, selection, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DBItem sightsee = cursorToSightsee(cursor);
            listSightsee.add(sightsee);
            cursor.moveToNext();
        }
        cursor.close();
        return listSightsee;
    }

    private DBItem cursorToStages(Cursor cursor){
        DBItem entry=new DBItem();
        entry.setId(cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_ID)));
        entry.setDaynum(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_DAYNUM)));
        entry.setStagename(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_STAGENAME)));
        entry.setKmtotal(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_KMTOTAL)));
        entry.setDescription(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_DESC)));
        entry.setImage(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_IMAGE)));
        entry.setAltimage(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_ALTIMAGE)));
        entry.setKmpart(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_KMPART)));
        entry.setStartcity(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_STARTCITY)));
        entry.setEndcity(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENDCITY)));
        return entry;
    }

    private DBItem cursorToCities(Cursor cursor){
        DBItem entry=new DBItem();
        entry.setId(cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_ID)));
        entry.setCityname(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_CITYNAME)));
        entry.setDistfromlast(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.COLUMN_DISTFROMLAST)));
        entry.setDistfromaltlast(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.COLUMN_DISTFROMALTLAST)));
        entry.setDistfromsjpd(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.COLUMN_DISTFROMSJPD)));
        entry.setDisttoso(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.COLUMN_DISTTOSO)));
        entry.setAltitude(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_ALTITUDE)));
        entry.setWater(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_WATER)));
        entry.setAtmbank(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_ATMBANK)));
        entry.setRestaurant(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_RESTAURANT)));
        entry.setCafe(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_CAFE)));
        entry.setGrocery(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_GROCERY)));
        entry.setHospital(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_HOSPITAL)));
        entry.setPharmacy(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_PHARMACY)));
        entry.setPost(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_POST)));
        entry.setTaxi(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_TAXI)));
        entry.setBus(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_BUS)));
        entry.setTrain(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_TRAIN)));
        entry.setAirport(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_AIRPORT)));
        entry.setChurch(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_CHURCH)));
        entry.setPilgrimof(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_PILGRIMOF)));
        entry.setMunalb(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_MUNALB)));
        entry.setParalb(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_PARALB)));
        entry.setPrivalb(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_PRIVALB)));
        entry.setHotel(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_HOTEL)));
        entry.setCamp(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_CAMP)));
        entry.setStagesid(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_STAGESID)));
        entry.setCitycode(cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_CITYCODE)));
        entry.setDescription(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_DESC)));
        entry.setLatitude(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.COLUMN_LATITUDE)));
        entry.setLongitude(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.COLUMN_LONGITUDE)));
        entry.setImage(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_IMAGE)));
        entry.setWikilink(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_WIKILINK)));
        entry.setFbplaceid(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_FBPLACEID)));
        return entry;
    }

    private DBItem cursorToAlbergues(Cursor cursor){
        DBItem entry=new DBItem();
        entry.setId(cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_ID)));
        entry.setAlbergueName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_ALBNAME)));
        entry.setAlbergueType(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_ALBTYPE)));
        entry.setAddress(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_ADDRESS)));
        entry.setTel(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_TEL)));
        entry.setCredential(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_CREDENTIAL)));
        entry.setNumbeds(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_NUMBEDS)));
        entry.setBedcost(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_BEDCOST)));
        entry.setKitchen(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_KITCHEN)));
        entry.setCostwithbfast(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_BFASTINCL)));
        entry.setCostwithmeal(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_MEALINCL)));
        entry.setBfastcost(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_BFASTCOST)));
        entry.setMealcost(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_MEALCOST)));
        entry.setPicnicpack(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_PICNICPACK)));
        entry.setWhasm(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_WASHM)));
        entry.setDrym(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_DRYM)));
        entry.setVendmachine(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_VENDMACHINE)));
        entry.setOpentime(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_OPENTIME)));
        entry.setOpenseason(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_OPENSEASON)));
        entry.setReservation(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_RESERVATION)));
        entry.setCityid(cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_CITYID)));
        entry.setInternet(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_INTERNET)));
        entry.setInternetcost(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_INTERNETCOST)));
        entry.setWifi(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_WIFI)));
        entry.setBike(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_BIKE)));
        entry.setLatitude(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.COLUMN_LATITUDE)));
        entry.setLongitude(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.COLUMN_LONGITUDE)));
        entry.setLink(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_LINK)));
        entry.setEmail(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_EMAIL)));
        return entry;
    }


    private DBItem cursorToSightsee(Cursor cursor){
        DBItem entry=new DBItem();
        entry.setId(cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_ID)));
        entry.setSightseename(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_SIGHTSEENAME)));
        entry.setCityid(cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_CITYID)));
        entry.setAddress(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_ADDRESS)));
        entry.setLatitude(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.COLUMN_LATITUDE)));
        entry.setLongitude(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.COLUMN_LONGITUDE)));
        entry.setImage(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_IMAGE)));
        entry.setDescription(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_DESC)));
        entry.setSource(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_SOURCE)));
        return entry;
    }
}
