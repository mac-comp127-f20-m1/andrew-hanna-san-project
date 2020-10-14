package tetris;

import java.util.List;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;

public class Board {
    private GraphicsGroup boardGroup;
    private List<List<Boolean>> grid;
    private List<List<Rectangle>> visualGrid;

    public Board(){

    }

    public void addSquares(Tetromino squares){
        //TODO: Implement method to add squares from a tetromino that has hit the ground to the board.
    }

    public void removeFullRows(){
        //TODO: Implement method to check if any rows in the grid are full, remove them, and move down all the other rows.

    }

    public List<List<Boolean>> getGrid() {
        return grid;
    }

    public GraphicsGroup getGroup(){
        return boardGroup;
    }
}
