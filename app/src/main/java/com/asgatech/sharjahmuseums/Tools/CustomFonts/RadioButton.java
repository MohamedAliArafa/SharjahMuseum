package com.asgatech.sharjahmuseums.Tools.CustomFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 *  Created by halima.reda on 7/12/2016.
 */

public class RadioButton extends android.widget.RadioButton {
    public RadioButton(Context context) {
        super(context);
    }
    public RadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Arabic_Fonts_Dec_2014_normal.ttf"));

    }
}
