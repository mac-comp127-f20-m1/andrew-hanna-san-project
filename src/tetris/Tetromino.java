package tetris;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;

public class Tetromino {
    GraphicsGroup shape;
    private double squareSize = 20;
    List<Rectangle> squares;
    List<Integer> xPositions; //this is in terms of grids like 0, 1, 2, ...
    List<Integer> yPositions; //this is in terms of grids like 0, 1, 2, ... 

    public Tetromino(CanvasWindow canvas){

        generateSquares();
        lShape1();

        canvas.add(shape);
        canvas.draw();
        //TODO: Implement method to randomly generate a tetromino consisting of 4 squares
    }

    public void move(){
        for (Rectangle square: squares){
            shape.moveBy(squareSize, 0);
        }
        for (Integer y : yPositions){
            y = y + 1;
        }
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


    private void generateSquares(){
        squares = new ArrayList<Rectangle>();
        shape = new GraphicsGroup();
        Collections.addAll(squares, new Rectangle(0, 0, squareSize, squareSize),
        new Rectangle(0, 0, squareSize, squareSize),
        new Rectangle(0, 0, squareSize, squareSize),
        new Rectangle(0, 0, squareSize, squareSize));
        for (Rectangle sqaure: squares){
            shape.add(sqaure);
        }
    }


    private void Line1(){
        xPositions = new ArrayList<Integer>();
        yPositions = new ArrayList<Integer>();

        int i = 0;
        for (Rectangle square: squares){
            square.setPosition(i * squareSize,0);
            xPositions.add(i);
            yPositions.add(0);
            i++;
        }
    }

    private void Line2(){
        xPositions = new ArrayList<Integer>();
        yPositions = new ArrayList<Integer>();

        int i = 0;
        for (Rectangle square: squares){
            square.setPosition(0, i*squareSize);
            xPositions.add(0);
            yPositions.add(i);
            i++;
        }
    }

    private void square(){
        xPositions = new ArrayList<Integer>();
        Collections.addAll(xPositions, 0, 1, 0, 1);
        yPositions = new ArrayList<Integer>();
        Collections.addAll(yPositions, 0, 0, 1, 1);
        squares.get(0).setPosition(0,0);
        squares.get(1).setPosition(squareSize,0);
        squares.get(2).setPosition(0, squareSize);
        squares.get(3).setPosition(squareSize, squareSize);
    }
    
    private void zShape1(){
        xPositions = new ArrayList<Integer>();
        Collections.addAll(xPositions, 0, 1, 1, 2);
        yPositions = new ArrayList<Integer>();
        Collections.addAll(yPositions, 0, 0, 1, 1);
        squares.get(0).setPosition(0, 0);
        squares.get(1).setPosition(squareSize,0);
        squares.get(2).setPosition(squareSize, squareSize);
        squares.get(3).setPosition(2 * squareSize, squareSize);
    }

    private void lShape1(){
        xPositions = new ArrayList<Integer>();
        Collections.addAll(xPositions, 0, 0, 0, 1);
        yPositions = new ArrayList<Integer>();
        Collections.addAll(yPositions, 0, 1, 2, 2);
        squares.get(0).setPosition(0, 0);
        squares.get(1).setPosition(0, squareSize);
        squares.get(2).setPosition(0, 2* squareSize);
        squares.get(3).setPosition( squareSize, 2 * squareSize);
    }

}
