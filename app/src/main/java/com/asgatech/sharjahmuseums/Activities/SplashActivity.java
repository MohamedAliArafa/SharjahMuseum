package com.asgatech.sharjahmuseums.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.Activities.Home.HomeActivity;
import com.asgatech.sharjahmuseums.Models.Request.InsertDeviceTokenRequestModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import okhttp3.ResponseBody;

public class SplashActivity extends AppCompatActivity {

    private static final int TIME_SPLASH_CLOSE = 10000;//millis
    private static final int TIME_ANIMATION_DURATION = 3000;//millis
    private static final int TIME_ANIMATION_START = 500;//millis

    private ImageView SplashImageView;
    Runnable mRunnable;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        insertDeviceToken(Utils.insertDeviceToken(getApplicationContext()));
        setupViews();
    }

    private void setupViews() {
        SplashImageView = findViewById(R.id.splash_image_view);
        animateFadeIN(SplashImageView);
        mRunnable = () -> {
            /* Create an Intent that will start the Menu-Activity. */
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        };
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, TIME_ANIMATION_DURATION);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRunnable != null) mHandler.removeCallbacks(mRunnable);
    }

    private void animateFadeIN(ImageView imgv) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setStartOffset(TIME_ANIMATION_START);
        fadeIn.setDuration(TIME_ANIMATION_DURATION);
        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
        imgv.setAnimation(animation);
    }

    @Override
    protected void onResume() {
        if (UserData.getLocalization(SplashActivity.this) == -1) { // no found lang before .. first set up application
            if (Localization.getDefaultLocal(SplashActivity.this) == Localization.ARABIC_VALUE) { //RTL
                UserData.saveLocalization(SplashActivity.this, Localization.ARABIC_VALUE);
            } else {
                UserData.saveLocalization(SplashActivity.this, Localization.ENGLISH_VALUE);
            }
        }
        Localization.setLanguage(SplashActivity.this, UserData.getLocalization(SplashActivity.this));
        super.onResume();
    }

    private void insertDeviceToken(InsertDeviceTokenRequestModel insertDevicetokenRequestModel) {
        ServerTool.InsertDeviceToken(this, insertDevicetokenRequestModel, new ServerTool.APICallBack<Integer>() {
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
