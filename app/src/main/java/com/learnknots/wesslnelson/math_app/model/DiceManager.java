package com.learnknots.wesslnelson.math_app.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.learnknots.wesslnelson.math_app.R;

import java.util.List;

/**
 * Created by wesslnelson on 6/19/16.
 */
public class DiceManager {

    private Context context; // so that I can access resources

    public DiceManager(Context context) {
        this.context = context;
    }



    public Die getRandomDie() {
        int dieNumber = rndInt(1,6);
        Bitmap dieBitmap = BitmapFactory.decodeResource(context.getResources(), getDieResource(dieNumber));
        Die newDie = new Die(dieBitmap,0,0,dieNumber);
        return newDie;
    }


    // Return an integer that ranges from min inclusive to max inclusive.
    static int rndInt(int min, int max) {
        return (int) (min + Math.random() * (max - min + 1));
    }

    // returns an int[] containing the numbers of each die
    public int[] getDiceNumbers(List<Die> dieList) {
        int[] dieNumbers = new int[dieList.size()];
        for (int x=0; x<dieList.size(); x++) {
            dieNumbers[x] = dieList.get(x).getNumber();
        }

        return dieNumbers;
    }



    // returns the 6 die if a number outside of 1-6 is chosen
    public int getDieResource(int dieNumber) {
        if (dieNumber ==1 ) {
            return R.drawable.die_1;
        } else if (dieNumber == 2) {
            return R.drawable.die_2;
        } else if (dieNumber == 3) {
            return R.drawable.die_3;
        } else if (dieNumber == 4) {
            return R.drawable.die_4;
        } else if (dieNumber == 5) {
            return R.drawable.die_5;
        } else {
            return R.drawable.die_6;
        }
    }
}
