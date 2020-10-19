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

    public void addSquares(List<Square> squares){
        for(Square square : squares){
            grid.get(square.getY()).set(square.getX(), true);
            visualGrid.get(square.getY()).set(square.getX(), square.getRectangle());
            boardGroup.add(square.getRectangle());
        }
    }

    /**
     * Checks for and removes any full rows. Also moves any rows above those rows down.
     */
    public void removeFullRows(){
        for(int i = 0; i < maxHeight; i++){
            List<Boolean> row = grid.get(i);
            if(row.stream().allMatch(square -> square)){
                grid.remove(i);
                //Remove the visuals for the full row from the boardGroup
                for(Rectangle square : visualGrid.get(i)){
                    if(square != null){
                        boardGroup.remove(square);
                    }
                }
                //Remove the row from the visual grid
                visualGrid.remove(i);
                //Add new rows in the grid and visual grid
                grid.add(generateNewBooleanRow());
                visualGrid.add(generateNewVisualRow());
                //Finally, move all the squares above the full row down by one square
                //moveAboveRowsDown(i);
                updatePositions();
            }
        }
    }

    public List<List<Boolean>> getGrid() {
        return grid;
    }

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

    public void updatePositions(){
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
