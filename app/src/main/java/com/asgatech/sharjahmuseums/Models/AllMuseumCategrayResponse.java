package com.asgatech.sharjahmuseums.Models;

/**
 * Created by esraa.reda on 10/18/2017.
 */

public class AllMuseumCategrayResponse  {
    /**
     * ID : 1
     * Image : Upload/2017100515558131962.png
     * Title : متاحف  الفنون
     */

    private int ID;
    private String Image;
    private String Title;

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public int getID() {
        return ID;
    }

    public String getImage() {
        return Image;
    }

    public String getTitle() {
        return Title;
    }

}
