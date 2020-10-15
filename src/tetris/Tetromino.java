package tetris;

import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;

public class Tetromino {
    GraphicsGroup shape;
    List<Rectangle> squares;
    List<Integer> xPositions;
    List<Integer> yPositions;

    public Tetromino(){
        //TODO: Implement method to randomly generate a tetromino consisting of 4 squares
    }

    public void move(){
        //TODO: Implement method to move tetromino down by 1 "block"
    }

    public List<Rectangle> getSquares(){
        return new ArrayList<>(squares);
    }

    public GraphicsGroup getShape(){
        return shape;
    }

    public List<Integer> getXs() {
        return xPositions;
    }

    public List<Integer> getYs() {
        return yPositions;
    }
}
