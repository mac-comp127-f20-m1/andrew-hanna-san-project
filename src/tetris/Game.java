package tetris;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.events.Key;
import edu.macalester.graphics.events.KeyboardEvent;

public class Game {
    private CanvasWindow canvas;
    private Board board;
    private Tetromino current;
    private double timeUntilPieceMoves;
    private int score = 0;
    private GraphicsText scoreDisplay;

    final double INITIAL_MOVE_TIME = 0.5;
    final int WINDOW_WIDTH = 440, WINDOW_HEIGHT = 800;
    final int BOARD_WIDTH = 11, BOARD_HEIGHT = 20;
    final int SQUARE_SIZE = WINDOW_WIDTH / BOARD_WIDTH;
    final double MOVE_TIME_SCORE_FACTOR = 4000;

    /**
     * Creates a new Tetris Game, including the window
     * and everything else the game needs to function.
     */
    public Game() {
        timeUntilPieceMoves = INITIAL_MOVE_TIME;
        canvas = new CanvasWindow("Tetris!", WINDOW_WIDTH, WINDOW_HEIGHT);
        restartGame();
        canvas.onKeyDown(this::keyDownHandler);
        canvas.animate(this::gameLoop);
    }

    /**
     * Restart game by removing all the existing squares, and generate+add a new tetro to the canvas
     */
    private void restartGame() {
        canvas.removeAll();
        createTetromino();
        board = new Board(BOARD_WIDTH, BOARD_HEIGHT, SQUARE_SIZE);
        canvas.add(board.getGroup());
        score = 0;
        scoreDisplay = new GraphicsText();
        scoreDisplay.setFontSize(WINDOW_HEIGHT / SQUARE_SIZE);
        updateScore();
        canvas.add(scoreDisplay);
    }

    private void gameLoop(double dt) {
        // First, check if the timer is at 0.
        timeUntilPieceMoves -= dt;
        if (timeUntilPieceMoves < 0) {
            // If it is, first check if the tetromino is colliding with another/the bottom
            if (current.checkCollision(board, 0, 1)) {
                // If the tetromino is hitting something, add it to the board and make a new one
                board.addSquares(current.getSquares());
                canvas.remove(current.getShape());
                createTetromino();
                score += board.removeFullRows();
                // After adding it to the board, check if the game has been lost.
                if (checkLoss() == true)
                    restartGame();
            } else {
                // Otherwise, move the tetromino down a block.
                current.moveDown(board);
            }
            // Finally, reset the timer and update the score display.
            resetTimer();
            updateScore();
        }
    }

    private void resetTimer(){
        timeUntilPieceMoves = INITIAL_MOVE_TIME / (1 + (score / MOVE_TIME_SCORE_FACTOR));
    }

    private void updateScore(){
        scoreDisplay.setText("Score: " + score);
        scoreDisplay.setPosition(WINDOW_WIDTH / 40, 0 + scoreDisplay.getLineHeight());
    }

    private void keyDownHandler(KeyboardEvent pressed) {
        double oldTime = timeUntilPieceMoves;
        resetTimer();
        if(pressed.getKey().equals(Key.LEFT_ARROW) || pressed.getKey().equals(Key.A)){
            current.moveLeft(board);
        } else if(pressed.getKey().equals(Key.RIGHT_ARROW) || pressed.getKey().equals(Key.D)){
            current.moveRight(board);
        } else if (pressed.getKey().equals(Key.UP_ARROW) || pressed.getKey().equals(Key.W)){
            current.rotateShape(board);
        } else if (pressed.getKey().equals(Key.DOWN_ARROW) || pressed.getKey().equals(Key.S)){
            current.moveDown(board);
        } else {
            timeUntilPieceMoves = oldTime;
        }
    }

    private Boolean checkLoss() {
        if (board.getGrid().get(0).contains(true)) {
            return true;
        }
        return false;
    }

    private void createTetromino() {
        current = new Tetromino(BOARD_WIDTH / 2, 0, SQUARE_SIZE);
        canvas.add(current.getShape());
    }

    public static void main(String[] args) {
        new Game();
    }
}
