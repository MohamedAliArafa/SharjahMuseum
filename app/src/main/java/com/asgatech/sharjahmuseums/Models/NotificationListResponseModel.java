package com.asgatech.sharjahmuseums.Models;

/**
 * Created by esraa.reda on 10/12/2017.
 */

public class NotificationListResponseModel {
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

    private int UserNotfactionID;
    private Object DeviceID;
    private boolean IsRead;
    private int NotficationID;
    private String Title;
    private String Text;
    private String Image;
    private int NoticationType;
    private int DestiationID;
    private int PageTotal;

    public void setUserNotfactionID(int UserNotfactionID) {
        this.UserNotfactionID = UserNotfactionID;
    }

    public void setDeviceID(Object DeviceID) {
        this.DeviceID = DeviceID;
    }

    public void setIsRead(boolean IsRead) {
        this.IsRead = IsRead;
    }

    public void setNotficationID(int NotficationID) {
        this.NotficationID = NotficationID;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setText(String Text) {
        this.Text = Text;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public void setNoticationType(int NoticationType) {
        this.NoticationType = NoticationType;
    }

    public void setDestiationID(int DestiationID) {
        this.DestiationID = DestiationID;
    }

    public void setPageTotal(int PageTotal) {
        this.PageTotal = PageTotal;
    }

    public int getUserNotfactionID() {
        return UserNotfactionID;
    }

    public Object getDeviceID() {
        return DeviceID;
    }

    public boolean getIsRead() {
        return IsRead;
    }

    public int getNotficationID() {
        return NotficationID;
    }

    public String getTitle() {
        return Title;
    }

    public String getText() {
        return Text;
    }

    public String getImage() {
        return Image;
    }

    public int getNoticationType() {
        return NoticationType;
    }

    public int getDestiationID() {
        return DestiationID;
    }

    public int getPageTotal() {
        return PageTotal;
    }
}
