package com.asgatech.sharjahmuseums.Tools.DataBaseHandler;

import android.provider.BaseColumns;

/**
 * Created by halima.reda on 11/13/2017.
 */
public final class DBContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DBContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class UserDates implements BaseColumns {
        public static final String TABLE_NAME = "GEO";
        public static final String TITLE = "title";
        public static final String LAT = "lat";
        public static final String LANG = "lang";


        private static final String SQL_DELETE_CALNDER =
                "DROP TABLE IF EXISTS " + UserDates.TABLE_NAME;
        private static final String TAG = "UserDates";


    }


}
