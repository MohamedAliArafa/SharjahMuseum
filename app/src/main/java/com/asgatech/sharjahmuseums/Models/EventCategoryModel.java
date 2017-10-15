package com.asgatech.sharjahmuseums.Models;

/**
 * Created by khaledbadawy on 9/10/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventCategoryModel {

    @SerializedName("EventCatID")
    @Expose
    private Integer eventCatID;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Color")
    @Expose
    private String color;

    public Integer getEventCatID() {
        return eventCatID;
    }

    public void setEventCatID(Integer eventCatID) {
        this.eventCatID = eventCatID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}