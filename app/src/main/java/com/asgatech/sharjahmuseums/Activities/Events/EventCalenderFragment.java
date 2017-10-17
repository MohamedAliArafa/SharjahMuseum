package com.asgatech.sharjahmuseums.Activities.Events;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Activities.Home.HomeActivity;
import com.asgatech.sharjahmuseums.Fragments.CalenderHelper.EventDecorator;
import com.asgatech.sharjahmuseums.Fragments.CalenderHelper.OneDayDecorator;
import com.asgatech.sharjahmuseums.Models.EventCategoryModel;
import com.asgatech.sharjahmuseums.Models.EventModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.CircleImageView;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventCalenderFragment extends Fragment implements OnDateSelectedListener,
        OnMonthChangedListener, View.OnClickListener {

    @BindView(R.id.calendarView)
    MaterialCalendarView widget;

    @BindView(R.id.main_filter_layout)
    LinearLayout mainFilterLayout;

    @BindView(R.id.arrowIV)
    ImageView arrowImg;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();

    public EventCalenderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_calender, container, false);
        ButterKnife.bind(this, view);

        widget.setOnDateChangedListener(this);
        widget.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

//        Calendar instance = Calendar.getInstance();
//        widget.setSelectedDate(instance.getTime());
//
//        Calendar instance1 = Calendar.getInstance();
//        instance1.set(instance1.get(Calendar.YEAR), Calendar.JANUARY, 1);

//        Calendar instance2 = Calendar.getInstance();
//        instance2.set(instance2.get(Calendar.YEAR), Calendar.DECEMBER, 31);

        widget.state().edit()
//                .setMinimumDate(instance1.getTime())
//                .setMaximumDate(instance2.getTime())
                .commit();

//        widget.addDecorators(
//                new MySelectorDecorator(this)
//                new HighlightWeekendsDecorator(),
//                oneDayDecorator
//        );

        getEventCategories(new UserData().getLocalization(getActivity()));
        return view;
    }

    private void getEventCategories(final int langauge) {

        ServerTool.getEventsCategories(getActivity(), langauge, new ServerTool.APICallBack<List<EventCategoryModel>>() {
            @Override
            public void onSuccess(List<EventCategoryModel> response) {
                if (Utils.validList(response)) {
                    setCategoryData(response);
                    if (response.size() > 0) {
                        getEvents(response.get(0).getEventCatID(), 1, 20, langauge);
                    }
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
            }
        });
    }

    private void getEvents(int catId, int pageNumber, int pageSize, int langauge) {

        ServerTool.getEvents(getActivity(), catId, pageNumber, pageSize, langauge, new ServerTool.APICallBack<List<EventModel>>() {
            @Override
            public void onSuccess(List<EventModel> response) {
                if (Utils.validList(response)) {
                    setData(response);
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
            }
        });
    }

    private void setData(List<EventModel> models) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Calendar cal;
        ArrayList<CalendarDay> dates = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            cal = Calendar.getInstance();
            try {
                cal.setTime(sdf.parse(models.get(i).getStartDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            CalendarDay day = CalendarDay.from(cal);
            dates.add(day);

            try {
                int colr = Color.parseColor("#" + models.get(i).getColor());
//                int colr  = Integer.parseInt("#"+models.get(i).getColor(), 16);
//                int n = (int) Long.parseLong(models.get(i).getColor(), 16);

                widget.addDecorator(new EventDecorator(colr, dates));
            } catch (NumberFormatException ex) {
                widget.addDecorator(new EventDecorator(Color.RED, dates));
            }
            dates.clear();
        }
    }

    private void setCategoryData(List<EventCategoryModel> list) {
        for (int i = 0; i < list.size(); i++) {
            final View itemView = LayoutInflater.from(getActivity())
                    .inflate(R.layout.filter_recycler_row, null);

            CircleImageView pallete = itemView.findViewById(R.id.pallete_for_filter_item);
            TextView name = itemView.findViewById(R.id.name_for_filter_item);
//            pallete.setSolidColor(list.get(i).getColor()); //throws exception becouse server data is dummy

            name.setText(list.get(i).getTitle().trim());
            itemView.setTag(list.get(i).getEventCatID());

            Drawable background = pallete.getBackground();
            if (list.get(i).getColor() != null) {
                Log.e("colorCode", list.get(i).getColor());
                background.setColorFilter(Color.parseColor(list.get(i).getColor()), PorterDuff.Mode.DARKEN);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainFilterLayout.setVisibility(View.GONE);
                    getEvents(Integer.parseInt(itemView.getTag().toString()), 1, 20, new UserData().getLocalization(getActivity()));
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

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        oneDayDecorator.setDate(date.getDate());
        widget.invalidateDecorators();
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                ((HomeActivity) getActivity()).openFragmentFromChild(new EventCalenderFragment(), null);
                break;
        }
    }
}
