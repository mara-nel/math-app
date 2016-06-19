package com.learnknots.wesslnelson.math_app;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by wesslnelson on 6/18/16.
 *
 *
 *  just some handy drawing functions?
 */
public class Draw {

    public void Draw() {}


    public void displayTextbyWidth(Canvas canvas, String text, int xCoord, int yCoord, int width) {
        if (canvas != null && text != null) {
            Paint paint = new Paint();
            paint.setARGB(255, 255, 255, 255);
            setTextSizeForWidth(paint, width, text);
            canvas.drawText(text, xCoord, yCoord, paint);
        }
    }

    public void displayText(Canvas canvas, String text, int xCoord, int yCoord) {
        if (canvas != null && text != null) {
            Paint paint = new Paint();
            paint.setARGB(255, 255, 255, 255);
            canvas.drawText(text, xCoord, yCoord, paint);
        }
    }


    // draws text but using the center x and center y coord
    public void displayTextCentered(Canvas canvas, String text, int cx, int cy, int desWidth) {
        if (canvas != null && text != null) {
            Paint paint = new Paint();
            paint.setARGB(255, 255, 255, 255);
            setTextSizeForWidth(paint, desWidth, text);
            float height = Math.abs(paint.ascent())+Math.abs(paint.descent());
            canvas.drawText(text, cx - desWidth/2, cy+height/4, paint);
        }

    }

    public void drawHLine(Canvas canvas, int yCoord) {
        if (canvas != null) {
            Paint paint = new Paint();
            paint.setARGB(255, 255, 255, 255);

            canvas.drawLine(0, yCoord, canvas.getWidth(), yCoord, paint);
        }
    }

    public void drawRectangleFilled(Canvas canvas, int x, int y, int width, int height) {
        if (canvas != null) {
            Paint paint = new Paint();
            paint.setARGB(255, 255, 255, 255);
            canvas.drawRect(x,y,x+width, y+height, paint);
        }
    }

    public void drawRectangleOutline(Canvas canvas, int x, int y, int width, int height) {
        if (canvas != null) {
            Paint paint = new Paint();
            paint.setARGB(255, 255, 255, 255);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(x,y,x+width, y+height, paint);
        }
    }


    /**
     * provided by M. Scheper via stackoverflow
     *
     * Sets the text size for a Paint object so a given string of text will be a
     * given width.
     *
     * @param paint
     *            the Paint to set the text size for
     * @param desiredWidth
     *            the desired width
     * @param text
     *            the text that should be that width
     */
    private static void setTextSizeForWidth(Paint paint, float desiredWidth,
                                            String text) {

        // Pick a reasonably large value for the test. Larger values produce
        // more accurate results, but may cause problems with hardware
        // acceleration. But there are workarounds for that, too; refer to
        // http://stackoverflow.com/questions/6253528/font-size-too-large-to-fit-in-cache
        final float testTextSize = 48f;

        // Get the bounds of the text, using our testTextSize.
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        // Calculate the desired size as a proportion of our testTextSize.
        float desiredTextSize = testTextSize * desiredWidth / bounds.width();

        // Set the paint for that size.
        paint.setTextSize(desiredTextSize);
    }
}

