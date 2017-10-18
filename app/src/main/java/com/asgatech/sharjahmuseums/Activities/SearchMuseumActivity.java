package com.asgatech.sharjahmuseums.Activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.Models.PlanYourVisitsModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewBold;
import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.List;

import okhttp3.ResponseBody;

public class SearchMuseumActivity extends AppCompatActivity {
    private TextViewBold ToolbarTitleTextView;
    private ImageView toolbarHomeImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_search_museum);

        setToolBar();
        setUpView();
    }
    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarHomeImageView = findViewById(R.id.toolbar_home_image_view);
        toolbarHomeImageView.setVisibility(View.VISIBLE);
//        toolbarHomeImageView.setOnClickListener(this);


        ToolbarTitleTextView = findViewById(R.id.tv_toolbar_title);
//        ToolbarTitleTextView.setText(getString(R.string.add_review));

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    void setUpView(){

        getALLMuseumCategray(new UserData().getLocalization(this));

    }

    void getALLMuseumCategray(int language) {
        ServerTool.getALLMuseumCategray(this, language, new ServerTool.APICallBack<List<PlanYourVisitsModel>>() {

            @Override
            public void onSuccess(List<PlanYourVisitsModel> response) {

                if (response.size()!=0){

                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {

            }
        });
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
        new Localization().setLanguage(SearchMuseumActivity.this, new UserData().getLocalization(SearchMuseumActivity.this));
        super.onResume();
    }
}
