package com.asgatech.sharjahmuseums.Tools.CustomFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by mohamed.arafa on 11/26/2017.
 */

public class TextGeo extends android.support.v7.widget.AppCompatTextView {
    public TextGeo(Context context) {
        super(context);
    }

    public TextGeo(Context context, AttributeSet attrs) {
        super(context, attrs);
            setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/english_light.TTF"));
    }
}