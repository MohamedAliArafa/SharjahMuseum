package com.asgatech.sharjahmuseums.Models;

/**
 * Created by khaledbadawy on 9/13/2017.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DemoImage extends RealmObject{
    @PrimaryKey
    @Expose
    @SerializedName("ID")
    private Integer imageID;

    @Expose
    @SerializedName("Image")
    private String image;

    @Expose
    @SerializedName("ImageEN")
    private String imageEN;

    @Expose
    @SerializedName("Title")
    private String title;

    @Expose
    @SerializedName("Dsecrption")
    private String description;

    public Integer getImageID() {
        return imageID;
    }

    public void setImageID(Integer imageID) {
        this.imageID = imageID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageEN() {
        return imageEN;
    }

    public void setImageEN(String imageEN) {
        this.imageEN = imageEN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
