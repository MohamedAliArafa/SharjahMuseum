package com.asgatech.sharjahmuseums.Activities.OurMuseums.MuseumDetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
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
import android.support.v4.view.ViewCompat;
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
import com.asgatech.sharjahmuseums.Adapters.FaciltsAdapter;
import com.asgatech.sharjahmuseums.Adapters.HighLightAdapter;
import com.asgatech.sharjahmuseums.Adapters.HomeSliderImagesAdapter;
import com.asgatech.sharjahmuseums.Adapters.TextAdapter;
import com.asgatech.sharjahmuseums.Models.HightLightEntity;
import com.asgatech.sharjahmuseums.Models.MuseumsDetailsModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
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

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;
import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PagerContainer;
import okhttp3.ResponseBody;

public class MuseumsDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    public ImageView toolbarHomeImageView;
    int NUM_PAGES2;
    String tetephoneNum, emailString, museumTitle, museumColor,museumsImage;
    Timer timer;
    double longtude, latitude;
    private int museumsID;
    private RtlViewPager imagesViewPager;
    private LinearLayout layoutDots;
    private LinearLayout addReviewLinear;
    private LinearLayout locationLinear;
    private LinearLayout callLinear;
    private LinearLayout mailLinear;
    private LinearLayout shareLinear;
    private List<ImageView> dots;
    private ImageView dot;
    private TextViewLight aboutMuseumsTextView;
    private RecyclerView openingHoursList;
    private RecyclerView entryFeesList;
    private FeatureCoverFlow highLightGallery;
    private LinearLayout layoutDotsGallery;
    private RecyclerView facilitiesList;
    private HighLightAdapter mAdapter;
    private TextViewBold ToolbarTitleTextView;
    private int currentPage = 0;
    private int currentPage2 = 0;
    private ImageView ivLine1, ivLine2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_museums_details);

        setToolBar();
        setUpView();
    }

    public void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarHomeImageView = findViewById(R.id.toolbar_home_image_view);

        museumTitle = getIntent().getStringExtra(ConstantUtils.MUSEUM_TITLE);
        museumColor = getIntent().getStringExtra(ConstantUtils.MUSEUM_COLOR);
        Drawable background = toolbar.getBackground();
        if (museumColor != null) {
            Log.e("museumColor", museumColor);
            background.setColorFilter(Color.parseColor(museumColor), PorterDuff.Mode.SRC_IN);
        }

        ToolbarTitleTextView = findViewById(R.id.tv_toolbar_title);
        ToolbarTitleTextView.setText(museumTitle);

        toolbarHomeImageView.setVisibility(View.VISIBLE);
        toolbarHomeImageView.setOnClickListener(this);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        new Localization().setLanguage(MuseumsDetailsActivity.this,  UserData.getLocalization(MuseumsDetailsActivity.this));
        super.onResume();
    }

    private void setUpView() {
        imagesViewPager = findViewById(R.id.images_view_pager);
        layoutDots = findViewById(R.id.layout_dots);
        layoutDotsGallery = findViewById(R.id.layout_dots_gallery);
        ivLine1 = findViewById(R.id.iv_line1);
        ivLine2 = findViewById(R.id.iv_line2);
        dot = null;
        dots = null;
        Drawable background = ivLine1.getBackground();
        if (museumColor != null) {

            background.setColorFilter(Color.parseColor(museumColor), PorterDuff.Mode.SRC_IN);
        }
        Drawable background2 = ivLine2.getBackground();
        if (museumColor != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Utils.getDarkColor(Color.parseColor(museumColor)));
            }
            background2.setColorFilter(Color.parseColor(museumColor), PorterDuff.Mode.SRC_IN);
        }

        addReviewLinear = findViewById(R.id.add_review_linear);
        locationLinear = findViewById(R.id.location_linear);
        callLinear = findViewById(R.id.call_linear);
        mailLinear = findViewById(R.id.mail_linear);
        shareLinear = findViewById(R.id.share_linear);
        aboutMuseumsTextView = findViewById(R.id.about_museums_text_view);
        openingHoursList = findViewById(R.id.opening_hours_list);
        entryFeesList = findViewById(R.id.entry_fees_list);
        facilitiesList = findViewById(R.id.facilities_list);
