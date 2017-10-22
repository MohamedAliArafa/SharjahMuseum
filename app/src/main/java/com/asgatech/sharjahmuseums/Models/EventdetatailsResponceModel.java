package com.asgatech.sharjahmuseums.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventdetatailsResponceModel {

    @SerializedName("Descrption")
    @Expose
    private String descrption;
    @SerializedName("Attach")
    @Expose
    private String attach;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("CatTitle")
    @Expose
    private String catTitle;
    @SerializedName("EventImages")
    @Expose
    private List<EventImage> eventImages = null;
    @SerializedName("EventsID")
    @Expose
    private Integer eventsID;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("StartTimeHours")
    @Expose
    private Integer startTimeHours;
    @SerializedName("StartTimeMin")
    @Expose
    private Integer startTimeMin;
    @SerializedName("EndTimeHours")
    @Expose
    private Integer endTimeHours;
    @SerializedName("EndTimeMin")
    @Expose
    private Integer endTimeMin;
    @SerializedName("CatId")
    @Expose
    private Integer catId;
    @SerializedName("Long")
    @Expose
    private String _long;
    @SerializedName("Lat")
    @Expose
    private String lat;
    @SerializedName("Adress")
    @Expose
    private String adress;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("Url")
    @Expose
    private String url;
    @SerializedName("PageTotal")
    @Expose
    private Integer pageTotal;

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

    public String getCatTitle() {
        return catTitle;
    }

    public void setCatTitle(String catTitle) {
        this.catTitle = catTitle;
    }

    public List<EventImage> getEventImages() {
        return eventImages;
    }

    public void setEventImages(List<EventImage> eventImages) {
        this.eventImages = eventImages;
    }

    public Integer getEventsID() {
        return eventsID;
    }

    public void setEventsID(Integer eventsID) {
        this.eventsID = eventsID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getStartTimeHours() {
        return startTimeHours;
    }

    public void setStartTimeHours(Integer startTimeHours) {
        this.startTimeHours = startTimeHours;
    }

    public Integer getStartTimeMin() {
        return startTimeMin;
    }

    public void setStartTimeMin(Integer startTimeMin) {
        this.startTimeMin = startTimeMin;
    }

    public Integer getEndTimeHours() {
        return endTimeHours;
    }

    public void setEndTimeHours(Integer endTimeHours) {
        this.endTimeHours = endTimeHours;
    }

    public Integer getEndTimeMin() {
        return endTimeMin;
    }

    public void setEndTimeMin(Integer endTimeMin) {
        this.endTimeMin = endTimeMin;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

}
