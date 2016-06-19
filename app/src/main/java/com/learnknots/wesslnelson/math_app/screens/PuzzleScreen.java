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
import com.learnknots.wesslnelson.math_app.model.DiceManager;
import com.learnknots.wesslnelson.math_app.model.Die;
import com.learnknots.wesslnelson.math_app.model.SquareHole;

import java.util.ArrayList;
import java.util.List;

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
    private List<Die> diceList;
    private List<SquareHole> sHoleList;
    private DiceManager diceManager;
    private Context context;


    public PuzzleScreen( Context context) {
        this.context = context;
        diceManager = new DiceManager(context);

        draw = new Draw();
        firstCoin = new Coin(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.coin_plus), 300,300, "+");


        diceList = makeDice();

        sHoleList = makeSHoles();

        firstDie = diceManager.getRandomDie();
        firstDie.setCenter(300, 100);
        firstSHole = new SquareHole( BitmapFactory.decodeResource(context.getResources(),
                R.drawable.square_hole64), 100, 500);
        firstCHole = new CircleHole( BitmapFactory.decodeResource(context.getResources(),
                R.drawable.circle_hole), 200, 500);
    }

    public void render(Canvas canvas) {
        draw.displayTextbyWidth(canvas, "testing", 100, 100, 100);
        // draw holes first, otherwise dice would be under the hole
        for (SquareHole shole: sHoleList) {
            shole.render(canvas);
        }
        //firstSHole.render(canvas);
        firstCHole.render(canvas);


        firstCoin.render(canvas);

        for (Die die: diceList) {
            die.render(canvas);
        }


        // just for some looksie  ie not permanent
        if (sHoleList.get(0).hasMessage()) {
            draw.displayText(canvas, sHoleList.get(0).getContainedMessage(), 100, 200);
        }
        if (firstCHole.hasMessage()) {
            draw.displayText(canvas, firstCHole.getContainedMessage(), 100, 210);
        }
        if (sHoleList.get(1).hasMessage()) {
            draw.displayText(canvas, sHoleList.get(1).getContainedMessage(), 100, 220);
        }

    }

    public void update() {

        // sets a hole to empty if necessary
        for (SquareHole shole: sHoleList) {
            int counter = 0;
            for (Die die : diceList) {
                if (shole.dieOverlaps(die)) {
                    counter += 1;
                }
            }
            if (counter == 0) {
                shole.setEmpty(true);
                shole.setContainedMessage(null);
            }
        }
    }
    

    public void onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (Die die: diceList) {
                die.handleActionDown((int) event.getX(), (int) event.getY());
            }

            firstCoin.handleActionDown((int) event.getX(), (int) event.getY());
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if(firstCoin.isTouched()) {
                firstCoin.setCenterCoord((int)event.getX(), (int)event.getY());
                if (!firstCHole.coinOverlaps(firstCoin)) {
                    firstCHole.setEmpty(true);
                    firstCHole.setContainedMessage(null);
                }
            }

            for (Die die: diceList) {
                if (die.isTouched()) {
                    die.setCenter((int)event.getX(), (int)event.getY());
                }
            }

        } if (event.getAction() == MotionEvent.ACTION_UP) {
            // touch was released
            if (firstCoin.isTouched()) {
                firstCoin.setTouched(false);
                firstCHole.snapIfClose(firstCoin);
            }
            for (Die die: diceList) {
                if (die.isTouched()) {
                    die.setTouched(false);
                    for (SquareHole shole : sHoleList) {
                        shole.snapIfClose(die);
                    }
                }
            }

        }
    }


    private List<Die> makeDice() {
        List<Die> dList = new ArrayList<Die>();
        dList.add(diceManager.getRandomDie());
        dList.add(diceManager.getRandomDie());
        dList.get(0).setCenter(300,150);
        dList.get(1).setCenter(300,200);

        return dList;

    }

    private List<SquareHole> makeSHoles() {
        List<SquareHole> shList = new ArrayList<SquareHole>();
        shList.add(new SquareHole( BitmapFactory.decodeResource(context.getResources(),
                R.drawable.square_hole64), 100, 500));
        shList.add(new SquareHole( BitmapFactory.decodeResource(context.getResources(),
                R.drawable.square_hole64), 300, 500));

        return shList;
    }
}
