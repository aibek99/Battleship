import actor.Player;

import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        try {
            Player first = new Player();
            Player second = new Player();
            boolean turn = true;

            do {
                if (turn) {
                    first.myTurn(second);
                    turn = false;
                } else {
                    second.myTurn(first);
                    turn = true;
                }
            } while (first.isGame() && second.isGame());

            System.out.print("You sank the last ship. You won. Congratulations!\n");

            first.closeScan();
            second.closeScan();
        } catch (InputMismatchException e) {
            System.out.printf("Error! %s Try again: \n\n", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


