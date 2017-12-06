package com.asgatech.sharjahmuseums.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by mohamed.arafa on 11/7/2017.
 */

public class InnerLocationModel  implements  Parcelable {
     private  double lat ;
    private double lang;
    private String title;
    private List<String>strings;

    public InnerLocationModel() {
    }

    public InnerLocationModel(Parcel in) {
        lat = in.readDouble();
        lang = in.readDouble();
        title = in.readString();
        strings = in.createStringArrayList();
    }

    public static final Creator<InnerLocationModel> CREATOR = new Creator<InnerLocationModel>() {
        @Override
        public InnerLocationModel createFromParcel(Parcel in) {
            return new InnerLocationModel(in);
        }

        @Override
        public InnerLocationModel[] newArray(int size) {
            return new InnerLocationModel[size];
        }
    };

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lang);
        dest.writeString(title);
        dest.writeStringList(strings);
    }
}
