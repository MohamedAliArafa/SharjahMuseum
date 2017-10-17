package com.asgatech.sharjahmuseums.Activities.OurMuseums.MuseumDetails;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.asgatech.sharjahmuseums.Activities.Home.HomeContract;
import com.asgatech.sharjahmuseums.Activities.ViewLocationMapActivity;
import com.asgatech.sharjahmuseums.Activities.ViewVisitorsReviewActivity;
import com.asgatech.sharjahmuseums.Adapters.FaciltsAdapter;
import com.asgatech.sharjahmuseums.Adapters.HomeSliderImagesAdapter;
import com.asgatech.sharjahmuseums.Adapters.TextAdapter;
import com.asgatech.sharjahmuseums.Models.MuseumsDetailsModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewLight;
import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.PermissionTool;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PagerContainer;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class MuseumDetailsFragment extends Fragment implements View.OnClickListener {

    int NUM_PAGES2;
    String tetephoneNum, emailString, museumTitle, museumColor;
    Timer timer;
    double longtude, latitude;
    private int museumsID;

    private int currentPage = 0;
    private int currentPage2 = 0;

    private List<ImageView> dots;
    private ImageView dot;

    @BindView(R.id.images_view_pager)
    ViewPager mImagesViewPager;
    @BindView(R.id.layout_dots)
    LinearLayout mDotsLayout;
    @BindView(R.id.iv_line1)
    ImageView ivLine1;
    @BindView(R.id.iv_line2)
    ImageView ivLine2;

    @BindView(R.id.add_review_linear)
    LinearLayout addReviewLinear;
    @BindView(R.id.location_linear)
    LinearLayout locationLinear;
    @BindView(R.id.call_linear)
    LinearLayout callLinear;
    @BindView(R.id.mail_linear)
    LinearLayout mailLinear;
    @BindView(R.id.share_linear)
    LinearLayout shareLinear;
    @BindView(R.id.about_museums_text_view)
    TextViewLight aboutMuseumsTextView;
    @BindView(R.id.opening_hours_list)
    RecyclerView openingHoursList;
    @BindView(R.id.entry_fees_list)
    RecyclerView entryFeesList;
    @BindView(R.id.facilities_list)
    RecyclerView facilitiesList;

    @BindView(R.id.pager_container)
    PagerContainer pagerContainer;


    public MuseumDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_museum_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView();
    }

    @Override
    public void onResume() {
        new Localization().setLanguage(getActivity(), new UserData().getLocalization(getActivity()));
        super.onResume();
    }

    private void setUpView() {
        dot = null;
        dots = null;

        Drawable background = ivLine1.getBackground();
        Drawable background2 = ivLine2.getBackground();
        if (museumColor != null) {
            background.setColorFilter(Color.parseColor(museumColor), PorterDuff.Mode.SRC_IN);
            background2.setColorFilter(Color.parseColor(museumColor), PorterDuff.Mode.SRC_IN);
        }

        addReviewLinear.setOnClickListener(this);
        locationLinear.setOnClickListener(this);
        callLinear.setOnClickListener(this);
        mailLinear.setOnClickListener(this);
        shareLinear.setOnClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        entryFeesList.setLayoutManager(mLayoutManager);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        openingHoursList.setLayoutManager(layoutManager);
        facilitiesList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));

        museumsID = getArguments().getInt(ConstantUtils.EXTRA_MUSEUMS_ID, 0);
        if (museumsID > 0) {
            getMuseumsDetails(museumsID, new UserData().getLocalization(getActivity()));
        }
    }

    private void getMuseumsDetails(int MuseumsID, int langauge) {
        ServerTool.getMuseumsDetails(getContext(), MuseumsID, langauge, new ServerTool.APICallBack<MuseumsDetailsModel>() {
            @Override
            public void onSuccess(MuseumsDetailsModel response) {
                if (Utils.validObject(response)) {
                    museumTitle = response.getTitle();
                    museumColor = response.getColor();
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

        ((HomeContract.ModelView) getActivity()).changeToolbarColor(museumColor);
        ((HomeContract.ModelView) getActivity()).changeToolbarTitle(museumTitle);

        aboutMuseumsTextView.setText(data.getAbout());
        if (Utils.validList(data.getOpeningHoursList())) {
            TextAdapter textAdapter = new TextAdapter(getActivity(), data.getOpeningHoursList(), 1); // type to opening hours
            openingHoursList.setAdapter(textAdapter);
        }
        if (Utils.validList(data.getPriceCategorySublist())) {
            TextAdapter textAdapter = new TextAdapter(data.getPriceCategorySublist(), getActivity(), 2); // type to entry fees
            entryFeesList.setAdapter(textAdapter);
        }
        if (Utils.validList(data.getFacilts())) {
            FaciltsAdapter faciltsAdapter = new FaciltsAdapter(getActivity(), data.getFacilts()); // type to entry fees
            facilitiesList.setAdapter(faciltsAdapter);
        }
        if (Utils.validList(data.getImageList())) {
            addDots(data.getImageList().size());
            selectDot(0, data.getImageList().size());

            HomeSliderImagesAdapter imagesAdapter = new HomeSliderImagesAdapter(data.getImageList(), getActivity(), 2);
            mImagesViewPager.setAdapter(imagesAdapter);
            final int NUM_PAGES = data.getImageList().size();
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == NUM_PAGES) {
                        currentPage = 0;
                    }
                    mImagesViewPager.setCurrentItem(currentPage++, true);
                }
            };

            timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 500, 2000);
            mImagesViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
            if (pagerContainer != null) {
                pagerContainer.setOverlapEnabled(true);
            }
            ViewPager viewPager = pagerContainer != null ? pagerContainer.getViewPager() : null;
            MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getFragmentManager(), data.getHightLight());
            if (viewPager != null) {
                viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
                viewPager.setAdapter(pagerAdapter);
            }
            NUM_PAGES2 = data.getHightLight().size();
            initCoverViewPager(NUM_PAGES2, viewPager);
        }
    }

    public void addDots(int size) {
        if (dot == null)
            dots = new ArrayList<>();
        mDotsLayout.removeAllViews();
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                if (dot == null) {
                    dot = new ImageView(getActivity());
                    dot.setImageDrawable(getResources().getDrawable(R.drawable.dot_slider_unactive));

                } else {
                    dot = new ImageView(getActivity());
                    dot.setImageDrawable(getResources().getDrawable(R.drawable.dot_slider_unactive));
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(4, 2, 4, 2);
                mDotsLayout.addView(dot, params);
                mDotsLayout.getChildCount();
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
                    dots.get(i).setBackgroundColor(drawableId);
                }
            }
        } catch (Exception e) {
        }
    }

    void initCoverViewPager(final int NUM_PAGES2, final ViewPager viewPager) {
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
                Intent intent = new Intent(new Intent(getActivity(), ViewVisitorsReviewActivity.class));
                intent.putExtra(ConstantUtils.EXTRA_MUSEUMS_ID, museumsID);
                intent.putExtra(ConstantUtils.MUSEUM_COLOR, museumColor);
                startActivity(intent);
                break;
            case R.id.location_linear:
                Intent intent1 = new Intent(getActivity(), ViewLocationMapActivity.class);
                intent1.putExtra(ConstantUtils.EXTRA_MUSEUMS_lATITUDE, latitude);
                intent1.putExtra(ConstantUtils.EXTRA_MUSEUMS_LONGTUDE, longtude);
                startActivity(intent1);
                break;
            case R.id.call_linear:
                if (tetephoneNum != null) {
                    PermissionTool.checkPermission(getActivity(), PermissionTool.PERMISSION_PHONE_CALL);
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
                break;
        }
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        ArrayList<MuseumsDetailsModel.HightLightEntity> highlightList;

        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<MuseumsDetailsModel.HightLightEntity> highlightList) {
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
