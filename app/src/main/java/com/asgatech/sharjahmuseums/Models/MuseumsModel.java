package com.asgatech.sharjahmuseums.Models;

import io.realm.annotations.PrimaryKey;

/**
 * Created by halima.reda on 9/12/2017.
 */

public class MuseumsModel {

    /**
     * Mus_ID : 1
     * Title : hh
     * About : kkk
     * Long : 255
     * Lat : 55
     * Eamil : jjj
     * Phone : 22255
     * Url : tttt
     * Adress : hhhhhh
     * Color : #4286f4
     * Image : Upload/201708221785260961.png
     * showPriority : 1
     * CatID : 1
     * PageTotal : 1
     */

    @PrimaryKey
    private int Mus_ID;
    private String Title;
    private String About;
    private String Long;
    private String Lat;
    private String Eamil;
    private String Phone;
    private String Url;
    private String Adress;
    private String Color;
    private String Image;
    private int showPriority;
    private int CatID;
    private int PageTotal;

    public void setMus_ID(int Mus_ID) {
        this.Mus_ID = Mus_ID;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setAbout(String About) {
        this.About = About;
    }

    public void setLong(String Long) {
        this.Long = Long;
    }

    public void setLat(String Lat) {
        this.Lat = Lat;
    }

    public void setEamil(String Eamil) {
        this.Eamil = Eamil;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public void setAdress(String Adress) {
        this.Adress = Adress;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public void setShowPriority(int showPriority) {
        this.showPriority = showPriority;
    }

    public void setCatID(int CatID) {
        this.CatID = CatID;
    }

    public void setPageTotal(int PageTotal) {
        this.PageTotal = PageTotal;
    }

    public int getMus_ID() {
        return Mus_ID;
    }

    public String getTitle() {
        return Title;
    }

    public String getAbout() {
        return About;
    }

    public String getLong() {
        return Long;
    }

    public String getLat() {
        return Lat;
    }

    public String getEamil() {
        return Eamil;
    }

    public String getPhone() {
        return Phone;
    }

    public String getUrl() {
        return Url;
    }

    public String getAdress() {
        return Adress;
    }

    public String getColor() {
        return Color;
    }

    public String getImage() {
        return Image;
    }

    public int getShowPriority() {
        return showPriority;
    }

    public int getCatID() {
        return CatID;
    }

    public int getPageTotal() {
        return PageTotal;
    }
}
