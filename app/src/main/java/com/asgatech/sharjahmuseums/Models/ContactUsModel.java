package com.asgatech.sharjahmuseums.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by khaled.badawy on 10/4/2017.
 */

public class ContactUsModel {

    @SerializedName("FB")
    @Expose
    private String fB;
    @SerializedName("Twitter")
    @Expose
    private String twitter;
    @SerializedName("Youtube")
    @Expose
    private String youtube;
    @SerializedName("Instgram")
    @Expose
    private String instgram;
    @SerializedName("ContactUsImage")
    @Expose
    private String contactUsImage;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Latitude")
    @Expose
    private double latitude;
    @SerializedName("Longitute")
    @Expose
    private double longitute;

    public String getFB() {
        return fB;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFB(String fB) {
        this.fB = fB;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getInstgram() {
        return instgram;
    }

    public void setInstgram(String instgram) {
        this.instgram = instgram;
    }

    public String getContactUsImage() {
        return contactUsImage;
    }

    public void setContactUsImage(String contactUsImage) {
        this.contactUsImage = contactUsImage;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitute() {
        return longitute;
    }

    public void setLongitute(double longitute) {
        this.longitute = longitute;
    }
}