//        highLightGallery =  (FeatureCoverFlow)findViewById(R.id.coverflow);
        addReviewLinear.setOnClickListener(this);
        locationLinear.setOnClickListener(this);
        callLinear.setOnClickListener(this);
        mailLinear.setOnClickListener(this);
        shareLinear.setOnClickListener(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        entryFeesList.setLayoutManager(mLayoutManager);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        openingHoursList.setLayoutManager(layoutManager);

        facilitiesList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));


        if (getIntent().hasExtra(ConstantUtils.EXTRA_MUSEUMS_ID)) {
            museumsID = getIntent().getIntExtra((ConstantUtils.EXTRA_MUSEUMS_ID), 0);
            if (museumsID > 0) {
                getMuseumsDetails(museumsID,  UserData.getLocalization(MuseumsDetailsActivity.this));
            }
        }


        if (getIntent().hasExtra(ConstantUtils.MUSEUM_IMAGE)) {
            museumsImage = getIntent().getStringExtra((ConstantUtils.MUSEUM_IMAGE));

        }
    }

    private void getMuseumsDetails(int MuseumsID, int langauge) {
        ServerTool.getMuseumsDetails(this, MuseumsID, langauge, new ServerTool.APICallBack<MuseumsDetailsModel>() {
            @Override
            public void onSuccess(MuseumsDetailsModel response) {
                if (Utils.validObject(response)) {
                    latitude = response.getLat();
                    longtude = response.getLong();
                    tetephoneNum = response.getPhone();
                    emailString = response.getEamil();
                    setData(response);
                }

            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
            }
        });
    }

    private void setData(final MuseumsDetailsModel data) {
        aboutMuseumsTextView.setText(data.getAbout());
        if (Utils.validList(data.getOpeningHoursList())) {
            TextAdapter textAdapter = new TextAdapter(this, data.getOpeningHoursList(), 1); // type to opening hours
            openingHoursList.setAdapter(textAdapter);
        }
        if (Utils.validList(data.getPriceCategorySublist())) {
            TextAdapter textAdapter = new TextAdapter(data.getPriceCategorySublist(), this, 2); // type to entry fees
            entryFeesList.setAdapter(textAdapter);
        }
        if (Utils.validList(data.getFacilts())) {
            FaciltsAdapter faciltsAdapter = new FaciltsAdapter(this, data.getFacilts()); // type to entry fees
            facilitiesList.setAdapter(faciltsAdapter);
        }
        if (Utils.validList(data.getImageList())) {
            addDots(data.getImageList().size());
            selectDot(0, data.getImageList().size());

            HomeSliderImagesAdapter imagesAdapter = new HomeSliderImagesAdapter(data.getImageList(), this, 2);
            imagesViewPager.setAdapter(imagesAdapter);
            final int NUM_PAGES = data.getImageList().size();
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == NUM_PAGES) {
                        currentPage = 0;
                    }
                    imagesViewPager.setCurrentItem(currentPage++, true);
                }
            };

            timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 500, 2000);
            imagesViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    currentPage = position;
                }

                @Override
                public void onPageSelected(int position) {
                    selectDot(position, data.getImageList().size());
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }

        if (Utils.validList(data.getHightLight())) {

            PagerContainer pagerContainer = findViewById(R.id.pager_container);
            if (pagerContainer != null) {
                pagerContainer.setOverlapEnabled(true);

            }

            final ViewPager viewPager = pagerContainer != null ? pagerContainer.getViewPager() : null;
            MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), data.getHightLight());
            if (viewPager != null) {
                viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
            }
            if (viewPager != null) {
                viewPager.setAdapter(pagerAdapter);
            }
