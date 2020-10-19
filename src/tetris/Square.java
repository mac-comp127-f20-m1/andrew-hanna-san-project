package tetris;

import edu.macalester.graphics.Rectangle;

public class Square {
    private int x;
    private int y;
    private Rectangle rectangle;

    public Square(int x, int y, Rectangle rectangle){
        this.x = x;
        this.y = y;
        this.rectangle = rectangle;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void decrementY() {
        y--;
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
