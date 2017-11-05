package com.asgatech.sharjahmuseums;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by mohamed.arafa on 10/17/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);

//        new RealmBrowser.Builder(this)
//                // add class, you want to view
//                .add(Realm.getDefaultInstance(), MuseumsDetailsModel.class)
//                .add(Realm.getDefaultInstance(), EventModel.class)
//                .add(Realm.getDefaultInstance(), ImageListEntity.class)
//                .add(Realm.getDefaultInstance(), OpeningHoursListEntity.class)
//                .add(Realm.getDefaultInstance(), FaciltsEntity.class)
//                .add(Realm.getDefaultInstance(), PriceCategorySublistEntity.class)
//                .add(Realm.getDefaultInstance(), HighLightEntity.class)
//                // call method showNotification()
//                .showNotification();
    }
}
