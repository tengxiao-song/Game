import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class WordFamilyTest {
    @Test
    public void testWordFamily() {
        WordFamily family = new WordFamily();
        ArrayList<String> allWords = new ArrayList<>(Arrays.asList("echo", "heal", "belt", "peel", "hazy"));
        char guess = 'e';
        ArrayList<Character> partialSolution = new ArrayList<>(Arrays.asList('_', '_', '_', '_'));
        ArrayList<String> largestWordFamily = family.start(allWords, guess, partialSolution);
        assertEquals(2, largestWordFamily.size());
        assertTrue(largestWordFamily.contains("heal"));
        assertTrue(largestWordFamily.contains("belt"));
        assertTrue("-e--".equals(family.getLargestPattern()));
        guess = 'l';
        largestWordFamily = family.start(allWords, guess, partialSolution);
        assertEquals(2, largestWordFamily.size());
        assertTrue("----".equals(family.getLargestPattern()));
        assertFalse(largestWordFamily.contains("heal"));
        assertFalse(largestWordFamily.contains("belt"));
        assertFalse(largestWordFamily.contains("peel"));
    }
}
