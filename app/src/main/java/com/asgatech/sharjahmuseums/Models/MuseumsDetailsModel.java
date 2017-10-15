package com.asgatech.sharjahmuseums.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List; /**
 * Created by halima.reda on 9/12/2017.
 */

public class MuseumsDetailsModel {

    private int Mus_ID;
    private String Title;
    private String About;
    private double Long;
    private double Lat;
    private String Eamil;
    private String Phone;
    private String Url;
    private String Adress;
    private String Color;
    private String Image;
    private int showPriority;
    private int CatID;
    private int PageTotal;
    private List<ImageListEntity> ImageList;
    private List<OpeningHoursListEntity> OpeningHoursList;
    private List<FaciltsEntity> Facilts;
    private List<PriceCategorySublistEntity> PriceCategorySublist;
    private ArrayList<HightLightEntity> HightLight;

    public void setMus_ID(int Mus_ID) {
        this.Mus_ID = Mus_ID;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setAbout(String About) {
        this.About = About;
    }

    public void setLong(double Long) {
        this.Long = Long;
    }

    public void setLat(double Lat) {
        this.Lat = Lat;
    }

    public void setEamil(String Eamil) {
        this.Eamil = Eamil;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public void setAdress(String Adress) {
        this.Adress = Adress;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public void setShowPriority(int showPriority) {
        this.showPriority = showPriority;
    }

    public void setCatID(int CatID) {
        this.CatID = CatID;
    }

    public void setPageTotal(int PageTotal) {
        this.PageTotal = PageTotal;
    }

    public void setImageList(List<ImageListEntity> ImageList) {
        this.ImageList = ImageList;
    }

    public void setOpeningHoursList(List<OpeningHoursListEntity> OpeningHoursList) {
        this.OpeningHoursList = OpeningHoursList;
    }

    public void setFacilts(List<FaciltsEntity> Facilts) {
        this.Facilts = Facilts;
    }

    public void setPriceCategorySublist(List<PriceCategorySublistEntity> PriceCategorySublist) {
        this.PriceCategorySublist = PriceCategorySublist;
    }

    public void setHightLight(ArrayList<HightLightEntity> HightLight) {
        this.HightLight = HightLight;
    }

    public int getMus_ID() {
        return Mus_ID;
    }

    public String getTitle() {
        return Title;
    }

    public String getAbout() {
        return About;
    }

    public double getLong() {
        return Long;
    }

    public double getLat() {
        return Lat;
    }

    public String getEamil() {
        return Eamil;
    }

    public String getPhone() {
        return Phone;
    }

    public String getUrl() {
        return Url;
    }

    public String getAdress() {
        return Adress;
    }

    public String getColor() {
        return Color;
    }

    public String getImage() {
        return Image;
    }

    public int getShowPriority() {
        return showPriority;
    }

    public int getCatID() {
        return CatID;
    }

    public int getPageTotal() {
        return PageTotal;
    }

    public List<ImageListEntity> getImageList() {
        return ImageList;
    }

    public List<OpeningHoursListEntity> getOpeningHoursList() {
        return OpeningHoursList;
    }

    public List<FaciltsEntity> getFacilts() {
        return Facilts;
    }

    public List<PriceCategorySublistEntity> getPriceCategorySublist() {
        return PriceCategorySublist;
    }

    public ArrayList<HightLightEntity> getHightLight() {
        return HightLight;
    }

    public static class ImageListEntity {
        /**
         * ImageID : 1
         * Mus_ID : 1
         * Image : Upload/201708221785260961.png
         */

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

    public static class OpeningHoursListEntity {
        /**
         * OpenID : 1
         * Mus_ID : 1
         * Title : ALLDAYESAR
         * From : 10
         * To : 8
         * ISCLOSED : false
         */

        private int OpenID;
        private int Mus_ID;
        private String Title;
        private int From;
        private int To;
        private boolean ISCLOSED;

        public void setOpenID(int OpenID) {
            this.OpenID = OpenID;
        }

        public void setMus_ID(int Mus_ID) {
            this.Mus_ID = Mus_ID;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public void setFrom(int From) {
            this.From = From;
        }

        public void setTo(int To) {
            this.To = To;
        }

        public void setISCLOSED(boolean ISCLOSED) {
            this.ISCLOSED = ISCLOSED;
        }

        public int getOpenID() {
            return OpenID;
        }

        public int getMus_ID() {
            return Mus_ID;
        }

        public String getTitle() {
            return Title;
        }

        public int getFrom() {
            return From;
        }

        public int getTo() {
            return To;
        }

        public boolean getISCLOSED() {
            return ISCLOSED;
        }
    }

    public static class FaciltsEntity {
        /**
         * FaciltsID : 4
         * Title : غرف أطفال
         * Image : Upload/20170912144145995961.png
         */

        private int FaciltsID;
        private String Title;
        private String Image;

        public void setFaciltsID(int FaciltsID) {
            this.FaciltsID = FaciltsID;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public void setImage(String Image) {
            this.Image = Image;
        }

        public int getFaciltsID() {
            return FaciltsID;
        }

        public String getTitle() {
            return Title;
        }

        public String getImage() {
            return Image;
        }
    }

    public static class PriceCategorySublistEntity {
        /**
         * sublist : [{"Price":50,"SubCatID":1,"Title":"oooiiiiiiii"}]
         * PriceCatID : 1
         * Title : 20ppppp
         */

        private int PriceCatID;
        private String Title;
        private List<SublistEntity> sublist;

        public void setPriceCatID(int PriceCatID) {
            this.PriceCatID = PriceCatID;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public void setSublist(List<SublistEntity> sublist) {
            this.sublist = sublist;
        }

        public int getPriceCatID() {
            return PriceCatID;
        }

        public String getTitle() {
            return Title;
        }

        public List<SublistEntity> getSublist() {
            return sublist;
        }

        public static class SublistEntity {
            /**
             * Price : 50
             * SubCatID : 1
             * Title : oooiiiiiiii
             */

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
    }

    public static class HightLightEntity implements Parcelable {
        /**
         * HightlightID : 1
         * Mus_ID : 1
         * Title : jkjk
         * Text : yyyi
         * Photo : Upload/201709121410129869611.png
         */

        private int HightlightID;
        private int Mus_ID;
        private String Title;
        private String Text;
        private String Photo;

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

        public static final Parcelable.Creator<HightLightEntity> CREATOR = new Parcelable.Creator<HightLightEntity>() {
            public HightLightEntity createFromParcel(Parcel source) {
                return new HightLightEntity(source);
            }

            public HightLightEntity[] newArray(int size) {
                return new HightLightEntity[size];
            }
        };
    }
}
