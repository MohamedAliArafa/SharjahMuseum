package com.asgatech.sharjahmuseums.Activities.OurMuseums.MuseumDetails.HighlightDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Models.HighLightEntity;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.ArrayList;

public class HighlightsDetailActivity extends AppCompatActivity {
    ImageView mToolbarHomeImageView;
    TextView mToolbarTitleTextView;
    ViewPager mImagesViewPager;
    ArrayList<HighLightEntity> mHighlightList;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_hight_light_detail);
        setToolBar();
        setUpView();
    }

    public void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mToolbarHomeImageView = findViewById(R.id.toolbar_home_image_view);
        mToolbarTitleTextView = findViewById(R.id.tv_toolbar_title);
        mToolbarTitleTextView.setText(getString(R.string.title_activity_highlight_detail));
        mToolbarHomeImageView.setVisibility(View.VISIBLE);
        mToolbarHomeImageView.setImageResource(R.drawable.ic_close_white);
        mToolbarHomeImageView.setOnClickListener(view -> onBackPressed());
        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_sahre_white);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    void setUpView() {
        mImagesViewPager = findViewById(R.id.images_view_pager);
        mHighlightList = getIntent().getParcelableArrayListExtra(ConstantUtils.HIGHLIGHT_LIST);
        mPosition = getIntent().getIntExtra(ConstantUtils.HIGHLIGHT_LIST_POSITION, 0);
        FragmentManager fm = getSupportFragmentManager();
        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(fm, mHighlightList);
        mImagesViewPager.setAdapter(pagerAdapter);
        mImagesViewPager.setCurrentItem(mPosition);
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
                HighLightEntity entity = mHighlightList.get(mImagesViewPager.getCurrentItem());
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType("text/plain");
                intentShare.putExtra(Intent.EXTRA_TEXT, entity.getTitle() + "\n" +
                        "\n" + getResources().getString(R.string.description) + ":" + entity.getText()
                        + "\n" + URLS.URL_BASE + entity.getPhoto());
                Intent chooser = Intent.createChooser(intentShare, getString(R.string.title_share_via));
                chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(chooser);
//                onBackPressed();
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
