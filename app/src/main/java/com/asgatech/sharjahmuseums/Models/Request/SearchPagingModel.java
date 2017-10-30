package com.asgatech.sharjahmuseums.Models.Request;

/**
 * Created by halima.reda on 9/12/2017.
 */

public class SearchPagingModel {

    /**
     * pagenumber : 1
     * pagesize : 1000
     * lang : 1
     */

    private String SearchKey;
    private int pagenumber;
    private int pagesize;
    private int lang;
    private int catID;

    public SearchPagingModel(int pagenumber, int pagesize, int lang, int catID, String keyword) {
        this.SearchKey = keyword;
        this.catID = catID;
        this.pagenumber = pagenumber;
        this.pagesize = pagesize;
        this.lang = lang;
    }

    public String getKeyword() {
        return SearchKey;
    }

    public String getSearchKey() {
        return SearchKey;
    }

    public void setSearchKey(String searchKey) {
        SearchKey = searchKey;
    }

    public int getCatID() {
        return catID;
    }

    public void setCatID(int catID) {
        this.catID = catID;
    }

    public void setKeyword(String keyword) {
        this.SearchKey = keyword;
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
