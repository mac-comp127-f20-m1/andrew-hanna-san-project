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

        current = new Tetromino(6, 1);
        current.addTetrominoToCanvas(canvas);

        board = new Board(BOARD_WIDTH, BOARD_HEIGHT, 1); // not sure about the last parameter "squaresize"

        // First goal: make something happen at a timed interval
        canvas.animate( dt -> {
            timeUntilPieceMoves -= dt;
            if (timeUntilPieceMoves < 0) {
                current.moveDown();
                // System.out.println("pretend the piece moves down!");
                // timeUntilPieceMoves = currentPieceSpeedOrWhatever;
            }
            gameLoop();
        });
    }
 
    private void gameLoop(){
        // TODO: Implement main game loop.
        // This will call everything needed to run the game.
        board.addSquares(current); // this may not be right, but is there a way to call private methods in other class's constructor?
        canvas.onMouseMove((mouseMotion)->current.(mouseMotion.getPosition()));
        canvas.onClick((click)->current.rotateShape());
        checkCollision();
        board.removeFullRows();
        if (checkRound() == true){
            board.addSquares(current);
        if (checkRound() == false){
            restartGame();
        }
        }
    }

    private boolean checkCollision(){
        // TODO: Implement collision check.
        // This checks the position of "current" against the position of blocks already on the board.
        // Uses getGrid() from Board and getXs()/getYs() from Tetromino.
        if (current.getXPosition().equals(board.getGrid()) && current.getYPosition().equals(board.getGrid())){
            return true;
        }
        return false;
        
    }
    
    /**
     * Check win or lose after each tetromino touches ground
     * Return boolean 
     * @return
     */
    private Boolean checkRound(){
        if (current.getYPosition().contains(0)){
            return false;
        }
        return true;
    }

    /**
     * Restart game by removing all the existing squares, and 
     * generate+add a new tetro to the canvas
     */
    private void restartGame(){
        canvas.removeAll();
        canvas.pause(3000);
        current = new Tetromino(6, 1);
        current.addTetrominoToCanvas(canvas);
        board = new Board(BOARD_WIDTH, BOARD_HEIGHT, 1);
    }

    public static void main(String[] args) {
        new Game();
    }
}
