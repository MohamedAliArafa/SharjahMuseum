package com.asgatech.sharjahmuseums.Models;

import io.realm.RealmObject;

/**
 * Created by mohamed.arafa on 11/23/2017.
 */

public class NewResponse extends RealmObject {
    /*
    "EventsID": 22,
        "Title": "From The Heart to the Pen",
        "StartDate": "2017-10-29T00:00:00",
        "EndDate": "2017-11-30T00:00:00",
        "StartTimeHours": 12,
        "StartTimeMin": 0,
        "EndTimeHours": 18,
        "EndTimeMin": 0,
        "CatId": 1,
        "Long": "55.51734924316406",
        "Lat": "25.401724200763503",
        "Adress": "1",
        "Image": "Upload/20171029124124219616.jpg",
        "color": "#ff0000",
        "Url": "http://www.sharjahmuseums.ae/",
        "PageTotal": 0
     */
    private String Image ;
    private String Adress ;
    private String Lat ;
    private String Long ;
    private int CatId ;
    private int PageTotal ;
    private int EventsID ;
    private int StartTimeMin ;
    private int EndTimeHours ;
    private int EndTimeMin ;
    private int StartTimeHours ;
    private String Title ;
    private String StartDate ;
    private String EndDate ;
    private String color ;
    private String Url ;

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }

    public int getCatId() {
        return CatId;
    }

    public void setCatId(int catId) {
        CatId = catId;
    }

    public int getPageTotal() {
        return PageTotal;
    }

    public void setPageTotal(int pageTotal) {
        PageTotal = pageTotal;
    }

    public int getEventsID() {
        return EventsID;
    }

    public void setEventsID(int eventsID) {
        EventsID = eventsID;
    }

    public int getStartTimeMin() {
        return StartTimeMin;
    }

    public void setStartTimeMin(int startTimeMin) {
        StartTimeMin = startTimeMin;
    }

    public int getEndTimeHours() {
        return EndTimeHours;
    }

    public void setEndTimeHours(int endTimeHours) {
        EndTimeHours = endTimeHours;
    }

    public int getEndTimeMin() {
        return EndTimeMin;
    }

    public void setEndTimeMin(int endTimeMin) {
        EndTimeMin = endTimeMin;
    }

    public int getStartTimeHours() {
        return StartTimeHours;
    }

    public void setStartTimeHours(int startTimeHours) {
        StartTimeHours = startTimeHours;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
