package tetris;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;

public class Tetromino {
    GraphicsGroup shape;
    private int squareSize;
    private List<Square> squares;

     /**
     * This randomly generates a tetromino at position x and y. 
     */
    public Tetromino(int initialX, int initialY, int squareSize){
        this.squareSize = squareSize;
        squares = new ArrayList<>();
        generateRandom();
        setPosition(initialX, initialY);
        drawShape();
    }

    public boolean checkCollision(Board board){
        for(Square square : squares){
            int x = square.getX();
            int y = square.getY();
            if(y + 1 >= board.getGrid().size() ||
                board.getGrid().get(y + 1).get(x)
            ){
                board.addSquares(squares);
                return true;
            }
        }
        return false;
    }

    /**
     * Move the Tetromino up by one block.
     */
    public void moveUp(){
        for (int i = 0; i < squares.size(); i++){
            int currentY = squares.get(i).getY();
            squares.get(i).setY(currentY - 1);
        }
        drawShape();
    }


    /**
     * Move the Tetromino down by one block.
     */
    public void moveDown(){
        for (int i = 0; i < squares.size(); i++){
            squares.get(i).incrementY();
        }
        drawShape();
    }

    /**
     * Move the Tetromino right by one block.
     */
    public void moveRight(){
        for (int i = 0; i < squares.size(); i++){
            int currentX = squares.get(i).getX();
            squares.get(i).setX(currentX + 1);
        }
        drawShape();
    }

    /**
     * Move the Tetromino left by one block.
     */
    public void moveLeft(){
        for (int i = 0; i < squares.size(); i++){
            int currentX = squares.get(i).getX();
            squares.get(i).setX(currentX - 1);
        }
        drawShape();
    }

    public void rotateShape(){
        List<Integer> oldX = getOldXs();
        List<Integer> oldY = getOldYs();
        for (int i = 0; i < squares.size(); i++){
            squares.get(i).setX(oldY.get(i));
        }
        for (int i = 0; i < squares.size(); i++){
            squares.get(i).setY(oldX.get(i));
        }
        drawShape();
    }

    private List<Integer> getOldXs(){
        List<Integer> result = new ArrayList<>();
        for(Square square : squares){
            result.add(square.getX());
        }
        return Collections.unmodifiableList(result);
    }

    private List<Integer> getOldYs(){
        List<Integer> result = new ArrayList<>();
        for(Square square : squares){
            result.add(square.getY());
        }
        return Collections.unmodifiableList(result);
    }

    private void zShape1(){
        squares.add(new Square(0, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(2, 1, new Rectangle(0, 0, squareSize, squareSize)));
    }

    private void zShape2(){
        squares.add(new Square(0, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(2, 0, new Rectangle(0, 0, squareSize, squareSize)));
    }

    private void lineShape(){
        squares.add(new Square(0, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(2, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(3, 0, new Rectangle(0, 0, squareSize, squareSize)));
    }

    private void lShape1(){
        squares.add(new Square(0, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(0, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(2, 1, new Rectangle(0, 0, squareSize, squareSize)));
    }

    private void lShape2(){
        squares.add(new Square(0, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(2, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(2, 0, new Rectangle(0, 0, squareSize, squareSize)));
    }

    private void squareShape(){
        squares.add(new Square(0, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(0, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 1, new Rectangle(0, 0, squareSize, squareSize)));
    }

    private void tShape(){
        squares.add(new Square(1, 0, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(0, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(1, 1, new Rectangle(0, 0, squareSize, squareSize)));
        squares.add(new Square(2, 1, new Rectangle(0, 0, squareSize, squareSize)));
    }

    private void drawShape(){
        for (int i = 0; i < squares.size(); i++){
            int x = squares.get(i).getX();
            int y = squares.get(i).getY();
            Rectangle rect = squares.get(i).getRectangle();
            rect.setPosition(x * squareSize, y * squareSize);
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
        shape = new GraphicsGroup();
        for(Square square : squares){
            shape.add(square.getRectangle());
        }
    }

    public GraphicsGroup getShape() {
        return shape;
    }

    private void setPosition(int x, int y){
        for (int i = 0; i < squares.size(); i++){
            int oldX = squares.get(i).getX();
            int oldY = squares.get(i).getY();
            squares.get(i).setX(oldX + x);
            squares.get(i).setY(oldY + y);
        }
    }

    // private static final List<Provider<Tetromino>> tetrominoGenerators = List.of(
    //     Tetromino::square,
    //     Tetromino::lShape,
    //     ...etc..
    // );


    
}
