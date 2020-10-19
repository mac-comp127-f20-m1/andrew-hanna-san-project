package tetris;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.events.Key;
import edu.macalester.graphics.events.KeyboardEvent;

public class Game {
    private CanvasWindow canvas;
    private Board board;
    private Tetromino current;
    private double timeUntilPieceMoves;

    final double INITIAL_MOVE_TIME = 0.14;
    final int WINDOW_WIDTH = 440, WINDOW_HEIGHT = 800;
    final int BOARD_WIDTH = 11, BOARD_HEIGHT = 20;
    final int SQUARE_SIZE = WINDOW_WIDTH / BOARD_WIDTH;

    public Game() {
        timeUntilPieceMoves = INITIAL_MOVE_TIME;
        canvas = new CanvasWindow("Tetris!", WINDOW_WIDTH, WINDOW_HEIGHT);
        restartGame();
        canvas.onKeyDown(this::keyDownHandler);
        canvas.animate(this::gameLoop);
    }

    private void gameLoop(double dt) {
        // First, check if the timer is at 0.
        timeUntilPieceMoves -= dt;
        if (timeUntilPieceMoves < 0) {
            // If it is, first check if the tetromino is colliding with another/the bottom
            if (current.checkCollision(board)) {
                // If the tetromino is hitting something, add it to the board and make a new one
                canvas.remove(current.getShape());
                createTetromino();
                board.removeFullRows();
                // After adding it to the board, check if the game has been lost.
                if (checkLoss() == true)
                    restartGame();
            } else {
                // Otherwise, move the tetromino down a block.
                current.moveDown();
            }
            // Finally, reset the timer.
            timeUntilPieceMoves = INITIAL_MOVE_TIME;
        }
    }

    private void keyDownHandler(KeyboardEvent pressed) {
        if(pressed.getKey().equals(Key.LEFT_ARROW) || pressed.getKey().equals(Key.A)){
            current.moveLeft();
        }else if(pressed.getKey().equals(Key.RIGHT_ARROW) || pressed.getKey().equals(Key.D)){
            current.moveRight();
        }
    }

    /**
     * Check win or lose after each tetromino touches ground Return boolean
     * 
     * @return
     */
    private Boolean checkLoss() {
        if (board.getGrid().get(0).contains(true)) {
            return true;
        }
        return false;
    }

    /**
     * Restart game by removing all the existing squares, and generate+add a new tetro to the canvas
     */
    private void restartGame() {
        canvas.removeAll();
        createTetromino();
        board = new Board(BOARD_WIDTH, BOARD_HEIGHT, SQUARE_SIZE);
        canvas.add(board.getGroup());
    }

    private void createTetromino() {
        current = new Tetromino(BOARD_WIDTH / 2, 0, SQUARE_SIZE);
        canvas.add(current.getShape());
    }

    public static void main(String[] args) {
        new Game();
    }
}
