package com.learnknots.wesslnelson.math_app.screens;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.learnknots.wesslnelson.math_app.Draw;
import com.learnknots.wesslnelson.math_app.R;
import com.learnknots.wesslnelson.math_app.model.CircleHole;
import com.learnknots.wesslnelson.math_app.model.Coin;
import com.learnknots.wesslnelson.math_app.model.Die;
import com.learnknots.wesslnelson.math_app.model.SquareHole;

/**
 * Created by wesslnelson on 6/18/16.
 */
public class PuzzleScreen {

    private static final String TAG = PuzzleScreen.class.getSimpleName();

    private Draw draw;
    private Coin firstCoin;
    private Die  firstDie;
    private SquareHole firstSHole;
    private CircleHole firstCHole;

    public PuzzleScreen( Context context) {

        draw = new Draw();
        firstCoin = new Coin(300,300,60, "+");
        firstDie  = new Die( BitmapFactory.decodeResource(context.getResources(),
                R.drawable.die_1), 300,100, 1);
        firstSHole = new SquareHole( BitmapFactory.decodeResource(context.getResources(),
                R.drawable.square_hole64), 100, 500);
        firstCHole = new CircleHole( BitmapFactory.decodeResource(context.getResources(),
                R.drawable.circle_hole), 200, 500);
    }

    public void render(Canvas canvas) {
        draw.displayTextbyWidth(canvas, "testing", 100, 100, 100);
        firstSHole.render(canvas); // draw holes first, otherwise dice would be under the hole
        firstCHole.render(canvas);



        firstCoin.render(canvas);
        firstDie.render(canvas);

        if (firstSHole.hasMessage()) {
            draw.displayText(canvas, firstSHole.getContainedMessage(), 100, 200);
        }

    }

    public void update() {

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
                if (!firstCHole.coinOverlaps(firstCoin)) {
                    firstCHole.setEmpty(true);
                    firstCHole.setContainedMessage(null);
                }
            }
            if(firstDie.isTouched()) {
                firstDie.setCenter((int)event.getX(), (int)event.getY());
                if (!firstSHole.dieOverlaps(firstDie)) {
                    firstSHole.setEmpty(true);
                    firstSHole.setContainedMessage(null);
                }
            }

        } if (event.getAction() == MotionEvent.ACTION_UP) {
            // touch was released
            if (firstCoin.isTouched()) {
                firstCoin.setTouched(false);
                firstCHole.snapIfClose(firstCoin);
            }
            if (firstDie.isTouched()) {
                firstDie.setTouched(false);
                firstSHole.snapIfClose(firstDie);
            }

        }
    }


}
