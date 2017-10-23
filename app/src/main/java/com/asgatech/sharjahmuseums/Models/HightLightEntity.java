package com.asgatech.sharjahmuseums.Models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mohamed.arafa on 10/22/2017.
 */
public class HightLightEntity extends RealmObject implements Parcelable {
    /**
     * HightlightID : 1
     * Mus_ID : 1
     * Title : jkjk
     * Text : yyyi
     * Photo : Upload/201709121410129869611.png
     */

    @PrimaryKey
    private int HightlightID;
    private int Mus_ID;
    private String Title;
    private String Text;
    private String Photo;

    public static final Creator<HightLightEntity> CREATOR = new Creator<HightLightEntity>() {
        @Override
        public HightLightEntity createFromParcel(Parcel in) {
            return new HightLightEntity(in);
        }

        @Override
        public HightLightEntity[] newArray(int size) {
            return new HightLightEntity[size];
        }
    };

    public void setHightlightID(int HightlightID) {
        this.HightlightID = HightlightID;
    }

    public void setMus_ID(int Mus_ID) {
        this.Mus_ID = Mus_ID;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setText(String Text) {
        this.Text = Text;
    }

    public void setPhoto(String Photo) {
        this.Photo = Photo;
    }

    public int getHightlightID() {
        return HightlightID;
    }

    public int getMus_ID() {
        return Mus_ID;
    }

    public String getTitle() {
        return Title;
    }

    public String getText() {
        return Text;
    }

    public String getPhoto() {
        return Photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.HightlightID);
        dest.writeInt(this.Mus_ID);
        dest.writeString(this.Title);
        dest.writeString(this.Text);
        dest.writeString(this.Photo);
    }

    public HightLightEntity() {
    }

    protected HightLightEntity(Parcel in) {
        this.HightlightID = in.readInt();
        this.Mus_ID = in.readInt();
        this.Title = in.readString();
        this.Text = in.readString();
        this.Photo = in.readString();
    }

}
