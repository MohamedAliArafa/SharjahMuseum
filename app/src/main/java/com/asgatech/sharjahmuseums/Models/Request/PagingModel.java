package com.asgatech.sharjahmuseums.Models.Request;

/**
 * Created by halima.reda on 9/12/2017.
 */

public class PagingModel {

    /**
     * pagenumber : 1
     * pagesize : 1000
     * lang : 1
     */

    private int pagenumber;
    private int pagesize;
    private int lang;

    public PagingModel(int pagenumber, int pagesize, int lang) {
        this.pagenumber = pagenumber;
        this.pagesize = pagesize;
        this.lang = lang;
    }

    public int getPagenumber() {
        return pagenumber;
    }

    public void setPagenumber(int pagenumber) {
        this.pagenumber = pagenumber;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getLang() {
        return lang;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }
}
