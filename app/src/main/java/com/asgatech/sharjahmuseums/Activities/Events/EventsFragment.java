package com.asgatech.sharjahmuseums.Activities.Events;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Activities.Home.HomeActivity;
import com.asgatech.sharjahmuseums.Models.EventCategoryModel;
import com.asgatech.sharjahmuseums.Models.EventModel;
import com.asgatech.sharjahmuseums.Models.NewModel;
import com.asgatech.sharjahmuseums.Models.NewResponse;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.CircleImageView;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;
import okhttp3.ResponseBody;

import static com.asgatech.sharjahmuseums.Tools.AndroidDialogTools.customToastView;

public class EventsFragment extends Fragment implements View.OnClickListener, EventsParentContract.ModelView {

    @BindView(R.id.filter_layout)
    LinearLayout filterLayout;

    @BindView(R.id.main_filter_layout)
    LinearLayout mainFilterLayout;

    @BindView(R.id.event_switch_to_calender)
    ImageView switchToCalender;

    @BindView(R.id.auto_complete_search_event)
    EditText mSearchEditText;

    @BindView(R.id.loading_layout)
    View loadingView;

    @BindView(R.id.arrowIV)
    ImageView arrowImg;
    private boolean isBundle;
    private String date;

    @BindView(R.id.event_container)
    ViewPager mEventViewPager;

    MyFragmentPagerAdapter pagerAdapter;

    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        ButterKnife.bind(this, view);
        ((HomeActivity) getActivity()).changeToolbarTitle(getString(R.string.Events));
        assignControls();
        if (getArguments() != null) {
        }
        pagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager());
        mEventViewPager.setAdapter(pagerAdapter);
        mEventViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switchToCalender.setImageDrawable(getResources()
                        .getDrawable(position == 0 ? R.drawable.ic_calender_tap : R.drawable.ic_list_tap));
