package tetris;

import edu.macalester.graphics.CanvasWindow;

public class TetrominoTest {
    public static void main(String[] args) {
        CanvasWindow canvas = new CanvasWindow("Tetromino Test", 600, 800);
        Tetromino tetromino = new Tetromino(2, 3, 40);
        // tetromino.rotateShape(board);
        canvas.add(tetromino.getShape());
    }
}
