package com.learnknots.wesslnelson.math_app.model;

/**
 * Created by wesslnelson on 6/18/16.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.learnknots.wesslnelson.math_app.Draw;

/**
 * Created by wesslnelson on 6/4/16.
 *
 * creates a box with text inside, has onClick listeners
 *
 * dont know about sizing drawn text, so there are some inherent min values to width
 * and height that are currently undocumented here
 */
public class TextButton {

    private static final String TAG = TextButton.class.getSimpleName();

    private String text;          // the text that should be displayed
    private Rect sourceRect;         // the rectangle to be drawn from the animation bitmap

    private int buttonWidth;    // the width of the textButton
    private int buttonHeight;   // the height of the textButton

    private int x; // the X coordinate for the top left corner
    private int y; // the y coordinate for the top left corner

    private boolean clicked; // true if the button gets clicked

    private Draw draw; // used for some of its draw functions



    public TextButton(String text, int x, int y, int buttonWidth, int buttonHeight) {

        this.text = text;
        this.x = x;
        this.y = y;
        this.buttonHeight = buttonHeight;
        this.buttonWidth = buttonWidth;
        sourceRect = new Rect(0, 0, buttonWidth, buttonHeight);

        clicked = false;

        draw = new Draw();
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getButtonWidth() { return buttonWidth; }
    public int getButtonHeight() { return buttonHeight; }

    public boolean isClicked() {
        return clicked;
    }
    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }


    public void draw(Canvas canvas) {
        // code for drawing lines and text and shit
        draw.drawRectangleOutline(canvas, getX(), getY(), getButtonWidth(), getButtonHeight());
        // dont know how to get length of text, otherwise I could center text much better
        draw.displayTextbyWidth(canvas, text, getX()+ 5, getY()+getButtonHeight()/2+5, buttonWidth-25);

    }

    public void handleActionDown(int eventX, int eventY) {
        if (eventX >= (x) && (eventX <= (x + getButtonWidth() ))) {
            if (eventY >= (y) && (eventY <= (y + getButtonHeight()))) {
                // button touched
                setClicked(true);
            } else {
                setClicked(false);
            }
        } else {
            setClicked(false);
        }
    }


}

