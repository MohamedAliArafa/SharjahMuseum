package com.asgatech.sharjahmuseums.Models.Request;

/**
 * Created by esraa.reda on 10/12/2017.
 */

public class HomeSliderRequestModel {
    /**
     * DeviceId : abc1
     * lang : 22cyzn6BtLio0:APA91bFlenjP2qdOj6p3xcOXkPKIadJZatOi5WJYzo-Aqe8bOhBX_go83-R-lpS1q9l0rXy4v7Tvl0f-3fPC6XMrgJqwNFSQ2EJ36ggpdJnjQWiWRKBKfvAF3ATd5rvhUjCr06l4RUv9
     */

    private String DeviceId;
    private int lang;

    public HomeSliderRequestModel(String deviceID, int lang) {
        DeviceId = deviceID;
        this.lang = lang;
    }

    public void setDeviceId(String DeviceID) {
        this.DeviceId = DeviceID;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public int getLang() {
        return lang;
    }
}
