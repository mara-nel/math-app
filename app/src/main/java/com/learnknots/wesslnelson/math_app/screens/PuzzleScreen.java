package com.learnknots.wesslnelson.math_app.screens;

import android.app.Activity;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.learnknots.wesslnelson.math_app.Draw;
import com.learnknots.wesslnelson.math_app.model.Coin;
import com.learnknots.wesslnelson.math_app.model.Die;

/**
 * Created by wesslnelson on 6/18/16.
 */
public class PuzzleScreen {

    private static final String TAG = PuzzleScreen.class.getSimpleName();

    private Draw draw;
    private Coin firstCoin;
    private Die  firstDie;

    public PuzzleScreen() {

        draw = new Draw();
        firstCoin = new Coin(300,300,60, "+");
        firstDie  = new Die( 300,100,60, 5);
    }

    public void render(Canvas canvas) {
        draw.displayText(canvas, "testing", 100, 100, 100);
        firstCoin.render(canvas);
        firstDie.render(canvas);

    }



    public void onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            firstCoin.handleActionDown((int) event.getX(), (int) event.getY());
            firstDie.handleActionDown((int) event.getX(), (int) event.getY());
            Log.d(TAG, String.valueOf(firstCoin.isTouched()));
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if(firstCoin.isTouched()) {
                firstCoin.setCenterCoord((int)event.getX(), (int)event.getY());
            }
            if(firstDie.isTouched()) {
                firstDie.setX((int)event.getX());
                firstDie.setY((int)event.getY());
            }

        } if (event.getAction() == MotionEvent.ACTION_UP) {
            // touch was released
            if (firstCoin.isTouched()) {
                firstCoin.setTouched(false);
            }
            if (firstDie.isTouched()) {
                firstDie.setTouched(false);
            }

        }
    }


}
