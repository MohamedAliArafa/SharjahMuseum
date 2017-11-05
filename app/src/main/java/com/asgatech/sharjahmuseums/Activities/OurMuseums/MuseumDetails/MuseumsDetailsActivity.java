package com.asgatech.sharjahmuseums.Activities.OurMuseums.MuseumDetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.asgatech.sharjahmuseums.Activities.Home.HomeActivity;
import com.asgatech.sharjahmuseums.Activities.ViewLocationMapActivity;
import com.asgatech.sharjahmuseums.Activities.ViewVisitorsReviewActivity;
import com.asgatech.sharjahmuseums.Adapters.FacilitiesAdapter;
import com.asgatech.sharjahmuseums.Adapters.HomeSliderImagesAdapter;
import com.asgatech.sharjahmuseums.Adapters.TextAdapter;
import com.asgatech.sharjahmuseums.Models.HighLightEntity;
import com.asgatech.sharjahmuseums.Models.MuseumsDetailsModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewBold;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewLight;
import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.PermissionTool;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;
import com.booking.rtlviewpager.RtlViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PagerContainer;

public class MuseumsDetailsActivity extends AppCompatActivity implements View.OnClickListener, MuseumDetailsContract.ModelView {

    private int mMuseumID;
    int NUM_PAGES2;
    String mMuseumTelephoneNum, mMuseumEmail, mMuseumTitle, mMuseumColor, mMuseumsImage;
    double mLongitude, mLatitude;
    private List<ImageView> mPagerDotList;
    private int mCurrentPage = 0;

