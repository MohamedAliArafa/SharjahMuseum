package com.asgatech.sharjahmuseums.Activities.Home;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.asgatech.sharjahmuseums.Activities.OurMuseums.OurMuseumsFragment;
import com.asgatech.sharjahmuseums.Adapters.HomeSliderImagesAdapter;
import com.asgatech.sharjahmuseums.Fragments.AboutUsFragment;
import com.asgatech.sharjahmuseums.Fragments.EducationListFragment;
import com.asgatech.sharjahmuseums.Activities.Events.EventsListFragment;
import com.asgatech.sharjahmuseums.Fragments.NotificationListFragment;
import com.asgatech.sharjahmuseums.Fragments.PlanYourVisitFragment;
import com.asgatech.sharjahmuseums.Models.AllSliderModel;
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
public class HomeFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.images_view_pager)
    RtlViewPager imagesViewPager;
    private List<ImageView> dots;
    private ImageView dot;
    private LinearLayout dotsLayout;
    private int currentPage = 0;
    Timer timer;
    private LinearLayout meusemsLinear,event_layout,planVisitsLinear,notifications_linear,about_us_layout,education_layout;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews(view);
    }

    private void setupViews(View view) {
        meusemsLinear = view.findViewById(R.id.meusems_linear);
        planVisitsLinear = view.findViewById(R.id.plan_visits_linear);
        notifications_linear = view.findViewById(R.id.notifications_linear);
        event_layout = view.findViewById(R.id.event_layout);
        dotsLayout = view.findViewById(R.id.layout_dots);
        education_layout = view.findViewById(R.id.education_layout);
        about_us_layout = view.findViewById(R.id.about_us_layout);
        dot = null;
        dots = null;
        meusemsLinear.setOnClickListener(this);
        event_layout.setOnClickListener(this);
        planVisitsLinear.setOnClickListener(this);
        notifications_linear.setOnClickListener(this);
        about_us_layout.setOnClickListener(this);
        education_layout.setOnClickListener(this);

        getAllSlider( UserData.getLocalization(getActivity()));
    }

    private void getAllSlider(int langauge) {
        ServerTool.getAllSlider(getActivity(), langauge, new ServerTool.APICallBack<List<AllSliderModel>>() {
            @Override
            public void onSuccess(List<AllSliderModel> response) {
                if (Utils.validList(response)) {
                    if (isAdded())
                        setData(response);
                }

            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        if (context instanceof HomeContract.ModelView)
            ((HomeContract.ModelView) context).showLogo();
        super.onAttach(context);
    }

    private void setData(final List<AllSliderModel> response) {
        addDots(response);
        selectDot(0, response);
        HomeSliderImagesAdapter imagesAdapter = new HomeSliderImagesAdapter(getActivity(), response, 1);
        imagesViewPager.setAdapter(imagesAdapter);
        final int NUM_PAGES = response.size();
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
        }, 1000, 2000);
        imagesViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPage = position;
            }

            @Override
            public void onPageSelected(int position) {
                selectDot(position, response);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void addDots(List<AllSliderModel> imagesList) {
        dots = new ArrayList<>();
        if (imagesList != null && imagesList.size() != 0) {
            for (int i = 0; i < imagesList.size(); i++) {
                if (dot == null) {
                    dot = new ImageView(getActivity());
                    dot.setImageDrawable(getResources().getDrawable(R.drawable.dot_slider_active));
                } else {
                    dot = new ImageView(getActivity());
                    dot.setImageDrawable(getResources().getDrawable(R.drawable.dot_slider_active));
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(4, 2, 4, 2);
                dotsLayout.addView(dot, params);
                dotsLayout.getChildCount();
                dots.add(dot);
            }

        }

    }

    public void selectDot(int idx, List<AllSliderModel> imageList) {
        try {
            Resources res = getResources();
            if (imageList != null && imageList.size() != 0) {
                for (int i = 0; i < imageList.size(); i++) {
                    int drawableId = (i == idx) ? (R.drawable.dot_slider_active) : (R.drawable.dot_slider_unactive);
                    Drawable drawable = res.getDrawable(drawableId);
                    dots.get(i).setImageDrawable(drawable);
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.meusems_linear:
                ((HomeActivity) getActivity()).openFragmentFromChild(new OurMuseumsFragment(), null);
                break;
            case R.id.event_layout:
                ((HomeActivity) getActivity()).openFragmentFromChild(new EventsListFragment(), null);
                break;
            case R.id.plan_visits_linear:
                ((HomeActivity) getActivity()).openFragmentFromChild(new PlanYourVisitFragment(), null);
                break;
            case R.id.notifications_linear:
                ((HomeActivity) getActivity()).openFragmentFromChild(new NotificationListFragment(), null);
                break;
            case R.id.education_layout:
                ((HomeActivity) getActivity()).openFragmentFromChild(new EducationListFragment(), null);
                break;
            case R.id.about_us_layout:
                ((HomeActivity) getActivity()).openFragmentFromChild(new AboutUsFragment(), null);
                break;
        }
    }
}
