package com.asgatech.sharjahmuseums.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;

public class SplashActivity extends AppCompatActivity {

    private static final int TIME_SPLASH_CLOSE = 10000;//millis
    private static final int TIME_ANIMATION_DURATION = 3000;//millis
    private static final int TIME_ANIMATION_START = 500;//millis

    private ImageView SplashImageView;
    private UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        userData = new UserData();
        setupViews();
    }

    private void setupViews() {
        SplashImageView = (ImageView) findViewById(R.id.splash_image_view);
        animateFadeIN(SplashImageView);
        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(TIME_ANIMATION_DURATION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                }
            }
        };
        timer.start();
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
        if (userData.getLocalization(SplashActivity.this) == -1) { // no found lang before .. first set up application
            if (new Localization().getDefaultLocal(SplashActivity.this) == Localization.ARABIC_VALUE) { //RTL
                userData.saveLocalization(SplashActivity.this, Localization.ARABIC_VALUE);
            } else {
                userData.saveLocalization(SplashActivity.this, Localization.ENGLISH_VALUE);
            }
        }
        new Localization().setLanguage(SplashActivity.this, userData.getLocalization(SplashActivity.this));
        super.onResume();
    }

}