    ImageView mSingleDotImageView;
    @BindView(R.id.toolbar_home_image_view)
    ImageView mToolbarHomeImageView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.images_view_pager)
    RtlViewPager mSliderViewPager;
    @BindView(R.id.layout_dots)
    LinearLayout mDotsLayout;
    @BindView(R.id.action_container_ll)
    LinearLayout mActionsLinear;
    @BindView(R.id.add_review_linear)
    ImageView mAddReviewLinear;
    @BindView(R.id.location_linear)
    ImageView mLocationLinear;
    @BindView(R.id.call_linear)
    ImageView callLinear;
    @BindView(R.id.mail_linear)
    ImageView mailLinear;
    @BindView(R.id.share_linear)
    ImageView shareLinear;
    @BindView(R.id.about_museums_text_view)
    TextViewLight aboutMuseumsTextView;
    @BindView(R.id.opening_hours_list)
    RecyclerView mOpeningHoursList;
    @BindView(R.id.entry_fees_list)
    RecyclerView mEntryFeesList;
    @BindView(R.id.facilities_list)
    RecyclerView mFacilitiesList;
    @BindView(R.id.tv_toolbar_title)
    TextViewBold ToolbarTitleTextView;
    @BindView(R.id.iv_line1)
    ImageView ivLine1;
    @BindView(R.id.iv_line2)
    ImageView ivLine2;
    @BindView(R.id.pager_container)
    PagerContainer pagerContainer;

    private Handler mHandler;
    private Runnable mRunnable;

    HomeSliderImagesAdapter imagesAdapter;
    private MuseumsDetailsModel mMuseum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_museums_details);
        ButterKnife.bind(this);
        mHandler = new Handler();
        mMuseumID = getIntent().getIntExtra((ConstantUtils.EXTRA_MUSEUMS_ID), 0);
        new MuseumDetailsPresenter(this, this, getLifecycle(), mMuseumID);
        mEntryFeesList.setLayoutManager(new LinearLayoutManager(this));
        mOpeningHoursList.setLayoutManager(new LinearLayoutManager(this));
        mFacilitiesList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
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
        Localization.setLanguage(MuseumsDetailsActivity.this, UserData.getLocalization(MuseumsDetailsActivity.this));
        super.onResume();
    }

    public void addDots(int size) {
        mPagerDotList = new ArrayList<>();
        mDotsLayout.removeAllViews();
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                if (mSingleDotImageView == null) {
                    mSingleDotImageView = new ImageView(this);
                    mSingleDotImageView.setImageDrawable(getResources().getDrawable(R.drawable.dot_slider_unactive));

                } else {
                    mSingleDotImageView = new ImageView(this);
                    mSingleDotImageView.setImageDrawable(getResources().getDrawable(R.drawable.dot_slider_unactive));
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(4, 2, 4, 2);
                mDotsLayout.addView(mSingleDotImageView, params);
                mDotsLayout.getChildCount();
                mPagerDotList.add(mSingleDotImageView);
            }

        }

    }

    @SuppressLint("ResourceAsColor")
    public void selectDot(int idx, int size) {
        try {
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    int drawableId = (i == idx) ? Color.parseColor(mMuseumColor) : (R.color.colorPrimary);
                    mPagerDotList.get(i).setBackgroundColor(drawableId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void autoStartOfViewPager(final int NUM_PAGES2, final ViewPager viewPager) {
        if (NUM_PAGES2 > 2) {
            try {
                viewPager.setCurrentItem(1, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        new CoverFlow.Builder().with(viewPager)
                .scale(0.3f)
                .pagerMargin(-60)
                .spaceSize(2f)
                .rotationY(0f)
                .build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRunnable != null) mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.add_review_linear:
                Intent intent = new Intent(new Intent(this, ViewVisitorsReviewActivity.class));
                intent.putExtra(ConstantUtils.EXTRA_MUSEUMS_ID, mMuseumID);
                intent.putExtra(ConstantUtils.MUSEUM_COLOR, mMuseumColor);
                startActivity(intent);
                break;
            case R.id.location_linear:
                Intent intent1 = new Intent(MuseumsDetailsActivity.this, ViewLocationMapActivity.class);
                intent1.putExtra(ConstantUtils.EXTRA_MUSEUMS_lATITUDE, mLatitude);
                intent1.putExtra(ConstantUtils.EXTRA_MUSEUMS_LONGTUDE, mLongitude);
                startActivity(intent1);
                break;
            case R.id.call_linear:
                if (mMuseumTelephoneNum != null) {
                    if (PermissionTool.checkPermission(this, PermissionTool.PERMISSION_PHONE_CALL)) {
                        String uri = "tel:" + mMuseumTelephoneNum.trim();
                        Intent intent2 = new Intent(Intent.ACTION_CALL);
                        intent2.setData(Uri.parse(uri));
                        startActivity(intent2);
                    }
                }
                break;
            case R.id.mail_linear:
                if (!mMuseumEmail.isEmpty()) {
                    Intent mailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", mMuseumEmail, null));
                    Log.e("mail", mMuseumEmail);
                    mailIntent.putExtra(Intent.EXTRA_SUBJECT, mMuseum.getTitle());
                    startActivity(Intent.createChooser(mailIntent, ""));
                }

                break;
            case R.id.share_linear:
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType("text/plain");
                intentShare.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                intentShare.putExtra(Intent.EXTRA_TEXT, mMuseum.getUrl() + "\n" +
                        "\n" + getResources().getString(R.string.about_museums) + ":" + aboutMuseumsTextView.getText().toString() + "\n" +
                        mMuseum.getUrl());
                Intent chooser = Intent.createChooser(intentShare, getString(R.string.title_share_via));
                chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(chooser);

                break;

            case R.id.toolbar_home_image_view:
                Intent in = new Intent(this, HomeActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                break;
        }
    }

    @Override
    public void updateUI(MuseumsDetailsModel model) {
        mSingleDotImageView = null;
        mPagerDotList = null;

        mMuseum = model;
        mMuseumTitle = model.getTitle();
        mMuseumColor = model.getColor();

        setSupportActionBar(mToolbar);
        mToolbarHomeImageView.setVisibility(View.VISIBLE);
        mToolbarHomeImageView.setOnClickListener(this);
        ToolbarTitleTextView.setText(model.getTitle());
        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (mMuseumColor != null) {
            Drawable background = mToolbar.getBackground();
            background.setColorFilter(Color.parseColor(model.getColor()), PorterDuff.Mode.SRC_IN);
        }

        if (mMuseumColor != null) {
            Drawable background = ivLine1.getBackground();
            background.setColorFilter(Color.parseColor(mMuseumColor), PorterDuff.Mode.SRC_IN);
        }

        if (mMuseumColor != null) {
            Drawable background = ivLine1.getBackground();
            background.setColorFilter(Color.parseColor(mMuseumColor), PorterDuff.Mode.SRC_IN);
        }

//        if (mMuseumColor != null) {
//            int count = mActionsLinear.getChildCount();
//            for (int i = 0; i < count; i++) {
//                ImageView view = (ImageView) mActionsLinear.getChildAt(i);
//                view.getDrawable().setTint(Color.parseColor(mMuseumColor));
//            }
//        }

        if (mMuseumColor != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Utils.getDarkColor(Color.parseColor(mMuseumColor)));
            }
            Drawable background = ivLine2.getBackground();
            background.setColorFilter(Color.parseColor(mMuseumColor), PorterDuff.Mode.SRC_IN);
        }

        mAddReviewLinear.setOnClickListener(this);
        mLocationLinear.setOnClickListener(this);
        callLinear.setOnClickListener(this);
        mailLinear.setOnClickListener(this);
        shareLinear.setOnClickListener(this);

        aboutMuseumsTextView.setText(model.getAbout());
        if (Utils.validList(model.getOpeningHoursList())) {
            TextAdapter textAdapter = new TextAdapter(this, model.getOpeningHoursList(), 1); // type to opening hours
            mOpeningHoursList.setAdapter(textAdapter);
        }
        if (Utils.validList(model.getPriceCategorySublist())) {
            TextAdapter textAdapter = new TextAdapter(model.getPriceCategorySublist(), this, 2); // type to entry fees
            mEntryFeesList.setAdapter(textAdapter);
        }
        if (Utils.validList(model.getFacilts())) {
            FacilitiesAdapter facilitiesAdapter = new FacilitiesAdapter(this, model.getFacilts()); // type to entry fees
            mFacilitiesList.setAdapter(facilitiesAdapter);
        }
        if (Utils.validList(model.getImageList())) {
            addDots(model.getImageList().size());
            selectDot(0, model.getImageList().size());

            imagesAdapter = new HomeSliderImagesAdapter(model.getImageList(), this, 2);
            mSliderViewPager.setAdapter(imagesAdapter);
            int NUM_PAGES = model.getImageList().size();
            Handler handler = new Handler();
            Runnable Update = () -> {
                if (mCurrentPage == NUM_PAGES - 1) {
                    mCurrentPage = 0;
                }
                mSliderViewPager.setCurrentItem(mCurrentPage++, true);
            };

            Timer timer = new Timer(); // This will create a new Thread
            timer.schedule(new TimerTask() { // task to be scheduled
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 500, 2000);

            mSliderViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    mCurrentPage = position;
                }

                @Override
                public void onPageSelected(int position) {
                    try {
                        selectDot(position, model.getImageList().size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }

        if (Utils.validList(model.getHightLight())) {

            if (pagerContainer != null) {
                pagerContainer.setOverlapEnabled(true);
            }

            final ViewPager viewPager = pagerContainer != null ? pagerContainer.getViewPager() : null;
            MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), model.getHightLight());
            if (viewPager != null) {
                viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
            }
            if (viewPager != null) {
                viewPager.setAdapter(pagerAdapter);
            }
//            Toast.makeText(MuseumsDetailsActivity.this,"hshshsh",Toast.LENGTH_LONG).show();
            NUM_PAGES2 = model.getHightLight().size();
            autoStartOfViewPager(NUM_PAGES2, viewPager);
        }

        mLatitude = model.getLat();
        mLongitude = model.getLong();
        mMuseumTelephoneNum = model.getPhone();
        mMuseumEmail = model.getEamil();

    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        List<HighLightEntity> highlightList;

        MyFragmentPagerAdapter(FragmentManager fm, List<HighLightEntity> highlightList) {
            super(fm);
            this.highlightList = highlightList;
        }

        @Override
        public Fragment getItem(int position) {
            return HighLightOverlappingFragment.newInstance(highlightList.get(position).getPhoto(),mMuseumColor, highlightList, position);
        }

        @Override
        public int getCount() {
            return highlightList.size();
        }

    }
}
