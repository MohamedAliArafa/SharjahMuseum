package com.asgatech.sharjahmuseums.Activities.Home;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Activities.Events.EventsFragment;
import com.asgatech.sharjahmuseums.Activities.OurMuseums.OurMuseumsFragment;
import com.asgatech.sharjahmuseums.Adapters.HomeSliderImagesAdapter;
import com.asgatech.sharjahmuseums.Fragments.AboutUsFragment;
import com.asgatech.sharjahmuseums.Fragments.EducationListFragment;
import com.asgatech.sharjahmuseums.Fragments.NotificationListFragment;
import com.asgatech.sharjahmuseums.Fragments.PlanYourVisitFragment;
import com.asgatech.sharjahmuseums.Models.AllSliderModel;
import com.asgatech.sharjahmuseums.Models.HomeModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.Localization;
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
    List<ImageView> dots;
    private ImageView dot;
    private LinearLayout dotsLayout;
    private int currentPage = 0;
    private ConstraintLayout meusemsLinear, event_layout, planVisitsLinear, notifications_linear, about_us_layout, education_layout;
    @BindView(R.id.tv_event_counter)
    TextView mEventCounterTextView;

    @BindView(R.id.tv_notification_counter)
    TextView mNotificationCounterTextView;

    @BindView(R.id.welcome_linear_layout)
    LinearLayout mWelcomeLinearLayout;

    @BindView(R.id.iv_event_counter)
    ImageView mEventCounterImageView;

    @BindView(R.id.iv_notification_counter)
    ImageView mNotificationCounterImageView;
    HomeSliderImagesAdapter imagesAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        setupViews(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

        if (Utils.isNetworkAvailable(getActivity())) {
            getAllSlider(UserData.getLocalization(getActivity()));
        }

        if (Localization.getCurrentLanguageID(getContext()) == Localization.ARABIC_VALUE) {
            Animation in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left);
            Animation out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
            out.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mWelcomeLinearLayout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            in.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
//                    mWelcomeLinearLayout.startAnimation(out);
                    new Handler().postDelayed(() -> mWelcomeLinearLayout.startAnimation(out), 4000);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mWelcomeLinearLayout.startAnimation(in);
        }else {
            Animation in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
            Animation out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_left);
            out.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mWelcomeLinearLayout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            in.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    new Handler().postDelayed(() -> mWelcomeLinearLayout.startAnimation(out), 4000);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mWelcomeLinearLayout.startAnimation(in);
        }
    }

    private void getAllSlider(int language) {
        ServerTool.getAllSlider(getActivity(), language, new ServerTool.APICallBack<HomeModel>() {
            @Override
            public void onSuccess(HomeModel response) {
                if (isAdded())
                    setData(response);
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

    private void setData(final HomeModel response) {
        addDots(response.getSliderList());
        selectDot(0, response.getSliderList());
        imagesAdapter = new HomeSliderImagesAdapter(getActivity(), response.getSliderList(), 1);
        imagesViewPager.setAdapter(imagesAdapter);
        int NUM_PAGES = response.getSliderList().size();
        Handler handler = new Handler();
        Runnable Update = () -> {
            if (currentPage == NUM_PAGES) {
                currentPage = 0;
            }
            imagesViewPager.setCurrentItem(currentPage++, true);
        };

        Timer timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
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
                selectDot(position, response.getSliderList());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        if (response.getEventCount() > 0) {
            mEventCounterImageView.setVisibility(View.VISIBLE);
            mEventCounterTextView.setVisibility(View.VISIBLE);
            mEventCounterTextView.setText(String.valueOf(response.getEventCount()));
        } else {
            mEventCounterImageView.setVisibility(View.GONE);
            mEventCounterTextView.setVisibility(View.GONE);
        }
        if (response.getNotfactionCount() > 0) {
            mNotificationCounterTextView.setVisibility(View.VISIBLE);
            mNotificationCounterImageView.setVisibility(View.VISIBLE);
            mNotificationCounterTextView.setText(String.valueOf(response.getNotfactionCount()));
        } else {
            mNotificationCounterImageView.setVisibility(View.GONE);
            mNotificationCounterTextView.setVisibility(View.GONE);
        }
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
//            Resources res = getResources();
            if (imageList != null && imageList.size() != 0) {
                for (int i = 0; i < imageList.size(); i++) {
                    int drawableId = (i == idx) ? (R.drawable.dot_slider_active) : (R.drawable.dot_slider_unactive);
                    Drawable drawable = getResources().getDrawable(drawableId);
                    dots.get(i).setImageDrawable(drawable);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.meusems_linear:
                if (Utils.isNetworkAvailable(getActivity())) {
                    ((HomeActivity) getActivity()).openFragmentFromChild(new OurMuseumsFragment(), null);
                }
                break;
            case R.id.event_layout:
                if (Utils.isNetworkAvailable(getActivity())) {
                    ((HomeActivity) getActivity()).openFragmentFromChild(new EventsFragment(), null);
                }
                break;
            case R.id.plan_visits_linear:
                if (Utils.isNetworkAvailable(getActivity())) {
                    ((HomeActivity) getActivity()).openFragmentFromChild(new PlanYourVisitFragment(), null);
                }
                break;
            case R.id.notifications_linear:
                if (Utils.isNetworkAvailable(getActivity())) {
                    ((HomeActivity) getActivity()).openFragmentFromChild(new NotificationListFragment(), null);
                }
                break;
            case R.id.education_layout:
                if (Utils.isNetworkAvailable(getActivity())) {
                    ((HomeActivity) getActivity()).openFragmentFromChild(new EducationListFragment(), null);
                }
                break;
            case R.id.about_us_layout:
                if (Utils.isNetworkAvailable(getActivity())) {
                    ((HomeActivity) getActivity()).openFragmentFromChild(new AboutUsFragment(), null);
                }
                break;
        }
    }
}
