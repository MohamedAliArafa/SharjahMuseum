package com.asgatech.sharjahmuseums.Models;

/**
 * Created by halima.reda on 12/15/2015.
 */
public class NavigationDrawerItem {

    public static final int HEADER_VIEW_TYPE = 1;
    public static final int MENU_TYPE = 2;
    public static final int FOOTER_VIEW_TYPE = 3;
    private int mType;
    private String title;
    private Integer image;

    public NavigationDrawerItem() {
    }

    public NavigationDrawerItem(String name, int type) {
        this.title = name;
        this.mType = type;
    }

    public NavigationDrawerItem(String name, Integer image, int type) {
        this.title = name;
        this.mType = type;
        this.image = image;
    }

    public NavigationDrawerItem(String title) {
        this.title = title;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}
