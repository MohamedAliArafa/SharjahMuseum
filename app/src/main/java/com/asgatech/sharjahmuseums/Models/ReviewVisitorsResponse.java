package com.asgatech.sharjahmuseums.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by esraa.reda on 10/10/2017.
 */

public class ReviewVisitorsResponse extends RealmObject{
    /**
     * ReviewID : 2
     * Email : 55Y
     * Text : 666UUU
     * Rate : 3
     * Mus_ID : 1
     * Reply :
     * PageTotal : 1
     */

    @PrimaryKey
    private int ReviewID;
    private String Email;
    private String Text;
    private int Rate;
    private int Mus_ID;
    private String Reply;
    private int PageTotal;
    private boolean ShowReply;

    public boolean isShowReply() {
        return ShowReply;
    }

    public void setShowReply(boolean showReply) {
        ShowReply = showReply;
    }

    public int getReviewID() {
        return ReviewID;
    }

    public void setReviewID(int ReviewID) {
        this.ReviewID = ReviewID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getText() {
        return Text;
    }

    public void setText(String Text) {
        this.Text = Text;
    }

    public int getRate() {
        return Rate;
    }

    public void setRate(int Rate) {
        this.Rate = Rate;
    }

    public int getMus_ID() {
        return Mus_ID;
    }

    public void setMus_ID(int Mus_ID) {
        this.Mus_ID = Mus_ID;
    }

    public String getReply() {
        return Reply;
    }

    public void setReply(String Reply) {
        this.Reply = Reply;
    }

    public int getPageTotal() {
        return PageTotal;
    }

    public void setPageTotal(int PageTotal) {
        this.PageTotal = PageTotal;
    }
}
