package com.learnknots.wesslnelson.math_app.screens;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.learnknots.wesslnelson.math_app.Draw;
import com.learnknots.wesslnelson.math_app.GameMath;
import com.learnknots.wesslnelson.math_app.R;
import com.learnknots.wesslnelson.math_app.model.CircleHole;
import com.learnknots.wesslnelson.math_app.model.Coin;
import com.learnknots.wesslnelson.math_app.model.DiceManager;
import com.learnknots.wesslnelson.math_app.model.Die;
import com.learnknots.wesslnelson.math_app.model.SquareHole;
import com.learnknots.wesslnelson.math_app.model.TextButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wesslnelson on 6/18/16.
 */
public class PuzzleScreen {

    private static final String TAG = PuzzleScreen.class.getSimpleName();

    private Draw draw;                      // used for easy access to drawing functions
    private List<Die> diceList;             // a list of dice
    private List<Coin> coinList;            // a list of coins
    private List<SquareHole> sHoleList;     // a list of the square holes
    private List<CircleHole> cHoleList;     // a list of the circle holes
    private DiceManager diceManager;        // a class with useful functions for managing dice
    private GameMath gameMath;              // a class with math related functions
    private Context context;                // a context so that resources can be referenced
    private TextButton replayButton;        // a button that when clicked starts everything over

    private int goal;                       // the number the player wants to get close to
    private int result;                     // the result of the die and operator combo
    private int closestPossible;            // the closest possible result based off of whats given

    private Boolean isCarrying;             // true if something is being carried/touched
    private Boolean hasWon;                 // true if player has entered a winning solution

    private int coinRowY;                   // the y coordinate for where coins start

    private int dieRowY;                    // the y coordinate for where dice start
    private int dieRowX;                    // the x coordinate for first die
    private int dieSpacing;                 // distance between left coord of one die and another

    private int holeRowY;                   // the y coordinate for where holes go

    public PuzzleScreen( Context context) {
        this.context = context;
        isCarrying = false;
        gameMath = new GameMath();
        diceManager = new DiceManager(context);

        coinRowY = 350; // this should be determined by screen size or something

        dieRowX = 100; // tsbdbss
        dieRowY = 200; // this should be determined by screen size too
        dieSpacing = 200; // tsbdbss

        holeRowY = 700; // tsbdbss


        replayButton = new TextButton("Play Again", 125, holeRowY+600, 250, 75);

        newRound();

        draw = new Draw();


        sHoleList = makeSHoles();
        cHoleList = makeCHoles();
    }

    public void render(Canvas canvas) {
        //testing drawing parenthesis
        draw.displayTextbyWidth(canvas,")", 300,600,40);
        draw.displayTextbyWidth(canvas,"(", 1,600,40);

        // draw holes first, otherwise dice would be under the hole
        for (SquareHole shole: sHoleList) {
            shole.render(canvas);
        }
        for (CircleHole chole: cHoleList) {
            chole.render(canvas);
        }

        for (Coin coin: coinList) {
            coin.render(canvas);
        }
        for (Die die: diceList) {
            die.render(canvas);
        }



        draw.displayText(canvas, Integer.toString(goal), 50, 50);

        if (hasWon) {
            draw.displayTextbyWidth(canvas, "WINNER", 125, holeRowY+400, 250);
            replayButton.draw(canvas);
        }
    }

    public void update() {

        // sets a hole to empty if necessary
        for (SquareHole shole: sHoleList) {
            int numDiceInHole = 0;
            for (Die die : diceList) {
                if (shole.dieOverlaps(die)) {
                    numDiceInHole += 1;
                }
            }
            if (numDiceInHole == 0) { // means no dice are in it
                shole.setEmpty(true);
                shole.setContainedMessage(null);
            }
        }
        for (CircleHole circleHole: cHoleList) {
            int numCoinsInHole = 0;
            for (Coin coin : coinList) {
                if (circleHole.coinOverlaps(coin)) {
                    numCoinsInHole += 1;
                }
            }
            if (numCoinsInHole == 0) { // means no coins in it
                circleHole.setEmpty(true);
                circleHole.setContainedMessage(null);
            }
        }

        // ASSUMES PARENTHESES ARE ON THE LEFT
        //calculates the result, only works if all holes are full
        if (areAllHolesFilled()) {
            int num1 = Integer.parseInt(sHoleList.get(0).getContainedMessage());
            int num2 = Integer.parseInt(sHoleList.get(1).getContainedMessage());
            int num3 = Integer.parseInt(sHoleList.get(2).getContainedMessage());
            // figure out the result of the first bin op
            // currently assumes the parentheses are on the left
            int midresult = gameMath.doBinaryOperation(getFirstOp(), num1, num2);
            // still assumes that p's on left, will need to change this
            result = gameMath.doBinaryOperation(getSecondOp(), midresult, num3);
        }

        if (Math.abs(goal-result) == closestPossible) {
            hasWon = true;
        }
        if (replayButton.isClicked()) {
            newRound();
        }
    }
    

