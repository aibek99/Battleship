package actor;

import field.Grid;
import object.Gun;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {
    public Grid field;
    Scanner scan;
    private static int cnt = 0;
    private final int id;

    private boolean game;

    public Player() throws IndexOutOfBoundsException {
        scan = new Scanner(System.in);
        this.game = true;
        if (cnt == 2) {
            throw new IndexOutOfBoundsException("Amount of players cannot exceed 2!");
        }
        cnt++;
        this.id = cnt;
        System.out.printf("Player %d, place your ships on the game field\n\n", this.id);
        field = takeGrid();
        askChangeTurns();
    }

    public void printRequest() {
        System.out.printf("\nPlayer %d, it's your turn: \n\n", this.id);
    }

    public void askChangeTurns() {
        System.out.print("Press Enter and pass the move to another player\n...");
        @SuppressWarnings("unused")
        String str = scan.nextLine();
    }

    public void closeScan() throws NullPointerException {
        scan.close();
    }

    public void checkGame(Player opponent) {
        if (opponent.field.isGameOver()) {
            this.game = false;
        }
    }

    public boolean isGame() {
        return this.game;
    }

    private Grid takeGrid() {
        Grid field = new Grid();

        field.print();

        while (field.getIndShip() < 5) {

            boolean flag = false;

            do {
                try {
                    String[] input = scan.nextLine().split(" ");

                    checkInput(input);

                    int x1 = input[0].charAt(0) - 'A';
                    int y1 = input[0].charAt(1) - '0';
                    int x2 = input[1].charAt(0) - 'A';
                    int y2 = input[1].charAt(1) - '0';

                    if (input[0].length() == 3) {
                        y1 *= 10;
                        y1 += input[0].charAt(2) - '0';
                    }

                    if (input[1].length() == 3) {
                        y2 *= 10;
                        y2 += input[1].charAt(2) - '0';
                    }

                    if (!(Grid.isIn(x1) && Grid.isIn(y1 - 1) && Grid.isIn(x2) && Grid.isIn(y2 - 1))) {
                        throw new InputMismatchException("Wrong ship location!");
                    }

                    if (!(x1 == x2 || y1 == y2)) {
                        throw new InputMismatchException("Wrong ship location!");
                    }

                    field.locateShip(x1, y1, x2, y2);
                    flag = false;
                } catch (InputMismatchException e) {
                    System.out.printf("\nError! %s Try again: \n\n", e.getMessage());
                    flag = true;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } while (flag);
            System.out.println();
            field.print();
        }
        System.out.println();
        return field;
    }

    private void checkInput(String[] input) throws InputMismatchException {
        if (input.length != 2) {
            throw new InputMismatchException("Input is incorrect!");
        }
        checkParticle(input[0]);
        checkParticle(input[1]);
    }

    private void checkParticle(String particle) throws InputMismatchException {
        int sz = particle.length();

        if (!(1 < sz && sz < 4)) {
            throw new InputMismatchException("Input is incorrect!");
        }

        if (!Character.isLetter(particle.charAt(0))) {
            throw new InputMismatchException("Input is incorrect!");
        }

        if (!Character.isDigit(particle.charAt(1))) {
            throw new InputMismatchException("Input is incorrect!");
        }

        if (sz == 3 && !Character.isDigit(particle.charAt(2))) {
            throw new InputMismatchException("Input is incorrect!");
        }
    }

    public void myTurn(Player opponent) throws InputMismatchException {
        printBattleField(opponent);
        printRequest();

        // hit a ship
        String[] input = scan.nextLine().split(" ");
        if (input.length != 1) {
            throw new InputMismatchException("Input is incorrect!");
        }
        checkParticle(input[0]);
        System.out.println();
        Gun gun = new Gun(input[0]);
        gun.hit(opponent.field);
        checkGame(opponent);

        if (isGame()) {
            askChangeTurns();
        }
    }

    public void printBattleField(Player opponent) {
        System.out.println();
        opponent.field.printFogView();
        System.out.println("---------------------");
        field.print();
    }
}
