import java.util.Scanner;

public class BattleshipGame {
    private static int row;
    private static int col;

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        while (true) {
            Ocean o = new Ocean();
            o.placeAllShipsRandomly();
            while (!o.isGameOver()) {
                o.print();
                askingInputs(s);
                runShot(o, row, col);
            }
            if (rematch(s, o).equals("n")) {
                break;
            }
        }
    }

    public static void askingInputs(Scanner s) {
        String wrongInput = "Invalid input. Please enter a valid number: ";
        System.out.print("Enter the row number that you want to shot at: ");
        int tempRow = -1;
        while (tempRow < 0 || tempRow > 9) {
            while (!s.hasNextInt()) {
                System.out.printf(wrongInput);
                s.next();
            }
            tempRow = s.nextInt();
            if (tempRow < 0 || tempRow > 9) {
                System.out.printf(wrongInput);
            }
        }
        row = tempRow;
        int tempCol = -1;
        System.out.print("Enter the column number that you want to shot at: ");
        while (tempCol < 0 || tempCol > 9) {
            while (!s.hasNextInt()) {
                System.out.printf(wrongInput);
                s.next();
            }
            tempCol = s.nextInt();
            if (tempCol < 0 || tempCol > 9) {
                System.out.printf(wrongInput);
            }
        }
        col = tempCol;
    }

    public static void runShot(Ocean o, int row, int col) {
        boolean hit;
        hit = o.shootAt(row, col);
        if (hit) {
            System.out.println("hit");
        } else {
            System.out.println("miss");
        }
        System.out.printf("You fired %d shoots, ", o.getShotsFired());
        System.out.printf("hit %d times, ", o.getHitCount());
        System.out.printf("sunk %d ships.\n", o.getShipsSunk());
    }

    public static String rematch(Scanner s, Ocean o) {
        System.out.printf("Congratulations! You sunk all the ships with %d shots.\n", o.getShotsFired());
        System.out.println("Would you like another game? Type y for Yes, n for No: ");
        String answer = s.next();
        while (!answer.equals("y") && !answer.equals("n")) {
            System.out.println("Invalid input. Enter again: ");
            answer = s.next();
        }
        return answer;
    }

    public static int getRow() {
        return row;
    }

    public static int getCol() {
        return col;
    }

}
