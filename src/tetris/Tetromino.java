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
    public final List<Color> COLORS = List.of(
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
        setPosition(initialX, initialY);
        drawShape();
    }


    /**
     * Move the Tetromino down by one block if it is still within bounds 
     * and if there is no object to its right.  
     */
    public void moveDown(Board board) {
        if (!checkBottomSideCollision(board)) {
            for (int i = 0; i < squares.size(); i++) {
                squares.get(i).incrementY();
            }
            rotationPointY = rotationPointY + 1;
            drawShape();
        }
    }

    /**
     * Move the Tetromino right by one block if it is still within bounds 
     * and if there is no object to its right.
     */
    public void moveRight(Board board) {
        if (!checkRightSideCollision(board)) {
            for (int i = 0; i < squares.size(); i++) {
                int currentX = squares.get(i).getX();
                squares.get(i).setX(currentX + 1);
            }
            rotationPointX = rotationPointX + 1;
            drawShape();
        }
    }

    /**
     * Move the Tetromino right by one block if it is still within bounds 
     * and if there is no object to its left.
     */
    public void moveLeft(Board board) {
        if (!checkLeftSideCollision(board)) {
            for (int i = 0; i < squares.size(); i++) {
                int currentX = squares.get(i).getX();
                squares.get(i).setX(currentX - 1);
            }
            rotationPointX = rotationPointX - 1;
            drawShape();
        }
    }

    /**
     * Takes in a board, this method returns true if there is any square objects from
     * the board right below any square of the current tetromino. If there is, it also 
     * adds the tetromino to the board.
     */
    public boolean checkBottomSideCollision(Board board) {
        for (Square square : squares) {
            int x = square.getX();
            int y = square.getY();
            if (y + 1 >= board.getGrid().size() ||
                board.getGrid().get(y + 1).get(x)) {
                board.addSquares(squares);
                return true;
            }
        }
        return false;
    }

    /**
     * Takes in a board, this method returns false if it is still within bounds 
     * and if there is no object to its left.
     */
    public boolean checkLeftSideCollision(Board board) {
        for (Square square : squares) {
            int x = square.getX();
            int y = square.getY();
            if (Collections.min(getOldXs()) - 1 < 0 ||
            board.getGrid().get(y).get(x - 1) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Takes in a board, this method returns false if it is still within bounds 
     * and if there is no object to its right.
     */
    public boolean checkRightSideCollision(Board board) {
        for (Square square : squares) {
            int x = square.getX();
            int y = square.getY();
            if (Collections.max(getOldXs()) + 1 >= board.getMaxWidth() ||
            board.getGrid().get(y).get(x + 1))
             {
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
        if (type != 5) {
            List<Integer> oldX = getOldXs();
            List<Integer> oldY = getOldYs();
            if (checkCollisionWithWall(board) || checkCollisionWithTetromino(board)){
                return;
            }
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
    private Boolean checkCollisionWithWall(Board board){
        List<Integer> oldY = getOldYs();
        List<Integer> oldX = getOldXs();
        for (int i = 0; i < squares.size(); i++) {
            if  (rotationPointX + rotationPointY - oldY.get(i) < 0
                || rotationPointX + rotationPointY - oldY.get(i) >= board.getMaxWidth()
                || rotationPointY - rotationPointX + oldX.get(i) >= board.getGrid().size()){
                    return true;
                }
        }
        return false;
    }

    /**
     * This method returns true if the new position of each square of the current tetrimono after
     * rotation is occupied by other tetrominoes already added to the board.
     */
    private Boolean checkCollisionWithTetromino(Board board){
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

    public GraphicsGroup getShape() {
        return shape;
    }

    /**
     * Move the tetromino to the given x and y.
     * 
     */
    private void setPosition(int x, int y) {
        for (int i = 0; i < squares.size(); i++) {
            int oldX = squares.get(i).getX();
            int oldY = squares.get(i).getY();
            squares.get(i).setX(oldX + x);
            squares.get(i).setY(oldY + y);
        }
        rotationPointX = 1 + x;
        if(type == 6){
            rotationPointY = y;
        }else{
            rotationPointY = 1 + y;
        }    
    }
}
