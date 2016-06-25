package com.learnknots.wesslnelson.math_app;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wesslnelson on 6/25/16.
 *
 * Class that contains all the necessary math related logic
 */
public class GameMath {

    private static final String TAG = GameMath.class.getSimpleName();


    public GameMath() {

    }

    // Method that returns the closest int to the goal
    // a player can get using the rolled dice and the available ops
    // **currently only coded to handle the use of 2 die and 1 op
    public int getClosestSolution(String[] operations, int[] diceNumbers, int goal) {
        int closestDistance = 9999;

        //gets all permutations of 2 of the included ints
        List<int[]> possibilities = allPermutationsOf2(diceNumbers);

        // iterates over all possible 2 die choices
        for(int[] perm: possibilities) {
            // iterates over each possible bin op
            for (String op: operations) {
                int result = doBinaryOperation(op, perm[0], perm[1]);
                int distance = Math.abs(goal-result);
                if (distance < closestDistance) {
                    closestDistance = distance;
                }
                //Log.d(TAG, Integer.toString(perm[0])+op+Integer.toString(perm[1])+"="+Integer.toString(result));
            }
        }
        return closestDistance;
    }


    // figures out which bin op to use (based off of the string of it)
    // and returns that op done with the two inputted numbers
    // returns int for now (but might need to change)
    public int doBinaryOperation(String binOpString, int num1, int num2) {
        if (binOpString == "+") {
            return num1 + num2;
        } else if (binOpString == "*"){
            return num1 * num2;
        } else if (binOpString == "-") {
            return num1 - num2;
        } else if (binOpString == "^") {
            return num1 ^ num2;
        } else if (binOpString == "%") {
            return num1%num2;
        } else {
            return -9999;
        }
    }

    // Return an integer that ranges from min inclusive to max inclusive.
    public static int rndInt(int min, int max) {
        return (int) (min + Math.random() * (max - min + 1));
    }


    // a method for generating permutations with repetition
    // of length 2 for a String array
    private List<String[]> permutationsWrepLength2(String[] ops) {
        List<String[]> result = new ArrayList<String[]>();

        for (String x: ops) {
            for (String y: ops) {
                String[] perm = {x,y};
                result.add(perm);
            }
        }
        return result;
    }

    // returns all perms of length 2, without replacement
    private List<int[]> allPermutationsOf2(int[] nums) {
        List<int[]> result = new ArrayList<int[]>();

        for (int x=0; x<nums.length; x++) {
            for (int y=0; y<nums.length; y++) {
                if (x!=y) {
                    int[] perm = {nums[x], nums[y]};
                    result.add(perm);
                }
            }
        }

        return result;
    }

}
