package com.asgatech.sharjahmuseums.Models;

/**
 * Created by esraa.reda on 10/10/2017.
 */

public class ReviewVisitorsResponse {
    /**
     * ReviewID : 2
     * Email : 55Y
     * Text : 666UUU
     * Rate : 3
     * Mus_ID : 1
     * Reply :
     * PageTotal : 1
     */

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

    public void setReviewID(int ReviewID) {
        this.ReviewID = ReviewID;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setText(String Text) {
        this.Text = Text;
    }

    public void setRate(int Rate) {
        this.Rate = Rate;
    }

    public void setMus_ID(int Mus_ID) {
        this.Mus_ID = Mus_ID;
    }

    public void setReply(String Reply) {
        this.Reply = Reply;
    }

    public void setPageTotal(int PageTotal) {
        this.PageTotal = PageTotal;
    }

    public int getReviewID() {
        return ReviewID;
    }

    public String getEmail() {
        return Email;
    }

    public String getText() {
        return Text;
    }

    public int getRate() {
        return Rate;
    }

    public int getMus_ID() {
        return Mus_ID;
    }

    public String getReply() {
        return Reply;
    }

    public int getPageTotal() {
        return PageTotal;
    }
}
