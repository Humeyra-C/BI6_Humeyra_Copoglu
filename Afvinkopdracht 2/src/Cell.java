import java.awt.*;

/**
 * Author: Humeyra Copoglu
 * Date created: 21-11-2022
 */

public class Cell {
    private static int amount = 0;
    private int cellNumber;
    private boolean alive = false;
    private Color color = Color.black;
    private int xPos;
    private int yPos;

    public Cell(int x, int y) {
        this.xPos = x;
        this.yPos = y;
        this.amount++;
        cellNumber = amount;
    }

    // Getters & Setters
    public boolean isAlive() {
        return alive;
    }

    public void setState(boolean state) {
        this.alive = state;
        if (state) {
            setColor(Color.red);
        } else {
            setColor(Color.black);
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Cell:" + this.cellNumber + " Alive:" + this.alive;
    }
}
