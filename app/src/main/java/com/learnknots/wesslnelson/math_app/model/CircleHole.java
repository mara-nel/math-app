package com.learnknots.wesslnelson.math_app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by wesslnelson on 6/19/16.
 */
public class CircleHole extends Hole {

    private Bitmap bitmap;

    public CircleHole(Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        setWidth(bitmap.getWidth());
        setHeight(bitmap.getHeight());
        setX(x);
        setY(y);
    }

    public void render(Canvas canvas) {
        if (canvas != null) {
            Rect sourceRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            Rect destRect = new Rect(getX(), getY(), getX() + getWidth(), getY() + getWidth());
            canvas.drawBitmap(bitmap, sourceRect, destRect, null);
        }

    }



}
