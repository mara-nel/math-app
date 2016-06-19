package com.learnknots.wesslnelson.math_app.model;

/**
 *
 * Extendable class that die and coins can be
 * dragged into
 *
 *
 * Created by wesslnelson on 6/19/16.
 */
public class Hole {

    private static final String TAG = Hole.class.getSimpleName();

    private int x;              // x coord of the top left corner
    private int y;              // y coord of the top left corner
    private int width;          // width of the draggable object
    private int height;         // height of the draggable object

    private boolean empty;    // true if there is nothing in the hole

    public Hole() {}

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isEmpty() {
        return empty;
    }
    public void setEmpty(boolean empty) {
        this.empty = empty;
    }



}
