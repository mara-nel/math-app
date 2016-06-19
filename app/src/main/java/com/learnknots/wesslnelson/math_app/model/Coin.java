package com.learnknots.wesslnelson.math_app.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.learnknots.wesslnelson.math_app.Draw;

/**
 * Created by wesslnelson on 6/18/16.
 */
public class Coin extends Draggable {

    private int cx;     // x coord of the center of coin
    private int cy;     // y coord of the center of coin

    private Draw draw;  // used for handy drawing functions

    public Coin(int x, int y, int width, String operation) {

        setX(x);
        setY(y);
        setHeight(width);
        setWidth(width);
        setMessage(operation);
        this.cx = x+width/2;
        this.cy = y+width/2;

        draw  = new Draw();

    }

    public void setCX(int x){
        this.cx = x;
    }
    public int getCx() {
        return cx;
    }


    public void setCY(int y){
        this.cy = y;
    }
    public int getCy() {
        return cy;
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
            draw.displayTextCentered(canvas, getMessage(), cx, cy, getWidth()-20);
        }
    }


}
