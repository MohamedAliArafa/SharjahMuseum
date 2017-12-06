package com.asgatech.sharjahmuseums.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mohamed.arafa on 10/22/2017.
 */

public class FaciltsEntity extends RealmObject{
    /**
     * FaciltsID : 4
     * Title : غرف أطفال
     * Image : Upload/20170912144145995961.png
     */

    @PrimaryKey
    private int FaciltsID;
    private String Title;
    private String Image;

    public void setFaciltsID(int FaciltsID) {
        this.FaciltsID = FaciltsID;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public int getFaciltsID() {
        return FaciltsID;
    }

    public String getTitle() {
        return Title;
    }

    public String getImage() {
        return Image;
    }


}
