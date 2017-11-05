package com.asgatech.sharjahmuseums.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by halima.reda on 9/11/2017.
 */

public class HomeModel {


    @SerializedName("SliderList")
    @Expose
    private List<AllSliderModel> sliderList = null;
    @SerializedName("EventCount")
    @Expose
    private Integer eventCount;
    @SerializedName("NotfactionCount")
    @Expose
    private Integer notfactionCount;

    public List<AllSliderModel> getSliderList() {
        return sliderList;
    }

    public void setSliderList(List<AllSliderModel> sliderList) {
        this.sliderList = sliderList;
    }

    public Integer getEventCount() {
        return eventCount;
    }

    public void setEventCount(Integer eventCount) {
        this.eventCount = eventCount;
    }

    public Integer getNotfactionCount() {
        return notfactionCount;
    }

    public void setNotfactionCount(Integer notfactionCount) {
        this.notfactionCount = notfactionCount;
    }
}
