package com.asgatech.sharjahmuseums.Activities;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.asgatech.sharjahmuseums.Fragments.HighLightOverlapingFragment;
import com.asgatech.sharjahmuseums.Models.MuseumsDetailsModel;
import com.asgatech.sharjahmuseums.R;

import java.util.ArrayList;

import java.util.Timer;
import java.util.TimerTask;

import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PagerContainer;

public class HighLightOverlapingActivity extends AppCompatActivity {
    int currentPage = 0;
    int NUM_PAGES = 0;
    ArrayList<MuseumsDetailsModel.HightLightEntity> highlightList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_light_overlaping);
        setUpView();
    }

    void setUpView() {
        PagerContainer pagerContainer = (PagerContainer) findViewById(R.id.pager_container);
        if (pagerContainer != null) {
            pagerContainer.setOverlapEnabled(true);

        }
        if (getIntent().hasExtra("highlightList")) {
            highlightList = getIntent().getParcelableArrayListExtra("highlightList");
        }
        final ViewPager viewPager = pagerContainer.getViewPager();
        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        viewPager.setAdapter(pagerAdapter);

        NUM_PAGES = highlightList.size();
        autoStartOfViewPager(NUM_PAGES, viewPager);

    }

    void autoStartOfViewPager(final int NUM_PAGES, final ViewPager viewPager) {

// Auto start of viewpager
        final Handler handler;
        handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {

                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);


        new CoverFlow.Builder().with(viewPager)
                .scale(0.3f)
                .pagerMargin(R.dimen.overlap)
                .spaceSize(0f)
                .rotationY(0f)
                .build();
//<dimen name="overlap_pager_margin">-60dp</dimen>
        //Manually setting the first View to be elevated
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                Fragment fragment = (Fragment) viewPager.getAdapter().instantiateItem(viewPager, 0);
                ViewCompat.setElevation(fragment.getView(), 8.0f);
            }
        });
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return HighLightOverlapingFragment.newInstance(String.valueOf(highlightList.get(position)));
        }

        @Override
        public int getCount() {
            return highlightList.size();
        }
    }
}
