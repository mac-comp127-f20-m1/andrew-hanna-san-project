package tetris;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.events.MouseMotionEventHandler;
import edu.macalester.graphics.Point;

public class Game {
    CanvasWindow canvas;
    Board board;
    Tetromino current;


    final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;
    final int BOARD_WIDTH = 11, BOARD_HEIGHT = 20;

    public Game(){
        canvas = new CanvasWindow("Tetris!", WINDOW_WIDTH, WINDOW_HEIGHT);
        new Tetromino(canvas);

        board = new Board(BOARD_WIDTH, BOARD_HEIGHT, 1); // not sure about the last parameter "squaresize"

        gameLoop();
    }

    private void gameLoop(){
        // TODO: Implement main game loop.
        // This will call everything needed to run the game.
        current.generateSquares();
        board.addSquares(current);
        current.move();
        canvas.onMouseMove((mouseMotion)->current.moveHorirontal(mouseMotion.getPosition()));
        checkCollision();
        if (checkRound() == true){
            board.addSquares(current);
        if (checkRound() == false){
            restartGame();
        }
        }
    }

    private void checkCollision(){
        // TODO: Implement collision check.
        // This checks the position of "current" against the position of blocks already on the board.
        // Uses getGrid() from Board and getXs()/getYs() from Tetromino.
    }
    
    /**
     * Check win or lose after each tetromino touches ground
     * Return boolean 
     * @return
     */
    private Boolean checkRound(){
        return false;
    }

    /**
     * Restart game by removing all the existing squares, and 
     * generate+add a new tetro to the canvas
     */
    private void restartGame(){

    }

    public static void main(String[] args) {
        new Game();
    }
}
