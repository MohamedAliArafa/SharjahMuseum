package com.asgatech.sharjahmuseums.Models;

/**
 * Created by khaledbadawy on 9/13/2017.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class EventImage extends RealmObject{

    @PrimaryKey
    @SerializedName("EventsImageID")
    @Expose
    private Integer eventsImageID;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("EventsID")
    @Expose
    private Integer eventsID;

    public Integer getEventsImageID() {
        return eventsImageID;
    }

    public void setEventsImageID(Integer eventsImageID) {
        this.eventsImageID = eventsImageID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getEventsID() {
        return eventsID;
    }

    public void setEventsID(Integer eventsID) {
        this.eventsID = eventsID;
    }

}
