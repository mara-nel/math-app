package com.learnknots.wesslnelson.math_app;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by wesslnelson on 6/18/16.
 *
 *
 *  just some handy drawing functions?
 */
public class Draw {

    public void Draw() {

    }


    public void displayText(Canvas canvas, String text, int xCoord, int yCoord) {
        if (canvas != null && text != null) {
            Paint paint = new Paint();
            paint.setARGB(255, 255, 255, 255);
            canvas.drawText(text, xCoord, yCoord, paint);
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
}

