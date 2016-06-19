package com.learnknots.wesslnelson.math_app.model;

import android.graphics.Bitmap;


/**
 * Created by wesslnelson on 6/18/16.
 */
public class Coin extends Draggable {

    private int cx;     // x coord of the center of coin
    private int cy;     // y coord of the center of coin

    public Coin(Bitmap bitmap, int x, int y, String operation) {

        setBitmap(bitmap);
        setX(x);
        setY(y);
        setHeight(bitmap.getHeight());
        setWidth(bitmap.getWidth());
        setMessage(operation);
        this.cy = y+getHeight()/2;
        this.cx = x+getWidth()/2;

    }

    public void setCx(int x){
        this.cx = x;
    }
    public int getCx() {
        return cx;
    }

    public void setCy(int y){
        this.cy = y;
    }
    public int getCy() {
        return cy;
    }

    public void setCenterCoord(int cx, int cy) {
        this.cx = cx;
        this.cy = cy;
        setX(cx - getWidth()/2);
        setY(cy - getHeight()/2);
    }




}
