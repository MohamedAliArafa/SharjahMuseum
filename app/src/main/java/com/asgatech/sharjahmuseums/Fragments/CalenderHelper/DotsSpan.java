package com.asgatech.sharjahmuseums.Fragments.CalenderHelper;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import java.util.ArrayList;

/**
 * Span to draw a dot centered under a section of text
 */
public class DotsSpan implements LineBackgroundSpan {

    /**
     * Default radius used
     */
    public static final float DEFAULT_RADIUS = 3;

    /**
     * Default radius used
     */
    public static final float DEFAULT_MARGIN_BETWEEN_DOTS = 3;

    private final float radius;
    private final float marginBetweenDots;
    private final ArrayList<Integer> colors;

    /**
     * Create a span to draw a dot using default radius and color
     *
     * @see #DotsSpan(float, float, ArrayList)
     * @see #DEFAULT_RADIUS
     */
    public DotsSpan() {
        this.radius = DEFAULT_RADIUS;
        this.marginBetweenDots = DEFAULT_MARGIN_BETWEEN_DOTS;
        this.colors = new ArrayList<>();
    }

    /**
     * Create a span to draw a dot using a specified color
     *
     * @param colors color of the dot
     * @see #DotsSpan(float, float, ArrayList<Integer>)
     * @see #DEFAULT_RADIUS
     */
//    public DotsSpan(int... colors) {
//        this.radius = DEFAULT_RADIUS;
//        this.marginBetweenDots = DEFAULT_MARGIN_BETWEEN_DOTS;
//        this.colors = colors;
//    }

    /**
     * Create a span to draw a dot using a specified radius
     *
     * @param radius radius for the dot
     * @see #DotsSpan(float, float, ArrayList<Integer>)
     */
//    public DotsSpan(float radius) {
//        this.radius = radius;
//        this.marginBetweenDots = DEFAULT_MARGIN_BETWEEN_DOTS;
//        this.colors = new int[]{0};
//    }

    /**
     * Create a span to draw a dot using a specified radius and color
     *
     * @param radius radius for the dots
     * @param colors colors of the dots
     */
    public DotsSpan(float radius, float marginBetweenDots, ArrayList<Integer> colors) {
        this.radius = radius;
        this.marginBetweenDots = marginBetweenDots;
        this.colors = colors;
    }

    @Override
    public void drawBackground(
            Canvas canvas, Paint paint,
            int left, int right, int top, int baseline, int bottom,
            CharSequence charSequence,
            int start, int end, int lineNum
    ) {
        int oldColor = paint.getColor();
        final float totalWidth = (colors.size()) * (2 * radius) + ((colors.size() - 1) * marginBetweenDots);
        final float centerPosition = (left + right) / 2;
        float startLeft = centerPosition - (totalWidth / 2);
        for (int color : colors) {
            if (color != 0) {
                paint.setColor(color);
            }
            canvas.drawCircle(startLeft + radius, bottom + radius, radius, paint);
            startLeft = startLeft + (radius * 2) + marginBetweenDots;
        }
        paint.setColor(oldColor);
    }
}

