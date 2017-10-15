package com.asgatech.sharjahmuseums.Tools.CustomFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;

/**
 * Created by halima.reda on 1/17/2017.
 */

public class TextViewBold extends android.support.v7.widget.AppCompatTextView {
    public TextViewBold(Context context) {
        super(context);
    }

    public TextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (new UserData().getLocalization(context) == Localization.ARABIC_VALUE) {
            setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/arabic_bold.otf"));
        } else {
            setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/english_bold.TTF"));

        }

    }
}
