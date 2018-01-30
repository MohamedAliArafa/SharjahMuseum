package com.asgatech.sharjahmuseums.Activities.OurMuseums.MuseumDetails.HighlightDetails;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Models.HighLightEntity;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.GlideApp;
import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class HighlightsDetailActivity extends AppCompatActivity {
    ImageView mToolbarHomeImageView;
    TextView mToolbarTitleTextView, titleTextView, descTextView;
    ViewPager mImagesViewPager;
    ImageView coverImageView;
    ArrayList<HighLightEntity> mHighlightList;
    private int mPosition;
    private String mColor, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_hight_light_detail);
        setToolBar();
        setUpView();
    }

    public void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleMarginStart(-8);
        mToolbarHomeImageView = findViewById(R.id.toolbar_home_image_view);
        mToolbarTitleTextView = findViewById(R.id.tv_toolbar_title);
        mToolbarTitleTextView.setText(getString(R.string.high_lights));
        mToolbarHomeImageView.setVisibility(View.VISIBLE);
        mToolbarHomeImageView.setImageResource(R.drawable.ic_close_white);
        mToolbarHomeImageView.setOnClickListener(view -> onBackPressed());
        mColor = getIntent().getStringExtra(ConstantUtils.HIGHLIGHT_COLOR);
        url = getIntent().getStringExtra(ConstantUtils.HIGHLIGHT_URL);
        if (mColor != null) {
            Drawable background = toolbar.getBackground();
            background.setColorFilter(Color.parseColor(mColor), PorterDuff.Mode.SRC_IN);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Utils.getDarkColor(Color.parseColor(mColor)));
            }
        }
        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_sahre_white);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    void setUpView() {
        mHighlightList = getIntent().getParcelableArrayListExtra(ConstantUtils.HIGHLIGHT_LIST);
        mPosition = getIntent().getIntExtra(ConstantUtils.HIGHLIGHT_LIST_POSITION, 0);
        Log.e("position", mPosition + "");
        coverImageView = findViewById(R.id.image_cover);
        titleTextView = findViewById(R.id.tv_title);
        ImageView imageRight = findViewById(R.id.imageright);
        ImageView imageLeft = findViewById(R.id.imageleft);
        descTextView = findViewById(R.id.tv_text_desc);
        setData();
        imageRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPosition == mHighlightList.size() - 1) {
                    mPosition = 0;
                } else {
                    if (mHighlightList.size() > 1) {
                        mPosition++;
                    }
                }
                setData();

            }
        });
        imageLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPosition == mHighlightList.size() - 1) {
                    if (mHighlightList.size() > 1) {
                        mPosition--;
                    }
                } else {
                    if (mPosition == 0) {
                        mPosition = mHighlightList.size() - 1;
                    } else {
                        mPosition = 0;
                    }

                }
                setData();

            }
        });


//        FragmentManager fm = getSupportFragmentManager();
//        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(fm, mHighlightList);
//        mImagesViewPager.setAdapter(pagerAdapter);
//        mImagesViewPager.setCurrentItem(mPosition);
    }

    private void setData() {
        titleTextView.setText(mHighlightList.get(mPosition).getTitle());
        descTextView.setText(mHighlightList.get(mPosition).getText());
        GlideApp.with(this)
                .load(URLS.URL_BASE + mHighlightList.get(mPosition).getPhoto()).placeholder(R.drawable.no_image)
                .apply(RequestOptions.option(Option.memory(ConstantUtils.GLIDE_TIMEOUT), 0))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into((coverImageView));
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        ArrayList<HighLightEntity> highlightList;

        MyFragmentPagerAdapter(FragmentManager fm, ArrayList<HighLightEntity> highlightList) {
            super(fm);
            this.highlightList = highlightList;
        }

        @Override
        public Fragment getItem(int position) {
            return HighLightPagerFragment.newInstance(highlightList.get(position).getPhoto(), highlightList.get(position));
        }

        @Override
        public int getCount() {
            return highlightList.size();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //icon share to
//                HighLightEntity entity = mHighlightList.get(mImagesViewPager.getCurrentItem());
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType("text/plain");
//                intentShare.putExtra(Intent.EXTRA_TEXT, mHighlightList.get(mPosition).getTitle() + "\n" +
//                        "\n" + getResources().getString(R.string.description) + ":" + mHighlightList.get(mPosition).getText()
//                        + "\n" + URLS.URL_BASE + mHighlightList.get(mPosition).getPhoto());
                Log.e("getPackage", getApplicationContext().getPackageName() + "");
                intentShare.putExtra(Intent.EXTRA_TEXT, mHighlightList.get(mPosition).getTitle());
//                + "\n" + url + "\n" + "https://play.google.com/store/apps/details?id=" +
//                        getApplicationContext().getPackageName() + "&hl=en");
                Intent chooser = Intent.createChooser(intentShare, getString(R.string.title_share_via));
                chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(chooser);
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
        Localization.setLanguage(HighlightsDetailActivity.this, UserData.getLocalization(HighlightsDetailActivity.this));
        super.onResume();
    }
}
