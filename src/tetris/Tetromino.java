package tetris;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;

/**
 * This class takes care of randomly generating a tetromino, moving it down, left, right
 * acording to the rule of the game.
 * 
 */
public class Tetromino {
    public static final List<Color> COLORS = List.of(
        Color.GREEN,
        Color.RED,
        Color.ORANGE,
        Color.BLUE,
        Color.MAGENTA,
        Color.YELLOW,
        Color.CYAN
    );
    GraphicsGroup shape;
    private int squareSize;
    private List<Square> squares;
    private int type;
    private int rotationPointX; 
    private int rotationPointY;


    /**
     * This randomly generates a tetromino at position x and y. Note that x 
     * and y has unit of square.
     */
    public Tetromino(int initialX, int initialY, int squareSize) {
        this.squareSize = squareSize;
        squares = new ArrayList<>();
        generateRandom();
        if (type != 6){
            rotationPointY = 1;
            rotationPointX = 1;
        } else {
            rotationPointX = 1;
            rotationPointY = 0;
        }
        moveBy(initialX, initialY);
    }

    /**
     * Move the Tetromino down by one block if it is still within bounds 
     * and if there is no object below it.  
     */
    public void moveDown(Board board) {
        if (!checkCollision(board, 0, 1)) {
           moveBy(0,1);
        }
    }

    /**
     * Move the Tetromino right by one block if it is still within bounds 
     * and if there is no object to its right.
     */
    public void moveRight(Board board) {
        if (!checkCollision(board, 1, 0)) {
            moveBy(1, 0);
        }
    }

    /**
     * Move the Tetromino right by one block if it is still within bounds 
     * and if there is no object to its left.
     */
    public void moveLeft(Board board) {
        if (!checkCollision(board, -1, 0)) {
            moveBy(-1, 0);
        }
    }

    /**
     * Checks collision with tetrominos that have already landed,
     * i.e. that are on the board, and the walls.
     * @param board The Board Object to check for collision with
     * @param xOffset The distance away from the current x position to check.
     * @param yOffset The distance away from the current y position to check.
     * @return
     */
    public boolean checkCollision(Board board, int xOffset, int yOffset){
        for(Square square : squares) {
            int x = square.getX();
            int y = square.getY();
            if (y + yOffset >= board.getGrid().size() ||
                x + xOffset >= board.getGrid().get(y).size() ||
                x + xOffset < 0 ||
                board.getGrid().get(y + yOffset).get(x + xOffset)
            ){
                return true;
            }
        }
        return false;
    }

    /**
     * Takes in a board, this method rotates the tetromino 90 degrees clockwise if there is 
     * space to rotate (within bounds and not rotating over the other tetrominoes in 
     * the board).
     */
    public void rotateShape(Board board) {
        // First, check that tetromino is not of shape Square.
        if (type != 5) {
            List<Integer> oldX = getOldXs();
            List<Integer> oldY = getOldYs();
            // Now, check if we rotate it, it doesn't collide with the wall or tetromino.
            if (checkWallCollisionAfterRotation(board) || checkBoardCollisionAfterRotation(board)){
                return;
            }
            // Rotation of 90 degrees around the rotatioal point is given by a formula:
            // newX = rotationPointX + rotationPointY - oldY;
            // newY = rotationPointY + rotationPointX - oldX;
            for (int i = 0; i < squares.size(); i++) {
                squares.get(i).setX(rotationPointX + rotationPointY - oldY.get(i));
                squares.get(i).setY(rotationPointY - rotationPointX + oldX.get(i));
            }
            drawShape();
        }
    }

     /**
     * This method returns true if the new position of each square of the tetrimono after
     * rotation is out of bounds.
     */
    private Boolean checkWallCollisionAfterRotation(Board board){
        List<Integer> oldY = getOldYs();
        List<Integer> oldX = getOldXs();
        for (int i = 0; i < squares.size(); i++) {
            if  (rotationPointX + rotationPointY - oldY.get(i) < 0
                || rotationPointX + rotationPointY - oldY.get(i) >= board.getWidth()
                || rotationPointY - rotationPointX + oldX.get(i) >= board.getGrid().size()
                || rotationPointY - rotationPointX + oldX.get(i) < 0){
                    return true;
                }
        }
        return false;
    }

