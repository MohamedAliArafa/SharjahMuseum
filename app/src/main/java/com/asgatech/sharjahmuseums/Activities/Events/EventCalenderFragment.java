package com.asgatech.sharjahmuseums.Activities.Events;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asgatech.sharjahmuseums.Adapters.ColorAdapter;
import com.asgatech.sharjahmuseums.Fragments.CalenderHelper.OneDayDecorator;
import com.asgatech.sharjahmuseums.Models.EventCategoryModel;
import com.asgatech.sharjahmuseums.Models.EventModel;
import com.asgatech.sharjahmuseums.Models.NewResponse;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewBold;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventCalenderFragment extends Fragment implements OnDateSelectedListener,
        OnMonthChangedListener, EventsContract.ModelView {

    @BindView(R.id.calendarView)
    CompactCalendarView widget;

    @BindView(R.id.month_tv)
    TextViewBold mMonthTextView;

    @BindView(R.id.main_filter_layout)
    RecyclerView mainFilterLayout;

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
        getEventCategories(UserData.getLocalization(getActivity()));
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
        widget.setUseThreeLetterAbbreviation(true);
        widget.shouldDrawIndicatorsBelowSelectedDays(true);
        mMonthTextView.setText(sdf.format(widget.getFirstDayOfCurrentMonth()));
        widget.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Log.e("dateClicked", dateClicked + "");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", new Locale("en"));
                String date = sdf.format(dateClicked);
                List<EventModel> models = new ArrayList<>();
                List<EventModel> eventModels = Realm.getDefaultInstance()
                        .where(EventModel.class)
                        .findAll();
                for (int i = 0; i < eventModels.size(); i++) {
                    boolean isValid;
                    try {
                        isValid = daysBetween(sdf.parse(eventModels.get(i).getStartDate()).getTime(),
                                sdf.parse(eventModels.get(i).getEndDate()).getTime(), sdf.parse(date).getTime());
                        if (isValid) {
                            models.add(eventModels.get(i));
                        }
                        if (!models.isEmpty())
                            ((EventsFragment) getParentFragment()).setBundle(true);
                        ((EventsFragment) getParentFragment()).openList(models);
                        ((EventsFragment) getParentFragment()).setDate(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
                mMonthTextView.setText(sdf.format(firstDayOfNewMonth));
            }
        });
        return view;
    }

    private void getEventCategories(int langauge) {
        ServerTool.getEventsCategories(getActivity(), langauge, new ServerTool.APICallBack<List<EventCategoryModel>>() {
            @Override
            public void onSuccess(List<EventCategoryModel> response) {
                if (Utils.validList(response)) {
                    if (isAdded())
                        setCategoryData(response);
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
            }
        });
    }

    private void setCategoryData(List<EventCategoryModel> list) {
        mainFilterLayout.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        ColorAdapter colorAdapter = new ColorAdapter(getActivity(), list);
        mainFilterLayout.setAdapter(colorAdapter);
    }

    private void setData(List<EventModel> models) {
        widget.removeAllEvents();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        for (int i = 0; i < models.size(); i++) {
            int color;
            try {
                color = Color.parseColor(models.get(i).getColor());
                int days = daysBetween(sdf.parse(models.get(i).getStartDate()).getTime(), sdf.parse(models.get(i).getEndDate()).getTime());
                Log.e("days", days + "");
                for (int j = 0; j <= days; j++) {
                    if (sdf.parse(models.get(i).getStartDate()).getTime() + ((1000 * 60 * 60 * 24) * j) <= sdf.parse(models.get(i).getEndDate()).getTime()) {
                        Event event = new Event(color, sdf.parse(models.get(i).getStartDate()).getTime() + ((1000 * 60 * 60 * 24) * j), models.get(i).getTitle());
                        widget.addEvent(event, true);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void setDatas(List<NewResponse> models) {
        widget.removeAllEvents();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        for (int i = 0; i < models.size(); i++) {
            int color;
            try {
                color = Color.parseColor(models.get(i).getColor());
                int days = daysBetween(sdf.parse(models.get(i).getStartDate()).getTime(), sdf.parse(models.get(i).getEndDate()).getTime());
                Log.e("days", days + "");
                for (int j = 0; j <= days; j++) {
                    if (sdf.parse(models.get(i).getStartDate()).getTime() + ((1000 * 60 * 60 * 24) * j) <= sdf.parse(models.get(i).getEndDate()).getTime()) {
                        Event eventt = new Event(color, sdf.parse(models.get(i).getStartDate()).getTime() + ((1000 * 60 * 60 * 24) * j), models.get(i).getTitle());
                        widget.addEvent(eventt, true);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public int daysBetween(long d1, long d2) {
        return (int) ((d2 - d1) / (1000 * 60 * 60 * 24));
    }

    public boolean daysBetween(long startDate, long endDate, long currentDate) {
        if (currentDate >= startDate && currentDate <= endDate) {
            return true;
        } else {
            return false;
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
    public void updateView(List<EventModel> models, List<EventCategoryModel> categoryModels) {
        if (models.isEmpty()) {
//            new NoDataDialog().showDialog(getContext());
            setData(Realm.getDefaultInstance()
                    .where(EventModel.class)
                    .findAll());
        }
        setData(models);
    }

    @Override
    public void updateViews(List<NewResponse> models, List<EventCategoryModel> categoryModels) {
        if (models.isEmpty()) {
//            new NoDataDialog().showDialog(getContext());
            setData(Realm.getDefaultInstance()
                    .where(EventModel.class)
                    .findAll());
        }
        setDatas(models);
    }

    @Override
    public void showList() {

    }

    @Override
    public void hideList() {

    }
}
