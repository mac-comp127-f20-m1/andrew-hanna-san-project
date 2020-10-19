package tetris;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.events.MouseMotionEventHandler;
import edu.macalester.graphics.Point;

public class Game {
    private CanvasWindow canvas;
    private Board board;
    private Tetromino current;
    private double timeUntilPieceMoves;

    final double INITIAL_MOVE_TIME = 1;
    final int WINDOW_WIDTH = 440, WINDOW_HEIGHT = 800;
    final int BOARD_WIDTH = 11, BOARD_HEIGHT = 20;
    final int SQUARE_SIZE = WINDOW_WIDTH / BOARD_WIDTH;

    public Game(){
        timeUntilPieceMoves = INITIAL_MOVE_TIME;
        canvas = new CanvasWindow("Tetris!", WINDOW_WIDTH, WINDOW_HEIGHT);

        current = new Tetromino(6, 1, SQUARE_SIZE);
        current.addTetrominoToCanvas(canvas);

        board = new Board(BOARD_WIDTH, BOARD_HEIGHT, SQUARE_SIZE);
        // First goal: make something happen at a timed interval
        canvas.animate( dt -> {
            timeUntilPieceMoves -= dt;
            if (timeUntilPieceMoves < 0) {
                current.moveDown();
                timeUntilPieceMoves = INITIAL_MOVE_TIME;
            }
            gameLoop();
        });
    }
 
    private void gameLoop(){
        // TODO: Implement main game loop.
        // This will call everything needed to run the game.
        //canvas.onMouseMove((mouseMotion)->current.(mouseMotion.getPosition()));
        canvas.onClick((click)->current.rotateShape());
        if(checkCollision()){
            board.addSquares(current);
            current = new Tetromino(6, 1, SQUARE_SIZE);
        }
        board.removeFullRows();
        // if (checkRound() == true)
        //     board.addSquares(current);
        // if (checkRound() == false)
        //     restartGame();
        
    }

    private boolean checkCollision(){
        // TODO: Implement collision check.
        // This checks the position of "current" against the position of blocks already on the board.
        // Uses getGrid() from Board and getXs()/getYs() from Tetromino.
        // if (current.getXPosition().equals(board.getGrid()) && current.getYPosition().equals(board.getGrid())){
        //     return true;
        // }
        return false;
    }
    
    /**
     * Check win or lose after each tetromino touches ground
     * Return boolean 
     * @return
     */
    private Boolean checkRound(){
        // if (current.getYPosition().contains(0)){
        //     return false;
        // }
        return true;
    }

    /**
     * Restart game by removing all the existing squares, and 
     * generate+add a new tetro to the canvas
     */
    private void restartGame(){
        canvas.removeAll();
        canvas.pause(3000);
        current = new Tetromino(6, 1, SQUARE_SIZE);
        current.addTetrominoToCanvas(canvas);
        board = new Board(BOARD_WIDTH, BOARD_HEIGHT, SQUARE_SIZE);
    }

    public static void main(String[] args) {
        new Game();
    }
}
