package com.learnknots.wesslnelson.math_app.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.learnknots.wesslnelson.math_app.Draw;

/**
 * Created by wesslnelson on 6/18/16.
 */
public class Die extends Draggable{

    private Draw draw;

    public Die(int x, int y, int width, int number) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(width);
        setMessage(Integer.toString(number));

        draw = new Draw();
    }


    public void render(Canvas canvas) {
        if (canvas != null) {
            Paint paint = new Paint();
            paint.setARGB(255, 255, 255, 255);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(getX(),getY(),getX() +getWidth(),getY()+getHeight(), paint);
            draw.displayText(canvas, getMessage(),getX()+10 ,getY()-10+getHeight(),getWidth()-30);

        }
    }
}
