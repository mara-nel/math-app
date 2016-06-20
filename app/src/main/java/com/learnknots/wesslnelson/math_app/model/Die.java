package com.learnknots.wesslnelson.math_app.model;

import android.graphics.Bitmap;


import com.learnknots.wesslnelson.math_app.Draw;

/**
 * Created by wesslnelson on 6/18/16.
 */
public class Die extends Draggable{

    private Draw draw;
    private int number;

    public Die(Bitmap bitmap, int x, int y, int number) {
        setBitmap(bitmap);
        setX(x);
        setY(y);
        setWidth(bitmap.getWidth());
        setHeight(bitmap.getHeight());
        setMessage(Integer.toString(number));

        this.number = number;
        draw = new Draw();
    }

    public void setCenter(int cx, int cy){
        setX(cx - getWidth()/2);
        setY(cy - getHeight()/2);
    }
    public int getCenterX(){
        return getX()+getWidth()/2;
    }
    public int getCenterY(){
        return getY()+getHeight()/2;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

}
