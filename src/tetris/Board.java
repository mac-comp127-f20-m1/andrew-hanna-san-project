package tetris;

import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;

public class Board {
    private GraphicsGroup boardGroup;
    private List<List<Boolean>> grid;
    private List<List<Rectangle>> visualGrid;
    private final int maxWidth, maxHeight;
    private final double squareSize;

    /**
     * Creates a new Board object. This object manages existing tiles on the tetris board.
     * @param gridWidth The width of the grid in squares
     * @param gridHeight The height of the grid in squares
     * @param squareSize The width/height of each square, in pixels
     */
    public Board(int gridWidth, int gridHeight, double squareSize){
        maxWidth = gridWidth;
        maxHeight = gridHeight;
        this.squareSize = squareSize;

        grid = new ArrayList<>();
        visualGrid = new ArrayList<>();
        for(int i = 0; i < maxHeight; i++){
            grid.add(generateNewBooleanRow());
            visualGrid.add(generateNewVisualRow());
        }

        boardGroup = new GraphicsGroup();
    }

    /**
     * Adds the given List of Square objects to the grid.
     */
    public void addSquares(List<Square> squares){
        for(Square square : squares){
            grid.get(square.getY()).set(square.getX(), true);
            visualGrid.get(square.getY()).set(square.getX(), square.getRectangle());
            boardGroup.add(square.getRectangle());
        }
        updatePositions();
    }

    /**
     * Checks for and removes any full rows. Also moves any rows above those rows down.
     */
    public void removeFullRows(){
        for(int i = 0; i < maxHeight; i++){
            List<Boolean> row = grid.get(i);
            if(row.stream().allMatch(square -> square)){
                //Remove the visuals for the full row from the boardGroup
                visualGrid.get(i).forEach(boardGroup::remove);
                //Add new rows in the grid and visual grid
                grid.add(0, generateNewBooleanRow());
                visualGrid.add(0, generateNewVisualRow());
                //Remove the row from the visual grid
                grid.remove(i + 1);
                visualGrid.remove(i + 1);
                //Finally, move all the squares above the full row down by one square
                updatePositions();
            }
        }
    }

    /**
     * Returns the 2d grid of booleans used to track whether or not a block is full.
     */
    public List<List<Boolean>> getGrid() {
        return grid;
    }

    /**
     * Returns the GraphicsGroup that any blocks already on the board are placed in.
     */
    public GraphicsGroup getGroup(){
        return boardGroup;
    }

    private List<Boolean> generateNewBooleanRow(){
        List<Boolean> row = new ArrayList<>();
        for(int i = 0; i < maxWidth; i++){
            row.add(false);
        }
        return row;
    }

    private List<Rectangle> generateNewVisualRow(){
        List<Rectangle> row = new ArrayList<>();
        for(int i = 0; i < maxWidth; i++){
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
}
