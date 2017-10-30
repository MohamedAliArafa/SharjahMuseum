package com.asgatech.sharjahmuseums.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by esraa.reda on 10/12/2017.
 */

public class NotificationListResponseModel extends RealmObject {
    /**
     * UserNotfactionID : 27
     * DeviceID : null
     * IsRead : false
     * NotficationID : 0
     * Title : frrertrtrt
     * Text : null
     * Image : Upload/2017101010440905961.png
     * NoticationType : 1
     * DestiationID : 0
     * PageTotal : 1
     */

    @PrimaryKey
    private int UserNotfactionID;
    private String DeviceID;
    private boolean IsRead;
    private int NotficationID;
    private String Title;
    private String Text;
    private String Image;
    private int NoticationType;
    private int DestiationID;
    private int PageTotal;

    public int getUserNotfactionID() {
        return UserNotfactionID;
    }

    public void setUserNotfactionID(int UserNotfactionID) {
        this.UserNotfactionID = UserNotfactionID;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String DeviceID) {
        this.DeviceID = DeviceID;
    }

    public boolean getIsRead() {
        return IsRead;
    }

    public void setIsRead(boolean IsRead) {
        this.IsRead = IsRead;
    }

    public int getNotficationID() {
        return NotficationID;
    }

    public void setNotficationID(int NotficationID) {
        this.NotficationID = NotficationID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getText() {
        return Text;
    }

    public void setText(String Text) {
        this.Text = Text;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public int getNoticationType() {
        return NoticationType;
    }

    public void setNoticationType(int NoticationType) {
        this.NoticationType = NoticationType;
    }

    public int getDestiationID() {
        return DestiationID;
    }

    public void setDestiationID(int DestiationID) {
        this.DestiationID = DestiationID;
    }

    public int getPageTotal() {
        return PageTotal;
    }

    public void setPageTotal(int PageTotal) {
        this.PageTotal = PageTotal;
    }
}
