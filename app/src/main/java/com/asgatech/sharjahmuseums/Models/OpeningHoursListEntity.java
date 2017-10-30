package com.asgatech.sharjahmuseums.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mohamed.arafa on 10/22/2017.
 */

public class OpeningHoursListEntity extends RealmObject {
    /**
     * OpenID : 1
     * Mus_ID : 1
     * Title : ALLDAYESAR
     * From : 10
     * To : 8
     * ISCLOSED : false
     */

    @PrimaryKey
    private int OpenID;
    private int Mus_ID;
    private String Title;
    private int From;
    private int FormMinute;
    private int To;
    private int ToMinute;
    private boolean ISCLOSED;

    public void setOpenID(int OpenID) {
        this.OpenID = OpenID;
    }

    public void setMus_ID(int Mus_ID) {
        this.Mus_ID = Mus_ID;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setFrom(int From) {
        this.From = From;
    }

    public void setTo(int To) {
        this.To = To;
    }

    public void setISCLOSED(boolean ISCLOSED) {
        this.ISCLOSED = ISCLOSED;
    }

    public int getOpenID() {
        return OpenID;
    }

    public int getMus_ID() {
        return Mus_ID;
    }

    public String getTitle() {
        return Title;
    }

    public int getFormMinute() {
        return FormMinute;
    }

    public void setFormMinute(int formMinute) {
        FormMinute = formMinute;
    }

    public int getToMinute() {
        return ToMinute;
    }

    public void setToMinute(int toMinute) {
        ToMinute = toMinute;
    }

    public boolean isISCLOSED() {
        return ISCLOSED;
    }

    public int getFrom() {
        return From;
    }

    public int getTo() {
        return To;
    }

    public boolean getISCLOSED() {
        return ISCLOSED;
    }
}
