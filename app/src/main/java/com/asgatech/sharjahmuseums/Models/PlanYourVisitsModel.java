package com.asgatech.sharjahmuseums.Models;

import java.util.List;

/**
 * Created by esraa.reda on 10/8/2017.
 */

public class PlanYourVisitsModel {


    /**
     * PlanViste : [{"PlanID":1,"Title":"Exploring Sharjah's History ","Text":"Exploring Sharjah's History\" package includes:\nMuseums tours that will unveil the glorious history of Sharjah\nMuseums Tour Duration: Sharjah Archeology Museum - 60 minutes, Al Eslah School Museum \u2013 20 minutes, Al Mahatta Museum \u2013 40 minutes, Sharjah Maritime Museum \u2013 40 minutes, Sharjah Classic Cars Museum \u2013 30 minutes, Sharjah Heritage Museum - 40 minutes, Sharjah Fort (Al Hisn)- 60 minutes.\nFor entry fees and working hours kindly check each museum's page.\nSelecting this package is not considered as purchasing entry tickets.\nEntry tickets should be purchased separately at each museum.","Image":"Upload/20171003135852959962.png","BookLink":"jjj"}]
     * PlaneText : testen
     * Grouptext : aaaaaaaa
     * GroupBookLink : http://help.surveygizmo.com/help
     */

    private String PlaneText;
    private String Grouptext;
    private String GroupBookLink;
    private List<PlanVisteEntity> PlanViste;

    public void setPlaneText(String PlaneText) {
        this.PlaneText = PlaneText;
    }

    public void setGrouptext(String Grouptext) {
        this.Grouptext = Grouptext;
    }

    public void setGroupBookLink(String GroupBookLink) {
        this.GroupBookLink = GroupBookLink;
    }

    public void setPlanViste(List<PlanVisteEntity> PlanViste) {
        this.PlanViste = PlanViste;
    }

    public String getPlaneText() {
        return PlaneText;
    }

    public String getGrouptext() {
        return Grouptext;
    }

    public String getGroupBookLink() {
        return GroupBookLink;
    }

    public List<PlanVisteEntity> getPlanVist() {
        return PlanViste;
    }

    public static class PlanVisteEntity {
        /**
         * PlanID : 1
         * Title : Exploring Sharjah's History
         * Text : Exploring Sharjah's History" package includes:
         Museums tours that will unveil the glorious history of Sharjah
         Museums Tour Duration: Sharjah Archeology Museum - 60 minutes, Al Eslah School Museum – 20 minutes, Al Mahatta Museum – 40 minutes, Sharjah Maritime Museum – 40 minutes, Sharjah Classic Cars Museum – 30 minutes, Sharjah Heritage Museum - 40 minutes, Sharjah Fort (Al Hisn)- 60 minutes.
         For entry fees and working hours kindly check each museum's page.
         Selecting this package is not considered as purchasing entry tickets.
         Entry tickets should be purchased separately at each museum.
         * Image : Upload/20171003135852959962.png
         * BookLink : jjj
         */

        private int PlanID;
        private String Title;
        private String Text;
        private String Image;
        private String BookLink;

        public void setPlanID(int PlanID) {
            this.PlanID = PlanID;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public void setText(String Text) {
            this.Text = Text;
        }

        public void setImage(String Image) {
            this.Image = Image;
        }

        public void setBookLink(String BookLink) {
            this.BookLink = BookLink;
        }

        public int getPlanID() {
            return PlanID;
        }

        public String getTitle() {
            return Title;
        }

        public String getText() {
            return Text;
        }

        public String getImage() {
            return Image;
        }

        public String getBookLink() {
            return BookLink;
        }
    }
}
