package tetris;

import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;

public class Board {
    private GraphicsGroup boardGroup;
    private List<List<Boolean>> grid;
    private List<List<Rectangle>> visualGrid;
    private final int width, height;
    private final double squareSize;

    /**
     * Creates a new Board object. This object manages existing tiles on the tetris board.
     * @param gridWidth The width of the grid in squares
     * @param gridHeight The height of the grid in squares
     * @param squareSize The width/height of each square, in pixels
     */
    public Board(int gridWidth, int gridHeight, double squareSize){
        width = gridWidth;
        height = gridHeight;
        this.squareSize = squareSize;

        grid = new ArrayList<>();
        visualGrid = new ArrayList<>();
        for(int i = 0; i < height; i++){
            grid.add(generateNewBooleanRow());
            visualGrid.add(generateNewVisualRow());
        }

        boardGroup = new GraphicsGroup();
    }

    /**
     * Adds the given List of Square objects to the board.
     */
    public void addSquares(List<Square> squares){
        for(Square square : squares){
            grid.get(square.getY()).set(square.getX(), true);
            visualGrid.get(square.getY()).set(square.getX(), square.getRectangle());
            boardGroup.add(square.getRectangle());
        }
    }

    /**
     * Checks for and removes any full rows from board.
     * Also moves down all rows above, and adds a new
     * (empty) row at the top.
     */
    public int removeFullRows(){
        int clearedRows = 0;
        for(int i = 0; i < height; i++){
            List<Boolean> row = grid.get(i);
            if(row.stream().allMatch(square -> square)){
                clearedRows++;
                //Remove the visuals for the full row from the boardGroup
                visualGrid.get(i).forEach(boardGroup::remove);
                //Add new row at the top of both grids
                grid.add(0, generateNewBooleanRow());
                visualGrid.add(0, generateNewVisualRow());
                //Remove the full row from both grids
                grid.remove(i + 1);
                visualGrid.remove(i + 1);
                //Finally, update the visuals of all the squares to reflect their new positions
                updatePositions();
            }
        }
        int score = 0;
        if(clearedRows > 0){
            score += clearedRows * 200;
            if(clearedRows < 4){
                score -= 100;
            }
        }
        return score;
    }

    private List<Boolean> generateNewBooleanRow(){
        List<Boolean> row = new ArrayList<>();
        for(int i = 0; i < width; i++){
            row.add(false);
        }
        return row;
    }

    private List<Rectangle> generateNewVisualRow(){
        List<Rectangle> row = new ArrayList<>();
        for(int i = 0; i < width; i++){
            row.add(null);
        }
        return row;
    }

    private void updatePositions(){
        for(int i = 0; i < visualGrid.size(); i++){
            for(int j = 0; j < visualGrid.get(i).size(); j++){
                Rectangle square = visualGrid.get(i).get(j);
                if(square != null){
                    square.setX(j * squareSize);
                    square.setY(i * squareSize);
                }
            }
        }
    }

    /**
     * Returns the width of the board in squares.
     */
    public int getWidth(){
        return width;
    }

    /**
     * Returns the 2d grid of booleans used to track
     * whether or not a block is full.
     * 
     * The grid is in the format of rows containing columns.
     * (y comes before x when retrieving values)
     */
    public List<List<Boolean>> getGrid() {
        return grid;
    }

    /**
     * Returns the GraphicsGroup that any blocks
     * already on the board are in.
     */
    public GraphicsGroup getGroup(){
        return boardGroup;
    }
}
