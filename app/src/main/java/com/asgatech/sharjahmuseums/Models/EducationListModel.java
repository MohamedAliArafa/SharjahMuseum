package com.asgatech.sharjahmuseums.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by khaled.badawy on 10/4/2017.
 */

public class EducationListModel {

    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Descrption")
    @Expose
    private String descrption;
    @SerializedName("Attach")
    @Expose
    private String attach;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("booklink")
    @Expose
    private String booklink;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBooklink() {
        return booklink;
    }

    public void setBooklink(String booklink) {
        this.booklink = booklink;
    }

}
