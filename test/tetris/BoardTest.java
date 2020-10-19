package tetris;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BoardTest {
    final List<List<Boolean>> testGrid = List.of(
        List.of(false, false, false, false),
        List.of(false, false, false, false),
        List.of(true, true, true, true)
    );
    final List<List<Boolean>> testEmptyGrid = List.of(
        List.of(false, false, false, false),
        List.of(false, false, false, false),
        List.of(false, false, false, false)
    );
    Board board = new Board(4, 3, 1);

    // @Test
    // void testAddSquares(){
    //     Tetromino test = new Tetromino(2, 0, 1);
    //     test.setSquares(new ArrayList<>());
    //     test.lineShape();
    //     while(!test.checkCollision(board)){
    //         test.moveDown();
    //     }
    //     assertTrue(testGrid.equals(board.getGrid()));
    // }

    @Test
    void testRemoveFullRows() {
        board.removeFullRows();
        assertTrue(testEmptyGrid.equals(board.getGrid()));
    }
}
