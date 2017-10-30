package com.asgatech.sharjahmuseums.Fragments.CalenderHelper;

import com.asgatech.sharjahmuseums.Models.EventDecoratorModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Decorate several days with a dot
 */
public class EventDecorator implements DayViewDecorator {

    private ArrayList<Integer> colors = new ArrayList<>();
    private HashSet<CalendarDay> dates;

    public EventDecorator(ArrayList<Integer> colors, Collection<CalendarDay> dates) {
        this.colors = colors;
        this.dates = new HashSet<>(dates);
    }

    public EventDecorator(ArrayList<EventDecoratorModel> decoratorModels) {
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotsSpan(4, 2, colors));
    }
}
