package com.learnknots.wesslnelson.math_app.screens;

import android.graphics.Canvas;

import com.learnknots.wesslnelson.math_app.Draw;

/**
 * Created by wesslnelson on 6/18/16.
 */
public class PuzzleScreen {

    private Draw draw;

    public PuzzleScreen() {

        draw = new Draw();

    }

    public void render(Canvas canvas) {
        draw.displayText(canvas, "testing", 100, 100);
    }
}
