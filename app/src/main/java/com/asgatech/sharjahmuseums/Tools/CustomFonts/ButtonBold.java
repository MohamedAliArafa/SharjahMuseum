package com.asgatech.sharjahmuseums.Tools.CustomFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;

/**
 * Created by halima.reda on 7/12/2016.
 */

public class ButtonBold extends android.support.v7.widget.AppCompatButton {
    public ButtonBold(Context context) {
        super(context);
    }

    public ButtonBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        if ( UserData.getLocalization(context) == Localization.ARABIC_VALUE) {
            setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/arabic_bold.otf"));
        } else {
            setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/english_bold.TTF"));
        }

    }
}
