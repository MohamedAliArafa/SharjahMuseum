package com.asgatech.sharjahmuseums.Activities;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.Fragments.HighLightOverlapingFragment;
import com.asgatech.sharjahmuseums.Fragments.HighLightPagerFragment;
import com.asgatech.sharjahmuseums.Models.MuseumsDetailsModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.ArrayList;

import me.crosswall.lib.coverflow.core.PageItemClickListener;

public class HightLightDetailActivity extends AppCompatActivity {
    public ImageView toolbarHomeImageView;
    ViewPager imagesViewPager;
    ArrayList<MuseumsDetailsModel.HightLightEntity> highlightList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_hight_light_detail);
        setToolBar();
        setUpView();

    }

    public void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarHomeImageView = (ImageView) findViewById(R.id.toolbar_home_image_view);
        toolbarHomeImageView.setVisibility(View.VISIBLE);
        toolbarHomeImageView.setImageResource(R.drawable.ic_close_white);
        toolbarHomeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_sahre_white);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    void setUpView() {
        imagesViewPager = (ViewPager) findViewById(R.id.images_view_pager);
        highlightList=getIntent().getParcelableArrayListExtra(ConstantUtils.HIGHLIGHT_LIST);


        FragmentManager fm = getSupportFragmentManager();
        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(fm,highlightList);
        imagesViewPager.setAdapter(pagerAdapter);

    }
    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        ArrayList<MuseumsDetailsModel.HightLightEntity> highlightList;

        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<MuseumsDetailsModel.HightLightEntity> highlightList) {
            super(fm);
            this.highlightList = highlightList;
        }

        @Override
        public Fragment getItem(int position) {
            return HighLightPagerFragment.newInstance(highlightList.get(position).getPhoto(), highlightList);
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
        new Localization().setLanguage(HightLightDetailActivity.this, new UserData().getLocalization(HightLightDetailActivity.this));
        super.onResume();
    }
}
