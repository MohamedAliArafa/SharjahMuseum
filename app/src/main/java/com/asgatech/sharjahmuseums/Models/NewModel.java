package com.asgatech.sharjahmuseums.Models;

/**
 * Created by mohamed.arafa on 11/23/2017.
 */

public class NewModel {
    /*
    {
"Date": "2017-10-30",
"lang": 1,
"Catid":2
}
     */
    private int Catid;
    private int lang;
    private String Date;

    public int getCatid() {
        return Catid;
    }

    public void setCatid(int catid) {
        Catid = catid;
    }

    public int getLang() {
        return lang;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    public String getDate() {
        return Date;
    }

    public NewModel(int catid, int lang, String date) {
        Catid = catid;
        this.lang = lang;
        Date = date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
