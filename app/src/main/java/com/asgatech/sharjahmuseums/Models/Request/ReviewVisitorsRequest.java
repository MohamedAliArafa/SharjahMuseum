package com.asgatech.sharjahmuseums.Models.Request;

/**
 * Created by esraa.reda on 10/10/2017.
 */

public class ReviewVisitorsRequest {
    /**
     * ID : 1
     * pagenumber : 1
     * pagesize : 1000
     * lang : 1
     */

    private int ID;
    private int pagenumber;
    private int pagesize;
    private int lang;

    public ReviewVisitorsRequest(int ID, int lang) {
        this.ID = ID;
        this.lang = lang;
        this.pagesize=20;
        this.pagenumber=1;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public int getID() {
        return ID;
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
}
