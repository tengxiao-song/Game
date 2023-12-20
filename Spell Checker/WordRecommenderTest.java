import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class WordRecommenderTest {

    private WordRecommender rec;

    @BeforeEach
    void init() throws IOException {
        // Run before each test; make a new object
        rec = new WordRecommender("engDictionary.txt");
    }

    @Test
    void testGetSimilarity() {
        // Below are 2 test cases
        // Test if we can get expected similarity values
        assertEquals(3.0, rec.getSimilarity("boom", "poom"));
        assertEquals(2.0, rec.getSimilarity("chance", "chans"));
    }

    @Test
    void testGetWordSuggestions() {
        // Below are 3 test cases
        // Test whether we can get expected word suggestions
        String[] answer1 = {"suppose", "purpose", "supposes", "supposed", "superpose"};
        ArrayList<String> suggestion1 = rec.getWordSuggestions("suppoze", 2, 0.7, 5);
        boolean result1 = Objects.equals(suggestion1, new ArrayList<>(Arrays.asList(answer1).subList(0, 5)));
        assertTrue(result1);

        String[] answer2 = {"common", "column", "commune"};
        ArrayList<String> suggestion2 = rec.getWordSuggestions("concum", 1, 0.8, 3);
        boolean result2 = Objects.equals(suggestion2, new ArrayList<>(Arrays.asList(answer2).subList(0, 3)));
        assertTrue(result2);

        ArrayList<String> suggestion3 = rec.getWordSuggestions("sublim", 0, 0.9, 4);
        assertEquals(0, suggestion3.size());
    }

    @Test
    void testGetMax() {
        // Below are 2 test cases
        // Test whether we can get expected word with the highest similarity
        HashMap<String, Double> h1 = new HashMap<>();
        assertNull(rec.getMax(h1));

        HashMap<String, Double> h2 = new HashMap<>();
        h2.put("sunnier", 4.0);
        h2.put("sunrise", 6.0);
        h2.put("sunrises", 3.0);
        assertEquals("sunrise", rec.getMax(h2));
    }
}