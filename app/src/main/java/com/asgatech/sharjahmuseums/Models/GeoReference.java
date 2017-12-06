package com.asgatech.sharjahmuseums.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mohamed.arafa on 11/5/2017.
 */

public class GeoReference {
    @SerializedName("ID")
    @Expose
    private int iD;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Lat")
    @Expose
    private String lat;
    @SerializedName("Long")
    @Expose
    private String _long;

    public int getID() {
        return iD;
    }

    public void setID(int iD) {
        this.iD = iD;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }
}
