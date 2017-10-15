package com.asgatech.sharjahmuseums.Tools.CustomFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by halima.reda on 1/8/2017.
 */

public class CheckBox extends  android.support.v7.widget.AppCompatCheckBox {
    public CheckBox(Context context) {
        super(context);
    }
    public CheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Arabic_Fonts_Dec_2014_normal.ttf"));

    }
}
