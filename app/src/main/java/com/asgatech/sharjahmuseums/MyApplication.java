package com.asgatech.sharjahmuseums;

import android.app.Application;
import android.util.Log;

import com.asgatech.sharjahmuseums.Models.InsertDevicetokenRequestModel;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import okhttp3.ResponseBody;

/**
 * Created by mohamed.arafa on 10/17/2017.
 */

public class MyApplication extends Application {
    UserData mUserData;

    @Override
    public void onCreate() {
        super.onCreate();
        mUserData = new UserData();
        if (!mUserData.getUserStateOfInsertToken(this)) {
            insertDeviceToken(Utils.insertDeviceToken(getApplicationContext()));
        }
    }

    private void insertDeviceToken(InsertDevicetokenRequestModel insertDevicetokenRequestModel) {
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
