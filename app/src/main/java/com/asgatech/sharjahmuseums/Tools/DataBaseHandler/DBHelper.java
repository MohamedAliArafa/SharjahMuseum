package com.asgatech.sharjahmuseums.Tools.DataBaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.asgatech.sharjahmuseums.Models.InnerLocationModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by halima.reda on 11/13/2017.
 */
public class DBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SM";
    private static final String TAG = "DBHelper";


    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + DBContract.UserDates.TABLE_NAME + "(" + DBContract.UserDates._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + DBContract.UserDates.TITLE + " TEXT,"
            + DBContract.UserDates.LAT + " TEXT,"
            + DBContract.UserDates.LANG + " TEXT );";


    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.UserDates.TABLE_NAME);
    }

    /**
     * insert TicketsID into Calender table
     *
     * @param context
     **/
    public void insert_Date_SystemCalenderId_DB(Context context, List<InnerLocationModel> locationModels) {
        DBHelper mDbHelper = new DBHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i = 0; i < locationModels.size(); i++) {
            values.put(DBContract.UserDates.TITLE, locationModels.get(i).getTitle());
            values.put(DBContract.UserDates.LANG, locationModels.get(i).getLang());
            values.put(DBContract.UserDates.LAT, locationModels.get(i).getLat());

        }
        long newRowId;
        newRowId = db.insert(
                DBContract.UserDates.TABLE_NAME, null,
                values);
        Log.d(TAG, " newRowId " + newRowId);
        db.close();
    }

    public void DeleteFromSql(Context context) {

        DBHelper mDbHelper = new DBHelper(context);
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String Delete = "DELETE FROM " + DBContract.UserDates.TABLE_NAME;
        db.execSQL(Delete);
        db.close();


    }


    //return list from data   to get all date found in sql to test delet
    public List<InnerLocationModel> getAllCachedDates(Context context, List<InnerLocationModel> titles) {
        List<InnerLocationModel> locationModels = new ArrayList<>();
        DBHelper mDbHelper = new DBHelper(context);
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String retunList = null;
        for (int i = 0; i < titles.size(); i++) {
            retunList = "SELECT * FROM " + DBContract.UserDates.TABLE_NAME + " WHERE " + DBContract.UserDates.TITLE + " = " + "'" + titles.get(i).getTitle() + "'";
        }
        Cursor cursor = db.rawQuery(retunList, null);
        cursor.moveToFirst();
        int cc = cursor.getCount();
        if (cursor.getCount() > 0) {
            do {
                InnerLocationModel reservationModels = new InnerLocationModel();
                reservationModels.setLat(Double.parseDouble(cursor.getString(cursor.getColumnIndex(DBContract.UserDates.LAT))));
                reservationModels.setLang(Double.parseDouble(cursor.getString(cursor.getColumnIndex(DBContract.UserDates.LANG))));
                locationModels.add(reservationModels);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return locationModels;

    }


}
