package com.asgatech.sharjahmuseums.Fragments.CalenderHelper;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

/**
 * Created by khaledbadawy on 9/3/2017.
 */

public class MyCustomOrangeSpan implements LineBackgroundSpan {
    int color , radius;

    public MyCustomOrangeSpan(int color ,int radius) {
        this.color = color;
        this.radius =radius;

    }

    @Override
    public void drawBackground(Canvas canvas, Paint paint, int left, int right, int top, int baseline,
                               int bottom, CharSequence text, int start, int end, int lnum) {

        int oldColor = paint.getColor();
        if (color != 0) {
            paint.setColor(color);
        }

        canvas.drawCircle((left + right) / 2 - 20, bottom + radius, radius, paint);
        paint.setColor(oldColor);

    }
}
