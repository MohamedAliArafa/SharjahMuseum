package com.asgatech.sharjahmuseums.Tools.SharedTool;

import android.content.Context;


/**
 * Created by halima.reda on 8/28/2016.
 */
public class UserData {

    public String TAG_SAVE_LOGIN_DATA = "login_data";
    public String TAG_LANGUAGE_SCREEN = "language";
    public String TAG_SHOPPING_CART = "shopping_carts";
    public String TAG_LOCALIZATION = "localization";
    public String TAG_NOTIFICATION_STATE = "notification_state";

    public String TAG_CURRENCY = "Currency";
    public String TAG_USER_CODE = "user_code";
    public String TAG_USER_PASSWORD = "user_password";
    public String TAG_REMEMBER_ME = "remmeber_me";
    public String TAG_SAVE_PROFILE_INFO = "profile_info";

    public String TAG_INSERT_TOKEN= "insert_token";
    public Boolean TAG_TOKEN_INSERT_STATE= false;



    public void saveUserObject(Context context, Object myObject, boolean rememberMe) {
        SharedPreferencesTool.saveObject(context, TAG_SAVE_LOGIN_DATA, myObject);
        SharedPreferencesTool.setBoolean(context, rememberMe, TAG_REMEMBER_ME);
    }

//    public LoginResponse getUserObject(Context context) {
//        return SharedPreferencesTool.getObject(context, TAG_SAVE_LOGIN_DATA, LoginResponse.class);
//    }

    public boolean getRemmemberMe(Context context) {
        return SharedPreferencesTool.getBoolean(context, TAG_REMEMBER_ME);
    }

    public void saveRemmemberMe(Context context, boolean rememberMe) {
        SharedPreferencesTool.setBoolean(context, rememberMe, TAG_REMEMBER_ME);
    }

    public void saveLocalization(Context context, int value) {
        SharedPreferencesTool.setInt(context, TAG_LOCALIZATION, value);
    }

    public int getLocalization(Context context) {
        return SharedPreferencesTool.getInt(context, TAG_LOCALIZATION);
    }

    public void saveNotificationState(Context context, int value) {
        SharedPreferencesTool.setInt(context, TAG_NOTIFICATION_STATE, value);
    }
    public int getNotificationState(Context context) {
        return SharedPreferencesTool.getInt(context, TAG_NOTIFICATION_STATE);
    }

    public void saveUserCurrencyID(Context context, int value) {
        SharedPreferencesTool.setInt(context, TAG_CURRENCY, value);
    }

    public int getUserCurrencyID(Context context) {
        return SharedPreferencesTool.getInt(context, TAG_CURRENCY);
    }
    public void saveUserStateOfInsertToken(Context context, boolean state,String value) {
        SharedPreferencesTool.setBoolean(context, state, value);
    }
    public Boolean getUserStateOfInsertToken(Context context) {
        return SharedPreferencesTool.getBoolean(context, TAG_INSERT_TOKEN);
    }
    public void saveUserCode(Context context, String value) {
        SharedPreferencesTool.setString(context, TAG_USER_CODE, value);
    }

    public String getUserCode(Context context) {
        return SharedPreferencesTool.getString(context, TAG_USER_CODE);
    }

    public void saveUserPassword(Context context, String value) {
        SharedPreferencesTool.setString(context, TAG_USER_PASSWORD, value);
    }

    public String getUserPassword(Context context) {
        return SharedPreferencesTool.getString(context, TAG_USER_PASSWORD);
    }

    public void clearShared(Context context) {
        SharedPreferencesTool.clearObject(context);
    }

    public void setLanguageScreen(Context context, boolean isShown) {
        SharedPreferencesTool.setBoolean(context, isShown, TAG_LANGUAGE_SCREEN);
    }

    public boolean isLanguageShown(Context context) {
        return SharedPreferencesTool.getBooleanlang(context, TAG_LANGUAGE_SCREEN);
    }

    public void saveReadyShoppingCart(Context context, boolean isShown) {
        SharedPreferencesTool.setBoolean(context, isShown, TAG_SHOPPING_CART);
    }

    public boolean getReadyShoppingCart(Context context) {
        return SharedPreferencesTool.getBoolean(context, TAG_SHOPPING_CART);
    }
}