    /**
     * This method returns true if the new position of each square of the current tetrimono after
     * rotation is occupied by other tetrominoes already added to the board.
     */
    private Boolean checkBoardCollisionAfterRotation(Board board){
        List<Integer> oldX = getOldXs();
        List<Integer> oldY = getOldYs();
        for (int i = 0; i < squares.size(); i++) {
            if (board.getGrid().get(rotationPointY - rotationPointX + oldX.get(i)).get(rotationPointX + rotationPointY - oldY.get(i))){
                return true;
            }
        }
        return false;
    }

    /**
     * This methods returns an unmodifiable list of the current x position
     * of each square that makes up the tetromino.
     * 
     */
    private List<Integer> getOldXs() {
        List<Integer> result = new ArrayList<>();
        for (Square square : squares) {
            result.add(square.getX());
        }
        return Collections.unmodifiableList(result);
    }
    /**
     * This methods returns an unmodifiable list of the current y position
     * of each square that makes up the tetromino.
     * 
     */
    private List<Integer> getOldYs() {
        List<Integer> result = new ArrayList<>();
        for (Square square : squares) {
            result.add(square.getY());
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * This methods add 4 square objects to the list of squares to make
     * a tetromino of type z Shape 1.
     * 
     */
    private void zShape1() {
        squares.add(new Square(0, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(2, 1, new Rectangle(0, 0, squareSize, squareSize)));
    }

    /**
     * This methods add 4 square objects to the list of squares to make
     * a tetromino of type z Shape 2.
     * 
     */
    private void zShape2() {
        squares.add(new Square(0, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(2, 0, new Rectangle(0, 0, squareSize, squareSize)));
    }

    /**
     * This methods add 4 square objects to the list of squares to make
     * a tetromino of type line Shape.
     * 
     */
    private void lineShape() {
        squares.add(new Square(0, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(2, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(3, 0, new Rectangle(0, 0, squareSize, squareSize)));
    }

    /**
     * This methods add 4 square objects to the list of squares to make
     * a tetromino of type L Shape 1.
     * 
     */
    private void lShape1() {
        squares.add(new Square(0, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(0, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(2, 1, new Rectangle(0, 0, squareSize, squareSize)));
    }

    /**
     * This methods add 4 square objects to the list of squares to make
     * a tetromino of type L Shape 2.
     * 
     */
    private void lShape2() {
        squares.add(new Square(0, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(2, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(2, 0, new Rectangle(0, 0, squareSize, squareSize)));
    }

    /**
     * This methods add 4 square objects to the list of squares to make
     * a tetromino of type square Shape.
     * 
     */
    private void squareShape() {
        squares.add(new Square(0, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(0, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 1, new Rectangle(0, 0, squareSize, squareSize)));
    }

    /**
     * This methods add 4 square objects to the list of squares to make
     * a tetromino of type T Shape.
     * 
     */
    private void tShape() {
        squares.add(new Square(1, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(0, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(2, 1, new Rectangle(0, 0, squareSize, squareSize)));
    }


    /**
     * This methods draw the tetromino using the x and y position of each square
     * that makes up the tetromino.
     * 
     */
    private void drawShape() {
        for (int i = 0; i < squares.size(); i++) {
            int x = squares.get(i).getX();
            int y = squares.get(i).getY();
            Rectangle rect = squares.get(i).getRectangle();
            rect.setPosition(x * squareSize, y * squareSize);
        }
    }

    /**
     * This method randomly generates a tetromino of a certain type with
     * its corresponding color.
     * 
     */
    private void generateRandom() {
        type = new Random().nextInt(7);
        switch (type) {
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
                tShape();
                break;
            case 5:
                squareShape();
                break;
            case 6:
                lineShape();
                break;
        }
        shape = new GraphicsGroup();
        for (Square square : squares) {
            shape.add(square.getRectangle());
            square.getRectangle().setFillColor(COLORS.get(type));
        }
    }

    /**
     * Returns the graphics that represent the Tetromino.
     */
    public GraphicsGroup getShape() {
        return shape;
    }

    /**
     * Returns the list of Square objects that make up the Tetromino.
     */
    public List<Square> getSquares() {
        return squares;
    }

    /**
     * Move the tetromino to the given x and y.
     * 
     */
    private void moveBy(int x, int y) {
        for (int i = 0; i < squares.size(); i++) {
            int oldX = squares.get(i).getX();
            int oldY = squares.get(i).getY();
            squares.get(i).setX(oldX + x);
            squares.get(i).setY(oldY + y);
        }
        rotationPointX += x;
        rotationPointY += y; 
        drawShape();
    }
}
