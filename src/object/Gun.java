package object;

import field.Grid;

import java.util.InputMismatchException;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class Gun {
    private int x;
    private int y;

    public Gun(String input) throws InputMismatchException {
        int x = input.charAt(0) - 'A';
        int y = input.charAt(1) - '0';

        if (input.length() == 3) {
            y *= 10;
            y += input.charAt(2) - '0';
        }
        this.x = x;
        y--;
        this.y = y;
        checkCoordinates();
    }

    public Gun(int x, int y) throws InputMismatchException {
        this.x = x;
        y--;
        this.y = y;
        checkCoordinates();
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private void checkCoordinates() throws InputMismatchException {
        if (!Grid.isIn(this.x) || !Grid.isIn(this.y)) {
            throw new InputMismatchException("You entered the wrong coordinates!");
        }
    }

    public void hit(Grid field) {
        if (field.isShip(this.x, this.y)) {
            field.setHit('X', this.x, this.y);
            if (field.checkShip(this.x, this.y)) {
                if (field.isGameOver()) {
                    return;
                }
                System.out.print("You sank a ship! Specify a new target: \n");
            } else {
                System.out.print("You hit a ship!\n");
            }
        } else {
            if (field.getCellOfField(this.x, this.y) == 'X') {
                System.out.print("You hit a ship!\n");
            } else {
                field.setHit('M', this.x, this.y);
                System.out.print("You missed!\n");
            }
        }
    }
}

