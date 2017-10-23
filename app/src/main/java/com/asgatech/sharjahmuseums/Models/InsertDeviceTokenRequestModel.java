package com.asgatech.sharjahmuseums.Models;

/**
 * Created by esraa.reda on 10/12/2017.
 */

public class InsertDeviceTokenRequestModel {
    /**
     * DeviceID : abc1
     * DeviceToken : 22cyzn6BtLio0:APA91bFlenjP2qdOj6p3xcOXkPKIadJZatOi5WJYzo-Aqe8bOhBX_go83-R-lpS1q9l0rXy4v7Tvl0f-3fPC6XMrgJqwNFSQ2EJ36ggpdJnjQWiWRKBKfvAF3ATd5rvhUjCr06l4RUv9
     */

    private String DeviceID;
    private String DeviceToken;

    public InsertDeviceTokenRequestModel(String deviceID, String deviceToken) {
        DeviceID = deviceID;
        DeviceToken = deviceToken;
    }

    public void setDeviceID(String DeviceID) {
        this.DeviceID = DeviceID;
    }

    public void setDeviceToken(String DeviceToken) {
        this.DeviceToken = DeviceToken;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public String getDeviceToken() {
        return DeviceToken;
    }
}
