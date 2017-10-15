package com.asgatech.sharjahmuseums.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by khaled.badawy on 10/3/2017.
 */

public class AboutUsModel {
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("OfficialImage")
    @Expose
    private String officialImage;
    @SerializedName("OfficalName")
    @Expose
    private String officalName;
    @SerializedName("Misson")
    @Expose
    private String misson;
    @SerializedName("Vision")
    @Expose
    private String vision;
    @SerializedName("OfficalWord")
    @Expose
    private String officalWord;
    @SerializedName("Policies")
    @Expose
    private String policies;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOfficialImage() {
        return officialImage;
    }

    public void setOfficialImage(String officialImage) {
        this.officialImage = officialImage;
    }

    public String getOfficalName() {
        return officalName;
    }

    public void setOfficalName(String officalName) {
        this.officalName = officalName;
    }

    public String getMisson() {
        return misson;
    }

    public void setMisson(String misson) {
        this.misson = misson;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getOfficalWord() {
        return officalWord;
    }

    public void setOfficalWord(String officalWord) {
        this.officalWord = officalWord;
    }

    public String getPolicies() {
        return policies;
    }

    public void setPolicies(String policies) {
        this.policies = policies;
    }
}
