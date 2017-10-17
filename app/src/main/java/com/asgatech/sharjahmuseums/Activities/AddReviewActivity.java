package com.asgatech.sharjahmuseums.Activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.asgatech.sharjahmuseums.Models.AddReviewRequest;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.EditText;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewBold;
import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import okhttp3.ResponseBody;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_add_review);
        setToolBar();
        setUpView();
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarHomeImageView = findViewById(R.id.toolbar_home_image_view);
        toolbarHomeImageView.setVisibility(View.VISIBLE);
        museumColor = getIntent().getStringExtra(ConstantUtils.MUSEUM_COLOR);
        Drawable background = toolbar.getBackground();
        if (museumColor != null) {
            Log.e("museumColor", museumColor);
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
        tvReview = findViewById(R.id.tv_review);
        barReviewStars = findViewById(R.id.bar_review_stars);
        postButton = findViewById(R.id.btn_post);
        postButton.setOnClickListener(this);

        Drawable background = postButton.getBackground();
        if (museumColor != null) {
            Log.e("museumColor", museumColor);
            background.setColorFilter(Color.parseColor(museumColor), PorterDuff.Mode.SRC_IN);
        }

        barReviewStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rateValue = barReviewStars.getRating();
                // System.out.println("Rate for Module is"+rateValue);
                switch ((int) rateValue) {
                    case 1:
                        rateValue = 20;
                        break;
                    case 2:
                        rateValue = 40;
                        break;
                    case 3:
                        rateValue = 60;
                        break;
                    case 4:
                        rateValue = 80;
                        break;
                    case 5:
                        rateValue = 100;
                        break;


                }
                Log.e("Rate for Module is", String.valueOf(rateValue));
            }
        });


    }

    private void AddReview(AddReviewRequest addReviewRequest) {
        ServerTool.AddReview(this, addReviewRequest, new ServerTool.APICallBack<Integer>() {
            @Override
            public void onSuccess(Integer response) {
                if (response == 1) {
                    Toast.makeText(AddReviewActivity.this, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddReviewActivity.this, "failure to send review", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {

            }
        });

    }
    //    GetReviewList

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
        new Localization().setLanguage(AddReviewActivity.this, new UserData().getLocalization(AddReviewActivity.this));
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_post:

                if (getIntent().hasExtra(ConstantUtils.EXTRA_MUSEUMS_ID)) {
                    museumsID = getIntent().getIntExtra((ConstantUtils.EXTRA_MUSEUMS_ID), 0);
                    if (museumsID > 0) {
                        AddReviewRequest addReviewRequest = new AddReviewRequest(tvEmail.getText().toString(), tvReview.getText().toString(), (int) rateValue, museumsID);
                        AddReview(addReviewRequest);
                    }
                }
//                    if (nameEditText.getText()==null || nameEditText.getText().toString().trim().equals("")){
//                        nameEditText.setError("Name required");
//                        return;
//                    }
//                    if (phoneEditText.getText()==null || phoneEditText.getText().toString().trim().equals("")){
//                        phoneEditText.setError("phone required");
//                        return;
//                    }
//                    if (emailEditText.getText()==null || emailEditText.getText().toString().trim().equals("")){
//                        emailEditText.setError("email required");
//                        return;
//                    }
//                    if (messageEditText.getText()==null || messageEditText.getText().toString().trim().equals("")){
//                        messageEditText.setError("message required");
//                        return;
//                    }
//                    AddReview()
                break;
        }
    }

}
