package com.asgatech.sharjahmuseums.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by halima.reda on 9/11/2017.
 */

public class AllSliderModel {

    /**
     * Image : Upload/20170822145359436961.png
     */

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Image")
    @Expose
    private String image;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
