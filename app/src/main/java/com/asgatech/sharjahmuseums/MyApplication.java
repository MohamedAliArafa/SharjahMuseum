package com.asgatech.sharjahmuseums;

import android.app.Application;
import android.util.Log;

import com.asgatech.sharjahmuseums.Models.EventModel;
import com.asgatech.sharjahmuseums.Models.InsertDeviceTokenRequestModel;
import com.asgatech.sharjahmuseums.Models.MuseumsDetailsModel;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;
import com.scand.realmbrowser.RealmBrowser;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.ResponseBody;

/**
 * Created by mohamed.arafa on 10/17/2017.
 */

public class MyApplication extends Application {
    UserData mUserData;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);

                new RealmBrowser.Builder(this)
                        // add class, you want to view
                        .add(Realm.getDefaultInstance(), MuseumsDetailsModel.class)
                        .add(Realm.getDefaultInstance(), EventModel.class)
                        // call method showNotification()
                        .showNotification();
        mUserData = new UserData();
        if (!mUserData.getUserStateOfInsertToken(this)) {
            insertDeviceToken(Utils.insertDeviceToken(getApplicationContext()));
        }
    }

    private void insertDeviceToken(InsertDeviceTokenRequestModel insertDevicetokenRequestModel) {
        ServerTool.InsertDeviceToken(this, insertDevicetokenRequestModel, new ServerTool.APICallBack<Integer>() {
            @Override
            public void onSuccess(Integer response) {
                if (response == 1) {
                    Log.e("insertDeviceToken", "Success");
                    mUserData.saveUserStateOfInsertToken(getApplicationContext(), true, mUserData.TAG_INSERT_TOKEN);

                } else {
                    Log.e("insertDeviceToken", "failure to insert token");
//                    Toast.makeText(HomeActivity.this, "failure to insert token", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {

            }
        });

    }
}
