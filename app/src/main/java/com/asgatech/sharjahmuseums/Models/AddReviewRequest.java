package com.asgatech.sharjahmuseums.Models;

/**
 * Created by esraa.reda on 10/11/2017.
 */

public class AddReviewRequest {
    /**
     * Email : ahmed@you
     * Text : jjjrgrgr
     * Rate : 5
     * Mus_ID : 1
     */

    private String Email;
    private String Text;
    private int Rate;
    private int Mus_ID;

    public AddReviewRequest(String email, String text, int rate, int mus_ID) {
        Email = email;
        Text = text;
        Rate = rate;
        Mus_ID = mus_ID;
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
}
