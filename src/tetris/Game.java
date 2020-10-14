package tetris;

import edu.macalester.graphics.CanvasWindow;

public class Game {
    CanvasWindow canvas;
    Board board;
    Tetromino current;

    final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;
    final int BOARD_WIDTH = 11, BOARD_HEIGHT = 20;

    public Game(){
        canvas = new CanvasWindow("Tetris!", WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    private void gameLoop(){
        // TODO: Implement main game loop.
        // This will call everything needed to run the game.
    }

    private void checkCollision(){
        // TODO: Implement collision check.
        // This checks the position of "current" against the position of blocks already on the board.
        // Uses getGrid() from Board and getXs()/getYs() from Tetromino.
    }

    public static void main(String[] args) {
        new Game();
    }
}
