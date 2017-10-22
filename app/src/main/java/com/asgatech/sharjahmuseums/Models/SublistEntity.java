package com.asgatech.sharjahmuseums.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mohamed.arafa on 10/22/2017.
 */

public class SublistEntity extends RealmObject {
    /**
     * Price : 50
     * SubCatID : 1
     * Title : oooiiiiiiii
     */

    @PrimaryKey
    private int Price;
    private int SubCatID;
    private String Title;

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public void setSubCatID(int SubCatID) {
        this.SubCatID = SubCatID;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public int getPrice() {
        return Price;
    }

    public int getSubCatID() {
        return SubCatID;
    }

    public String getTitle() {
        return Title;
    }
}