package com.asgatech.sharjahmuseums.Models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mohamed.arafa on 10/22/2017.
 */

public class PriceCategorySublistEntity extends RealmObject {
    /**
     * sublist : [{"Price":50,"SubCatID":1,"Title":"oooiiiiiiii"}]
     * PriceCatID : 1
     * Title : 20ppppp
     */

    @PrimaryKey
    private int PriceCatID;
    private String Title;
    private RealmList<SublistEntity> sublist;

    public void setPriceCatID(int PriceCatID) {
        this.PriceCatID = PriceCatID;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setSublist(RealmList<SublistEntity> sublist) {
        this.sublist = sublist;
    }

    public int getPriceCatID() {
        return PriceCatID;
    }

    public String getTitle() {
        return Title;
    }

    public RealmList<SublistEntity> getSublist() {
        return sublist;
    }
}

