package com.asgatech.sharjahmuseums.Models;

/**
 * Created by esraa.reda on 10/12/2017.
 */

public class NotificationListRequestModel {
    /**
     * pagenumber : 1
     * pagesize : 1000
     * lang : 1
     * DeviceId : 12
     */

    private int pagenumber;
    private int pagesize;
    private int lang;
    private String DeviceId;

    public NotificationListRequestModel(int lang, String deviceId) {
        this.lang = lang;
        DeviceId = deviceId;
        this.pagenumber=1;
        this.pagesize=20;
    }

    public void setPagenumber(int pagenumber) {
        this.pagenumber = pagenumber;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    public void setDeviceId(String DeviceId) {
        this.DeviceId = DeviceId;
    }

    public int getPagenumber() {
        return pagenumber;
    }

    public int getPagesize() {
        return pagesize;
    }

    public int getLang() {
        return lang;
    }

    public String getDeviceId() {
        return DeviceId;
    }
}
