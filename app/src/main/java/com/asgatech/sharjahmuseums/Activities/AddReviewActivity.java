package com.asgatech.sharjahmuseums.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.asgatech.sharjahmuseums.Activities.Home.HomeActivity;
import com.asgatech.sharjahmuseums.Models.Request.AddReviewRequest;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.EditText;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewBold;
import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;
import com.asgatech.sharjahmuseums.Tools.ValidationTool;

import okhttp3.ResponseBody;

import static com.asgatech.sharjahmuseums.Tools.AndroidDialogTools.customToastView;

public class AddReviewActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView toolbarHomeImageView;
    private EditText tvEmail;
    private EditText tvReview;
    private Button postButton;
    private RatingBar barReviewStars;
    private int museumsID;
    float rateValue;
    private TextViewBold ToolbarTitleTextView;
    private String museumColor;
    private ValidationTool validationTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_add_review);
        validationTool = new ValidationTool(this);
        setToolBar();
        setUpView();
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleMarginStart(-8);
        toolbarHomeImageView = findViewById(R.id.toolbar_home_image_view);
        toolbarHomeImageView.setVisibility(View.VISIBLE);
        toolbarHomeImageView.setOnClickListener(this);

        museumColor = getIntent().getStringExtra(ConstantUtils.MUSEUM_COLOR);
        Drawable background = toolbar.getBackground();
        if (museumColor != null) {
            Log.e(getString(R.string.tag_musmeum_color), museumColor);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Utils.getDarkColor(Color.parseColor(museumColor)));
            }
            background.setColorFilter(Color.parseColor(museumColor), PorterDuff.Mode.SRC_IN);
        }
        ToolbarTitleTextView = findViewById(R.id.tv_toolbar_title);
        ToolbarTitleTextView.setText(getString(R.string.add_review));

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setUpView() {

        tvEmail = findViewById(R.id.tv_email);
        tvEmail.setMovementMethod(new ScrollingMovementMethod());

        tvReview = findViewById(R.id.tv_review);
        tvReview.setMovementMethod(new ScrollingMovementMethod());

        barReviewStars = findViewById(R.id.bar_review_stars);
//        barReviewStars.setStarsSeparation(20, Dimension.DP);
        postButton = findViewById(R.id.btn_post);
        postButton.setOnClickListener(this);

        Drawable background = postButton.getBackground();
        if (museumColor != null) {
            Log.e(getString(R.string.tag_musmeum_color), museumColor);
            background.setColorFilter(Color.parseColor(museumColor), PorterDuff.Mode.SRC_IN);
        }

        barReviewStars.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            rateValue = barReviewStars.getRating();
            // System.out.println("Rate for Module is"+rateValue);
            switch ((int) rateValue) {
                case 1:
                    rateValue = 1;
                    break;
                case 2:
                    rateValue = 2;
                    break;
                case 3:
                    rateValue = 3;
                    break;
                case 4:
                    rateValue = 4;
                    break;
                case 5:
                    rateValue = 5;
                    break;
            }
            Log.e("Rate for Module is", String.valueOf(rateValue));
        });


    }

    private void AddReview(AddReviewRequest addReviewRequest) {
        ServerTool.AddReview(this, addReviewRequest, new ServerTool.APICallBack<Integer>() {
            @Override
            public void onSuccess(Integer response) {
                if (response == 1) {
                    customToastView(AddReviewActivity.this, getResources().getString(R.string.success_send_review));
//                    Toast.makeText(AddReviewActivity.this, getString(R.string.success_send_review), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    customToastView(AddReviewActivity.this, getResources().getString(R.string.failed_send_review));

//                    Toast.makeText(AddReviewActivity.this, getString(R.string.failed_send_review), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
//                new ErrorDialog().showDialog(AddReviewActivity.this);

            }
        });

    }

    void emptyFields() {
        tvEmail.setText(" ");
        tvReview.setText(" ");
        barReviewStars.setRating(0);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Utils.hideKeypad(this);
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onResume() {
        Localization.setLanguage(AddReviewActivity.this, UserData.getLocalization(AddReviewActivity.this));
        super.onResume();
    }

    private boolean isValid() {
        boolean validName = validationTool.validateEmail(tvEmail, getString(R.string.invalid_email));
        boolean validMail = validationTool.validateRequiredField(tvReview, getString(R.string.messeage_hint));
        return validMail && validName;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_post:
                if (Utils.isNetworkAvailable(AddReviewActivity.this))
                    if (isValid())
                        if (barReviewStars.getRating() > 0)
                            if (getIntent().hasExtra(ConstantUtils.EXTRA_MUSEUMS_ID)) {
                                museumsID = getIntent().getIntExtra((ConstantUtils.EXTRA_MUSEUMS_ID), 0);
                                if (museumsID > 0) {
                                    AddReviewRequest addReviewRequest = new AddReviewRequest(tvEmail.getText().toString(), tvReview.getText().toString(), (int) rateValue, museumsID);
                                    AddReview(addReviewRequest);
                                }
                            } else
                                Toast.makeText(this, R.string.rate_error_hint, Toast.LENGTH_SHORT).show();
                break;


            case R.id.toolbar_home_image_view:
                Intent in = new Intent(this, HomeActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                break;
        }
    }

}
