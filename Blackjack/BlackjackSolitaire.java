import java.util.Scanner;

public class BlackjackSolitaire{
    private String[][] display;

    private String[][] discardPile;
    private int validCount;
    private int discardCount;

    public BlackjackSolitaire(){
        display = new String[][]{{"1","2","3","4","5"}, {"6","7","8","9","10"}, {"","11","12","13",""}, {"","14","15","16",""}};
        discardPile = new String[][]{{"17","18"},{"19","20"}};
        validCount = 0;
        discardCount = 0;
    }

    public void play(){
        System.out.println("Blackjack Solitaire");
        showDisplay();
        System.out.println("Discards");
        showDiscard();
        Deck deck = new Deck();
        deck.shuffle();
        Scanner scanner = new Scanner(System.in);
        int position;
        while (validCount != 16){
            String card = deck.draw();
            System.out.printf("You have discarded %d cards. You can still discard %d cards.%n", discardCount,4-discardCount);
            System.out.printf("Your card is %s, enter a position number to place or discard the card: ", card);
            while (true){
                String warning = "Invalid input! Enter a position number to place or discard the card: ";
                if (!scanner.hasNextInt()){
                    System.out.print(warning);
                    scanner.next();
                    continue;
                }
                position = scanner.nextInt();
                if (checkPosition(position)){
                    break;
                }
                System.out.print(warning);
            }
            if (position >= 17){
                setDiscard(position, card);
                discardCount += 1;
            }else {
                setDisplay(position, card);
                validCount += 1;
            }
            System.out.println("Blackjack Solitaire");
            showDisplay();
            System.out.println("Discards");
            showDiscard();
        }
        int score = score();
        System.out.printf("Game over! You scored %d points.", score);
    }

    public boolean checkPosition(int position){
        if (position < 1 || position > 20){
            return false;
        } else if (position <= 16){
            int row = calculateRow(position);
            int col = calculateCol(position);
            return display[row][col].equals(""+position);
        }
        else {
            int row = discardRow(position);
            int col = discardCol(position);
            return discardPile[row][col].equals(""+position);
        }
    }

    public int calculateRow(int position){
        int row;
        if (position >= 1 && position <= 5){
            row = 0;
        } else if (position >= 6 && position <= 10) {
            row = 1;
        } else if (position >= 11 && position <= 13) {
            row = 2;
        }else {
            row = 3;
        }
        return row;
    }

    public int calculateCol(int position){
        int col;
        if (position >= 1 && position <= 5){
            col = position - 1;
        } else if (position >= 6 && position <= 10) {
            col = position - 6;
        } else if (position >= 11 && position <= 13) {
            col = position - 10;
        }else {
            col = position - 13;
        }
        return col;
    }

    public int discardRow(int position){
        if (position == 17 || position == 18){
            return 0;
        }else{
            return 1;
        }
    }

    public int discardCol(int position){
        if (position == 17 || position == 19){
            return 0;
        }else{
            return 1;
        }
    }

    public void setDisplay(int position, String card){
        display[calculateRow(position)][calculateCol(position)] = card;
    }

    public void setDiscard(int position, String card){
        discardPile[discardRow(position)][discardCol(position)] = card;
    }

    public void showDisplay(){
        for (int i = 0; i < display.length; i++){
            for (int j = 0; j < display[i].length; j++){
                System.out.print(display[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public void showDiscard(){
        for (int i = 0; i < discardPile.length; i++){
            for (int j = 0; j < discardPile[i].length; j++){
                System.out.print(discardPile[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public int score(){
        int score = 0;
        int total;
        boolean aceExist;
        for (int i = 0; i < display.length; i++){
            total = 0;
            aceExist = false;
            for (int j = 0; j < display[i].length; j++){
                if ((i >= 2 && j == 0) || (i >= 2 && j == 4)){
                    continue;
                }
                char temp = display[i][j].charAt(0);
                if (temp == 'J' || temp == 'Q' || temp == 'K' || temp == '1'){
                    total += 10;
                } else if (temp == 'A') {
                    total += 1;
                    aceExist = true;
                }else {
                    total += (int) temp - '0';
                }
            }
            if (aceExist && total <= 11){
                total += 10;
            }
            score += scoring(total);
        }
        for (int j = 0; j < display[0].length; j++){
            total = 0;
            aceExist = false;
            for (int i = 0; i < display.length; i++){
                if ((j == 0 && i >= 2) || (j == 4 && i >= 2)){
                    continue;
                }
                char temp = display[i][j].charAt(0);
                if (temp == 'J' || temp == 'Q' || temp == 'K' || temp == '1'){
                    total += 10;
                } else if (temp == 'A') {
                    total += 1;
                    aceExist = true;
                }else {
                    total += (int) temp - '0';
                }
            }
            if (aceExist && total <= 11){
                total += 10;
            }
            score += scoring(total);
            if ((j == 0 || j == 4) && total == 21){
                score += 3;
            }
        }
        return score;
    }

    public int scoring(int total){
        int score = 0;
        if (total <= 16){
            score += 1;
        } else if (total == 17) {
            score += 2;
        } else if (total == 18) {
            score += 3;
        } else if (total == 19) {
            score += 4;
        } else if (total == 20) {
            score += 5;
        } else if (total == 21) {
            score += 7;
        }
        return score;
    }
}
