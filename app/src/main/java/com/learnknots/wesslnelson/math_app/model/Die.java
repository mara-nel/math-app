package com.learnknots.wesslnelson.math_app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.learnknots.wesslnelson.math_app.Draw;

/**
 * Created by wesslnelson on 6/18/16.
 */
public class Die extends Draggable{

    private Draw draw;
    private Bitmap bitmap;

    public Die(Bitmap bitmap, int x, int y, int number) {
        this.bitmap = bitmap;
        setX(x);
        setY(y);
        setWidth(bitmap.getWidth());
        setHeight(bitmap.getHeight());
        setMessage(Integer.toString(number));

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

    public void render(Canvas canvas) {
        if (canvas != null) {
            /*
            Paint paint = new Paint();
            paint.setARGB(255, 255, 255, 255);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(getX(),getY(),getX() +getWidth(),getY()+getHeight(), paint);
            draw.displayText(canvas, getMessage(),getX()+10 ,getY()-10+getHeight(),getWidth()-30);
            */
            Rect sourceRect = new Rect(0, 0, getWidth(), getHeight());
            Rect destRect = new Rect(getX(), getY(), getX() + getWidth(), getY() + getWidth());
            canvas.drawBitmap(bitmap, sourceRect, destRect, null);

        }
    }
}
