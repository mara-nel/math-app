package com.learnknots.wesslnelson.math_app.model;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by wesslnelson on 6/18/16.
 */
public class Coin extends Draggable {

    private int cx;     // x coord of the center of coin
    private int cy;     // y coord of the center of coin


    public Coin(int x, int y, int width) {

        setX(x);
        setY(y);
        setHeight(width);
        setWidth(width);
        this.cx = x+width/2;
        this.cy = y+width/2;

    }

    public void setCX(int x){
        this.cx = x;
    }
    public void setCY(int y){
        this.cy = y;
    }

    public void setCenterCoord(int cx, int cy) {
        this.cx = cx;
        this.cy = cy;
        setX(cx - getWidth()/2);
        setY(cy - getWidth()/2);
    }

    public void render(Canvas canvas) {
        if (canvas != null) {
            Paint paint = new Paint();
            paint.setARGB(255, 255, 255, 255);
            paint.setStyle(Paint.Style.STROKE);

            canvas.drawCircle(cx,cy,getWidth()/2, paint);
        }
    }


}
