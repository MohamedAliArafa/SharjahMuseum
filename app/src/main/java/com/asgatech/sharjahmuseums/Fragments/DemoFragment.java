package com.asgatech.sharjahmuseums.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Activities.Home.HomeActivity;
import com.asgatech.sharjahmuseums.Activities.Home.HomeContract;
import com.asgatech.sharjahmuseums.Adapters.DemoPagerAdapter;
import com.asgatech.sharjahmuseums.Adapters.DemoPagerDescAdapter;
import com.asgatech.sharjahmuseums.Models.DemoImage;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;
import com.booking.rtlviewpager.RtlViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class DemoFragment extends Fragment {

    @BindView(R.id.vp_demo)
    RtlViewPager mDemoImageViewPager;

    @BindView(R.id.vp_demo_title)
    RtlViewPager mDemoDescriptionViewPager;

//    @BindView(R.id.btn_skip)
//    Button mSkipButton;

    private List<ImageView> mPagerDotList;
    ImageView mSingleDotImageView;

    private TextView[] dots;
    @BindView(R.id.layoutDots)
    LinearLayout dotsLayout;
    int size = 0;
    private int mCurrentPage;

    public DemoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_demo, container, false);
        ButterKnife.bind(this, view);
        ((HomeActivity) getActivity()).changeToolbarTitle(getString(R.string.demo));
        ((HomeContract.ModelView) getActivity()).hideLogo();
        if (Utils.isNetworkAvailable(getActivity())) {
            getData(UserData.getLocalization(getContext()));
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mSkipButton.setOnClickListener(view1 -> {
//            if (isAdded())
//                ((HomeContract.ModelView) getActivity()).getPresenter().openHome();
//        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[size];
        dotsLayout.removeAllViews();
        LinearLayout.LayoutParams params = new AppBarLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.rightMargin = 10;
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setHeight(10);
            dots[i].setLayoutParams(params);
            dots[i].setBackground(getResources().getDrawable(R.drawable.indicator_background_inactive));
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[currentPage].setBackground(getResources().getDrawable(R.drawable.indicator_background_active));
    }

    public void addDots(int size) {
        mPagerDotList = new ArrayList<>();
        dotsLayout.removeAllViews();
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                if (mSingleDotImageView == null) {
                    mSingleDotImageView = new ImageView(getContext());
                    mSingleDotImageView.setImageDrawable(getResources().getDrawable(R.drawable.dot_slider_unactive));

                } else {
                    mSingleDotImageView = new ImageView(getContext());
                    mSingleDotImageView.setImageDrawable(getResources().getDrawable(R.drawable.dot_slider_unactive));
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(4, 2, 4, 2);
                dotsLayout.addView(mSingleDotImageView, params);
                dotsLayout.getChildCount();
                mPagerDotList.add(mSingleDotImageView);
            }

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (timer != null)
            timer.cancel();
        if (Update != null && handler != null)
            handler.removeCallbacks(Update);
    }

    Handler handler;
    Runnable Update;
    Timer timer;

    private void getData(int lang) {
        ServerTool.getDemo(getActivity(), lang, new ServerTool.APICallBack<List<DemoImage>>() {
            @Override
            public void onSuccess(final List<DemoImage> model) {
                if (Utils.validObject(model)) {
                    mDemoImageViewPager.setAdapter(new DemoPagerAdapter(getContext(), model));
                    mDemoDescriptionViewPager.setAdapter(new DemoPagerDescAdapter(getContext(), model));
                    size = model.size();
                    addDots(size);
                    selectDot(0, size);
//                    addBottomDots(0);
                    mDemoImageViewPager.addOnPageChangeListener(viewPagerPageChangeListener);
                    mDemoDescriptionViewPager.addOnPageChangeListener(viewPagerPageChangeListener);

                    handler = new Handler();
                    Update = () -> {
                        if (mCurrentPage == size) {
                            mCurrentPage = 0;
                        }
                        mDemoImageViewPager.setCurrentItem(mCurrentPage++, true);
                    };

                    timer = new Timer(); // This will create a new Thread
                    timer.schedule(new TimerTask() { // task to be scheduled
                        @Override
                        public void run() {
                            handler.post(Update);
                        }
                    }, 500, 2000);

                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
                Log.e(this.getClass().getName(), "Failed");
            }
        });
    }

    public void selectDot(int idx, int size) {
        try {
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    int drawableId = (i == idx) ? (R.color.GreenLight) : (R.color.GrayLight);
                    mPagerDotList.get(i).setBackgroundColor(getResources().getColor(drawableId));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //	viewpager change listener
    RtlViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
//            addBottomDots(position);
            selectDot(position, size);
            mDemoImageViewPager.setCurrentItem(position);
            mDemoDescriptionViewPager.setCurrentItem(position);
            mCurrentPage = position;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

}
