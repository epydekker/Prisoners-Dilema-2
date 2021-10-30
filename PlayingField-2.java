/**
 * Assignment 6 -- Prisoner's Dilemma -- 2ip90
 * part PlayingField
 *
 * All extensions have been implemented.
 *
 * @author Mark Shekhtman 1710133
 * @author Erik Dekker 1665049
 * assignment group 155
 * <p>
 * assignment copyright Kees Huizing
 */
import java.awt.*;
import java.util.Random;
import javax.swing.JPanel;
/*
Setting the length and height of the grid
*/
class PlayingField extends JPanel {
    
    private int gridLength = 35;
    private int gridHeight = 35;
    private Patch[][] grid = new Patch[gridLength][gridHeight];
    private double alpha = 1; // defection award factor
    // Boolean to initialise the extra rule
    private boolean extraRule = false;
    // random number genrator
    private static final long SEED = 37L; // seed for random number generator; any number goes
    public static final Random random = new Random(SEED);
    //...

    /**
     * calculate and execute one step in the simulation 
     */
    public void step() {
        // Reverting the colour to its original form if patch is changed
        for (int x = 0; x < gridLength; x++) {
            for (int y = 0; y < gridHeight; y++) {
                if (grid[x][y].getJustChanged()) {
                    if (grid[x][y].isCooperating()) {
                        // Colour changed to orignial if it was changed in the previous round
                        grid[x][y].setBackground(Color.blue);
                    } else {
                        grid[x][y].setBackground(Color.red);
                    }
                }
            }
        }
        // Setting the score to zero
        for (int x = 0; x < gridLength; x++) {
            for (int y = 0; y < gridHeight; y++) {
                grid[x][y].resetScore();
            }
        }
        // Calculating the score of each patch of the grid
        calculateScore();
        // Determing if the patch has to change in the following round
        for (int x = 0; x < gridLength; x++) {
            for (int y = 0; y < gridHeight; y++) {
                determineNextStrategy(x, y);
            }
        }
        // Changing strategies for patches that want to be changed
        for (int x = 0; x < gridLength; x++) {
            for (int y = 0; y < gridHeight; y++) {
                // Boolean set back to false if strategy changed
                if (grid[x][y].getChangeStrategy()) {
                    grid[x][y].toggleStrategy();
                    grid[x][y].setChangeStrategy(false);
                }
            }
        }
    }
    
    private void determineNextStrategy(int x, int y) { // Checking next strategy of the patch
        double highestScore = 0;
        Patch highestScorePatch = grid[x][y];
        // Determines the neighbour with the highest score
        for (Patch neighbour: grid[x][y].neighbours) {
            if (neighbour.getScore() == highestScore && extraRule) {
                highestScorePatch = neighbour;
            } else if (neighbour.getScore() == highestScore) {
                if (random.nextBoolean()) {
                    highestScorePatch = neighbour;
                }
            }
            if (neighbour.getScore() > highestScore) {
                highestScore = neighbour.getScore();
                highestScorePatch = neighbour;
            }
        }
        if (grid[x][y].isCooperating() != highestScorePatch.isCooperating()) {
            grid[x][y].setChangeStrategy(true);
        } else {
            grid[x][y].setChangeStrategy(false);
        }
    }
    private void calculateScore() { // Calculating the score of each patch
        for (int x = 0; x < gridLength; x++) {
            for (int y = 0; y < gridHeight; y++) {
                if (grid[x][y].isCooperating()) {
                    for (Patch neighbour: grid[x][y].neighbours) {
                        if (neighbour.isCooperating() && (neighbour != grid[x][y])) {
                            grid[x][y].scoreIncrement(1.0);
                        }
                    }
                } else {
                    for (Patch neighbour: grid[x][y].neighbours) {
                        if (neighbour.isCooperating() && (neighbour != grid[x][y])) {
                            grid[x][y].scoreIncrement(alpha);
                        }
                    }
                }
            }
        }
    }
    private void addNeighbours(int row, int col) { // Adding the neighbours to each patch
        int rowx;
        int rowy;
        int i = 0;
        for (int x = row - 1; x <= row + 1; x++) {
            for (int y = col - 1; y <= col + 1; y++) {
                rowx = x;
                rowy = y;
                if (x < 0) {
                    rowx = gridLength - 1;
                }
                if (y < 0) {
                    rowy = gridHeight - 1;
                }
                if (x >= gridLength) {
                    rowx = 0;
                }
                if (y >= gridHeight) {
                    rowy = 0;
                }
                grid[row][col].neighbours[i] = grid[rowx][rowy];
                i++;
            }
        }
    }

    public void Neighbours() { // Adding the neighbours to each patch
        for (int x = 0; x < gridLength; x++) {
            for (int y = 0; y < gridHeight; y++) {
                addNeighbours(x, y);
            }
        }
    }
    public int getGridLength() {
        return this.gridLength;
    }
    public int getGridHeight() {
        return this.gridHeight;
    }
    public void fillPatchGrid() { // Filing the grid with patches
        for (int x = 0; x < gridLength; x++) {
            for (int y = 0; y < gridHeight; y++) {
                grid[x][y] = new Patch();
            }
        }
    }
    public void generatePatchGrid() {  // Randomizes the strategies
        for (int x = 0; x < gridLength; x++) {
            for (int y = 0; y < gridHeight; y++) {
                grid[x][y].setCooperating(random.nextBoolean());
            }
        }
    }
    public Patch getPatch(int x, int y) {
        return grid[x][y];
    }
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }
    public double getAlpha() {
        return this.alpha;
    }
    public boolean[][] getGrid() { // Returns the grid (2D array)
        boolean[][] resultGrid = new boolean[grid.length][grid[0].length];
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                resultGrid[x][y] = grid[x][y].isCooperating();
            }
        }
        return resultGrid;
    }
    public void setGrid(boolean[][] inGrid) { // Sets the Grid (dependent on the parameter)
        for (int x = 0; x < inGrid.length; x++) {
            for (int y = 0; y < inGrid[0].length; y++) {
                grid[x][y].setCooperating(inGrid[x][y]);
            }
        }
    }
    public void setExtraRule(boolean x) { // Method for setting the state of the extra rule
        this.extraRule = x;
    }
    public boolean getExtraRule() { // Method for getting extra rule
        return this.extraRule;
    }
}