    public void onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!hasWon) {
                for (Die die : diceList) {
                    if (!isCarrying) {
                        die.handleActionDown((int) event.getX(), (int) event.getY());
                        if (die.isTouched()) {
                            isCarrying = true;
                        }
                    }
                }
                for (Coin coin : coinList) {
                    if (!isCarrying) {
                        coin.handleActionDown((int) event.getX(), (int) event.getY());
                        if (coin.isTouched()) {
                            isCarrying = true;
                        }
                    }
                }

                // if tap low enough, resets all dice and coins
                if (event.getY()>holeRowY+200) {
                    organizeObjects();
                }
            } else {
                replayButton.handleActionDown((int) event.getX(), (int) event.getY());
            }

        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            for (Coin coin: coinList) {
                if (coin.isTouched()) {
                    coin.setCenterCoord((int) event.getX(), (int) event.getY());
                }
            }

            for (Die die: diceList) {
                if (die.isTouched()) {
                    die.setCenter((int)event.getX(), (int)event.getY());
                }
            }

        } if (event.getAction() == MotionEvent.ACTION_UP) {
            // touch was released
            for (Coin coin: coinList) {
                if (coin.isTouched()) {
                    coin.setTouched(false);
                    for (CircleHole circleHole: cHoleList) {
                        circleHole.snapIfClose(coin);
                    }
                }
            }
            for (Die die: diceList) {
                if (die.isTouched()) {
                    die.setTouched(false);
                    for (SquareHole shole : sHoleList) {
                        shole.snapIfClose(die);
                    }
                }
            }

            isCarrying = false;

        }
    }


    private List<Die> makeDice() {
        List<Die> dList = new ArrayList<Die>();
        dList.add(diceManager.getRandomDie());
        dList.add(diceManager.getRandomDie());
        dList.add(diceManager.getRandomDie());

        return dList;

    }

    private List<Coin> makeCoins() {
        List<Coin> cList = new ArrayList<Coin>();
        cList.add( new Coin(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.coin_plus), 100,coinRowY, "+") );
        cList.add( new Coin(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.coin_mult), 250,coinRowY, "*"));
        cList.add( new Coin(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.coin_minus), 400,coinRowY, "-"));
        cList.add( new Coin(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.coin_exp), 550,coinRowY, "^"));
        cList.add( new Coin(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.coin_mod), 700,coinRowY, "%"));

        return cList;
    }

    private List<SquareHole> makeSHoles() {
        List<SquareHole> shList = new ArrayList<SquareHole>();
        shList.add(new SquareHole( BitmapFactory.decodeResource(context.getResources(),
                R.drawable.square_hole64), 25, holeRowY));
        shList.add(new SquareHole( BitmapFactory.decodeResource(context.getResources(),
                R.drawable.square_hole64), 200, holeRowY));
        shList.add(new SquareHole( BitmapFactory.decodeResource(context.getResources(),
                R.drawable.square_hole64), 375, holeRowY));
        return shList;
    }

    private List<CircleHole> makeCHoles() {
        List<CircleHole> chList = new ArrayList<CircleHole>();
        chList.add(new CircleHole( BitmapFactory.decodeResource(context.getResources(),
                R.drawable.circle_hole), 120, holeRowY+10));
        chList.add(new CircleHole( BitmapFactory.decodeResource(context.getResources(),
                R.drawable.circle_hole), 295, holeRowY+10));
        return chList;
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
        for (CircleHole circleHole: cHoleList) {
            if (circleHole.isEmpty()) {
                countEmpty += 1;
            }
        }

        if (countEmpty == 0) {
            return true;
        } else {
            return false;
        }
    }

    // resets everything that would need to be reset in the case
    // of starting a new round
    private void newRound() {
        hasWon = false;
        diceList = makeDice();
        organizeObjects();
        replayButton.setClicked(false);
        result = -9999;
        goal = gameMath.rndInt(1,20);
        String[] ops = {"+", "-", "*", "^", "%"}; // should be made not hard coded
        int[] diceNums = diceManager.getDiceNumbers(diceList);
        closestPossible  = gameMath.getClosestSolution(ops, diceNums, goal );
        Log.d(TAG, "closest: "+closestPossible);
    }

    // returns the operation found in the coin hole
    // that is contained by the parentheses
    private String getFirstOp() {
        // probably something that checks where parenth's are
        // then act accordingly

        // just assuming parenth's are on the left for now
        return  cHoleList.get(0).getContainedMessage();
    }

    // assumes p's on left
    private String getSecondOp() {
        return  cHoleList.get(1).getContainedMessage();
    }


    // puts all the objects in the original spot
    private void organizeObjects() {
        coinList = makeCoins(); // just remake them
        diceList.get(0).setCenter(dieRowX, dieRowY);
        diceList.get(1).setCenter(dieRowX+dieSpacing,dieRowY);
        diceList.get(2).setCenter(dieRowX+(dieSpacing*2),dieRowY);

    }
}
