package com.learnknots.wesslnelson.math_app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by wesslnelson on 6/18/16.
 *
 *
 * Class that includes the needed functions for an
 * object that can be dragged by a user
 *
 */
public class Draggable {

    private static final String TAG = Draggable.class.getSimpleName();

    private int x;          // x coord of the top left corner
    private int y;          // y coord of the top left corner
    private int width;      // width of the draggable object
    private int height;     // height of the draggable object
    private String message; // message, what is often displayed inside drawn object

    private Bitmap bitmap;  // the bitmap of the draggable object

    private boolean touched; // true if the draggable is touched/picked up



    public Draggable(int x, int y, int width, int height){

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    public Draggable() {
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
        this.message = null;
        this.bitmap = null;
    }

    public void setX(int x) {
        this.x = x;
    }
    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public int getY() {
        return y;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public int getHeight() {
        return height;
    }

    public boolean isTouched() {
        return touched;
    }
    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    public void handleActionDown(int eventX, int eventY) {
        if (eventY >= (y) && (eventY <= (y + height))) {
            if (eventX >= (x) && (eventX <= (x + width))) {
                // draggable touched
                setTouched(true);
            } else {
                setTouched(false);
            }
        } else {
            setTouched(false);
        }
    }


    public void render(Canvas canvas) {
        if (canvas != null) {
            Rect sourceRect = new Rect(0, 0, getWidth(), getHeight());
            Rect destRect = new Rect(getX(), getY(), getX() + getWidth(), getY() + getWidth());
            canvas.drawBitmap(getBitmap(), sourceRect, destRect, null);
        }
    }


}
