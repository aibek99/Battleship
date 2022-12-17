package object;

import java.util.InputMismatchException;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class Ship {
    public final static String[] nameOfShip = {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};
    public final static int[] sizeOfShip = {5, 4, 3, 3, 2};
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int size;
    private int indShip;
    private int damage;

    private boolean axis;

    public Ship(int x1, int y1, int x2, int y2, int indShip) {
        if (x2 < x1) {
            int tmp = x1;
            x1 = x2;
            x2 = tmp;
        }
        if (y2 < y1) {
            int tmp = y1;
            y1 = y2;
            y2 = tmp;
        }
        y1--;
        y2--;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.size = x2 - x1 + 1 + y2 - y1;
        this.indShip = indShip;
        this.axis = (x1 == x2);
        this.damage = 0;
        if (this.size != sizeOfShip[indShip]) {
            throw new InputMismatchException(String.format("Wrong length of the %s!", nameOfShip[indShip]));
        }
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return nameOfShip[this.indShip];
    }

    public int getIndShip() {
        return this.indShip;
    }

    public void setIndShip(int indShip) {
        this.indShip = indShip;
    }

    public int getSizeOfShip() {
        return sizeOfShip[this.indShip];
    }

    public boolean isAxis() {
        return axis;
    }

    public boolean isPartOfShip(int x, int y) {
        return x1 <= x && x <= x2 && y1 <= y && y <= y2;
    }

    public void hit() {
        this.damage++;
    }

    public boolean isSunk() {
        return this.damage == getSize();
    }
}
