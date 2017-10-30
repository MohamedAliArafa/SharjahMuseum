package com.asgatech.sharjahmuseums.Models;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;

/**
 * Created by mohamed.arafa on 10/25/2017.
 */

public class EventDecoratorModel {

    private ArrayList<Integer> colors;
    private CalendarDay date;

    public EventDecoratorModel(ArrayList<Integer> colors, CalendarDay dates) {
        this.colors = colors;
        this.date = dates;
    }

    public ArrayList<Integer> getColors() {
        return colors;
    }

    public void setColor(ArrayList<Integer> color) {
        this.colors = color;
    }

    public void addColor(Integer color) {
        this.colors.add(color);
    }

    public CalendarDay getDate() {
        return date;
    }

    public void setDate(CalendarDay date) {
        this.date = date;
    }
}
