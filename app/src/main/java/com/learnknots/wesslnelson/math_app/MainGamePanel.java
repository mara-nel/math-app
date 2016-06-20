package com.learnknots.wesslnelson.math_app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.learnknots.wesslnelson.math_app.screens.PuzzleScreen;

/**
 * Created by wesslnelson on 6/15/16.
 */
public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private static final String TAG = MainGamePanel.class.getSimpleName();
    private static final int PUZZLE_STATE = 1;

    private MainThread thread;

    // the fps to be displayed
    private String avgFps;
    public void setAvgFps(String avgFps) {
        this.avgFps = avgFps;
    }

    private PuzzleScreen puzzleScreen;
    private int GAME_STATE;


    public MainGamePanel(Context context) {
        super(context);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);


        // create the main game loop thread
        thread = new MainThread(getHolder(), this);

        // make the GamePanel focusable so it can handle events
        setFocusable(true);

        GAME_STATE = 1;
        puzzleScreen = new PuzzleScreen(getContext());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Surface is being destroyed");
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // try again shutting down the thread®
            }
        }
        Log.d(TAG, "Thread was shut down cleanly");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (GAME_STATE == PUZZLE_STATE) {
            puzzleScreen.onTouchEvent(event);
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // check if in lower part of screen to see if exit
            if (event.getY() > getHeight() - 50) {
                thread.setRunning(false);
                ((Activity)getContext()).finish();
            } else {
                Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
            }

        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            // the gestures

        } if (event.getAction() == MotionEvent.ACTION_UP) {
            // touch was released

        }
        return true;
    }

    public void render(Canvas canvas) {
        canvas.drawColor(Color.DKGRAY);
        puzzleScreen.render(canvas);

    }

    public void update() {
        puzzleScreen.update();
    }


    public void endIt() {
        thread.setRunning(false);
        ((Activity) getContext()).finish();
    }

}