//            Toast.makeText(MuseumsDetailsActivity.this,"hshshsh",Toast.LENGTH_LONG).show();


            NUM_PAGES2 = data.getHightLight().size();
            autoStartOfViewPager(NUM_PAGES2, viewPager);

        }

    }

    public void addDots(int size) {
        dots = new ArrayList<>();

        if (size != 0) {
            for (int i = 0; i < size; i++) {
                if (dot == null) {
                    dot = new ImageView(this);
                    dot.setImageDrawable(getResources().getDrawable(R.drawable.dot_slider_unactive));
//                    Drawable drawable = res.getDrawable(R.drawable.dot_slider_unactive);
//                    dot.setBackgroundColor(Color.parseColor(museumColor));

                } else {
                    dot = new ImageView(this);
//                    dot.setBackgroundColor(Color.parseColor(museumColor));
                    dot.setImageDrawable(getResources().getDrawable(R.drawable.dot_slider_unactive));
//
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(4, 2, 4, 2);
                layoutDots.addView(dot, params);
                layoutDots.getChildCount();
                dots.add(dot);
            }

        }

    }

    @SuppressLint("ResourceAsColor")
    public void selectDot(int idx, int size) {
        try {
            Resources res = getResources();
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    int drawableId = (i == idx) ? Color.parseColor(museumColor) : (R.color.colorPrimary);
//                    Drawable drawable = res.getDrawable(drawableId);
//                        dots.get(i).setImageDrawable(drawableId);
                    dots.get(i).setBackgroundColor(drawableId);


//                    if (i == idx) {
////                        Drawable drawable = res.getDrawable(R.drawable.dot_slider_active);
////                        dots.get(i).setImageDrawable(drawable);
//                        dots.get(i).setBackgroundColor(Color.parseColor(museumColor));
//                    } else {
////                        dots.get(i).setBackgroundColor(getResources().getColor(R.color.White));
//
//                        Drawable drawable = res.getDrawable(R.drawable.dot_slider_unactive);
//                        dots.get(i).setImageDrawable(drawable);
//                    }
                }
            }
        } catch (Exception e) {
        }
    }

    void autoStartOfViewPager(final int NUM_PAGES2, final ViewPager viewPager) {

// Auto start of viewpager
//        final Handler handler;
//        handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//
//                if (currentPage2 == NUM_PAGES2) {
//                    currentPage2 = 0;
//                }
//                viewPager.setCurrentItem(currentPage2++, true);
////                viewPager.setCurrentItem(10,true);
//            }
//        };

//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 3000, 3000);
        if (NUM_PAGES2 > 2) {
            viewPager.setCurrentItem(1, true);

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

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.add_review_linear:
                Intent intent = new Intent(new Intent(this, ViewVisitorsReviewActivity.class));
                intent.putExtra(ConstantUtils.EXTRA_MUSEUMS_ID, museumsID);
                intent.putExtra(ConstantUtils.MUSEUM_COLOR, museumColor);
                startActivity(intent);
                break;


            case R.id.location_linear:
                Intent intent1 = new Intent(MuseumsDetailsActivity.this, ViewLocationMapActivity.class);
                intent1.putExtra(ConstantUtils.EXTRA_MUSEUMS_lATITUDE, latitude);
                intent1.putExtra(ConstantUtils.EXTRA_MUSEUMS_LONGTUDE, longtude);
                startActivity(intent1);

                break;

            case R.id.call_linear:
                if (tetephoneNum != null) {
                    PermissionTool.checkPermission(MuseumsDetailsActivity.this, PermissionTool.PERMISSION_PHONE_CALL);

                    String uri = "tel:" + tetephoneNum.trim();
                    Intent intent2 = new Intent(Intent.ACTION_CALL);
                    intent2.setData(Uri.parse(uri));
                    startActivity(intent2);
                }
                break;
            case R.id.mail_linear:
                if (!emailString.isEmpty()) {
                    Intent mailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailString, null));
                    Log.e("mail", emailString);
                    mailIntent.putExtra(Intent.EXTRA_SUBJECT, " ");
                    startActivity(Intent.createChooser(mailIntent, ""));
                }

                break;
            case R.id.share_linear:

                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType("text/plain");
                intentShare.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                intentShare.putExtra(Intent.EXTRA_TEXT, URLS.URL_BASE +  museumsImage+ "\n" +
                        "\n" + getResources().getString(R.string.about_museums) +":"+ aboutMuseumsTextView.getText().toString());
                Intent chooser = Intent.createChooser(intentShare, "share");
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

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        List<HightLightEntity> highlightList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<HightLightEntity> highlightList) {
            super(fm);
            this.highlightList = highlightList;
        }

        @Override
        public Fragment getItem(int position) {
            return HighLightOverlapingFragment.newInstance(highlightList.get(position).getPhoto(), highlightList, position);
        }

        @Override
        public int getCount() {
            return highlightList.size();
        }

    }
}
