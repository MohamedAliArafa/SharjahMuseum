package com.asgatech.sharjahmuseums.Tools.CustomFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;

/**
 * Created by halima.reda on 9/11/2017.
 */

public class ButtonLight extends android.support.v7.widget.AppCompatButton {
    public ButtonLight(Context context) {
        super(context);
    }

    public ButtonLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (new UserData().getLocalization(context) == Localization.ARABIC_VALUE) {
            setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/arabic_light.otf"));
        } else {
            setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/english_light.TTF"));
        }

    }
}
