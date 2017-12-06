package com.asgatech.sharjahmuseums.Tools.FCM;

import android.provider.Settings;
import android.util.Log;

import com.asgatech.sharjahmuseums.Models.Request.InsertDeviceTokenRequestModel;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import okhttp3.ResponseBody;

/*
 * Created by mohamed.arafa on 10/25/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        String androidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        InsertDeviceTokenRequestModel insertDeviceTokenRequestModel = new InsertDeviceTokenRequestModel(androidID, token);
        insertDeviceToken(insertDeviceTokenRequestModel);

    }

    private void insertDeviceToken(InsertDeviceTokenRequestModel insertDevicetokenRequestModel) {
        ServerTool.InsertDeviceToken(insertDevicetokenRequestModel, new ServerTool.APICallBack<Integer>() {
            @Override
            public void onSuccess(Integer response) {
                if (response == 1) {
                    Log.e("insertDeviceToken", "Success");
                    UserData.saveUserStateOfInsertToken(getApplicationContext(), true, UserData.TAG_INSERT_TOKEN);
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
