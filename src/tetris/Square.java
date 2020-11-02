package tetris;

import edu.macalester.graphics.Rectangle;

public class Square {
    private int x;
    private int y;
    private Rectangle rectangle;

    /**
     * Creates a new Square, with an integer x, y position and a 
     * rectangle to visually represent it.
     */
    public Square(int x, int y, Rectangle rectangle){
        this.x = x;
        this.y = y;
        this.rectangle = rectangle;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void incrementY() {
        setY(y + 1);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
