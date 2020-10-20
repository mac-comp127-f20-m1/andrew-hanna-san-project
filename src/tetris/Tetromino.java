package tetris;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;

public class Tetromino {
    public final Color zColor1 = Color.GREEN;
    public final Color zColor2 = Color.RED;
    public final Color lineColor = Color.CYAN;
    public final Color lColor1 = Color.ORANGE;
    public final Color lColor2 = Color.BLUE;
    public final Color squareColor = Color.YELLOW;
    public final Color tColor = Color.MAGENTA;
    GraphicsGroup shape;
    private int squareSize;
    private List<Square> squares;
    private int type;
    private int pivotX;
    private int pivotY;


    /**
     * This randomly generates a tetromino at position x and y.
     */
    public Tetromino(int initialX, int initialY, int squareSize) {
        this.squareSize = squareSize;
        squares = new ArrayList<>();
        generateRandom();
        setPosition(initialX, initialY);
        drawShape();
    }

    public boolean checkCollision(Board board) {
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
     * Move the Tetromino up by one block.
     */
    public void moveUp() {
        for (int i = 0; i < squares.size(); i++) {
            int currentY = squares.get(i).getY();
            squares.get(i).setY(currentY - 1);
        }
        drawShape();
    }


    /**
     * Move the Tetromino down by one block.
     */
    public void moveDown(Board board) {
        if (!checkCollision(board)) {
            for (int i = 0; i < squares.size(); i++) {
                squares.get(i).incrementY();
            }
            pivotY = pivotY + 1;
            drawShape();
        }
    }

    /**
     * Move the Tetromino right by one block if within bounds.
     */
    public void moveRight(Board board) {
        if (Collections.max(getOldXs()) + 1 < board.getMaxWidth()
            && checkRightSideWayCollision(board)) {
            for (int i = 0; i < squares.size(); i++) {
                int currentX = squares.get(i).getX();
                squares.get(i).setX(currentX + 1);
            }
            pivotX = pivotX + 1;
            drawShape();
        }
    }

    /**
     * Move the Tetromino left by one block if within bounds.
     */
    public void moveLeft(Board board) {
        if (Collections.min(getOldXs()) - 1 >= 0
            && checkLeftSideWayCollision(board)) {
            for (int i = 0; i < squares.size(); i++) {
                int currentX = squares.get(i).getX();
                squares.get(i).setX(currentX - 1);
            }
            pivotX = pivotX - 1;
            drawShape();
        }
    }


    public boolean checkLeftSideWayCollision(Board board) {
        for (Square square : squares) {
            int x = square.getX();
            int y = square.getY();
            if (board.getGrid().get(y).get(x - 1)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkRightSideWayCollision(Board board) {
        for (Square square : squares) {
            int x = square.getX();
            int y = square.getY();
            if (board.getGrid().get(y).get(x + 1)) {
                return false;
            }
        }
        return true;
    }

    public void rotateShape(Board board) {
        if (type == 5) {
        } else {
            List<Integer> oldX = getOldXs();
            List<Integer> oldY = getOldYs();
            for (int i = 0; i < squares.size(); i++) {
                if (board.getGrid().get(pivotY - pivotX + oldX.get(i)).get(pivotX + pivotY - oldY.get(i))
                    || (pivotX + pivotY - oldY.get(i) <= 0
                        || pivotX + pivotY - oldY.get(i) >= board.getMaxWidth() - 1)) {
                    return;
                }
            }
            for (int i = 0; i < squares.size(); i++) {
                squares.get(i).setX(pivotX + pivotY - oldY.get(i));
                squares.get(i).setY(pivotY - pivotX + oldX.get(i));
            }
            drawShape();
        }
    }

    private List<Integer> getOldXs() {
        List<Integer> result = new ArrayList<>();
        for (Square square : squares) {
            result.add(square.getX());
        }
        return Collections.unmodifiableList(result);
    }

    private List<Integer> getOldYs() {
        List<Integer> result = new ArrayList<>();
        for (Square square : squares) {
            result.add(square.getY());
        }
        return Collections.unmodifiableList(result);
    }

    private void zShape1() {
        squares.add(new Square(0, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(2, 1, new Rectangle(0, 0, squareSize, squareSize)));
        for (Square square : squares) {
            square.getRectangle().setFillColor(zColor1);
        }
    }

    private void zShape2() {
        squares.add(new Square(0, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(2, 0, new Rectangle(0, 0, squareSize, squareSize)));
        for (Square square : squares) {
            square.getRectangle().setFillColor(zColor2);
        }
    }

    private void lineShape() {
        squares.add(new Square(0, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(2, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(3, 0, new Rectangle(0, 0, squareSize, squareSize)));
        for (Square square : squares) {
            square.getRectangle().setFillColor(lineColor);
        }
    }

    private void lShape1() {
        squares.add(new Square(0, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(0, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(2, 1, new Rectangle(0, 0, squareSize, squareSize)));
        for (Square square : squares) {
            square.getRectangle().setFillColor(lColor1);
        }
    }

    private void lShape2() {
        squares.add(new Square(0, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(2, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(2, 0, new Rectangle(0, 0, squareSize, squareSize)));
        for (Square square : squares) {
            square.getRectangle().setFillColor(lColor2);
        }
    }

    private void squareShape() {
        squares.add(new Square(0, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(0, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 1, new Rectangle(0, 0, squareSize, squareSize)));
        for (Square square : squares) {
            square.getRectangle().setFillColor(squareColor);
        }
    }

    private void tShape() {
        squares.add(new Square(1, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(0, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(2, 1, new Rectangle(0, 0, squareSize, squareSize)));
        for (Square square : squares) {
            square.getRectangle().setFillColor(tColor);
        }
    }

    private void drawShape() {
        for (int i = 0; i < squares.size(); i++) {
            int x = squares.get(i).getX();
            int y = squares.get(i).getY();
            Rectangle rect = squares.get(i).getRectangle();
            rect.setPosition(x * squareSize, y * squareSize);
        }
    }

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
        }
    }

    public GraphicsGroup getShape() {
        return shape;
    }

    private void setPosition(int x, int y) {
        for (int i = 0; i < squares.size(); i++) {
            int oldX = squares.get(i).getX();
            int oldY = squares.get(i).getY();
            squares.get(i).setX(oldX + x);
            squares.get(i).setY(oldY + y);
        }
        pivotX = 1 + x;
        pivotY = 1 + y;
    }
}
