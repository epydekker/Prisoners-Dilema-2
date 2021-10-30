/**
 * Assignment 6 -- Prisoner's Dilemma -- 2ip90
 * part Patch
 *
 * 
 * @author Mark Shekhtman 1710133
 * @author Erik Dekker 1665049
 * assignment group 155
 * 
 * assignment copyright Kees Huizing
 */

import java.awt.*;

class Patch extends Button {
    // Current state of the patch
    private boolean cooperating;
    private double score;
    private boolean changeStrategy;
    private boolean justChanged;
    // Array that stores all the neighbours of a patch
    Patch[] neighbours = new Patch[9];

    // returns true if and only if patch is cooperating
    boolean isCooperating() {
        return this.cooperating;
    }

    // set strategy to C if isC is true and to D if false
    void setCooperating(boolean isC) {
        this.cooperating = isC;
        if (this.cooperating) {
            this.setBackground(Color.blue);
        } else {
            this.setBackground(Color.red);
        }
    }
    /*
     *change strategy from C to D and vice versa
    */
    void toggleStrategy() {
        if (this.isCooperating()) {
            this.setBackground(Color.white);
        } else {
            this.setBackground(Color.magenta);
        }
        this.cooperating = !cooperating;
        this.justChanged = true;
    }
    /*
     *Returns score of the patch in current round
     */
    double getScore() { 
        return this.score;
    }
    void scoreIncrement(double score) { // Sets the score of the patch
        this.score = this.score + score;
    }
    void resetScore() { // Resets the score of the patch
        this.score = 0.0;
    }
    void setChangeStrategy(boolean x) { // Sets the state of changeStrategy
        this.changeStrategy = x;
    }
    boolean getChangeStrategy() { // Gets the state of changeStrategy
        return this.changeStrategy;
    }
    public boolean getJustChanged() { // Gets the state of justChanged
        return this.justChanged;
    }
}