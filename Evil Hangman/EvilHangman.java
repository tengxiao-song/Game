import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class EvilHangman {
    private ArrayList<String> wordList;
    private final int wordLength;
    private HashSet<Character> previousGuesses;
    private TreeSet<Character> incorrectGuesses;
    private Solution solution;
    private Scanner inputScanner;
    private WordFamily wordFamilyRunner;

    public EvilHangman() {
        this("engDictionary.txt");
    }

    public EvilHangman(String filename) {
        try {
            wordList = dictionaryToList(filename);
        } catch (IOException e) {
            System.out.printf(
                    "Couldn't read from the file %s. Verify that you have it in the right place and try running again.",
                    filename);
            System.exit(0);
        }
        previousGuesses = new HashSet<>();
        incorrectGuesses = new TreeSet<>();
        int randomIndex = new Random().nextInt(wordList.size());
        String randomWord = wordList.get(randomIndex);
        wordLength = randomWord.length();
        ArrayList<String> newList = new ArrayList<String>();
        for (String word : wordList) {
            if (word.length() == wordLength) {
                newList.add(word);
            }
        }
        wordList = newList;
        solution = new Solution(wordLength);
        inputScanner = new Scanner(System.in);
        wordFamilyRunner = new WordFamily();
    }

    private static ArrayList<String> dictionaryToList(String filename) throws IOException {
        FileInputStream fs = new FileInputStream(filename);
        Scanner scnr = new Scanner(fs);
        ArrayList<String> wordList = new ArrayList<>();
        while (scnr.hasNext()) {
            wordList.add(scnr.next());
        }
        scnr.close();
        return wordList;
    }

    public void start() {
        while (!solution.isSolved()) {
            char guess = promptForGuess();
            wordList = wordFamilyRunner.start(wordList, guess, solution.getPartialSolution());
            recordGuess(guess);
        }
        printVictory();
    }

    private char promptForGuess() {
        while (true) {
            System.out.println("Guess a letter.\n");
            solution.printProgress();
            System.out.println("Incorrect guesses:\n" + incorrectGuesses.toString());
            String input = inputScanner.next();
            if (input.length() != 1 || !Character.isLowerCase(input.charAt(0))) {
                System.out.println("Please enter a single lower case character.");
            } else if (previousGuesses.contains(input.charAt(0))) {
                System.out.println("You've already guessed that.");
            } else {
                return input.charAt(0);
            }
        }
    }

    private void recordGuess(char guess) {
        previousGuesses.add(guess);
        int changedNum = solution.updatePartialSolution(wordFamilyRunner.getLargestPattern());
        if (changedNum == 0) {
            incorrectGuesses.add(guess);
        }
    }

    private void printVictory() {
        String res = "";
        for (char letter : solution.getPartialSolution()) {
            res += letter;
        }
        System.out.printf("Congrats! The word was %s%n", res);
    }
}
