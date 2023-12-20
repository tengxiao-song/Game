import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class SpellChecker {
    private static HashSet<String> allWords;
    private static FileInputStream input;
    private static FileOutputStream output;
    private static WordRecommender rec;

    public SpellChecker() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.printf(Util.DICTIONARY_PROMPT);
            String dicName = scanner.next();
            try {
                FileInputStream dic = new FileInputStream(dicName);
                Scanner scnr = new Scanner(dic);
                allWords = new HashSet<>();
                while (scnr.hasNextLine()) {
                    allWords.add(scnr.nextLine());
                }
                dic.close();
                rec = new WordRecommender(dicName);
                System.out.printf(Util.DICTIONARY_SUCCESS_NOTIFICATION, dicName);
                break;
            } catch (IOException e) {
                System.out.printf(Util.FILE_OPENING_ERROR);
            }
        }
        while (true) {
            System.out.printf(Util.FILENAME_PROMPT);
            String inputName = scanner.next();
            String outputName = inputName.replace(".txt", "_chk.txt");
            try {
                input = new FileInputStream(inputName);
                output = new FileOutputStream(outputName);
                System.out.printf(Util.FILE_SUCCESS_NOTIFICATION, inputName, outputName);
                break;
            } catch (IOException e) {
                System.out.printf(Util.FILE_OPENING_ERROR);
            }
        }
    }

    public static void start() {
        Scanner inputScnr = new Scanner(input);
        PrintWriter outWriter = new PrintWriter(output);
        Scanner scnr = new Scanner(System.in);
        while (inputScnr.hasNextLine()) {
            Scanner lineScnr = new Scanner(inputScnr.nextLine());
            while (lineScnr.hasNext()) {
                String word = lineScnr.next();
                if (allWords.contains(word)) {
                    outWriter.print(word + " ");
                } else {
                    System.out.printf(Util.MISSPELL_NOTIFICATION, word);
                    ArrayList<String> wordSuggestions = rec.getWordSuggestions(word, 2, 0.5, 4);
                    if (wordSuggestions.isEmpty()) {
                        System.out.printf(Util.NO_SUGGESTIONS);
                        System.out.println(Util.TWO_OPTION_PROMPT);
                        String response = scnr.next();
                        while (!response.equals("a") && !response.equals("t")) {
                            System.out.printf(Util.INVALID_RESPONSE);
                            response = scnr.next();
                        }
                        if (response.equals("a")) {
                            outWriter.print(word + " ");
                        } else {
                            System.out.printf(Util.MANUAL_REPLACEMENT_PROMPT);
                            String type = scnr.next();
                            outWriter.print(type + " ");
                        }
                    } else {
                        System.out.printf(Util.FOLLOWING_SUGGESTIONS);
                        for (int i = 0; i < wordSuggestions.size(); i++) {
                            System.out.printf(Util.SUGGESTION_ENTRY, i + 1, wordSuggestions.get(i));
                        }
                        System.out.printf(Util.THREE_OPTION_PROMPT);
                        String response = scnr.next();
                        while (!response.equals("a") && !response.equals("t") && !response.equals("r")) {
                            System.out.printf(Util.INVALID_RESPONSE);
                            response = scnr.next();
                        }
                        if (response.equals("a")) {
                            outWriter.print(word + " ");
                        } else if (response.equals("t")) {
                            System.out.printf(Util.MANUAL_REPLACEMENT_PROMPT);
                            String type = scnr.next();
                            outWriter.print(type + " ");
                        } else {
                            System.out.printf(Util.AUTOMATIC_REPLACEMENT_PROMPT);
                            int index;
                            while (true) {
                                if (!scnr.hasNextInt()) {
                                    System.out.printf(Util.INVALID_RESPONSE);
                                    scnr.next();
                                    continue;
                                }
                                index = scnr.nextInt();
                                if (index <= 0 || index > wordSuggestions.size()) {
                                    System.out.printf(Util.INVALID_RESPONSE);
                                } else {
                                    break;
                                }
                            }
                            outWriter.print(wordSuggestions.get(index - 1) + " ");
                        }
                    }
                }
            }
        }
        scnr.close();
        inputScnr.close();
        outWriter.close();
    }
}