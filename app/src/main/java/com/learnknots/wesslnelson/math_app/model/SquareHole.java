package com.learnknots.wesslnelson.math_app.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by wesslnelson on 6/19/16.
 */
public class SquareHole extends Hole {

    private Bitmap bitmap;

    public SquareHole(Bitmap bitmap, int x, int y) {
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

    // if the hole is empty and
    // if the die is overlapping with this hole
    // the die gets snapped into place and hole gets filled
    public void snapIfClose(Die die) {
        if (isEmpty()) {
            if (dieOverlaps(die)) {
                die.setCenter(getCenterX(), getCenterY());
                setEmpty(false);
                setContainedMessage(die.getMessage());
            }
        }

    }

    // returns true if the center of the die is within the hole
    public boolean dieOverlaps(Die die) {
        if (die.getCenterX() > getX() && die.getCenterX() < (getX()+getWidth()) ) {
            if (die.getCenterY() > getY() && die.getCenterY() < (getY()+getHeight()) ) {
                return true;
            }
        }

        return false;
    }

}
