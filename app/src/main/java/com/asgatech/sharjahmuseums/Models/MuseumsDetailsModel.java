package com.asgatech.sharjahmuseums.Models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by halima.reda on 9/12/2017.
 */

public class MuseumsDetailsModel extends RealmObject {

    @PrimaryKey
    private int Mus_ID;
    private String Title;
    private String About;
    private double Long;
    private double Lat;
    private String Eamil;
    private String Phone;
    private String Url;
    private String Adress;
    private String Color;
    private String Image;
    private int showPriority;
    private int CatID;
    private int PageTotal;
    private RealmList<ImageListEntity> ImageList;
    private RealmList<OpeningHoursListEntity> OpeningHoursList;
    private RealmList<FaciltsEntity> Facilts;
    private RealmList<PriceCategorySublistEntity> PriceCategorySublist;
    private RealmList<HighLightEntity> HightLight;

    public void setMus_ID(int Mus_ID) {
        this.Mus_ID = Mus_ID;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setAbout(String About) {
        this.About = About;
    }

    public void setLong(double Long) {
        this.Long = Long;
    }

    public void setLat(double Lat) {
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

    public void setImageList(RealmList<ImageListEntity> ImageList) {
        this.ImageList = ImageList;
    }

    public void setOpeningHoursList(RealmList<OpeningHoursListEntity> OpeningHoursList) {
        this.OpeningHoursList = OpeningHoursList;
    }

    public void setFacilts(RealmList<FaciltsEntity> Facilts) {
        this.Facilts = Facilts;
    }

    public void setPriceCategorySublist(RealmList<PriceCategorySublistEntity> PriceCategorySublist) {
        this.PriceCategorySublist = PriceCategorySublist;
    }

    public void setHightLight(RealmList<HighLightEntity> HightLight) {
        this.HightLight = HightLight;
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

    public double getLong() {
        return Long;
    }

    public double getLat() {
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

    public RealmList<ImageListEntity> getImageList() {
        return ImageList;
    }

    public RealmList<OpeningHoursListEntity> getOpeningHoursList() {
        return OpeningHoursList;
    }

    public RealmList<FaciltsEntity> getFacilts() {
        return Facilts;
    }

    public RealmList<PriceCategorySublistEntity> getPriceCategorySublist() {
        return PriceCategorySublist;
    }

    public RealmList<HighLightEntity> getHightLight() {
        return HightLight;
    }

}
