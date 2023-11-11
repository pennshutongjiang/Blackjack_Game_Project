import java.util.Arrays;
import java.util.Scanner;

public class BlackjackSolitaire {
    public BlackjackSolitaire() {
    }

    public void play() {

        String[] positions = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
        int[] values = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        //print the initial state as a grid
        displayGrid(positions);

        Card cards = new Card();

        Scanner sc = new Scanner(System.in);

        //below is a while loop
        int i = 0;
        while (!gameFinished(positions)) {

            //Deal a card, and ask for user input
            System.out.println("The card you just got is " + cards.deal(i) + ", where do you wanna put it?");
            System.out.println("Tell me the index of the position: ");

            //Scan the input and deal with errors
            int userInput;
            while (true) {
                String s = sc.next();
                if (isInt(s)) {
                    userInput = Integer.parseInt(s);
                    if (userInput >= 1 && userInput <= 20 && !positionOccupied(positions, userInput)) {
                        break;
                    }
                }
                System.out.println("Invalid input. Please enter again: ");
            }

            //Update the grid and display it
            positions[userInput - 1] = cards.getCard();
            displayGrid(positions);
            //Update array of values
            values[userInput - 1] = cards.getValue();

            i++;
        }

        System.out.println("Please wait. Scoring the hands...");

        //Convert the array to a 2D array representing rows of grid;
        int[][] rows = ConvertTo2DArrayForRow(values);
        //Convert the array to a 2D array representing columns of grid;
        int[][] cols = ConvertTo2DArrayForColumn(values);


        int totalScore = 0;
        //Add up the scores of rows on the grid
        for (int[] row : rows) {
            totalScore += calculateScore345(row);
        }

        //Add up the scores of columns on the grid
        for (int j = 0; j < cols.length; j++) {
            // j = 0 or 4 means it's a 2-card column
            if (j == 0 || j == 4) {
                totalScore += calculateScore2(cols[j]);
            } else {
                totalScore += calculateScore345(cols[j]);
            }
        }

        //Print the total score
        System.out.print("Game over! You scored " + totalScore + " points!");
    }

    //End of game (Below are methods used during gaming)
    public static void displayGrid(String[] positions) {
        //print the grid
        for (int i = 0; i < 5; i++) {
            if (i != 4) {
                System.out.printf("%2s" + "\t\t", positions[i]);
            } else {
                System.out.println(positions[i]);
            }
        }
        for (int i = 5; i < 10; i++) {
            if (i != 9) {
                System.out.printf("%2s" + "\t\t", positions[i]);
            } else {
                System.out.println(positions[i]);
            }
        }
        for (int i = 10; i < 13; i++) {
            if (i == 10) {
                System.out.printf("\t\t" + "%2s" + "\t\t", positions[i]);
            }
            if (i == 11) {
                System.out.printf("%2s" + "\t\t", positions[i]);
            }
            if (i == 12) {
                System.out.printf("%2s" + "\t\t", positions[i]);
            }
        }
        System.out.println();
        for (int i = 13; i < 16; i++) {
            if (i == 13) {
                System.out.printf("\t\t" + "%2s" + "\t\t", positions[i]);
            }
            if (i == 14) {
                System.out.printf("%2s" + "\t\t", positions[i]);
            }
            if (i == 15) {
                System.out.printf("%2s", positions[i]);
            }
        }
        System.out.println();
        System.out.println("Below are discarded cards: ");
        for (int i = 16; i < 20; i++) {
            System.out.printf("%2s" + "\t\t", positions[i]);
        }
        System.out.println();
    }

    public static boolean isInt(String s) {
        //test whether user input is integer
        for (int k = 0; k < s.length(); k++) {
            if (!Character.isDigit(s.charAt(k))) {
                return false;
            }
        }
        return true;
    }

    public static boolean positionOccupied(String[] positions, int userInput) {
        //test whether the position has been taken
        for (String s : positions) {
            if (s.equals(String.valueOf(userInput))) {
                return false;
            }
        }
        return true;
    }

    public static boolean gameFinished(String[] positions) {
        //test whether the game is finished
        for (int i = 0; i < 16; i++) {
            if (positions[i].equals(String.valueOf(i + 1))) {
                return false;
            }
        }
        return true;
    }

    public static int[][] ConvertTo2DArrayForColumn(int[] values) {
        return new int[][]{
                {values[0], values[5]},
                {values[1], values[6], values[10], values[13]},
                {values[2], values[7], values[11], values[14]},
                {values[3], values[8], values[12], values[15]},
                {values[4], values[9]}};
    }

    public static int[][] ConvertTo2DArrayForRow(int[] values) {
        return new int[][]{
                {values[0], values[1], values[2], values[3], values[4]},
                {values[5], values[6], values[7], values[8], values[9]},
                {values[10], values[11], values[12]},
                {values[13], values[14], values[15]}};
    }

    public static int valueToScore345(int value) {
        //calculate the score of column/row consisting of 3, 4, or 5 cards
        if (value >= 22) {
            return 0;
        } else if (value == 21) {
            return 7;
        } else if (value == 20) {
            return 5;
        } else if (value == 19) {
            return 4;
        } else if (value == 18) {
            return 3;
        } else if (value == 17) {
            return 2;
        } else {
            return 1;
        }
    }

    public static int valueToScore2(int value) {
        //calculate the score of column consisting of 2 cards
        if (value >= 22) {
            return 0;
        } else if (value == 21) {
            return 10;
        } else if (value == 20) {
            return 5;
        } else if (value == 19) {
            return 4;
        } else if (value == 18) {
            return 3;
        } else if (value == 17) {
            return 2;
        } else {
            return 1;
        }
    }

    public static boolean containA(int[] ints) {
        //test whether a row/column contains Ace (set as 1 before);
        return Arrays.stream(ints).anyMatch(value -> value == 1);
    }

    public static int calculateScore345(int[] ints) {
        //Calculate scores for a row/column containing 3, 4, or 5 cards
        if (!containA(ints)) {
            return valueToScore345(Arrays.stream(ints).sum());
        } else {
            return Math.max(valueToScore345(Arrays.stream(ints).sum() + 10),
                    valueToScore345(Arrays.stream(ints).sum()));
        }
    }

    public static int calculateScore2(int[] ints) {
        //Calculate scores for a column containing 2 cards
        if (!containA(ints)) {
            return valueToScore2(Arrays.stream(ints).sum());
        } else {
            return Math.max(valueToScore2(Arrays.stream(ints).sum() + 10),
                    valueToScore2(Arrays.stream(ints).sum()));
        }
    }
}