package com.asgatech.sharjahmuseums.Activities.Events;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asgatech.sharjahmuseums.Fragments.CalenderHelper.OneDayDecorator;
import com.asgatech.sharjahmuseums.Models.EventCategoryModel;
import com.asgatech.sharjahmuseums.Models.EventModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewBold;
import com.asgatech.sharjahmuseums.Tools.DialogTool.NoDataDialog;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventCalenderFragment extends Fragment implements OnDateSelectedListener,
        OnMonthChangedListener, EventsContract.ModelView {

    @BindView(R.id.calendarView)
    CompactCalendarView widget;

    @BindView(R.id.month_tv)
    TextViewBold mMonthTextView;

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
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
        widget.setUseThreeLetterAbbreviation(true);
        widget.shouldDrawIndicatorsBelowSelectedDays(true);
        mMonthTextView.setText(sdf.format( widget.getFirstDayOfCurrentMonth()));
        widget.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String date = sdf.format(dateClicked);
                ((EventsFragment) getParentFragment()).openList(
                        Realm.getDefaultInstance()
                                .where(EventModel.class)
                                .equalTo("startDate", date)
                                .findAll());
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
                mMonthTextView.setText(sdf.format(firstDayOfNewMonth));
            }
        });
        return view;
    }

    private void setData(List<EventModel> models) {
        widget.removeAllEvents();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        for (int i = 0; i < models.size(); i++) {
            int color;
            try {
                color = Color.parseColor(models.get(i).getColor());
                Event event = new Event(color, sdf.parse(models.get(i).getStartDate()).getTime(), models.get(i).getTitle());
                widget.addEvent(event, true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
        if (models.isEmpty())
            new NoDataDialog().showDialog(getContext());
        setData(models);
    }
}
