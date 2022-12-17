package field;

import object.Ship;
import java.util.ArrayList;
import java.util.InputMismatchException;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class Grid {
    final int amountOfShips = 5;
    final int nRow = 10;
    final int nCol = 10;
    private char[][] field = new char[10][10];
    private boolean[][] ship = new boolean[10][10];
    private boolean[][] fog = new boolean[10][10];
    private Ship[][] refShip = new Ship[10][10];

    private int indShip = 0;
    ArrayList<Ship> listOfShips = new ArrayList<>();

    public Grid() {
        for (int i = 0; i < nRow; i++) {
            for (int j = 0; j < nCol; j++) {
                this.field[i][j] = '~';
                this.ship[i][j] = false;
                this.fog[i][j] = false;
                this.refShip[i][j] = null;
            }
        }
    }

    public void locateShip(int x, int y, int x2, int y2) throws InputMismatchException {
        if (indShip == this.amountOfShips) {
            throw new ArrayIndexOutOfBoundsException("Field can have only 5 ships!");
        }
        Ship newShip = new Ship(x, y, x2, y2, indShip);
        listOfShips.add(newShip);
        if (checkShipNeighborhood(newShip)) {
            throw new InputMismatchException("You placed it too close to another one.");
        }
        for (int i = newShip.getX1(); i <= newShip.getX2(); i++) {
            for (int j = newShip.getY1(); j <= newShip.getY2(); j++) {
                this.ship[i][j] = true;
                this.field[i][j] = 'O';
                this.refShip[i][j] = newShip;
            }
        }
        indShip++;
    }

    private boolean checkShipNeighborhood(Ship newShip) {
        for (int i = newShip.getX1(); i <= newShip.getX2(); i++) {
            for (int j = newShip.getY1(); j <= newShip.getY2(); j++) {
                if (checkNeighborhood(i, j, newShip) || this.ship[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkNeighborhood(int x, int y, Ship newShip) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int tx = x + i;
                int ty = y + j;
                if (isIn(tx) && isIn(ty) && !newShip.isPartOfShip(tx, ty) && this.ship[tx][ty]) {
                    return true;
                }
            }
        }
        return false;
    }

    public void print() {
        printCommand(true);
    }

    public void printFogView() {
        printCommand(false);
    }

    public void printCommand(boolean flag) {
        System.out.print("  ");
        for (int i = 1; i <= nCol; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < nRow; i++) {
            int row = ('A') + i;
            System.out.print((char)row + " ");
            for (int j = 0; j < nCol; j++) {
                if (flag || this.fog[i][j]) {
                    System.out.print(this.field[i][j] + " ");
                } else {
                    System.out.print("~ ");
                }
            }
            System.out.println();
        }
        if (this.indShip < 5) {
            printRequest();
        }
    }

    public static boolean isIn(int x) {
        return 0 <= x && x < 10;
    }

    public void printRequest() {
        System.out.printf("\nEnter the coordinates of the %s (%d cells):\n\n", Ship.nameOfShip[this.indShip],
                Ship.sizeOfShip[this.indShip]);
    }
    public int getIndShip() {
        return indShip;
    }

    public char getCellOfField(int x, int y) {
        return this.field[x][y];
    }

    public void setCellOfField(char ch, int x, int y) {
        this.field[x][y] = ch;
    }

    public boolean isShip(int x, int y) {
        return this.ship[x][y];
    }

    public void setHit(char ch, int x, int y) {
        this.ship[x][y] = false;
        this.fog[x][y] = true;
        this.field[x][y] = ch;
        if (this.refShip[x][y] != null) {
            this.refShip[x][y].hit();
        }
    }

    public boolean checkShip(int x, int y) {
        return this.refShip[x][y].isSunk();
    }

    public boolean isGameOver() {
        int cnt = 0;
        for (Ship sh : listOfShips) {
            cnt += sh.isSunk() ? 1 : 0;
        }
        return cnt == 5;
    }
}
