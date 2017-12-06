package com.asgatech.sharjahmuseums.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by mohamed.arafa on 11/7/2017.
 */

public class InnerLocationClass implements Parcelable {
    private List<InnerLocationModel> innerLocationModels ;

    public InnerLocationClass(List<InnerLocationModel> innerLocationModels) {
        this.innerLocationModels = innerLocationModels;
    }

    public InnerLocationClass() {
    }

    public List<InnerLocationModel> getInnerLocationModels() {
        return innerLocationModels;
    }

    public void setInnerLocationModels(List<InnerLocationModel> innerLocationModels) {
        this.innerLocationModels = innerLocationModels;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.innerLocationModels);
    }

    protected InnerLocationClass(Parcel in) {
        this.innerLocationModels = in.createTypedArrayList(InnerLocationModel.CREATOR);
    }

    public static final Parcelable.Creator<InnerLocationClass> CREATOR = new Parcelable.Creator<InnerLocationClass>() {
        @Override
        public InnerLocationClass createFromParcel(Parcel source) {
            return new InnerLocationClass(source);
        }

        @Override
        public InnerLocationClass[] newArray(int size) {
            return new InnerLocationClass[size];
        }
    };
}
