package com.asgatech.sharjahmuseums.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mohamed.arafa on 10/22/2017.
 */

public class ImageListEntity extends RealmObject  {
    /**
     * ImageID : 1
     * Mus_ID : 1
     * Image : Upload/201708221785260961.png
     */
    @PrimaryKey
    private int ImageID;
    private int Mus_ID;
    private String Image;

    public void setImageID(int ImageID) {
        this.ImageID = ImageID;
    }

    public void setMus_ID(int Mus_ID) {
        this.Mus_ID = Mus_ID;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public int getImageID() {
        return ImageID;
    }

    public int getMus_ID() {
        return Mus_ID;
    }

    public String getImage() {
        return Image;
    }



}
