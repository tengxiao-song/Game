import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class WordRecommender {

    private final HashSet<String> allWords;

    public WordRecommender(String dictionaryFile) throws IOException {
        FileInputStream dic = new FileInputStream(dictionaryFile);
        Scanner scnr = new Scanner(dic);
        allWords = new HashSet<>();
        while (scnr.hasNextLine()) {
            allWords.add(scnr.nextLine());
        }
        dic.close();
    }

    public double getSimilarity(String word1, String word2) {
        char[] word1Array = word1.toCharArray();
        char[] word2Array = word2.toCharArray();
        int left = 0;
        for (int i = 0; i < Math.min(word1.length(), word2.length()); i++) {
            if (word1Array[i] == word2Array[i]) {
                left += 1;
            }
        }
        int right = 0;
        int indexWord1 = word1.length() - 1;
        int indexWord2 = word2.length() - 1;
        while (indexWord1 >= 0 && indexWord2 >= 0) {
            if (word1Array[indexWord1] == word2Array[indexWord2]) {
                right += 1;
            }
            indexWord2 -= 1;
            indexWord1 -= 1;
        }
        return (left + right) / 2.0;
    }

    public ArrayList<String> getWordSuggestions(String word, int tolerance, double commonPercent, int topN) {
        ArrayList<String> replaceWords = new ArrayList<>();
        for (String element : allWords) {
            if (Math.abs(element.length() - word.length()) <= tolerance) {
                HashSet<Character> union = new HashSet<>();
                HashSet<Character> intersect = new HashSet<>();
                for (Character letter : element.toCharArray()) {
                    union.add(letter);
                    if (word.contains(letter.toString())) {
                        intersect.add(letter);
                    }
                }
                for (Character letter : word.toCharArray()) {
                    union.add(letter);
                }
                double percentage = (double) intersect.size() / union.size();
                if (percentage >= commonPercent) {
                    replaceWords.add(element);
                }
            }
        }
        HashMap<String, Double> wordSimilar = new HashMap<>();
        for (String choice : replaceWords) {
            wordSimilar.put(choice, getSimilarity(word, choice));
        }
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < topN; i++) {
            String mostSimilar = getMax(wordSimilar);
            if (mostSimilar == null) {
                break;
            }
            result.add(mostSimilar);
            wordSimilar.remove(mostSimilar);
        }
        return result;
    }

    public String getMax(HashMap<String, Double> h) {
        double max = 0.0;
        String maxWord = null;
        for (Map.Entry<String, Double> entry : h.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                maxWord = entry.getKey();
            }
        }
        return maxWord;
    }
}