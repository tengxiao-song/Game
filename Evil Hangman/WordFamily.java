import java.util.*;

public class WordFamily {
    private String largestPattern;

    public WordFamily() {
    }

    public ArrayList<String> start(ArrayList<String> allWords, char guess, ArrayList<Character> partialSolution) {
        HashMap<String, ArrayList<String>> wordPattern = new HashMap<>();
        for (String word : allWords) {
            String pattern = getPattern(word, guess, partialSolution);
            if (!wordPattern.containsKey(pattern)) {
                wordPattern.put(pattern, new ArrayList<>());
            }
            wordPattern.get(pattern).add(word);
        }
        int maxFamilySize = -1;
        largestPattern = "";
        ArrayList<String> largestWordFamily = new ArrayList<>();
        for (Map.Entry<String, ArrayList<String>> entry : wordPattern.entrySet()) {
            int familySize = wordPattern.get(entry.getKey()).size();
            if (familySize > maxFamilySize) {
                maxFamilySize = familySize;
                largestPattern = entry.getKey();
                largestWordFamily = entry.getValue();
            }
        }
        return largestWordFamily;
    }

    public String getPattern(String word, char guessedLetter, ArrayList<Character> partialSolution) {
        char[] pattern = new char[word.length()];

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guessedLetter || partialSolution.get(i) != '_') {
                pattern[i] = word.charAt(i);
            } else {
                pattern[i] = '-';
            }
        }
        return new String(pattern);
    }

    public String getLargestPattern() {
        return largestPattern;
    }
}

