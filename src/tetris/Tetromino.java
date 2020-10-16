package tetris;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;

public class Tetromino {
    GraphicsGroup shape;
    private double squareSize = 40;
    private List<Rectangle> squares;
    private final List<Integer> xPositions = new ArrayList<Integer>(); //this is in terms of grids like 0, 1, 2, ...
    private final List<Integer> yPositions = new ArrayList<Integer>(); //this is in terms of grids like 0, 1, 2, ... 

     /**
     * This randomly generates a tetromino at position x and y. 
     */
    public Tetromino(int initialX, int initialY){
        
        generateSquares();
        generateRandom();
        setPosition(initialX, initialY);
        drawShape();
    }


    /**
     * Move the Tetromino down by one block.
     */
    public void moveUp(){
        for (int i = 0; i < yPositions.size(); i++){
            yPositions.set(i, yPositions.get(i) - 1);
        }
        drawShape();
    }


    /**
     * Move the Tetromino down by one block.
     */
    public void moveDown(){
        for (int i = 0; i < yPositions.size(); i++){
            yPositions.set(i, yPositions.get(i) + 1);
        }
        drawShape();
    }

    /**
     * Move the Tetromino right by one block.
     */
    public void moveRight(){
        for (int i = 0; i < xPositions.size(); i++){
            xPositions.set(i, xPositions.get(i) + 1);
        }
        drawShape();
    }

    /**
     * Move the Tetromino left by one block.
     */
    public void moveLeft(){
        for (int i = 0; i < xPositions.size(); i++){
            xPositions.set(i, xPositions.get(i) -1);
        }
        drawShape();
    }

    public void addTetrominoToCanvas(CanvasWindow canvas){
        canvas.add(shape);
    }

    public List<Integer> getXPosition(){
        return xPositions;
    }

    public List<Integer> getYPosition(){
        return yPositions;
    }


    public void rotateShape(){
        List<Integer> oldX = List.copyOf(xPositions);
        List<Integer> oldY = List.copyOf(yPositions);
        for (int i = 0; i < xPositions.size(); i++){
            xPositions.set(i, oldY.get(i));
        }
        for (int i = 0; i < xPositions.size(); i++){
            yPositions.set(i, oldX.get(i));
        }
        drawShape();
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


    private void zShape1(){
        Collections.addAll(xPositions, 0, 1, 1, 2);
        Collections.addAll(yPositions, 0, 0, 1, 1);
    }

    private void zShape2(){
        Collections.addAll(xPositions, 0, 1, 1, 2);
        Collections.addAll(yPositions, 1, 0, 1, 0);
    }

    private void lineShape(){
        Collections.addAll(xPositions, 0, 1, 2, 3);
        Collections.addAll(yPositions, 0, 0, 0, 0);
    }

    private void lShape1(){
        Collections.addAll(xPositions, 0, 0, 1, 2);
        Collections.addAll(yPositions, 0, 1, 1, 1);
    }

    private void lShape2(){
        Collections.addAll(xPositions, 0, 1, 2, 2);
        Collections.addAll(yPositions, 1, 1, 1, 0);
    }

    private void squareShape(){
        Collections.addAll(xPositions, 0, 0, 1, 1);
        Collections.addAll(yPositions, 0, 1, 0, 1);
    }

    private void tShape(){
        Collections.addAll(xPositions, 1, 0, 1, 2);
        Collections.addAll(yPositions, 0, 1, 1, 1);
    }

    private void drawShape(){
        for (int i = 0; i < yPositions.size(); i++){
            squares.get(i).setPosition(xPositions.get(i) * squareSize , yPositions.get(i) * squareSize);
        }
    }

    private void generateRandom() {
        Random rand = new Random();
        switch(rand.nextInt(7)) {
            case 0:
                zShape1();
                break;
            case 1:
                zShape2();
                break;
            case 2:
                lShape1();
                break;
            case 3:
                lShape2();
                break;
            case 4:
                squareShape();
                break;
            case 5:
                tShape();
                break;
            case 6:
                lineShape();
                break;
        }
    }

    private void setPosition(int x, int y){
        for (int i = 0; i < xPositions.size(); i++){
            xPositions.set(i, xPositions.get(i) + x);
            yPositions.set(i, yPositions.get(i) + y);
        }
    }

    // private static final List<Provider<Tetromino>> tetrominoGenerators = List.of(
    //     Tetromino::square,
    //     Tetromino::lShape,
    //     ...etc..
    // );


    
}