//                ((EventsContract.ModelView) pagerAdapter
//                        .getItem(position))
//                        .updateView(Realm.getDefaultInstance()
//                                .where(EventModel.class)
//                                .findAll());
            }

            @Override
            public void onPageSelected(int position) {
                switchToCalender.setImageDrawable(getResources()
                        .getDrawable(position == 0 ? R.drawable.ic_calender_tap : R.drawable.ic_list_tap));
//                ((EventsContract.ModelView) pagerAdapter
//                        .getItem(position))
//                        .updateView(Realm.getDefaultInstance()
//                                .where(EventModel.class)
//                                .findAll());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty())
                    ((EventsContract.ModelView) pagerAdapter
                            .getItem(mEventViewPager.getCurrentItem()))
                            .updateView(Realm.getDefaultInstance()
                                    .where(EventModel.class)
                                    .findAll(), null);
                else
                    ((EventsContract.ModelView) pagerAdapter
                            .getItem(mEventViewPager.getCurrentItem()))
                            .updateView(Realm.getDefaultInstance().where(EventModel.class)
                                    .contains("title", s.toString(), Case.INSENSITIVE)
                                    .findAll(), null);

            }
        });
        getEventCategories(UserData.getLocalization(getActivity()));
        return view;
    }

    private void getEvents(int catId, int pageNumber, int pageSize, int langauge) {

        ServerTool.getEvents(getActivity(), catId, pageNumber, pageSize, langauge, new ServerTool.APICallBack<RealmList<EventModel>>() {
            @Override
            public void onSuccess(RealmList<EventModel> response) {
                if (Utils.validList(response)) {
                    setData(response);
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
            }
        });
    }

    private void getEventCategories(int langauge) {
        ServerTool.getEventsCategories(getActivity(), langauge, new ServerTool.APICallBack<List<EventCategoryModel>>() {
            @Override
            public void onSuccess(List<EventCategoryModel> response) {
                if (Utils.validList(response)) {
                    if (isAdded())
                        setCategoryData(response);
                    if (response.size() > 0) {
                        if (isAdded())
                            getEvents(0, 1, 15, langauge);
                    }
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
            }
        });
    }

    private void setDatas(List<NewResponse> response) {
        ((EventsContract.ModelView) pagerAdapter
                .getItem(0))
                .updateViews(response, null);
        ((EventsContract.ModelView) pagerAdapter
                .getItem(1))
                .updateViews(response, null);
    }

    private void setData(List<EventModel> response) {
        mEventViewPager.setCurrentItem(0);
        ((EventsContract.ModelView) pagerAdapter
                .getItem(0)).showList();

        ((EventsContract.ModelView) pagerAdapter
                .getItem(0))
                .updateView(response, null);
        ((EventsContract.ModelView) pagerAdapter
                .getItem(1))
                .updateView(response, null);
    }

    private void setCategoryData(List<EventCategoryModel> list) {
        addReset();
        for (int i = 0; i < list.size(); i++) {
            final View itemView = LayoutInflater.from(getActivity())
                    .inflate(R.layout.filter_recycler_row, null);
            CircleImageView pallete = itemView.findViewById(R.id.pallete_for_filter_item);
            TextView name = itemView.findViewById(R.id.name_for_filter_item);
//            pallete.setSolidColor(list.get(i).getColor()); //throws exception because server data is dummy
            name.setText(list.get(i).getTitle().trim());
            itemView.setTag(list.get(i).getEventCatID());
            Drawable background = pallete.getBackground();
            if (list.get(i).getColor() != null) {
                Log.e(getString(R.string.tag_color_code), list.get(i).getTitle() + ":" + list.get(i).getColor());
                background.setColorFilter(Color.parseColor(list.get(i).getColor()), PorterDuff.Mode.SRC_IN);
            }
            itemView.setOnClickListener(view -> {
                mainFilterLayout.setVisibility(View.GONE);
                if (isBundle) {
                    NewModel newModel = new NewModel(Integer.parseInt(itemView.getTag().toString()),
                            UserData.getLocalization(getActivity()), date);
                    getDateList(newModel);
                    Log.e("true", "true");
                } else {
                    List<EventModel> response = Realm.getDefaultInstance()
                            .where(EventModel.class)
                            .findAll();
                    boolean isFound = false;
                    if (Utils.validList(response)) {
                        for (int j = 0; j < response.size(); j++) {
                            if (Integer.parseInt(itemView.getTag().toString()) == (response.get(j).getCatId())) {
                                isFound = true;
                            }
                        }
                        if (isFound) {
                            setData(Realm.getDefaultInstance()
                                    .where(EventModel.class)
                                    .equalTo("catId", Integer.parseInt(itemView.getTag().toString()))
                                    .findAll());
                        } else {
                            mEventViewPager.setCurrentItem(0);
                            ((EventsContract.ModelView) pagerAdapter
                                    .getItem(mEventViewPager.getCurrentItem())).hideList();
                            customToastView(getActivity(), getActivity().getString(R.string.no_event));
                        }
                    }
                }

            });
            //otherwise throw exception java.lang.IllegalStateException: The specified
            // child already has a parent. You must call removeView() on the child's parent first.
            if (itemView.getParent() != null) {
                ((ViewGroup) itemView.getParent()).removeView(itemView);
            }
            mainFilterLayout.addView(itemView);
        }
    }

    private void getDateList(NewModel newModel) {
        ServerTool.getDateList(newModel, new ServerTool.APICallBack<List<NewResponse>>() {
            @Override
            public void onSuccess(List<NewResponse> response) {
                if (response.size() != 0) {
                    setDatas(response);
                } else {
//                    recyclerViewReviews.setVisibility(View.GONE);
//                    ErrorMessageTextView.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {

            }
        });
    }


    private void addReset() {
        final View itemView = LayoutInflater.from(getActivity())
                .inflate(R.layout.filter_recycler_row, null);
        CircleImageView pallete = itemView.findViewById(R.id.pallete_for_filter_item);
        TextView name = itemView.findViewById(R.id.name_for_filter_item);
        name.setText(R.string.title_filter_all);
        itemView.setTag("#939597");
        Drawable background = pallete.getBackground();
        Log.e(getString(R.string.tag_color_code), "#939597");
        background.setColorFilter(Color.parseColor("#939597"), PorterDuff.Mode.SRC_IN);
        itemView.setOnClickListener(view -> {
            mainFilterLayout.setVisibility(View.GONE);
//            if(isBundle){
//                NewModel newModel = NewModel
//                Log.e("true" , "true");
//            }else {
            setData(Realm.getDefaultInstance()
                    .where(EventModel.class)
                    .findAll());
//            }

        });
        //otherwise throw exception java.lang.IllegalStateException: The specified
        // child already has a parent. You must call removeView() on the child's parent first.
        if (itemView.getParent() != null) {
            ((ViewGroup) itemView.getParent()).removeView(itemView);
        }
        mainFilterLayout.addView(itemView);
    }

    private void assignControls() {
        filterLayout.setOnClickListener(this);
        switchToCalender.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filter_layout:

                if (mainFilterLayout.getVisibility() == View.VISIBLE) {
                    mainFilterLayout.setVisibility(View.GONE);

                } else {
                    mainFilterLayout.setVisibility(View.VISIBLE);
                }
                if (arrowImg.getRotation() == 0) {
                    arrowImg.setRotation(180);
                } else {
                    arrowImg.setRotation(arrowImg.getRotation() + 180);
                }
                break;
            case R.id.event_switch_to_calender:
                if (mEventViewPager.getCurrentItem() == 0) {
                    mSearchEditText.setVisibility(View.GONE);
                } else {
                    mSearchEditText.setVisibility(View.VISIBLE);
                }
                mainFilterLayout.setVisibility(View.GONE);
                mEventViewPager.setCurrentItem(mEventViewPager.getCurrentItem() == 0 ? 1 : 0);
                break;
        }
    }

    @Override
    public void openList(List<EventModel> models) {
        mEventViewPager.setCurrentItem(0);
        ((EventsContract.ModelView) pagerAdapter
                .getItem(mEventViewPager.getCurrentItem()))
                .updateView(models, null);
    }

    @Override
    public void setBundle(boolean bundle) {
        isBundle = bundle;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        Fragment[] fragments = new Fragment[]{new EventsListFragment(), new EventCalenderFragment()};

        MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
