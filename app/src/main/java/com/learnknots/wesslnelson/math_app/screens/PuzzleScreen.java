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

    private Draw draw;                      // used for easy access to drawing functions
    private Coin firstCoin;                 // a single coin, will be deprecated
    private CircleHole firstCHole;          // a single hole, will be deprecated
    private List<Die> diceList;             // a list of dice
    private List<SquareHole> sHoleList;     // a list of square holes
    private DiceManager diceManager;        // a class with useful functions for managing dice
    private Context context;                // a context so that resources can be referenced

    private int result;                     // the result of the die and operator combo


    public PuzzleScreen( Context context) {
        this.context = context;
        diceManager = new DiceManager(context);
        result = -9999;

        draw = new Draw();
        firstCoin = new Coin(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.coin_plus), 300,300, "+");


        diceList = makeDice();

        sHoleList = makeSHoles();



        firstCHole = new CircleHole( BitmapFactory.decodeResource(context.getResources(),
                R.drawable.circle_hole), 200, 500);
    }

    public void render(Canvas canvas) {
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
        if (result != -9999 ) {
            draw.displayText(canvas, "=", 100, 230);
            draw.displayText(canvas, Integer.toString(result), 100, 240);
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

        //calculates the result, only works if all holes are full
        if (areAllHolesFilled()) {
            int num1 = Integer.parseInt(sHoleList.get(0).getContainedMessage());
            int num2 = Integer.parseInt(sHoleList.get(1).getContainedMessage());
            // figure out the result of the first bin op
            result = doBinaryOperation(firstCHole.getContainedMessage(), num1, num2);
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
        dList.add(diceManager.getRandomDie());
        dList.get(0).setCenter(100,100);
        dList.get(1).setCenter(200,100);
        dList.get(2).setCenter(300,100);

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

    // returns true if all holes have an object in it
    private boolean areAllHolesFilled() {
        // this counter will be added to whenever a hole is empty
        // if at the end of everything its still 0 then all holes are filled
        int countEmpty = 0;
        for (SquareHole squareHole: sHoleList) {
            if (squareHole.isEmpty()) {
                countEmpty += 1;
            }
        }
        if (firstCHole.isEmpty()) {
            countEmpty += 1;
        }

        if (countEmpty == 0) {
            return true;
        } else {
            return false;
        }
    }

    // figures out which bin op to use (based off of the string of it)
    // and returns that op done with the two inputted numbers
    // returns int for now (but might need to change)
    private int doBinaryOperation(String binOpString, int num1, int num2) {
        if (binOpString == "+") {
            return num1 + num2;
        } else {
            return -9999;
        }
    }

}
