package com.asgatech.sharjahmuseums.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mohamed.arafa on 11/5/2017.
 */

public class geoFenceResponseModel {
    @SerializedName("Distance")
    @Expose
    private Integer distance;
    @SerializedName("georeferencList")
    @Expose
    private List<GeoReference> geoReferenceList = null;

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public List<GeoReference> getGeoReferenceList() {
        return geoReferenceList;
    }

    public void setGeoReferenceList(List<GeoReference> geoReferenceList) {
        this.geoReferenceList = geoReferenceList;
    }
}
